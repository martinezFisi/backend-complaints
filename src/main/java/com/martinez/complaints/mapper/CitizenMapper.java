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
                .password(citizen.getPassword())
                .documentType(DocumentType.valueOf(citizen.getDocumentType()))
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
                .password(citizenDto.getPassword())
                .documentType(citizenDto.getDocumentType().toString())
                .documentNumber(citizenDto.getDocumentNumber())
                .firstName(citizenDto.getFirstName())
                .lastName(citizenDto.getLastName())
                .age(citizenDto.getAge())
                .build();
    }
}
