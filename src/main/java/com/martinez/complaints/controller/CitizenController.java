package com.martinez.complaints.controller;

import com.martinez.complaints.dto.CitizenDto;
import com.martinez.complaints.service.CitizenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/citizens", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
public class CitizenController {

    private final CitizenService citizenService;

    public CitizenController(CitizenService citizenService) {
        this.citizenService = citizenService;
    }

    @PostMapping
    public ResponseEntity<CitizenDto> createCitizen(HttpServletRequest request, @Valid @RequestBody CitizenDto reqCitizenDto){
        var resCitizenDto = citizenService.create(reqCitizenDto);

        var uri = URI.create(request.getRequestURI() + "/" + resCitizenDto.getId());
        return ResponseEntity.created(uri).body(resCitizenDto);
    }

    @GetMapping(value = "{citizenId}")
    public ResponseEntity<CitizenDto> getById(@PathVariable Long citizenId){

        var citizenDto = citizenService.findById(citizenId);

        return ResponseEntity.ok(citizenDto);
    }

    @GetMapping
    public ResponseEntity<List<CitizenDto>> filterBySearchCriterias(@RequestParam(value = "searchCriterias") String searchCriterias){

        var citizensDto = citizenService.filterBySearchCriterias(searchCriterias);

        return ResponseEntity.ok(citizensDto);
    }

}
