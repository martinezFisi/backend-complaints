package com.martinez.complaints.service;

import com.martinez.complaints.dto.ComplaintDto;

import java.util.List;

public interface ComplaintService {

    ComplaintDto create(ComplaintDto complaintDto);

    ComplaintDto findById(Long id);

    List<ComplaintDto> filterBySearchCriterias(String searchCriterias);
}
