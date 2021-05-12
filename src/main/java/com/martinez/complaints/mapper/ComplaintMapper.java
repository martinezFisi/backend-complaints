package com.martinez.complaints.mapper;

import com.martinez.complaints.dto.ComplaintDto;
import com.martinez.complaints.entity.Citizen;
import com.martinez.complaints.entity.Complaint;
import org.springframework.stereotype.Component;

@Component
public class ComplaintMapper {

    private final CitizenMapper citizenMapper;

    public ComplaintMapper(CitizenMapper citizenMapper) {
        this.citizenMapper = citizenMapper;
    }

    public ComplaintDto complaintToComplaintDto(Complaint complaint) {
        return ComplaintDto.builder()
                .id(complaint.getId())
                .address(complaint.getAddress())
                .latitude(complaint.getLatitude())
                .longitude(complaint.getLongitude())
                .complaintType(complaint.getComplaintType())
                .commentary(complaint.getCommentary())
                .citizenDto(citizenMapper.citizenToCitizenDto(complaint.getCitizen()))
                .build();
    }

    public Complaint complaintDtoToComplaint(ComplaintDto complaintDto) {
        return Complaint.builder()
                .address(complaintDto.getAddress())
                .latitude(complaintDto.getLatitude())
                .longitude(complaintDto.getLongitude())
                .complaintType(complaintDto.getComplaintType())
                .commentary(complaintDto.getCommentary())
                .citizen(Citizen.builder().id(complaintDto.getCitizenId()).build())
                .build();
    }
}
