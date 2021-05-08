package fmi.pchmi.project.mySchedule.validator;

import fmi.pchmi.project.mySchedule.internal.CommonUtils;
import fmi.pchmi.project.mySchedule.internal.constants.DatabaseConstants;
import fmi.pchmi.project.mySchedule.internal.constants.ExceptionMessages;
import fmi.pchmi.project.mySchedule.model.database.event.EventStatus;
import fmi.pchmi.project.mySchedule.model.database.event.Priority;
import fmi.pchmi.project.mySchedule.model.exception.ValidationException;
import fmi.pchmi.project.mySchedule.model.request.event.EventStatusRequest;
import fmi.pchmi.project.mySchedule.model.request.event.EventUpdateRequest;
import fmi.pchmi.project.mySchedule.model.validation.ValidationResult;
import org.springframework.stereotype.Component;

@Component
public class EventValidator {
    public ValidationResult validateEventRequest(EventUpdateRequest eventUpdateRequest) {
        if (eventUpdateRequest.getName() == null || eventUpdateRequest.getName().isBlank()) {
            return ValidationResult.failure(ExceptionMessages.EVENT_MUST_HAVE_NAME);
        }

        if (eventUpdateRequest.getName().length() < DatabaseConstants.EVENT_NAME_MIN
                || eventUpdateRequest.getName().length() > DatabaseConstants.EVENT_NAME_MAX) {
            return ValidationResult.failure(ExceptionMessages.EVENT_NAME_MUST_OUT_OF_RANGE);
        }

        if (eventUpdateRequest.getDescription() != null
                && eventUpdateRequest.getDescription().length() > DatabaseConstants.EVENT_DESCRIPTION_MAX) {
            return ValidationResult.failure(ExceptionMessages.EVENT_DESCRIPTION_TOO_LONG);
        }

        if (eventUpdateRequest.getStartTime() == null) {
            return ValidationResult.failure(ExceptionMessages.EVENT_START_TIME_MUST_BE_SPECIFIED);
        }

        if (eventUpdateRequest.getEndTime() == null) {
            return ValidationResult.failure(ExceptionMessages.EVENT_END_TIME_MUST_BE_SPECIFIED);
        }

        if (eventUpdateRequest.getEndTime().before(eventUpdateRequest.getStartTime())) {
            return ValidationResult.failure(ExceptionMessages.EVENT_END_CANNOT_BE_BEFORE_EVENT_START);
        }

        if (eventUpdateRequest.getPriority() != null && !CommonUtils.DoesEnumContain(Priority.class, eventUpdateRequest.getPriority())) {
            return ValidationResult.failure(ExceptionMessages.EVENT_PRIORITY_IS_INVALID);
        }

        return ValidationResult.success();
    }

    public ValidationResult validateEventStatusRequest(EventStatusRequest eventStatusRequest) {
        if (eventStatusRequest.getStatus() == null) {
            return ValidationResult.failure(ExceptionMessages.EVENT_MUST_HAVE_STATUS);
        }

        if (!CommonUtils.DoesEnumContain(EventStatus.class, eventStatusRequest.getStatus())) {
            return ValidationResult.failure(ExceptionMessages.EVENT_STATUS_IS_INVALID);
        }

        return ValidationResult.success();
    }
}
