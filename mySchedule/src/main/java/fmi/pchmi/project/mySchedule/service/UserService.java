package fmi.pchmi.project.mySchedule.service;

import fmi.pchmi.project.mySchedule.model.user.User;
import fmi.pchmi.project.mySchedule.model.user.UserPrincipal;
import fmi.pchmi.project.mySchedule.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserPrincipal loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElse(null);

        return new UserPrincipal(user);
    }
}
