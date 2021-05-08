//package fmi.pchmi.project.mySchedule.service;
//
//import fmi.pchmi.project.mySchedule.internal.CommonUtils;
//import fmi.pchmi.project.mySchedule.internal.constants.ExceptionMessages;
//import fmi.pchmi.project.mySchedule.model.database.group.Group;
//import fmi.pchmi.project.mySchedule.model.database.schedule.Schedule;
//import fmi.pchmi.project.mySchedule.model.database.user.Role;
//import fmi.pchmi.project.mySchedule.model.database.user.User;
//import fmi.pchmi.project.mySchedule.model.exception.ValidationException;
//import fmi.pchmi.project.mySchedule.model.request.group.GroupRequest;
//import fmi.pchmi.project.mySchedule.model.request.schedule.ScheduleRequest;
//import fmi.pchmi.project.mySchedule.model.request.schedule.ScheduleUpdateRequest;
//import fmi.pchmi.project.mySchedule.model.validation.ValidationResult;
//import fmi.pchmi.project.mySchedule.repository.GroupRepository;
//import fmi.pchmi.project.mySchedule.repository.ScheduleRepository;
//import fmi.pchmi.project.mySchedule.repository.UserRepository;
//import fmi.pchmi.project.mySchedule.validator.ScheduleValidator;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.concurrent.ScheduledExecutorService;
//
//@Service
//public class ScheduleService {
//    @Autowired
//    private ScheduleRepository scheduleRepository;
//
//    @Autowired
//    private GroupRepository groupRepository;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private ScheduleValidator scheduleValidator;
//
//    public Collection<Schedule> getAllSchedules() {
//        return CommonUtils.getAllAsCollection(scheduleRepository);
//    }
//
//    public Schedule getScheduleById(String scheduleId) {
//        return scheduleRepository.findById(scheduleId)
//                .orElseThrow(() -> ValidationException.create(String.format(ExceptionMessages.SCHEDULE_ID_DOES_NOT_EXIST, scheduleId)));
//    }
//
//    public Schedule createSchedule(ScheduleRequest scheduleRequest, User loggedUser) {
//        validateScheduleRequest(scheduleRequest);
//
//        if (scheduleRepository.findByName(scheduleRequest.getName()) != null) {
//            throw ValidationException.create(ExceptionMessages.NAME_ALREADY_EXISTS);
//        }
//
//        if (scheduleRequest.getGroupId() != null && groupRepository.findById(scheduleRequest.getGroupId()).isEmpty()) {
//            throw ValidationException.create(String.format(ExceptionMessages.GROUP_ID_DOES_NOT_EXIST, scheduleRequest.getGroupId()));
//        }
//
//        if (scheduleRequest.getUserId() != null && userRepository.findById(scheduleRequest.getUserId()).isEmpty()) {
//            throw ValidationException.create(String.format(ExceptionMessages.USER_ID_DOES_NOT_EXIST, scheduleRequest.getUserId()));
//        }
//
//        if (scheduleRequest.getIsPrivate()
//                && !scheduleRequest.getUserId().equals(loggedUser.getId())
//                && !Role.ADMINISTRATOR.equals(loggedUser.getRole())) {
//            throw ValidationException.create(ExceptionMessages.CANNOT_CREATE_SCHEDULE_FOR_OTHERS_UNLESS_ADMINISTRATOR);
//        }
//
//        //if creator is employee and tries to create group schedule
//        if (scheduleRequest.getGroupId() != null && Role.EMPLOYEE.equals(loggedUser.getRole())) {
//            throw ValidationException.create(ExceptionMessages.EMPLOYEE_CREATE_GROUP_SCHEDULE);
//        }
//        Schedule schedule = new Schedule();
//        schedule.setCreatorId(loggedUser.getId());
//        schedule.getEvents().addAll(scheduleRequest.getEvents());
//        if (scheduleRequest.getGroupId() != null) {
//            schedule.setGroupId(scheduleRequest.getGroupId());
//        }
//        if (scheduleRequest.getUserId() != null) {
//            schedule.setUserId(scheduleRequest.getUserId());
//        }
//        schedule.setIsPrivate(scheduleRequest.getIsPrivate());
//        schedule.setName(scheduleRequest.getName());
//        return scheduleRepository.save(schedule);
//    }
//
//    public Schedule updateSchedule(String scheduleId, ScheduleUpdateRequest scheduleUpdateRequest, User loggedUser) {
//        validateScheduleUpdateRequest(scheduleUpdateRequest);
//
//        Schedule scheduleToUpdate = getScheduleById(scheduleId);
//
//        if (scheduleRepository.findByName(scheduleUpdateRequest.getName()) != null
//                && !scheduleUpdateRequest.getName().equals(scheduleToUpdate.getName())){
//            throw ValidationException.create(ExceptionMessages.NAME_ALREADY_EXISTS);
//        }
//
//        if (scheduleToUpdate.getIsPrivate()
//                && !loggedUser.getId().equals(scheduleToUpdate.getUserId())
//                && !Role.ADMINISTRATOR.equals(loggedUser.getRole())) {
//            throw ValidationException.create(ExceptionMessages.CANNOT_UPDATE_SCHEDULE_FOR_OTHERS_UNLESS_ADMINISTRATOR);
//        }
//
//
//        if (!scheduleToUpdate.getIsPrivate()) {
//            Group group = groupRepository.findById(scheduleToUpdate.getGroupId())
//                    .orElseThrow(() -> ValidationException.create(String.format(ExceptionMessages.GROUP_ID_DOES_NOT_EXIST, scheduleToUpdate.getGroupId())));
//            boolean doExist = false;
//            for (String memberId : group.getMembers()) {
//                if (loggedUser.getId().equals(memberId)) {
//                    doExist = true;
//                    break;
//                }
//            }
//            if (!doExist && !Role.ADMINISTRATOR.equals(loggedUser.getRole())) {
//                throw ValidationException.create(ExceptionMessages.CANNOT_UPDATE_GROUP_SCHEDULE_FOR_OTHER_GROUPS_UNLESS_ADMINISTRATOR);
//            }
//        }
//
//        scheduleToUpdate.setName(scheduleUpdateRequest.getName());
//        scheduleToUpdate.getEvents().addAll(scheduleUpdateRequest.getEvents());
//        return scheduleRepository.save(scheduleToUpdate);
//    }
//
//    public void deleteSchedule(String scheduleId, User loggedUser) {
//        Schedule schedule = getScheduleById(scheduleId);
//
//        if (schedule.getIsPrivate()
//                && !loggedUser.getId().equals(schedule.getUserId())
//                && !Role.ADMINISTRATOR.equals(loggedUser.getRole())) {
//            throw ValidationException.create(ExceptionMessages.CANNOT_DELETE_SCHEDULE_FOR_OTHERS_UNLESS_ADMINISTRATOR);
//        }
//
//        if (!schedule.getIsPrivate()) {
//            if (schedule.getCreatorId())
//        }
//    }
//
//    private void validateScheduleRequest(ScheduleRequest scheduleRequest) {
//        ValidationResult validationResult = scheduleValidator.validateScheduleRequest(scheduleRequest);
//
//        if (!validationResult.isSuccess()) {
//            throw ValidationException.create(validationResult.getValidationError());
//        }
//    }
//
//    private void validateScheduleUpdateRequest(ScheduleUpdateRequest scheduleUpdateRequest) {
//        ValidationResult validationResult = scheduleValidator.validateScheduleUpdateRequest(scheduleUpdateRequest);
//
//        if (!validationResult.isSuccess()) {
//            throw ValidationException.create(validationResult.getValidationError());
//        }
//    }
//}
