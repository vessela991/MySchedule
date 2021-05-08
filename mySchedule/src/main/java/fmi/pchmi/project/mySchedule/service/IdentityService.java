package fmi.pchmi.project.mySchedule.service;

import fmi.pchmi.project.mySchedule.model.database.user.User;
import fmi.pchmi.project.mySchedule.model.exception.ValidationException;
import fmi.pchmi.project.mySchedule.model.request.identity.IdentityLoginRequest;
import fmi.pchmi.project.mySchedule.model.response.identity.IdentityLoginResponse;
import fmi.pchmi.project.mySchedule.model.validation.ValidationResult;
import fmi.pchmi.project.mySchedule.repository.helper.UserRepositoryHelper;
import fmi.pchmi.project.mySchedule.validator.IdentityValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IdentityService {

    @Autowired
    private UserRepositoryHelper userRepositoryHelper;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private IdentityValidator identityValidator;

    public IdentityLoginResponse login(IdentityLoginRequest identityLoginRequest) throws ValidationException {
        ValidationResult validationResult = identityValidator.validateRequest(identityLoginRequest);

        if (!validationResult.isSuccess()) {
            throw ValidationException.create(validationResult.getValidationError());
        }

        User user = userRepositoryHelper.findByUsername(identityLoginRequest.getUsername());

        validationResult = identityValidator.validateUser(identityLoginRequest, user);

        if (!validationResult.isSuccess()) {
            throw ValidationException.create(validationResult.getValidationError());
        }

        return new IdentityLoginResponse(jwtService.generateToken(user));
    }
}
