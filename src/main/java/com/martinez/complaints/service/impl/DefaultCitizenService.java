package com.martinez.complaints.service.impl;

import com.martinez.complaints.dto.CitizenDto;
import com.martinez.complaints.entity.Citizen;
import com.martinez.complaints.exception.WrongSearchCriteriaException;
import com.martinez.complaints.mapper.CitizenMapper;
import com.martinez.complaints.repository.CitizenRepository;
import com.martinez.complaints.repository.searchcriteria.SearchCriteria;
import com.martinez.complaints.repository.searchcriteria.SpecificationBuilder;
import com.martinez.complaints.service.CitizenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import static com.martinez.complaints.repository.searchcriteria.SearchCriteria.AND;

@Slf4j
@Service
public class DefaultCitizenService implements CitizenService {

    private final String citizenNotFoundMessage;
    private final String citizenNotFoundBySearchCriteriasMessage;
    private final String wrongSearchCriteriaMessage;

    private final CitizenRepository citizenRepository;
    private final CitizenMapper citizenMapper;
    private final ResourceBundle resourceBundle;

    public DefaultCitizenService(CitizenRepository citizenRepository, CitizenMapper citizenMapper,
                                 ResourceBundle resourceBundle) {
        this.citizenRepository = citizenRepository;
        this.citizenMapper = citizenMapper;
        this.resourceBundle = resourceBundle;
        citizenNotFoundMessage = resourceBundle.getString("exception.citizen.not.found");
        citizenNotFoundBySearchCriteriasMessage = resourceBundle.getString("exception.citizens.not.found.by.search.criterias");
        wrongSearchCriteriaMessage = resourceBundle.getString("exception.wrong.search.criteria");
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
    public void update(Long id, CitizenDto citizenDto) {

        var citizenRegistered = findById(id);
        citizenRegistered.setDocumentType(citizenDto.getDocumentType());
        citizenRegistered.setDocumentNumber(citizenDto.getDocumentNumber());
        citizenRegistered.setPhoneNumber(citizenDto.getPhoneNumber());
        citizenRegistered.setFirstName(citizenDto.getFirstName());
        citizenRegistered.setLastName(citizenDto.getLastName());
        citizenRegistered.setAge(citizenDto.getAge());

        create(citizenRegistered);//update
    }

    @Override
    public CitizenDto findById(Long id) {
        log.info("Finding citizen by id [{}]...", id);

        var citizen = citizenRepository.findById(id)
                                       .orElseThrow(() -> new EntityNotFoundException(
                                               citizenNotFoundMessage.replace("{}", String.valueOf(id))));

        var citizenDto = citizenMapper.citizenToCitizenDto(citizen);
        log.info("Citizen found: {}", citizenDto.toString());

        return citizenDto;
    }

    @Override
    public List<CitizenDto> filterBySearchCriterias(String searchCriterias) {
        log.info("Filter citizens by [{}]", searchCriterias);
        var specificationBuilder = new SpecificationBuilder<Citizen>(resourceBundle);

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
                                                                  .toList();

        if (citizensDto.isEmpty()) {
            throw new EntityNotFoundException(citizenNotFoundBySearchCriteriasMessage.replace("{}", searchCriterias));
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
            throw new WrongSearchCriteriaException(wrongSearchCriteriaMessage, e);
        }
    }
}
