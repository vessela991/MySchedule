package fmi.project.mySchedule.controller;

import fmi.project.mySchedule.internal.constants.Routes;
import fmi.project.mySchedule.model.request.identity.IdentityLoginRequest;
import fmi.project.mySchedule.model.response.identity.IdentityLoginResponse;
import fmi.project.mySchedule.service.IdentityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IdentityController {
    @Autowired
    private IdentityService identityService;

    @PostMapping(Routes.LOGIN)
    public ResponseEntity<IdentityLoginResponse> login(@RequestBody IdentityLoginRequest request) {
        return new ResponseEntity<>(identityService.login(request), HttpStatus.OK);
    }
}
