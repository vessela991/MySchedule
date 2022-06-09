package fmi.project.mySchedule.service;

import fmi.project.mySchedule.internal.CommonUtils;
import fmi.project.mySchedule.internal.constants.ExceptionMessages;
import fmi.project.mySchedule.model.database.event.Event;
import fmi.project.mySchedule.model.database.group.Group;
import fmi.project.mySchedule.model.database.user.User;
import fmi.project.mySchedule.model.exception.ValidationException;
import fmi.project.mySchedule.model.request.group.GroupRequest;
import fmi.project.mySchedule.model.request.group.GroupUpdateRequest;
import fmi.project.mySchedule.model.validation.ValidationResult;
import fmi.project.mySchedule.repository.helper.EventRepositoryHelper;
import fmi.project.mySchedule.repository.helper.GroupRepositoryHelper;
import fmi.project.mySchedule.repository.helper.UserRepositoryHelper;
import fmi.project.mySchedule.validator.GroupValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
public class GroupService {

    @Autowired
    private GroupRepositoryHelper groupRepositoryHelper;

    @Autowired
    private UserRepositoryHelper userRepositoryHelper;

    @Autowired
    private EventRepositoryHelper eventRepositoryHelper;

    @Autowired
    private GroupValidator groupValidator;

    public Collection<Group> getAllGroups() {
        return groupRepositoryHelper.findAllAsCollection();
    }

    public Group getGroupById(String groupId) {
        return groupRepositoryHelper.findById(groupId);
    }

    public Collection<Event> getAllEventsForGroup(String groupId) {
        Group group = groupRepositoryHelper.findById(groupId);

        Set<Event> groupEvents = new HashSet<>();

        for (String memberId : group.getMembers()) {
            User user = userRepositoryHelper.findById(memberId);
            for (String eventId : user.getEventIds()) {
                Event event = eventRepositoryHelper.findById(eventId);
                if (!event.isPersonal()) {
                    groupEvents.add(event);
                }
            }
        }

        return groupEvents;
    }

    public Group createGroup(GroupRequest groupRequest) {
        validateGroupRequest(groupRequest);

        if (groupRepositoryHelper.findByName(groupRequest.getName()) != null) {
            throw ValidationException.create(String.format(ExceptionMessages.GROUP_NAME_ALREADY_EXISTS, groupRequest.getName()));
        }

        userRepositoryHelper.verifyUsersExist(groupRequest.getMembers());

        Group group = new Group();
        group.setName(groupRequest.getName());

        group.setMembers(CommonUtils.asSet(groupRequest.getMembers()));

        return groupRepositoryHelper.save(group);
    }

    public Group updateGroup(String groupId, GroupUpdateRequest groupUpdateRequest) {
        validateGroupRequest(groupUpdateRequest);

        Group group = groupRepositoryHelper.findById(groupId);

        if (!group.getName().equals(groupUpdateRequest.getName())) {
            if(groupRepositoryHelper.findByName(groupUpdateRequest.getName()) != null) {
                throw ValidationException.create(String.format(ExceptionMessages.GROUP_NAME_ALREADY_EXISTS, groupUpdateRequest.getName()));
            }

            group.setName(groupUpdateRequest.getName());
        }

        userRepositoryHelper.verifyUsersExist(groupUpdateRequest.getMembers());

        if (groupUpdateRequest.getManagerId() != null) {
            userRepositoryHelper.findById(groupUpdateRequest.getManagerId()); //verify managerId exists
            group.setManagerId(groupUpdateRequest.getManagerId());
        }

        group.setMembers(CommonUtils.asSet(groupUpdateRequest.getMembers()));
        return groupRepositoryHelper.save(group);
    }

    public void deleteGroup(String groupId) {
        Group group = groupRepositoryHelper.findById(groupId);
        groupRepositoryHelper.deleteById(groupId); //first delete group, then delete all members, because group deletion may fail

        for (String memberId: group.getMembers()) {
            User user = userRepositoryHelper.findById(memberId);
            user.setGroupId(""); //remove groupId from members
            userRepositoryHelper.save(user);
        }
    }

    private void validateGroupRequest(GroupRequest groupRequest) {
        ValidationResult validationResult = groupValidator.validateGroupRequest(groupRequest);

        if (!validationResult.isSuccess()) {
            throw ValidationException.create(validationResult.getValidationError());
        }
    }
}
