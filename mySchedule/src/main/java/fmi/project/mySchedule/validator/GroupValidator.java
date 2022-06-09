package fmi.project.mySchedule.validator;

import fmi.project.mySchedule.internal.constants.ExceptionMessages;
import fmi.project.mySchedule.model.request.group.GroupRequest;
import fmi.project.mySchedule.model.validation.ValidationResult;
import org.springframework.stereotype.Component;

@Component
public class GroupValidator {
    public ValidationResult validateGroupRequest(GroupRequest groupRequest) {
        if (groupRequest.getName() == null || groupRequest.getName().isBlank()) {
            return ValidationResult.failure(ExceptionMessages.GROUP_MUST_HAVE_NAME);
        }

        if (groupRequest.getMembers() != null) {
            for (String memberId : groupRequest.getMembers()) {
                if (memberId == null || memberId.isBlank()) {
                    return ValidationResult.failure(ExceptionMessages.GROUP_MEMBERID_MUST_NOT_BE_BLANK);
                }
            }
        }

        return ValidationResult.success();
    }
}
