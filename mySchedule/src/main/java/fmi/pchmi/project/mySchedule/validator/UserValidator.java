package fmi.pchmi.project.mySchedule.validator;

import fmi.pchmi.project.mySchedule.internal.constants.ExceptionMessages;
import fmi.pchmi.project.mySchedule.internal.CommonUtils;
import fmi.pchmi.project.mySchedule.model.validation.ValidationResult;
import fmi.pchmi.project.mySchedule.internal.constants.DatabaseConstants;
import fmi.pchmi.project.mySchedule.model.database.user.Gender;
import fmi.pchmi.project.mySchedule.model.database.user.Role;
import fmi.pchmi.project.mySchedule.model.request.user.UserRequest;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class UserValidator {
    public static final String EMAIL_REGEX = "^(.+)@(.+[.].+)$";
    public static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,32}$";
    public static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    public static final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);

    public ValidationResult validateUserRequest(UserRequest userRequest) {
        if (userRequest.getUsername() == null || userRequest.getUsername().isBlank()) {
                return ValidationResult.failure(ExceptionMessages.USER_NO_USERNAME);
        }

        if (userRequest.getUsername().length() < DatabaseConstants.USER_USERNAME_MIN
                || userRequest.getUsername().length() > DatabaseConstants.USER_USERNAME_MAX) {
            return ValidationResult.failure(ExceptionMessages.USER_USERNAME_OUT_OF_RANGE);
        }

        if (userRequest.getPassword() == null || userRequest.getPassword().isBlank()) {
            return ValidationResult.failure(ExceptionMessages.USER_NO_PASSWORD);
        }

        if (!PASSWORD_PATTERN.matcher(userRequest.getPassword()).matches()) {
            return ValidationResult.failure(ExceptionMessages.USER_PASSWORD_REGEX_DOES_NOT_MATCH);
        }

        if (userRequest.getEmail() == null || userRequest.getEmail().isBlank()) {
            return ValidationResult.failure(ExceptionMessages.USER_NO_EMAIL);
        }

        if (!EMAIL_PATTERN.matcher(userRequest.getEmail()).matches()) {
             return ValidationResult.failure(ExceptionMessages.USER_EMAIL_IS_INVALID);
        }

        if (userRequest.getGender() == null) {
            return ValidationResult.failure(ExceptionMessages.USER_NO_GENDER);
        }

        if (!CommonUtils.DoesEnumContain(Gender.class, userRequest.getGender())) {
            return ValidationResult.failure(ExceptionMessages.USER_WRONG_GENDER);
        }

        if (userRequest.getRole() == null) {
            return ValidationResult.failure(ExceptionMessages.USER_NO_ROLE);
        }

        if (!CommonUtils.DoesEnumContain(Role.class, userRequest.getRole())) {
            return ValidationResult.failure(ExceptionMessages.USER_WRONG_ROLE);
        }

        if (userRequest.getUserInfo() != null && userRequest.getUserInfo().length() > DatabaseConstants.USER_INFO_MAX) {
            return ValidationResult.failure(ExceptionMessages.USER_INFO_TOO_LONG);
        }

        if (userRequest.getGroupId() == null || userRequest.getGroupId().isBlank()) {
            return ValidationResult.failure(ExceptionMessages.USER_MUST_HAVE_GROUP_ID);
        }

        return ValidationResult.success();
    }
}
