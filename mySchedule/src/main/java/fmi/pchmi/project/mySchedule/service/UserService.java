package fmi.pchmi.project.mySchedule.service;

import fmi.pchmi.project.mySchedule.internal.constants.ExceptionMessages;
import fmi.pchmi.project.mySchedule.model.exception.ForbiddenException;
import fmi.pchmi.project.mySchedule.model.exception.InternalException;
import fmi.pchmi.project.mySchedule.model.exception.ValidationException;
import fmi.pchmi.project.mySchedule.internal.CommonUtils;
import fmi.pchmi.project.mySchedule.model.validation.ValidationResult;
import fmi.pchmi.project.mySchedule.model.database.user.Gender;
import fmi.pchmi.project.mySchedule.model.database.user.Role;
import fmi.pchmi.project.mySchedule.model.database.user.User;
import fmi.pchmi.project.mySchedule.model.request.user.UserEditRequest;
import fmi.pchmi.project.mySchedule.model.request.user.UserRequest;
import fmi.pchmi.project.mySchedule.model.response.user.UserResponse;
import fmi.pchmi.project.mySchedule.repository.UserRepository;
import fmi.pchmi.project.mySchedule.validator.UserValidator;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.sql.rowset.serial.SerialBlob;
import java.io.File;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Collection;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private static final String DEFAULT_PICTURE_PATH = "src/main/resources/default-avatar.jpg";

    public Collection<UserResponse> getAllUsers() {
        Iterable<User> users = userRepository.findAll();
        return UserResponse.fromUsers(users);
    }

    public UserResponse getUserById(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                ValidationException.create(String.format(ExceptionMessages.USER_ID_DOES_NOT_EXIST, userId)));
        return UserResponse.fromUser(user);
    }

    public UserResponse createUser(UserRequest userRequest) {
        ValidationResult validationResult = userValidator.validateUserRequest(userRequest);
        if (!validationResult.isSuccess()) {
            throw ValidationException.create(validationResult.getValidationError());
        }

        if (userRepository.findByUsername(userRequest.getUsername()).isPresent()) {
            throw ValidationException.create(ExceptionMessages.USERNAME_ALREADY_EXISTS);
        }

        if (userRepository.findByEmail(userRequest.getEmail()).isPresent()) {
            throw ValidationException.create(ExceptionMessages.EMAIL_ALREADY_EXISTS);
        }

        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setEmail(userRequest.getEmail());
        user.setGender(Gender.valueOf(userRequest.getGender()));
        user.setRole(Role.valueOf(userRequest.getRole()));
        user.setUserInfo(userRequest.getUserInfo());
        user.setPicture(getDefaultPicture());
        userRepository.save(user);
        return UserResponse.fromUser(user);
    }

    public UserResponse editUser(String userId, UserEditRequest userEditRequest, User loggedUser) {
        if (!loggedUser.getId().equals(userId) || !loggedUser.getRole().equals(Role.ADMINISTRATOR)) {
            throw ForbiddenException.create();
        }

        ValidationResult validationResult = userValidator.validateUserRequest(userEditRequest);
        if (!validationResult.isSuccess()) {
            throw ValidationException.create(validationResult.getValidationError());
        }

        // highly unlikely to throw exception here, but still a valid case
        User userToEdit = userRepository.findById(userId).orElseThrow(() ->
                ValidationException.create(String.format(ExceptionMessages.USER_ID_DOES_NOT_EXIST, userId)));

        // both flows - user edits himself and admin edits are doing the below the sets
        if (userEditRequest.getPicture() != null && !userEditRequest.getPicture().getResource().getFilename().isEmpty())
        {
            userToEdit.setPicture(handleUserPicture(userEditRequest));
        }

        userToEdit.setUserInfo(userEditRequest.getUserInfo());

        if (loggedUser.getRole().equals(Role.ADMINISTRATOR)) {
            userToEdit.setUsername(userEditRequest.getUsername());
            userToEdit.setPassword(passwordEncoder.encode(userEditRequest.getPassword()));
            userToEdit.setEmail(userEditRequest.getEmail());
            userToEdit.setGender(Gender.valueOf(userEditRequest.getGender()));
            userToEdit.setRole(Role.valueOf(userEditRequest.getRole()));
        }

        userRepository.save(userToEdit);
        return UserResponse.fromUser(userToEdit);
    }

    public void deleteUser(String userId) {
        try {
            userRepository.deleteById(userId);
        }
        catch (EmptyResultDataAccessException e) {
            throw ValidationException.create(String.format(ExceptionMessages.USER_ID_DOES_NOT_EXIST, userId));
        }
    }

    private String getDefaultPicture() {
        try {
            byte[] fileContent = FileUtils.readFileToByteArray(new File(DEFAULT_PICTURE_PATH));
            return CommonUtils.encodeBase64(fileContent);
        }
        catch (IOException exception) {
            throw InternalException.create();
        }
    }

    private String handleUserPicture(UserEditRequest userEditRequest) {
        try {
            Blob pictureBlob = new SerialBlob(userEditRequest.getPicture().getBytes());
            return CommonUtils.encodeBase64(pictureBlob.getBytes(1, (int) pictureBlob.length()));
        } catch (IOException | SQLException ex) {
            throw ValidationException.create(ExceptionMessages.BROKEN_PICTURE);
        }
    }
}
