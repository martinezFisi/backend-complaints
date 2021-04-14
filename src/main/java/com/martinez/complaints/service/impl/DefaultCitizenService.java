package com.martinez.complaints.service.impl;

import com.martinez.complaints.dto.CitizenDto;
import com.martinez.complaints.entity.Citizen;
import com.martinez.complaints.exception.WrongSearchCriteriaException;
import com.martinez.complaints.mapper.CitizenMapper;
import com.martinez.complaints.repository.CitizenRepository;
import com.martinez.complaints.repository.searchcriteria.CitizenSpecificationBuilder;
import com.martinez.complaints.service.CitizenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.toList;

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
                                       .orElseThrow(() -> new EntityNotFoundException("Citizen with id [" + id + "] not found"));

        var citizenDto = citizenMapper.citizenToCitizenDto(citizen);
        log.info("Citizen found: {}", citizenDto.toString());

        return citizenDto;
    }

    @Override
    public List<CitizenDto> filterBySearchCriterias(String searchCriterias) {
        log.info("Filter citizens by [{}]", searchCriterias);
        var citizenSpecificationBuilder = new CitizenSpecificationBuilder();

        var pattern = Pattern.compile("(\\w+)(=|<|>|<=|>=)(\\w+)");
        var matcher = pattern.matcher(searchCriterias);

        while (matcher.find()) {
            citizenSpecificationBuilder.with(matcher.group(1), matcher.group(2), matcher.group(3));
        }

        var citizenSpecification = citizenSpecificationBuilder.build();
        var citizensDto = tryFindAllCitizens(citizenSpecification).stream()
                                                                  .map(citizenMapper::citizenToCitizenDto)
                                                                  .collect(toList());

        log.info("Citizens found: {}", citizensDto);
        return citizensDto;
    }

    private List<Citizen> tryFindAllCitizens(Specification<Citizen> citizenSpecification) {
        try {
            return citizenRepository.findAll(citizenSpecification);
        } catch (InvalidDataAccessApiUsageException e) {
            throw new WrongSearchCriteriaException("Wrong search criteria found");
        }
    }
}
