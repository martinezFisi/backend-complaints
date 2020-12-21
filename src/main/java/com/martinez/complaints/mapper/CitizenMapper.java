package com.martinez.complaints.mapper;

import com.martinez.complaints.dto.CitizenDto;
import com.martinez.complaints.entity.Citizen;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CitizenMapper {

    CitizenMapper INSTANCE = Mappers.getMapper(CitizenMapper.class);

    CitizenDto citizenToCitizenDto(Citizen citizen);

    Citizen citizenDtoToCitizen(CitizenDto citizenDto);
}
