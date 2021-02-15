package com.martinez.complaints.controller;

import com.martinez.complaints.dto.CitizenDto;
import com.martinez.complaints.service.CitizenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/citizen")
public class CitizenController {

    private final CitizenService citizenService;

    public CitizenController(CitizenService citizenService) {
        this.citizenService = citizenService;
    }

    @PostMapping("/save")
    public CitizenDto save(@RequestBody CitizenDto citizenDto){
        return citizenService.save(citizenDto);
    }

}
