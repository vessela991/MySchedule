package fmi.pchmi.project.mySchedule.controller;


import fmi.pchmi.project.mySchedule.model.user.User;
import fmi.pchmi.project.mySchedule.model.user.request.UserLoginRequestModel;
import fmi.pchmi.project.mySchedule.model.user.request.UserRegisterRequestModel;
import fmi.pchmi.project.mySchedule.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.ValidationException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/users")
    Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

//    @PutMapping("/users/{id}")
//    User editUser(@RequestBody() UserRegisterRequestModel userRegisterRequestModel) {
//        if ()
//    }


    @PostMapping("/login")
    User login(@RequestBody() UserLoginRequestModel userLoginRequestModel) throws ValidationException {
        if (!userRepository.findByUsername(userLoginRequestModel.getUsername()).isPresent()) {
            throw new ValidationException("User with this username does not exist!");
        }

        User user = userRepository.findByUsername(userLoginRequestModel.getUsername()).orElse(null);

        if (!user.getPassword().equals(userLoginRequestModel.getPassword())) {
            throw new ValidationException("Bad credentials");
        }

        return user;
    }

    @PostMapping("/create-user")
    User createUser(@RequestBody() UserRegisterRequestModel userRegisterRequestModel) throws ValidationException {
        validateUserRegisterData(userRegisterRequestModel);

        return userRepository.save(User.fromUserRegisterRequestModel(userRegisterRequestModel));
    }

    private void validateUserRegisterData(@RequestBody UserRegisterRequestModel userRegisterRequestModel) throws ValidationException {
        if (userRepository.findByUsername(userRegisterRequestModel.getUsername()).isPresent()) {
            throw new ValidationException("Username already exists!");
        }

        if (userRepository.findByEmail(userRegisterRequestModel.getEmail()).isPresent()) {
            throw new ValidationException("Email already exists!");
        }

        if(!isPasswordValid(userRegisterRequestModel.getPassword())) {
            throw new ValidationException("Password must contain at least one digit," +
                    "lowercase character, uppercase character and a special character," +
                    "no whitespaces permitted.");
        }

        if(!isEmailValid(userRegisterRequestModel.getEmail())) {
            throw new ValidationException("Email is invalid!");
        }
    }

    private boolean isEmailValid(String email) {
        return isPropertyValid("^(.+)@(.+)$", email);
    }

    private boolean isPasswordValid(String password) {
        return isPropertyValid("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", password);
    }

    private boolean isPropertyValid(String regexp, String property) {
        Pattern pattern = Pattern.compile(regexp);
        return pattern.matcher(property).matches();
    }
}
