package fmi.pchmi.project.mySchedule.controller;

import fmi.pchmi.project.mySchedule.internal.constants.Routes;
import fmi.pchmi.project.mySchedule.model.database.event.Event;
import fmi.pchmi.project.mySchedule.model.database.group.Group;
import fmi.pchmi.project.mySchedule.model.request.group.GroupRequest;
import fmi.pchmi.project.mySchedule.model.request.group.GroupUpdateRequest;
import fmi.pchmi.project.mySchedule.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class GroupController {

    @Autowired
    private GroupService groupService;

    @GetMapping(Routes.GROUPS)
    public ResponseEntity<Collection<Group>> getAllGroups() {
        return new ResponseEntity<>(groupService.getAllGroups(), HttpStatus.OK);
    }

    @GetMapping(Routes.GROUPS_ID)
    public ResponseEntity<Group> getGroupById(@PathVariable("id") String groupId) {
        return new ResponseEntity<>(groupService.getGroupById(groupId), HttpStatus.OK);
    }

    @GetMapping(Routes.GROUP_ID_EVENTS)
    public ResponseEntity<Collection<Event>> getAllEventsForGroup(@PathVariable("id") String groupId) {
        return new ResponseEntity<>(groupService.getAllEventsForGroup(groupId), HttpStatus.OK);
    }

    @PostMapping(Routes.GROUPS)
    public ResponseEntity<Group> createGroup(@RequestBody GroupRequest groupRequest) {
        return new ResponseEntity<>(groupService.createGroup(groupRequest), HttpStatus.CREATED);
    }

    @PutMapping(Routes.GROUPS_ID)
    public ResponseEntity<Group> updateGroup(@PathVariable("id") String groupId, @RequestBody GroupUpdateRequest groupUpdateRequest) {
        return new ResponseEntity<>(groupService.updateGroup(groupId, groupUpdateRequest), HttpStatus.OK);
    }

    @DeleteMapping(Routes.GROUPS_ID)
    public ResponseEntity deleteGroup(@PathVariable("id") String groupId) {
        groupService.deleteGroup(groupId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
