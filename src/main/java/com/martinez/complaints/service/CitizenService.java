package com.martinez.complaints.service;

import com.martinez.complaints.dto.CitizenDto;

import java.util.List;

public interface CitizenService {

    long create(CitizenDto citizenDto);

    void update(Long id, CitizenDto citizenDto);

    CitizenDto findById(Long id);

    List<CitizenDto> filterBySearchCriterias(String searchCriterias);

    long getCitizenIdByEmail(String email);
}
