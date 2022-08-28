package com.martinez.complaints.service.impl;

import com.martinez.complaints.dto.ComplaintDto;
import com.martinez.complaints.entity.Complaint;
import com.martinez.complaints.exception.WrongSearchCriteriaException;
import com.martinez.complaints.mapper.ComplaintMapper;
import com.martinez.complaints.repository.CitizenRepository;
import com.martinez.complaints.repository.ComplaintRepository;
import com.martinez.complaints.repository.searchcriteria.SearchCriteria;
import com.martinez.complaints.repository.searchcriteria.SpecificationBuilder;
import com.martinez.complaints.service.ComplaintService;
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
public class DefaultComplaintService implements ComplaintService {

    private final String complaintNotFoundMessage;
    private final String wrongSearchCriteriaMessage;

    private final ComplaintMapper complaintMapper;
    private final CitizenRepository citizenRepository;
    private final ComplaintRepository complaintRepository;
    private final ResourceBundle resourceBundle;

    public DefaultComplaintService(ComplaintMapper complaintMapper, CitizenRepository citizenRepository, ComplaintRepository complaintRepository,
                                   ResourceBundle resourceBundle) {
        this.complaintMapper = complaintMapper;
        this.citizenRepository = citizenRepository;
        this.complaintRepository = complaintRepository;
        this.resourceBundle = resourceBundle;
        complaintNotFoundMessage = resourceBundle.getString("exception.complaint.not.found");
        wrongSearchCriteriaMessage = resourceBundle.getString("exception.wrong.search.criteria");
    }

    @Override
    public long create(ComplaintDto complaintDto) {
        log.info(complaintDto.toString());
        var complaint = complaintMapper.complaintDtoToComplaint(complaintDto);
        var citizenReference = citizenRepository.getById(complaint.getCitizen().getId());

        complaint.setCitizen(citizenReference);
        complaint = complaintRepository.save(complaint);
        log.info(complaint.toString());

        return complaint.getId();
    }

    @Override
    public ComplaintDto findById(Long id) {
        log.info("Finding Complaint by id [{}]...", id);
        var complaint = complaintRepository.findById(id)
                                       .orElseThrow(() -> new EntityNotFoundException(
                                               complaintNotFoundMessage.replace("{}", String.valueOf(id))));

        var complaintDto = complaintMapper.complaintToComplaintDto(complaint);
        log.info("Complaint found: {}", complaintDto.toString());

        return complaintDto;
    }

    @Override
    public List<ComplaintDto> filterBySearchCriterias(String searchCriterias) {
        log.info("Filter complaints by [{}]", searchCriterias);
        var specificationBuilder = new SpecificationBuilder<Complaint>(resourceBundle);

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

        var complaintSpecification = specificationBuilder.build();
        var complaintDtos = tryFindAllComplaints(complaintSpecification).stream()
                                                                        .map(complaintMapper::complaintToComplaintDto)
                                                                        .toList();

        log.info("Complaints found: {}", complaintDtos);
        return complaintDtos;
    }

    private List<Complaint> tryFindAllComplaints(Specification<Complaint> complaintSpecification) {
        try {
            return complaintRepository.findAll(complaintSpecification);
        } catch (InvalidDataAccessApiUsageException e) {
            throw new WrongSearchCriteriaException(wrongSearchCriteriaMessage);
        }
    }
}
