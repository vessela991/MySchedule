//package fmi.pchmi.project.mySchedule.validator;
//
//import fmi.pchmi.project.mySchedule.internal.constants.ExceptionMessages;
//import fmi.pchmi.project.mySchedule.model.request.group.GroupRequest;
//import fmi.pchmi.project.mySchedule.model.request.schedule.ScheduleRequest;
//import fmi.pchmi.project.mySchedule.model.request.schedule.ScheduleUpdateRequest;
//import fmi.pchmi.project.mySchedule.model.validation.ValidationResult;
//import org.springframework.stereotype.Component;
//
//@Component
//public class ScheduleValidator {
//    public ValidationResult validateScheduleRequest(ScheduleRequest scheduleRequest) {
//        //schedule can be created without any events
//        if (scheduleRequest.getName() == null) {
//            return ValidationResult.failure(ExceptionMessages.NO_NAME);
//        }
//
//        if (scheduleRequest.getIsPrivate() == null) {
//            return ValidationResult.failure(ExceptionMessages.SCHEDULE_PRIVACY_NOT_SET);
//        }
//
//        if (scheduleRequest.getGroupId() != null && scheduleRequest.getUserId() != null) {
//            return ValidationResult.failure(ExceptionMessages.AMIGUOUS_SCHEDULE_REQUEST_CONTAINS_USERID_GROUPID);
//        }
//
//        if (scheduleRequest.getIsPrivate() && scheduleRequest.getUserId() == null) {
//            return ValidationResult.failure(ExceptionMessages.PRIVATE_SCHEDULE_WITHOUT_USERID);
//        }
//
//        if (!scheduleRequest.getIsPrivate() && scheduleRequest.getGroupId() == null) {
//            return ValidationResult.failure(ExceptionMessages.PUBLIC_SCHEDULE_WITHOUT_GROUPID);
//        }
//
//        if (scheduleRequest.getIsPrivate() && scheduleRequest.getGroupId() != null) {
//            return ValidationResult.failure(ExceptionMessages.PRIVATE_SCHEDULE_WITH_GROUPID);
//        }
//
//        if (!scheduleRequest.getIsPrivate() && scheduleRequest.getUserId() != null) {
//            return ValidationResult.failure(ExceptionMessages.PUBLIC_SCHEDULE_WITH_USERID);
//        }
//
//        return ValidationResult.success();
//    }
//
//    public ValidationResult validateScheduleUpdateRequest(ScheduleUpdateRequest scheduleUpdateRequest) {
//        if (scheduleUpdateRequest.getName() == null) {
//            return ValidationResult.failure(ExceptionMessages.NO_NAME);
//        }
//
//        return ValidationResult.success();
//    }
//}
