package fmi.pchmi.project.mySchedule.internal;

import fmi.pchmi.project.mySchedule.model.database.group.Group;
import fmi.pchmi.project.mySchedule.model.database.user.Gender;
import fmi.pchmi.project.mySchedule.model.database.user.Role;
import fmi.pchmi.project.mySchedule.model.database.user.User;
import fmi.pchmi.project.mySchedule.repository.UserRepository;
import fmi.pchmi.project.mySchedule.repository.helper.GroupRepositoryHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

import static fmi.pchmi.project.mySchedule.service.UserService.getDefaultPicture;

import static fmi.pchmi.project.mySchedule.service.UserService.getDefaultPicture;

@Component
public class ApplicationInitializer implements ApplicationRunner {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepositoryHelper groupRepositoryHelper;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) {
        if (groupRepositoryHelper.findByName("admin-group") == null) {
            Group group = new Group();
            group.setName("admin-group");
            Group g = groupRepositoryHelper.save(group);

            if (userRepository.findByUsername("admin") == null) {
                User user = new User();
                user.setUsername("admin");
                user.setPassword(passwordEncoder.encode("Admin1234!"));
                user.setEmail("admin@gmail.com");
                user.setUserInfo("initial admin account");
                user.setGender(Gender.MALE);
                user.setRole(Role.ADMINISTRATOR);
                user.setPicture(getDefaultPicture());
                user.setGroupId(g.getId());
                User u = userRepository.save(user);
                Set<String> userId = new HashSet<>();
                userId.add(u.getId());
                g.setMembers(userId);
                groupRepositoryHelper.save(g);

            }
        }
    }
}
