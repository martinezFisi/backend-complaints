package com.martinez.complaints.controller;

import com.martinez.complaints.dto.CitizenDto;
import com.martinez.complaints.service.CitizenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping(value = "/citizens", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
public class CitizenController {

    private final CitizenService citizenService;

    public CitizenController(CitizenService citizenService) {
        this.citizenService = citizenService;
    }

    @PostMapping
    public ResponseEntity<CitizenDto> save(HttpServletRequest request, @Valid @RequestBody CitizenDto citizenDto){
        var citizen = citizenService.save(citizenDto);

        var uri = URI.create(request.getRequestURI() + "/" + citizen.getId());
        return ResponseEntity.created(uri).body(citizen);
    }

}
