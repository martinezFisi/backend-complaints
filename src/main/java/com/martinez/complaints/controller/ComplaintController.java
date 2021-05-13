package com.martinez.complaints.controller;

import com.martinez.complaints.dto.ComplaintDto;
import com.martinez.complaints.service.ComplaintService;
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
@RequestMapping(value = "/api/v1/complaints", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
public class ComplaintController {

    private final ComplaintService complaintService;

    public ComplaintController(ComplaintService complaintService) {
        this.complaintService = complaintService;
    }

    @PostMapping
    public ResponseEntity<ComplaintDto> createCitizen(HttpServletRequest request, @Valid @RequestBody ComplaintDto complaintDto){
        var complaint = complaintService.create(complaintDto);

        var uri = URI.create(request.getRequestURI() + "/" + complaint.getId());
        return ResponseEntity.created(uri).body(complaint);
    }

    @GetMapping(value = "{complaintId}")
    public ResponseEntity<ComplaintDto> getById(@PathVariable Long complaintId){

        var complaintDto = complaintService.findById(complaintId);

        return ResponseEntity.ok(complaintDto);
    }

    @GetMapping
    public ResponseEntity<List<ComplaintDto>> filterBySearchCriterias(@RequestParam(value = "searchCriterias") String searchCriterias){

        var complaintsDto = complaintService.filterBySearchCriterias(searchCriterias);

        return ResponseEntity.ok(complaintsDto);
    }

}
