package fmi.pchmi.project.mySchedule.service;

import fmi.pchmi.project.mySchedule.internal.constants.ExceptionMessages;
import fmi.pchmi.project.mySchedule.model.exception.ValidationException;
import fmi.pchmi.project.mySchedule.model.database.user.User;
import fmi.pchmi.project.mySchedule.model.request.identity.IdentityLoginRequest;
import fmi.pchmi.project.mySchedule.model.response.identity.IdentityLoginResponse;
import fmi.pchmi.project.mySchedule.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class IdentityService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public IdentityLoginResponse login(IdentityLoginRequest identityLoginRequest) throws ValidationException {
        User user = userRepository.findByUsername(identityLoginRequest.getUsername())
                .orElseThrow(() -> ValidationException.create(ExceptionMessages.BAD_CREDENTIALS));

        if (!passwordEncoder.matches(identityLoginRequest.getPassword(),user.getPassword())) {
            throw ValidationException.create(ExceptionMessages.BAD_CREDENTIALS);
        }

        return new IdentityLoginResponse(jwtService.generateToken(user));
    }
}
