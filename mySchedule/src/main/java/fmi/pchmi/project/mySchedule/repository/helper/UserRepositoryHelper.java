package fmi.pchmi.project.mySchedule.repository.helper;

import fmi.pchmi.project.mySchedule.internal.constants.ExceptionMessages;
import fmi.pchmi.project.mySchedule.model.database.user.User;
import fmi.pchmi.project.mySchedule.model.exception.InternalException;
import fmi.pchmi.project.mySchedule.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserRepositoryHelper {
    @Autowired
    private RepositoryHelper repositoryHelper;

    @Autowired
    private UserRepository userRepository;

    public Iterable<User> findAll() {
        return repositoryHelper.findAll(userRepository);
    }

    public User findById(String userId) {
        String failureMessage = String.format(ExceptionMessages.USER_ID_DOES_NOT_EXIST, userId);
        return repositoryHelper.findById(userRepository, failureMessage, userId);
    }

    public User findByEmail(String email) {
        try {
            return userRepository.findByEmail(email);
        } catch (Exception ex) {
            throw InternalException.create();
        }
    }

    public User findByUsername(String username) {
        try {
            return userRepository.findByUsername(username);
        } catch (Exception ex) {
            throw InternalException.create();
        }
    }

    public boolean verifyUsersExist(List<String> userIds) {
        for (String userId : userIds) {
            findById(userId);
        }
        return true;
    }

    public User save(User user) {
        return repositoryHelper.save(userRepository, user);
    }

    public void deleteById(String userId) {
        String failureMessage = String.format(ExceptionMessages.USER_ID_DOES_NOT_EXIST, userId);
        repositoryHelper.deleteById(userRepository, failureMessage, userId);
    }
}