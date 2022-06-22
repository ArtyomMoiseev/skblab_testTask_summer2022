package com.moiseevTest.skbTest.controller;

import com.moiseevTest.skbTest.dto.RegistrationFromWebDto;
import com.moiseevTest.skbTest.service.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@AllArgsConstructor
public class UserController {

    private RegistrationService registrationService;

    @PostMapping("/user/sendRegistrationForm")
    public ResponseEntity<String> sendRegistrationForm(@Valid @RequestBody RegistrationFromWebDto registrationForm) {
        if (registrationService.registerNewUser(registrationForm))
            return new ResponseEntity<>("OK", HttpStatus.ACCEPTED);
        return new ResponseEntity<>("ERROR", HttpStatus.BAD_GATEWAY);
    }
}
