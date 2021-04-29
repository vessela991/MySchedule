package fmi.pchmi.project.mySchedule.controller;

import fmi.pchmi.project.mySchedule.internal.constants.CommonConstants;
import fmi.pchmi.project.mySchedule.internal.constants.ExceptionMessages;
import fmi.pchmi.project.mySchedule.internal.constants.Routes;
import fmi.pchmi.project.mySchedule.model.database.group.Group;
import fmi.pchmi.project.mySchedule.model.database.user.User;
import fmi.pchmi.project.mySchedule.model.exception.ValidationException;
import fmi.pchmi.project.mySchedule.model.request.group.GroupMembersRequest;
import fmi.pchmi.project.mySchedule.model.request.group.GroupRequest;
import fmi.pchmi.project.mySchedule.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("/")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @PostMapping(Routes.GROUPS)
    public ResponseEntity<Group> createGroup(@RequestBody GroupRequest groupRequest,
                                             @RequestAttribute(CommonConstants.LOGGED_USER) User loggedUser) {
        return new ResponseEntity<>(groupService.createGroup(groupRequest, loggedUser), HttpStatus.CREATED);
    }

    @DeleteMapping(Routes.GROUPS_ID)
    public ResponseEntity deleteGroup(@PathVariable("id") String groupId,
                                             @RequestAttribute(CommonConstants.LOGGED_USER) User loggedUser) {
        groupService.deleteGroup(groupId, loggedUser);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

//    @PutMapping(Routes.GROUPS_ID)
//    public ResponseEntity<Group> updateGroup(@PathVariable("id") String groupId,
//                                             @RequestBody GroupMembersRequest groupMembersRequest,
//                                             @RequestAttribute(CommonConstants.LOGGED_USER) User loggedUser) {
//        return new ResponseEntity<>(groupService.createGroup(groupRequest, loggedUser), HttpStatus.CREATED);
//    }

    @PostMapping(Routes.GROUPS_ID)
    public ResponseEntity<Group> addMembersToGroup(@PathVariable("id") String groupId,
                                                   @RequestBody GroupMembersRequest groupMembersRequest,
                                                   @RequestAttribute(CommonConstants.LOGGED_USER) User loggedUser) {
        return new ResponseEntity<>(groupService.addMembersToGroup(groupId, groupMembersRequest, loggedUser),
                HttpStatus.OK);
    }

    @DeleteMapping(Routes.GROUPS_ID)
    public ResponseEntity<Group> removeMembersFromGroup(@PathVariable("id") String groupId,
                                                        @RequestBody GroupMembersRequest groupMembersRequest,
                                                        @RequestAttribute(CommonConstants.LOGGED_USER) User loggedUser) {
        return new ResponseEntity<>(groupService.removeMembersFromGroup(groupId, groupMembersRequest, loggedUser),
                HttpStatus.OK);
    }
}
