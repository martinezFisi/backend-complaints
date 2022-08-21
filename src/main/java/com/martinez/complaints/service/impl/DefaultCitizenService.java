package com.martinez.complaints.service.impl;

import com.martinez.complaints.dto.CitizenDto;
import com.martinez.complaints.entity.Citizen;
import com.martinez.complaints.exception.WrongSearchCriteriaException;
import com.martinez.complaints.mapper.CitizenMapper;
import com.martinez.complaints.repository.CitizenRepository;
import com.martinez.complaints.repository.searchcriteria.SpecificationBuilder;
import com.martinez.complaints.repository.searchcriteria.SearchCriteria;
import com.martinez.complaints.service.CitizenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.regex.Pattern;

import static com.martinez.complaints.repository.searchcriteria.SearchCriteria.AND;
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
    public long create(CitizenDto citizenDto) {
        log.info(citizenDto.toString());
        var citizen = citizenMapper.citizenDtoToCitizen(citizenDto);

        citizen = citizenRepository.save(citizen);
        log.info(citizen.toString());

        return citizen.getId();
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
        var specificationBuilder = new SpecificationBuilder<Citizen>();

        var pattern = Pattern.compile("(,|\\|)(\\w+)(=|<|>|<=|>=|:)([A-Za-z.@_0-9]+)");
        var matcher = pattern.matcher(AND.concat(searchCriterias));

        while (matcher.find()) {
            var searchCriteria = SearchCriteria.builder()
                                               .connector(matcher.group(1))
                                               .key(matcher.group(2))
                                               .operation(matcher.group(3))
                                               .value(matcher.group(4))
                                               .build();
            log.info("Search Criteria found: {}", searchCriteria);
            specificationBuilder.with(searchCriteria);
        }

        var citizenSpecification = specificationBuilder.build();
        var citizensDto = tryFindAllCitizens(citizenSpecification).stream()
                                                                  .map(citizenMapper::citizenToCitizenDto)
                                                                  .collect(toList());

        if (citizensDto.isEmpty()) {
            throw new EntityNotFoundException("Citizens searched by {" + searchCriterias + "} not found");
        }

        log.info("Citizens found: {}", citizensDto);
        return citizensDto;
    }

    @Override
    public long getCitizenIdByEmail(String email) {
        var citizenOptional = citizenRepository.findCitizenByEmail(email);
        return citizenOptional.map(Citizen::getId).orElse(0L);
    }

    private List<Citizen> tryFindAllCitizens(Specification<Citizen> citizenSpecification) {
        try {
            return citizenRepository.findAll(citizenSpecification);
        } catch (InvalidDataAccessApiUsageException e) {
            throw new WrongSearchCriteriaException("Wrong search criteria found", e);
        }
    }
}
