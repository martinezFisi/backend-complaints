package com.martinez.complaints.controller;

import com.martinez.complaints.service.AuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/authentication", produces = APPLICATION_JSON_VALUE)
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping(value = "{idToken}")
    public ResponseEntity<Map<String, Long>> authenticate(@PathVariable String idToken) {
        var citizenId = authenticationService.authenticate(idToken);
        return ResponseEntity.ok().body(Map.of("citizenId", citizenId));
    }

}
