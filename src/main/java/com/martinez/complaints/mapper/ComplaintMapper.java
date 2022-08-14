package com.martinez.complaints.mapper;

import com.martinez.complaints.dto.ComplaintDto;
import com.martinez.complaints.entity.Citizen;
import com.martinez.complaints.entity.Complaint;
import com.martinez.complaints.util.ComplaintType;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
                .complaintType(ComplaintType.valueOf(complaint.getComplaintType()))
                .postalCode(complaint.getPostalCode())
                .locality(complaint.getLocality())
                .country(complaint.getCountry())
                .commentary(complaint.getCommentary())
                .creationTime(complaint.getCreationTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm")))
                .citizenDto(citizenMapper.citizenToCitizenDto(complaint.getCitizen()))
                .build();
    }

    public Complaint complaintDtoToComplaint(ComplaintDto complaintDto) {
        return Complaint.builder()
                .address(complaintDto.getAddress())
                .latitude(complaintDto.getLatitude())
                .longitude(complaintDto.getLongitude())
                .complaintType(complaintDto.getComplaintType().toString())
                .postalCode(complaintDto.getPostalCode())
                .locality(complaintDto.getLocality())
                .country(complaintDto.getCountry())
                .commentary(complaintDto.getCommentary())
                .creationTime(LocalDateTime.now())
                .citizen(Citizen.builder().id(complaintDto.getCitizenId()).build())
                .build();
    }
}
