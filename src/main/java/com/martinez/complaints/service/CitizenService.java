package com.martinez.complaints.service;

import com.martinez.complaints.dto.CitizenDto;

import java.util.List;

public interface CitizenService {

    CitizenDto create(CitizenDto citizenDto);

    CitizenDto findById(Long id);

    List<CitizenDto> filterBy(String searchCriterias);
}
