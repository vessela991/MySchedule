package fmi.pchmi.project.mySchedule.controller;

import fmi.pchmi.project.mySchedule.internal.constants.CommonConstants;
import fmi.pchmi.project.mySchedule.internal.constants.Routes;
import fmi.pchmi.project.mySchedule.model.database.user.User;
import fmi.pchmi.project.mySchedule.model.request.user.UserEditRequest;
import fmi.pchmi.project.mySchedule.model.request.user.UserRequest;
import fmi.pchmi.project.mySchedule.model.response.user.UserResponse;
import fmi.pchmi.project.mySchedule.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(Routes.USERS)
    public ResponseEntity<Collection<UserResponse>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping(Routes.USERS_ID)
    public ResponseEntity<UserResponse> getUserById(@PathVariable("id") String userId) {
        return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
    }

    @PostMapping(Routes.USERS)
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userRequest) {
        return new ResponseEntity<>(userService.createUser(userRequest), HttpStatus.CREATED);
    }

    @PutMapping(Routes.USERS_ID)
    public ResponseEntity<UserResponse> editUser(@PathVariable("id") String userId,
                                                 @RequestBody UserEditRequest userEditRequest,
                                                 @RequestAttribute(CommonConstants.LOGGED_USER) User loggedUser) {
        return new ResponseEntity<>(userService.editUser(userId, userEditRequest, loggedUser), HttpStatus.OK);
    }

    @DeleteMapping(Routes.USERS_ID)
    public ResponseEntity deleteUser(@PathVariable("id") String userId) {
        userService.deleteUser(userId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
