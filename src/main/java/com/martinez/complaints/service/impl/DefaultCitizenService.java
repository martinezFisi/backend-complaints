package com.martinez.complaints.service.impl;

import com.martinez.complaints.dto.CitizenDto;
import com.martinez.complaints.mapper.CitizenMapper;
import com.martinez.complaints.repository.CitizenRepository;
import com.martinez.complaints.service.CitizenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DefaultCitizenService implements CitizenService {

    private final CitizenRepository citizenRepository;
    private final CitizenMapper citizenMapper;

    public DefaultCitizenService(CitizenRepository citizenRepository, CitizenMapper citizenMapper) {
        this.citizenRepository = citizenRepository;
        this.citizenMapper = citizenMapper;
    }

    @Override
    public CitizenDto save(CitizenDto citizenDto) {
        log.info(citizenDto.toString());
        var citizen = citizenMapper.citizenDtoToCitizen(citizenDto);
        log.info(citizen.toString());

        citizen = citizenRepository.save(citizen);

        return citizenMapper.citizenToCitizenDto(citizen);
    }

}
