package com.martinez.complaints.controller;

import com.martinez.complaints.dto.ComplaintDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;

import java.net.URI;

import static com.martinez.complaints.util.CitizenDtoFactory.createCitizenDto;
import static com.martinez.complaints.util.ComplaintDtoFactory.CITIZEN_ID;
import static com.martinez.complaints.util.ComplaintDtoFactory.createComplaintDtoWithField;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.http.MediaType.APPLICATION_JSON;

class GetByIdComplaintControllerIT extends AbstractIntegrationTest {

    @DisplayName("""
            Given a valid Complaint already registered \
            When invoke a GET Method on URI "/api/v1/citizens/{citizenId}" \
            Then controller response with HttpStatus=OK(200) and the Complaint already registered
            """)
    @Test
    void testGetComplaintById() {
        var citizenDto = createCitizenDto();
        var citizenId = citizenService.create(citizenDto);
        citizenDto.setId(citizenId);

        var complaintDto = createComplaintDtoWithField(CITIZEN_ID, citizenId);
        var complaintId = complaintService.create(complaintDto);
        complaintDto.setId(complaintId);
        complaintDto.setCitizenId(null);//In response citizenId field must be null
        complaintDto.setCitizenDto(citizenDto);

        var requestEntity = RequestEntity
                .get(URI.create(COMPLAINTS_URI.concat("/" + complaintId)))
                .accept(APPLICATION_JSON)
                .build();

        var responseEntity = testRestTemplate.exchange(requestEntity, ComplaintDto.class);

        assertThat("HttpStatus must be OK(200)", responseEntity.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat("Complaint found must be equals to complaint registered", responseEntity.getBody(), equalTo(complaintDto));
    }

}
