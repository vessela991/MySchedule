package fmi.pchmi.project.mySchedule.validator;

import fmi.pchmi.project.mySchedule.internal.constants.ExceptionMessages;
import fmi.pchmi.project.mySchedule.model.request.group.GroupRequest;
import fmi.pchmi.project.mySchedule.model.request.user.UserRequest;
import fmi.pchmi.project.mySchedule.model.validation.ValidationResult;
import org.springframework.stereotype.Component;

@Component
public class GroupValidator {
    public ValidationResult validateGroupRequest(GroupRequest groupRequest) {
        //group can be created without any members
        if (groupRequest.getName() == null) {
            return ValidationResult.failure(ExceptionMessages.NO_NAME);
        }
        return ValidationResult.success();
    }
}
