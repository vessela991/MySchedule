package fmi.pchmi.project.mySchedule.repository.helper;

import fmi.pchmi.project.mySchedule.internal.CommonUtils;
import fmi.pchmi.project.mySchedule.internal.constants.ExceptionMessages;
import fmi.pchmi.project.mySchedule.model.database.group.Group;
import fmi.pchmi.project.mySchedule.model.exception.InternalException;
import fmi.pchmi.project.mySchedule.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class GroupRepositoryHelper {
    @Autowired
    private RepositoryHelper repositoryHelper;

    @Autowired
    private GroupRepository groupRepository;

    public Collection<Group> findAllAsCollection() {
        Iterable<Group> groups = repositoryHelper.findAll(groupRepository);
        return CommonUtils.asCollection(groups);
    }

    public Group findById(String groupId) {
        String failureMessage = String.format(ExceptionMessages.GROUP_ID_DOES_NOT_EXIST, groupId);
        return repositoryHelper.findById(groupRepository, failureMessage, groupId);
    }

    public Group findByName(String groupName) {
        try {
            return groupRepository.findByName(groupName);
        } catch (Exception ex) {
            throw InternalException.create();
        }
    }

    public Group save(Group group) {
        return repositoryHelper.save(groupRepository, group);
    }

    public void deleteById(String groupId) {
        String failureMessage = String.format(ExceptionMessages.GROUP_ID_DOES_NOT_EXIST, groupId);
        repositoryHelper.deleteById(groupRepository, failureMessage, groupId);
    }
}
