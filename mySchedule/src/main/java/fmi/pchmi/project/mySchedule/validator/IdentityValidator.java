package fmi.pchmi.project.mySchedule.validator;

import fmi.pchmi.project.mySchedule.internal.constants.ExceptionMessages;
import fmi.pchmi.project.mySchedule.model.database.user.User;
import fmi.pchmi.project.mySchedule.model.request.identity.IdentityLoginRequest;
import fmi.pchmi.project.mySchedule.model.validation.ValidationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class IdentityValidator {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public ValidationResult validateRequest(IdentityLoginRequest identityLoginRequest) {
        if (identityLoginRequest.getUsername() == null || identityLoginRequest.getUsername().isBlank()
                || identityLoginRequest.getPassword() == null || identityLoginRequest.getPassword().isBlank()) {
            return ValidationResult.failure(ExceptionMessages.IDENTITY_BAD_CREDENTIALS);
        }

        return ValidationResult.success();
    }

    public ValidationResult validateUser(IdentityLoginRequest identityLoginRequest, User user) {
        if (user == null || !passwordEncoder.matches(identityLoginRequest.getPassword(),user.getPassword())) {
            return ValidationResult.failure(ExceptionMessages.IDENTITY_BAD_CREDENTIALS);
        }

        return ValidationResult.success();
    }
}
