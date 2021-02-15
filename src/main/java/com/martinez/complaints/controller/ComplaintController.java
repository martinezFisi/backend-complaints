package com.martinez.complaints.controller;

import com.martinez.complaints.dto.ComplaintDto;
import com.martinez.complaints.service.ComplaintService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/complaint")
public class ComplaintController {

    private final ComplaintService complaintService;

    public ComplaintController(ComplaintService complaintService) {
        this.complaintService = complaintService;
    }

    @PostMapping("/save")
    public ComplaintDto save(@RequestBody ComplaintDto complaintDto){
        return complaintService.save(complaintDto);
    }
}
