package fmi.project.mySchedule.repository.helper;

import fmi.project.mySchedule.internal.CommonUtils;
import fmi.project.mySchedule.internal.constants.ExceptionMessages;
import fmi.project.mySchedule.model.database.event.Event;
import fmi.project.mySchedule.model.database.user.User;
import fmi.project.mySchedule.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class EventRepositoryHelper {
    @Autowired
    private RepositoryHelper repositoryHelper;

    @Autowired
    private UserRepositoryHelper userRepositoryHelper;

    @Autowired
    private GroupRepositoryHelper groupRepositoryHelper;

    @Autowired
    private EventRepository eventRepository;

    public Collection<Event> findAllAsCollection() {
        Iterable<Event> events = repositoryHelper.findAll(eventRepository);
        return CommonUtils.asCollection(events);
    }

    public Event findById(String eventId) {
        String failureMessage = String.format(ExceptionMessages.EVENT_ID_DOES_NOT_EXIST, eventId);
        return repositoryHelper.findById(eventRepository, failureMessage, eventId);
    }

    public Collection<Event> findAllByUserId(String userId) {
        User user = userRepositoryHelper.findById(userId);
        return eventRepository.getAllEventsInList(user.getEventIds());
    }

    public Event save(Event event) {
        return repositoryHelper.save(eventRepository, event);
    }

    public void deleteById(String eventId) {
        String failureMessage = String.format(ExceptionMessages.EVENT_ID_DOES_NOT_EXIST, eventId);
        repositoryHelper.deleteById(eventRepository, failureMessage, eventId);
    }
}
