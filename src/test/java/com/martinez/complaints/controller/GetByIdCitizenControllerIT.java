package com.martinez.complaints.controller;

import com.martinez.complaints.dto.CitizenDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;

import java.net.URI;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.http.MediaType.APPLICATION_JSON;

class GetByIdCitizenControllerIT extends AbstractIntegrationTest {

    @Autowired private TestRestTemplate testRestTemplate;

    @Order(1)
    @DisplayName("""
            Given a valid Citizen already registered and {citizenId} is its id \
            When invoke a GET Method on URI "/api/v1/citizens/{citizenId}" \
            Then controller response with HttpStatus=OK(200) and the Citizen already registered
            """)
    @MethodSource("expectedCitizenDto")
    @ParameterizedTest
    void testGetCitizenById(CitizenDto expectedCitizenDto) {
        var citizenId = citizenService.create(expectedCitizenDto);
        expectedCitizenDto.setId(citizenId);

        var requestEntity = RequestEntity
                .get(URI.create(CITIZENS_URI.concat("/"+citizenId)))
                .accept(APPLICATION_JSON)
                .build();

        var responseEntity = testRestTemplate.exchange(requestEntity, CitizenDto.class);

        assertThat("HttpStatus must be OK(200)", responseEntity.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat("Citizen found must be equals to citizen registered", responseEntity.getBody(), equalTo(expectedCitizenDto));
    }

    private static Stream<CitizenDto> expectedCitizenDto() {
        return Stream.of(CitizenDto.builder()
                                   .email("citizen1991@gmail.com")
                                   .password("pass123")
                                   .documentType("L")
                                   .documentNumber("88888888")
                                   .firstName("Angel")
                                   .lastName("Perez")
                                   .age(25)
                                   .build());
    }
}