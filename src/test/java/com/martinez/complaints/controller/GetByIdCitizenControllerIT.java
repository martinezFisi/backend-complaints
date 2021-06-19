package com.martinez.complaints.controller;

import com.martinez.complaints.dto.CitizenDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;

import java.net.URI;
import java.util.Map;

import static com.martinez.complaints.util.CitizenDtoFactory.createCitizenDto;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.http.MediaType.APPLICATION_JSON;

class GetByIdCitizenControllerIT extends AbstractIntegrationTest {

    public static final long CITIZEN_ID_NOT_REGISTERED = 999;
    public static final String ERROR_MESSAGE_CITIZEN_ID_NOT_FOUND = "Citizen with id [" + CITIZEN_ID_NOT_REGISTERED + "] not found";

    @Autowired private TestRestTemplate testRestTemplate;

    @DisplayName("""
            Given a valid Citizen already registered and {citizenId} is its id \
            When invoke a GET Method on URI "/api/v1/citizens/{citizenId}" \
            Then controller response with HttpStatus=OK(200) and the Citizen already registered
            """)
    @Test
    void testGetCitizenById() {
        var citizenDto = createCitizenDto();
        var citizenId = citizenService.create(citizenDto);
        citizenDto.setId(citizenId);

        var requestEntity = RequestEntity
                .get(URI.create(CITIZENS_URI.concat("/" + citizenId)))
                .accept(APPLICATION_JSON)
                .build();

        var responseEntity = testRestTemplate.exchange(requestEntity, CitizenDto.class);

        assertThat("HttpStatus must be OK(200)", responseEntity.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat("Citizen found must be equals to citizen registered", responseEntity.getBody(), equalTo(citizenDto));
    }

    @DisplayName("""
            Given a citizenId not registered \
            When invoke a GET Method on URI "/api/v1/citizens/{citizenId}" \
            Then controller response with HttpStatus=NOT_FOUND(404) and the error message "Citizen with id [{citizenId}] not found"
            """)
    @ValueSource(longs = {CITIZEN_ID_NOT_REGISTERED})
    @ParameterizedTest
    void testCitizenIdNotFound(Long citizenIdNotRegistered) {
        var requestEntity = RequestEntity
                .get(URI.create(CITIZENS_URI.concat("/" + citizenIdNotRegistered)))
                .accept(APPLICATION_JSON)
                .build();

        var responseEntity = testRestTemplate.exchange(requestEntity, Map.class);

        assertThat("HttpStatus must be NOT_FOUND(404)", responseEntity.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));
        assertThat("Error message must be " + ERROR_MESSAGE_CITIZEN_ID_NOT_FOUND,
                responseEntity.getBody().get("error"), equalTo(ERROR_MESSAGE_CITIZEN_ID_NOT_FOUND));
    }

}