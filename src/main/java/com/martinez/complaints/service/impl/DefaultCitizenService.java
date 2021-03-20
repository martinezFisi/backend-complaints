package com.martinez.complaints.service.impl;

import com.martinez.complaints.dto.CitizenDto;
import com.martinez.complaints.exception.NotFoundException;
import com.martinez.complaints.mapper.CitizenMapper;
import com.martinez.complaints.repository.CitizenRepository;
import com.martinez.complaints.service.CitizenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

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
    public CitizenDto create(CitizenDto citizenDto) {
        log.info(citizenDto.toString());
        var citizen = citizenMapper.citizenDtoToCitizen(citizenDto);

        citizen = citizenRepository.save(citizen);
        log.info(citizen.toString());

        return citizenMapper.citizenToCitizenDto(citizen);
    }

    @Override
    public CitizenDto findById(Long id) {
        log.info("Finding citizen by id [{}]...", id);

        var citizen = citizenRepository.findById(id)
                                       .orElseThrow(() -> new NotFoundException("Citizen with id [" + id + "] not found"));

        var citizenDto = citizenMapper.citizenToCitizenDto(citizen);
        log.info("Citizen found: {}", citizenDto.toString());

        return citizenDto;
    }

}
