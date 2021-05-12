package com.martinez.complaints.service;

import com.martinez.complaints.dto.ComplaintDto;

public interface ComplaintService {

    ComplaintDto create(ComplaintDto complaintDto);

    ComplaintDto findById(Long id);
}
