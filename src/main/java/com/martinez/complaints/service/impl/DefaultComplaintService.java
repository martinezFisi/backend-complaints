package com.martinez.complaints.service.impl;

import com.martinez.complaints.dto.CitizenDto;
import com.martinez.complaints.dto.ComplaintDto;
import com.martinez.complaints.mapper.ComplaintMapper;
import com.martinez.complaints.repository.CitizenRepository;
import com.martinez.complaints.repository.ComplaintRepository;
import com.martinez.complaints.service.ComplaintService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Slf4j
@Service
public class DefaultComplaintService implements ComplaintService {

    ComplaintMapper complaintMapper;
    CitizenRepository citizenRepository;
    ComplaintRepository complaintRepository;

    public DefaultComplaintService(ComplaintMapper complaintMapper, CitizenRepository citizenRepository, ComplaintRepository complaintRepository) {
        this.complaintMapper = complaintMapper;
        this.citizenRepository = citizenRepository;
        this.complaintRepository = complaintRepository;
    }

    @Override
    public ComplaintDto create(ComplaintDto complaintDto) {
        log.info(complaintDto.toString());
        var complaint = complaintMapper.complaintDtoToComplaint(complaintDto);
        var citizenReference = citizenRepository.getOne(complaint.getCitizen().getId());

        complaint.setCitizen(citizenReference);
        complaint = complaintRepository.save(complaint);
        log.info(complaint.toString());

        return complaintMapper.complaintToComplaintDto(complaint);
    }

    @Override
    public ComplaintDto findById(Long id) {
        log.info("Finding Complaint by id [{}]...", id);

        var complaint = complaintRepository.findById(id)
                                       .orElseThrow(() -> new EntityNotFoundException("Complaint with id [" + id + "] not found"));

        var complaintDto = complaintMapper.complaintToComplaintDto(complaint);
        log.info("Complaint found: {}", complaintDto.toString());

        return complaintDto;
    }

}
