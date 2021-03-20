package fmi.pchmi.project.mySchedule.controller;


import fmi.pchmi.project.mySchedule.model.Gender;
import fmi.pchmi.project.mySchedule.model.User;
import fmi.pchmi.project.mySchedule.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/users")
    String getAllUsers() {
        return "fsfs";
    }
}
