package fmi.pchmi.project.mySchedule.service;

import fmi.pchmi.project.mySchedule.internal.constants.ExceptionMessages;
import fmi.pchmi.project.mySchedule.model.database.group.Group;
import fmi.pchmi.project.mySchedule.model.database.user.Role;
import fmi.pchmi.project.mySchedule.model.database.user.User;
import fmi.pchmi.project.mySchedule.model.exception.ForbiddenException;
import fmi.pchmi.project.mySchedule.model.exception.ValidationException;
import fmi.pchmi.project.mySchedule.model.request.group.GroupMembersRequest;
import fmi.pchmi.project.mySchedule.model.request.group.GroupRequest;
import fmi.pchmi.project.mySchedule.model.validation.ValidationResult;
import fmi.pchmi.project.mySchedule.repository.GroupRepository;
import fmi.pchmi.project.mySchedule.repository.UserRepository;
import fmi.pchmi.project.mySchedule.validator.GroupValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupValidator groupValidator;

    public Group createGroup(GroupRequest groupRequest, User loggedUser) {
        //some validation here
        ValidationResult validationResult = groupValidator.validateGroupRequest(groupRequest);

        if (!validationResult.isSuccess()) {
            throw ValidationException.create(validationResult.getValidationError());
        }

        if (groupRepository.findByName(groupRequest.getName()) != null) {
            throw ValidationException.create(ExceptionMessages.NAME_ALREADY_EXISTS);
        }

        verifyGroupMembersExist(groupRequest.getMembers());

        if (loggedUser.getRole().equals(Role.MANAGER) && !groupRequest.getMembers().contains(loggedUser.getId())) {
            groupRequest.getMembers().add(loggedUser.getId());
        }

        return groupRepository.save(Group.fromGroupRequest(groupRequest, loggedUser.getId()));
    }

    private void verifyGroupMembersExist(List<String> memberIds) {
        for (String memberId : memberIds) {
            userRepository.findById(memberId)
                    .orElseThrow(() -> ValidationException.create(String.format(ExceptionMessages.USER_ID_DOES_NOT_EXIST, memberId)));
        }
    }

    public Group addMemberToGroup(Group group, String memberId) {

        if (!userRepository.findById(memberId).isPresent()) {
            throw ValidationException.create(String.format(ExceptionMessages.USER_ID_DOES_NOT_EXIST, memberId));
        }
        //refactor ->
        group.getMembers().add(memberId);
        return groupRepository.save(group);
    }

    public Group addMembersToGroup(String groupId, GroupMembersRequest groupMembersRequest, User loggedUser) {
        Group group = getGroupById(groupId);
        verifyGroupMembersExist(groupMembersRequest.getMemberIds());

        if (!group.getCreatorId().equals(loggedUser.getId()) || !loggedUser.getRole().equals(Role.ADMINISTRATOR)) {
            throw ForbiddenException.create();
        }

        group.getMembers().addAll(groupMembersRequest.getMemberIds());
        return groupRepository.save(group);
    }

    public Group removeMembersFromGroup(String groupId, GroupMembersRequest groupMembersRequest, User loggedUser) {
        Group group = getGroupById(groupId);
        verifyGroupMembersExist(groupMembersRequest.getMemberIds());

        if (!group.getCreatorId().equals(loggedUser.getId()) || !loggedUser.getRole().equals(Role.ADMINISTRATOR)) {
            throw ForbiddenException.create();
        }

        group.getMembers().removeAll(groupMembersRequest.getMemberIds());
        return groupRepository.save(group);
    }

    public void deleteGroup(String groupId, User loggedUser) {
        Group group = getGroupById(groupId);
        if (!group.getCreatorId().equals(loggedUser.getId()) || !loggedUser.getRole().equals(Role.ADMINISTRATOR)) {
            throw ForbiddenException.create();
        }

        groupRepository.delete(group);
    }

    private Group getGroupById(String groupId) {
        return groupRepository.findById(groupId)
                .orElseThrow(() -> ValidationException.create(String.format(ExceptionMessages.GROUP_ID_DOES_NOT_EXIST, groupId)));
    }
}
