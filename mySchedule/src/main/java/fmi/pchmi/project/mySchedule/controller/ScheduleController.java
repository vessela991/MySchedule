//package fmi.pchmi.project.mySchedule.controller;
//
//import fmi.pchmi.project.mySchedule.internal.constants.CommonConstants;
//import fmi.pchmi.project.mySchedule.internal.constants.Routes;
//import fmi.pchmi.project.mySchedule.model.database.schedule.Schedule;
//import fmi.pchmi.project.mySchedule.model.database.user.User;
//import fmi.pchmi.project.mySchedule.model.request.schedule.ScheduleRequest;
//import fmi.pchmi.project.mySchedule.model.request.schedule.ScheduleUpdateRequest;
//import fmi.pchmi.project.mySchedule.service.ScheduleService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Collection;
//
//@RestController
//public class ScheduleController {
//    @Autowired
//    private ScheduleService scheduleService;
//
//    @GetMapping(Routes.SCHEDULES)
//    public ResponseEntity<Collection<Schedule>> getAllSchedules() {
//        return new ResponseEntity<>(scheduleService.getAllSchedules(), HttpStatus.OK);
//    }
//
//    @GetMapping(Routes.SCHEDULES_ID)
//    public ResponseEntity<Schedule> getScheduleById(@PathVariable("id") String scheduleId) {
//        return new ResponseEntity<>(scheduleService.getScheduleById(scheduleId), HttpStatus.OK);
//    }
//
//    @PostMapping(Routes.SCHEDULES)
//    public ResponseEntity<Schedule> createSchedule(@RequestBody ScheduleRequest scheduleRequest,
//                                                   @RequestAttribute(CommonConstants.LOGGED_USER) User loggedUser) {
//        return new ResponseEntity<>(scheduleService.createSchedule(scheduleRequest, loggedUser), HttpStatus.OK);
//    }
//
//    @PutMapping(Routes.SCHEDULES_ID)
//    public ResponseEntity<Schedule> updateSchedule(@RequestParam("id") String scheduleId,
//                                                   @RequestBody ScheduleUpdateRequest scheduleUpdateRequest,
//                                                   @RequestAttribute(CommonConstants.LOGGED_USER) User loggedUser) {
//        return new ResponseEntity<>(scheduleService.updateSchedule(scheduleId, scheduleUpdateRequest, loggedUser), HttpStatus.OK);
//    }
//
//    @DeleteMapping(Routes.SCHEDULES_ID)
//    public ResponseEntity<Schedule> deleteSchedule(@RequestParam("id") String scheduleId,
//                                                   @RequestAttribute(CommonConstants.LOGGED_USER) User loggedUser) {
//        return new ResponseEntity<>(scheduleService.deleteSchedule(scheduleId, loggedUser), HttpStatus.OK);
//    }
//}
