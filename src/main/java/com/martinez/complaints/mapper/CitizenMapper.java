package com.martinez.complaints.mapper;

import com.martinez.complaints.dto.CitizenDto;
import com.martinez.complaints.entity.Citizen;
import com.martinez.complaints.util.DocumentType;
import org.springframework.stereotype.Component;

@Component
public class CitizenMapper {

    public CitizenDto citizenToCitizenDto(Citizen citizen){
        return CitizenDto.builder()
                .id(citizen.getId())
                .email(citizen.getEmail())
                .phoneNumber(citizen.getPhoneNumber())
                .password(citizen.getPassword())
                .documentType(citizen.getDocumentType() != null ? DocumentType.valueOf(citizen.getDocumentType()) : null)
                .documentNumber(citizen.getDocumentNumber())
                .firstName(citizen.getFirstName())
                .lastName(citizen.getLastName())
                .age(citizen.getAge())
                .build();
    }

    public Citizen citizenDtoToCitizen(CitizenDto citizenDto){
        return Citizen.builder()
                .id(citizenDto.getId())
                .email(citizenDto.getEmail())
                .phoneNumber(citizenDto.getPhoneNumber())
                .password(citizenDto.getPassword())
                .documentType(citizenDto.getDocumentType() != null ? citizenDto.getDocumentType().toString() : null)
                .documentNumber(citizenDto.getDocumentNumber())
                .firstName(citizenDto.getFirstName())
                .lastName(citizenDto.getLastName())
                .age(citizenDto.getAge())
                .build();
    }
}
