package fmi.pchmi.project.mySchedule.controller;

import fmi.pchmi.project.mySchedule.internal.constants.CommonConstants;
import fmi.pchmi.project.mySchedule.internal.constants.Routes;
import fmi.pchmi.project.mySchedule.model.database.event.Event;
import fmi.pchmi.project.mySchedule.model.database.user.User;
import fmi.pchmi.project.mySchedule.model.request.event.EventCreateRequest;
import fmi.pchmi.project.mySchedule.model.request.event.EventStatusRequest;
import fmi.pchmi.project.mySchedule.model.request.event.EventUpdateRequest;
import fmi.pchmi.project.mySchedule.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class EventController {
    @Autowired
    private EventService eventService;

    @GetMapping(Routes.EVENTS)
    public ResponseEntity<Collection<Event>> getAllEvents() {
        return new ResponseEntity<>(eventService.getAllEvents(), HttpStatus.OK);
    }

    @GetMapping(Routes.EVENTS_ID)
    public ResponseEntity<Event> getEventById(@PathVariable("id") String eventId) {
        return new ResponseEntity<>(eventService.getEventById(eventId), HttpStatus.OK);
    }

    @PostMapping(Routes.EVENTS)
    public ResponseEntity<Event> createEvent(@RequestBody EventCreateRequest eventCreateRequest,
                                             @RequestAttribute(CommonConstants.LOGGED_USER) User loggedUser) {
        return new ResponseEntity<>(eventService.createEvent(eventCreateRequest, loggedUser), HttpStatus.CREATED);
    }

    @PostMapping(Routes.EVENTS_ID)
    public ResponseEntity<Event> updateEventParticipantStatus(@PathVariable("id") String eventId,
                                                              @RequestBody EventStatusRequest eventStatusRequest,
                                                              @RequestAttribute(CommonConstants.LOGGED_USER) User loggedUser) {
        return new ResponseEntity<>(eventService.updateEventParticipantStatus(eventId, eventStatusRequest, loggedUser), HttpStatus.OK);
    }

    @PutMapping(Routes.EVENTS_ID)
    public ResponseEntity<Event> updateEvent(@PathVariable("id") String eventId,
                                             @RequestBody EventUpdateRequest eventUpdateRequest,
                                             @RequestAttribute(CommonConstants.LOGGED_USER) User loggedUser) {
        return new ResponseEntity<>(eventService.updateEvent(eventId, eventUpdateRequest, loggedUser), HttpStatus.OK);
    }

    @DeleteMapping(Routes.EVENTS_ID)
    public ResponseEntity deleteEvent(@PathVariable("id") String eventId,
                                      @RequestAttribute(CommonConstants.LOGGED_USER) User loggedUser ) {
        eventService.deleteEvent(eventId, loggedUser);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
