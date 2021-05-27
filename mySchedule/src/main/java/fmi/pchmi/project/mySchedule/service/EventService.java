package fmi.pchmi.project.mySchedule.service;

import fmi.pchmi.project.mySchedule.internal.constants.ExceptionMessages;
import fmi.pchmi.project.mySchedule.model.database.event.Event;
import fmi.pchmi.project.mySchedule.model.database.event.EventParticipant;
import fmi.pchmi.project.mySchedule.model.database.event.EventStatus;
import fmi.pchmi.project.mySchedule.model.database.event.Priority;
import fmi.pchmi.project.mySchedule.model.database.group.Group;
import fmi.pchmi.project.mySchedule.model.database.user.Role;
import fmi.pchmi.project.mySchedule.model.database.user.User;
import fmi.pchmi.project.mySchedule.model.exception.ValidationException;
import fmi.pchmi.project.mySchedule.model.request.event.EventCreateRequest;
import fmi.pchmi.project.mySchedule.model.request.event.EventStatusRequest;
import fmi.pchmi.project.mySchedule.model.request.event.EventUpdateRequest;
import fmi.pchmi.project.mySchedule.model.validation.ValidationResult;
import fmi.pchmi.project.mySchedule.repository.helper.EventRepositoryHelper;
import fmi.pchmi.project.mySchedule.repository.helper.GroupRepositoryHelper;
import fmi.pchmi.project.mySchedule.repository.helper.UserRepositoryHelper;
import fmi.pchmi.project.mySchedule.validator.EventValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@Service
public class EventService {
    @Autowired
    private EventRepositoryHelper eventRepositoryHelper;

    @Autowired
    private UserRepositoryHelper userRepositoryHelper;

    @Autowired
    private GroupRepositoryHelper groupRepositoryHelper;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private EventValidator eventValidator;

    public Collection<Event> getAllEvents() {
        return eventRepositoryHelper.findAllAsCollection();
    }

    public Event getEventById(String eventId) {
        return eventRepositoryHelper.findById(eventId);
    }

    public Event createEvent(EventCreateRequest eventCreateRequest, User loggedUser) {
        validateEventRequest(eventCreateRequest);

        Event event = new Event();
        event.setName(eventCreateRequest.getName());
        event.setDescription(eventCreateRequest.getDescription());
        event.setCreationTime(Date.from(Instant.now()));
        event.setStartTime(eventCreateRequest.getStartTime());
        event.setEndTime(eventCreateRequest.getEndTime());
        event.setParticipants(getEventParticipants(eventCreateRequest, loggedUser));
        event.setCreatorId(loggedUser.getId());
        if (eventCreateRequest.getPriority() != null) {
            event.setPriority(Priority.valueOf(eventCreateRequest.getPriority()));
        }
        event.setPersonal(eventCreateRequest.isPersonal());
        Event savedEvent = eventRepositoryHelper.save(event);
        loggedUser.getEventIds().add(savedEvent.getId());
        userRepositoryHelper.save(loggedUser);
        return savedEvent;
    }

    public Event updateEventParticipantStatus(String eventId, EventStatusRequest eventStatusRequest, User loggedUser) {
        ValidationResult validationResult = eventValidator.validateEventStatusRequest(eventStatusRequest);

        if (!validationResult.isSuccess()) {
            throw ValidationException.create(validationResult.getValidationError());
        }

        Event event = eventRepositoryHelper.findById(eventId);

        boolean isLoggedUserParticipant = false;
        EventStatus eventStatus = EventStatus.valueOf(eventStatusRequest.getStatus());
        for(EventParticipant eventParticipant : event.getParticipants()) {
            if (eventParticipant.getUserId().equals(loggedUser.getId())) {
                isLoggedUserParticipant = true;
                event.getParticipants().remove(eventParticipant);
                event.getParticipants().add(new EventParticipant(eventParticipant.getUserId(), eventStatus));
                break;
            }
        }

        if (!isLoggedUserParticipant) {
            throw ValidationException.create(ExceptionMessages.EVENT_USER_MUST_BE_PARTICIPANT);
        }

        if (EventStatus.DECLINED.equals(eventStatus)) {
            loggedUser.getEventIds().remove(eventId);
            userRepositoryHelper.save(loggedUser);
        }

        return eventRepositoryHelper.save(event);
    }

    public Event updateEvent(String eventId, EventUpdateRequest eventUpdateRequest, User loggedUser) {
        validateEventRequest(eventUpdateRequest);

        Event event = eventRepositoryHelper.findById(eventId);

        if (event.isPersonal()) {
            if (!isPersonalEligibleForUpdate(event, loggedUser)) {
                throw ValidationException.create(ExceptionMessages.EVENT_UPDATE_MUST_BE_CREATOR_OR_ADMIN);
            }
        } else {
            if (isGroupEligibleForUpdate(event, loggedUser)) {
                event.setParticipants(getEventParticipants(eventUpdateRequest, loggedUser));
            } else {
                throw ValidationException.create(ExceptionMessages.EVENT_UPDATE_MUST_BE_CREATOR_OR_MANAGER_OR_ADMIN);
            }
        }
        event.setName(eventUpdateRequest.getName());
        event.setDescription(eventUpdateRequest.getDescription());
        event.setStartTime(eventUpdateRequest.getStartTime());
        event.setEndTime(eventUpdateRequest.getEndTime());

        if (eventUpdateRequest.getPriority() != null) {
            event.setPriority(Priority.valueOf(eventUpdateRequest.getPriority()));
        }
        Event savedEvent = eventRepositoryHelper.save(event);
        for (EventParticipant eventParticipant : event.getParticipants()) {
            User user = userRepositoryHelper.findById(eventParticipant.getUserId());
            user.getEventIds().add(event.getId());
            userRepositoryHelper.save(user);
        }

        return savedEvent;
    }

    private Set<EventParticipant> getEventParticipants(EventUpdateRequest eventUpdateRequest, User loggedUser) {
        Set<EventParticipant> eventParticipantCollection = new HashSet<>();
        if (eventUpdateRequest.getParticipants() != null) {
            for (String participantId : eventUpdateRequest.getParticipants()) {
                if (participantId.equals(loggedUser.getId())) {
                    eventParticipantCollection.add(new EventParticipant(participantId, EventStatus.ACCEPTED));
                    continue;
                }
                eventParticipantCollection.add(new EventParticipant(participantId, EventStatus.PENDING));
                sendParticipantEmail(participantId, eventUpdateRequest.getName());
            }
        }
        return eventParticipantCollection;
    }

    public void deleteEvent(String eventId, User loggedUser) {
        Event event = getEventById(eventId);
        Set<EventParticipant> eventParticipants = event.getParticipants();
        boolean isLoggedUserManagerAndParticipant = false;
        for (EventParticipant eventParticipant : eventParticipants) {
            if (Role.MANAGER.equals(loggedUser.getRole()) && eventParticipant.getUserId().equals(loggedUser.getId())) {
                isLoggedUserManagerAndParticipant = true;
                break;
            }
        }

        if (!event.getCreatorId().equals(loggedUser.getId())
                && !Role.ADMINISTRATOR.equals(loggedUser.getRole())
                && !isLoggedUserManagerAndParticipant) {
            throw ValidationException.create(ExceptionMessages.EVENT_DELETE_MUST_BE_CREATOR_OR_ADMIN);
        }
        eventRepositoryHelper.deleteById(eventId);

        for (EventParticipant eventParticipant : eventParticipants) {
            User user = userRepositoryHelper.findById(eventParticipant.getUserId());
            user.getEventIds().remove(eventId);
        }
    }

    private void validateEventRequest(EventUpdateRequest eventUpdateRequest) {
        ValidationResult validationResult = eventValidator.validateEventRequest(eventUpdateRequest);

        if (!validationResult.isSuccess()) {
            throw ValidationException.create(validationResult.getValidationError());
        }

        if (eventUpdateRequest.getParticipants() != null) {
            userRepositoryHelper.verifyUsersExist(eventUpdateRequest.getParticipants());
        }
    }

    private boolean isPersonalEligibleForUpdate(Event event, User loggedUser) {
        return loggedUser.getId().equals(event.getCreatorId()) || Role.ADMINISTRATOR.equals(loggedUser.getRole());
    }

    private boolean isGroupEligibleForUpdate(Event event, User loggedUser) {
        boolean isParticipant = false;
        for (EventParticipant eventParticipant : event.getParticipants()) {
            if (eventParticipant.getUserId().equals(loggedUser.getId())) {
                isParticipant = true;
                break;
            }
        }
        return loggedUser.getId().equals(event.getCreatorId())
                || Role.ADMINISTRATOR.equals(loggedUser.getRole())
                || (Role.MANAGER.equals(loggedUser.getRole()) && isParticipant);
    }

//    private void verifyAllParticipantsAvailable(List<String> participants, Date startTime, Date endTime) {
//        for (String participant : participants) {
//            User user = userRepositoryHelper.findById(participant);
//            for (String eventId : user.getEventIds()) {
//                Event event = eventRepositoryHelper.findById(eventId);
//                if (event.getEndTime().after(startTime) || event.getStartTime().before(endTime)) {
//                    throw ValidationException.create("");
//                }
//            }
//        }
//    }

    private void sendParticipantEmail(String participantId, String eventName) {
        User participant = this.userRepositoryHelper.findById(participantId);

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(participant.getEmail());

        msg.setSubject("Invitation for event: " + eventName);
        msg.setText("Hello you have been invited to participate in the event: " + eventName);

        javaMailSender.send(msg);
    }
}
