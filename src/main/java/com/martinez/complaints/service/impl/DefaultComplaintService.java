package com.martinez.complaints.service.impl;

import com.martinez.complaints.dto.ComplaintDto;
import com.martinez.complaints.mapper.ComplaintMapper;
import com.martinez.complaints.repository.ComplaintRepository;
import com.martinez.complaints.service.ComplaintService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DefaultComplaintService implements ComplaintService {

    private final ComplaintRepository complaintRepository;
    private final ComplaintMapper complaintMapper;

    public DefaultComplaintService(ComplaintRepository complaintRepository, ComplaintMapper complaintMapper) {
        this.complaintRepository = complaintRepository;
        this.complaintMapper = complaintMapper;
    }

    @Override
    public ComplaintDto save(ComplaintDto complaintDto) {
        log.info(complaintDto.toString());
        var complaint = complaintMapper.complaintDtoToComplaint(complaintDto);
        log.info(complaint.toString());

        complaint = complaintRepository.save(complaint);

        return complaintMapper.complaintToComplaintDto(complaint);
    }
}
