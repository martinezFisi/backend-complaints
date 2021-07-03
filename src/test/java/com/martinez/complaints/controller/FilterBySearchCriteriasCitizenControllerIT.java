package com.martinez.complaints.controller;

import com.martinez.complaints.dto.CitizenDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;

import java.net.URI;
import java.util.List;

import static com.martinez.complaints.util.CitizenDtoFactory.createCitizenDtoWithField;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.MediaType.APPLICATION_JSON;

class FilterBySearchCriteriasCitizenControllerIT extends AbstractIntegrationTest {

    private static final String DOCUMENT_NUMBER = "33333333";
    private static final String EMAIL = "oreja.flores@gmail.com";
    private static final String DOCUMENT_TYPE = "DNI";
    private static final String FIRST_NAME = "Edison";
    private static final String LAST_NAME = "Flores";
    private static final int AGE = 200;//Value out of range from CitizenDtoFactory

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
        assertThat("Citizen found must be equals to citizen registered", responseEntity.getBody(), equalTo(List.of(citizenDto)));
    }

    @DisplayName("""
            Given a valid Citizen already registered with email=""" + EMAIL + """
            When invoke a GET Method on URI "/api/v1/citizens?searchCriterias=email=""" + EMAIL + """
            Then controller response with HttpStatus=OK(200) and the Citizen already registered
            """)
    @Test
    void testFilterByEmail() {
        var citizenDto = createCitizenDtoWithField("email", EMAIL);
        var citizenId = citizenService.create(citizenDto);
        citizenDto.setId(citizenId);

        var requestEntity = RequestEntity
                .get(URI.create(CITIZENS_URI.concat("?searchCriterias=email=" + EMAIL)))
                .accept(APPLICATION_JSON)
                .build();

        var responseEntity = testRestTemplate.exchange(requestEntity, new ParameterizedTypeReference<List<CitizenDto>>() {
        });

        assertThat("HttpStatus must be OK(200)", responseEntity.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat("Citizen found must be equals to citizen registered", responseEntity.getBody(), equalTo(List.of(citizenDto)));
    }

    @DisplayName("""
            Given a two Citizens already registered with documentType=""" + DOCUMENT_TYPE + """
            When invoke a GET Method on URI "/api/v1/citizens?searchCriterias=documentType=""" + DOCUMENT_TYPE + """
            Then controller response with HttpStatus=OK(200) and the list with the two Citizens already registered
            """)
    @Test
    void testFilterByDocumentType() {
        var citizenDto1 = createCitizenDtoWithField("documentType", DOCUMENT_TYPE);
        var citizenDto2 = createCitizenDtoWithField("documentType", DOCUMENT_TYPE);
        var citizenId1 = citizenService.create(citizenDto1);
        citizenDto1.setId(citizenId1);
        var citizenId2 = citizenService.create(citizenDto2);
        citizenDto2.setId(citizenId2);

        var citizensAlreadyRegistered = List.of(citizenDto1, citizenDto2);

        var requestEntity = RequestEntity
                .get(URI.create(CITIZENS_URI.concat("?searchCriterias=documentType=" + DOCUMENT_TYPE)))
                .accept(APPLICATION_JSON)
                .build();

        var responseEntity = testRestTemplate.exchange(requestEntity, new ParameterizedTypeReference<List<CitizenDto>>() {
        });

        assertThat("HttpStatus must be OK(200)", responseEntity.getStatusCode(), equalTo(HttpStatus.OK));
        assertTrue(responseEntity.getBody().containsAll(citizensAlreadyRegistered),
                "Citizens found must be equals to citizens already registered");
    }

    @DisplayName("""
            Given a two Citizens already registered with firstName=""" + FIRST_NAME + """
            When invoke a GET Method on URI "/api/v1/citizens?searchCriterias=firstName=""" + FIRST_NAME + """
            Then controller response with HttpStatus=OK(200) and the list with the two Citizens already registered
            """)
    @Test
    void testFilterByFirstName() {
        var citizenDto1 = createCitizenDtoWithField("firstName", FIRST_NAME);
        var citizenDto2 = createCitizenDtoWithField("firstName", FIRST_NAME);
        var citizenId1 = citizenService.create(citizenDto1);
        citizenDto1.setId(citizenId1);
        var citizenId2 = citizenService.create(citizenDto2);
        citizenDto2.setId(citizenId2);

        var requestEntity = RequestEntity
                .get(URI.create(CITIZENS_URI.concat("?searchCriterias=firstName=" + FIRST_NAME)))
                .accept(APPLICATION_JSON)
                .build();

        var responseEntity = testRestTemplate.exchange(requestEntity, new ParameterizedTypeReference<List<CitizenDto>>() {
        });

        assertThat("HttpStatus must be OK(200)", responseEntity.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat("Citizen found must be equals to citizen registered", responseEntity.getBody(), equalTo(List.of(citizenDto1, citizenDto2)));
    }

    @DisplayName("""
            Given a two Citizens already registered with lastName=""" + LAST_NAME + """
            When invoke a GET Method on URI "/api/v1/citizens?searchCriterias=lastName=""" + LAST_NAME + """
            Then controller response with HttpStatus=OK(200) and the list with the two Citizens already registered
            """)
    @Test
    void testFilterByLastName() {
        var citizenDto1 = createCitizenDtoWithField("lastName", LAST_NAME);
        var citizenDto2 = createCitizenDtoWithField("lastName", LAST_NAME);
        var citizenId1 = citizenService.create(citizenDto1);
        citizenDto1.setId(citizenId1);
        var citizenId2 = citizenService.create(citizenDto2);
        citizenDto2.setId(citizenId2);

        var requestEntity = RequestEntity
                .get(URI.create(CITIZENS_URI.concat("?searchCriterias=lastName=" + LAST_NAME)))
                .accept(APPLICATION_JSON)
                .build();

        var responseEntity = testRestTemplate.exchange(requestEntity, new ParameterizedTypeReference<List<CitizenDto>>() {
        });

        assertThat("HttpStatus must be OK(200)", responseEntity.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat("Citizen found must be equals to citizen registered", responseEntity.getBody(), equalTo(List.of(citizenDto1, citizenDto2)));
    }

    @DisplayName("""
            Given a two Citizens already registered with age=""" + AGE + """
            When invoke a GET Method on URI "/api/v1/citizens?searchCriterias=age=""" + AGE + """
            Then controller response with HttpStatus=OK(200) and the list with the two Citizens already registered
            """)
    @Test
    void testFilterByAge() {
        var citizenDto1 = createCitizenDtoWithField("age", AGE);
        var citizenDto2 = createCitizenDtoWithField("age", AGE);
        var citizenId1 = citizenService.create(citizenDto1);
        citizenDto1.setId(citizenId1);
        var citizenId2 = citizenService.create(citizenDto2);
        citizenDto2.setId(citizenId2);

        var requestEntity = RequestEntity
                .get(URI.create(CITIZENS_URI.concat("?searchCriterias=age=" + AGE)))
                .accept(APPLICATION_JSON)
                .build();

        var responseEntity = testRestTemplate.exchange(requestEntity, new ParameterizedTypeReference<List<CitizenDto>>() {
        });

        assertThat("HttpStatus must be OK(200)", responseEntity.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat("Citizen found must be equals to citizen registered", responseEntity.getBody(), equalTo(List.of(citizenDto1, citizenDto2)));
    }
}