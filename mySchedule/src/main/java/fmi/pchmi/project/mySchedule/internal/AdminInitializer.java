package fmi.pchmi.project.mySchedule.internal;

import fmi.pchmi.project.mySchedule.model.user.Gender;
import fmi.pchmi.project.mySchedule.model.user.Role;
import fmi.pchmi.project.mySchedule.model.user.User;
import fmi.pchmi.project.mySchedule.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class AdminInitializer implements ApplicationRunner {
    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (!userRepository.findByUsername("admin").isPresent()) {
            User user = new User();
            user.setUsername("admin");
            user.setPassword("Admin1234!");
            user.setEmail("admin@gmail.com");
            user.setUserInfo("initial admin account");
            user.setGender(Gender.MALE);
            user.setRole(Role.ADMINISTRATOR);
            userRepository.save(user);
        }
    }
}
