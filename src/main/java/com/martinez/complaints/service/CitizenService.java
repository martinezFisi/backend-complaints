package com.martinez.complaints.service;

import com.martinez.complaints.dto.CitizenDto;

public interface CitizenService {

    CitizenDto create(CitizenDto citizenDto);

    CitizenDto findById(Long id);
}
