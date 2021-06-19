package com.martinez.complaints.controller;

import com.martinez.complaints.dto.CitizenDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;

import java.net.URI;
import java.util.List;
import java.util.Map;

import static com.martinez.complaints.util.CitizenDtoFactory.createCitizenDtoWithField;
import static com.martinez.complaints.util.CitizenDtoFactory.createCitizenDtoWithFields;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;

class filterBySearchCriteriasCitizenControllerIT extends AbstractIntegrationTest {

    public static final String DOCUMENT_NUMBER = "33333333";
    public static final String EMAIL = "oreja.flores@gmail.com";

    @Autowired private TestRestTemplate testRestTemplate;

    @Order(1)
    @DisplayName("""
            Given a valid Citizen already registered with documentNumber=""" + DOCUMENT_NUMBER + """
            When invoke a GET Method on URI "/api/v1/citizens?searchCriterias=documentNumber=""" + DOCUMENT_NUMBER + """
            Then controller response with HttpStatus=OK(200) and the Citizen already registered
            """)
    @Test
    void testFilterByDocumentNumber() {
        var citizenDto = createCitizenDtoWithField("documentNumber", DOCUMENT_NUMBER);
        var citizenId = citizenService.create(citizenDto);
        citizenDto.setId(citizenId);

        var requestEntity = RequestEntity
                .get(URI.create(CITIZENS_URI.concat("?searchCriterias=documentNumber=" + DOCUMENT_NUMBER)))
                .accept(APPLICATION_JSON)
                .build();

        var responseEntity = testRestTemplate.exchange(requestEntity, new ParameterizedTypeReference<List<CitizenDto>>() {
        });

        assertThat("HttpStatus must be OK(200)", responseEntity.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat("Citizen found must be equals to citizen registered",
                responseEntity.getBody().stream().anyMatch(citizenDto1 -> citizenDto1.equals(citizenDto)), is(true));
    }

    @Order(2)
    @DisplayName("""
            Given a valid Citizen already registered with email=""" + EMAIL + """
            When invoke a GET Method on URI "/api/v1/citizens?searchCriterias=email=""" + EMAIL + """
            Then controller response with HttpStatus=OK(200) and the Citizen already registered
            """)
    @Test
    void testFilterByEmail() {
        var citizenDto = createCitizenDtoWithFields(Map.of("email", EMAIL));
        var citizenId = citizenService.create(citizenDto);
        citizenDto.setId(citizenId);

        var requestEntity = RequestEntity
                .get(URI.create(CITIZENS_URI.concat("?searchCriterias=email=" + EMAIL)))
                .accept(APPLICATION_JSON)
                .build();

        var responseEntity = testRestTemplate.exchange(requestEntity, new ParameterizedTypeReference<List<CitizenDto>>() {
        });

        assertThat("HttpStatus must be OK(200)", responseEntity.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat("Citizen found must be equals to citizen registered",
                responseEntity.getBody().stream().anyMatch(citizenDto1 -> citizenDto1.equals(citizenDto)), is(true));
    }

}