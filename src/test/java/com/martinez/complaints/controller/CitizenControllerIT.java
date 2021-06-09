package com.martinez.complaints.controller;

import com.martinez.complaints.dto.CitizenDto;
import com.martinez.complaints.service.CitizenService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.net.URI;
import java.util.Map;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Testcontainers
@SpringBootTest(webEnvironment = RANDOM_PORT)
class CitizenControllerIT extends AbstractIntegrationTest {

    public static final String CONTEXT = "/complaints";
    public static final String CITIZENS_URI = "/api/v1/citizens";
    public static final String DB_ERROR_MESSAGE_EMAIL_ALREADY_REGISTERED = "Key (email)=(citizen1990@gmail.com) already exists";
    public static final String DB_ERROR_MESSAGE_DOCUMENT_NUMBER_ALREADY_REGISTERED = "Key (document_number)=(7777777) already exists";

    @Autowired private CitizenService citizenService;
    @Autowired private TestRestTemplate testRestTemplate;

    @Order(1)
    @DisplayName("""
            Given a valid CitizenDto request \
            When invoke a POST Method on URI "/api/v1/citizens" \
            Then controller response with HttpStatus=CREATED(201) and a Location Header with the CitizenId generated
            """)
    @MethodSource("reqCitizenDto")
    @ParameterizedTest
    void testSuccessCreateCitizen(CitizenDto reqCitizenDto) {
        var requestEntity = RequestEntity
                .post(URI.create(CITIZENS_URI))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .body(reqCitizenDto);

        var responseEntity = testRestTemplate.exchange(requestEntity, Void.class);

        var expectedCitizenId = citizenService.filterBySearchCriterias("documentNumber=7777777").get(0).getId();
        var expectedLocation = URI.create(CONTEXT + CITIZENS_URI + "/" + expectedCitizenId);

        assertThat("HttpStatus must be CREATED(201)", responseEntity.getStatusCode(), equalTo(HttpStatus.CREATED));
        assertThat("Location URI must be " + expectedLocation, responseEntity.getHeaders()
                                                                             .getLocation(), equalTo(expectedLocation));
    }

    @Order(2)
    @DisplayName("""
              Given a CitizenDto request with an email already registered \
              When invoke a POST Method on URI "/api/v1/citizens" \
              Then controller response with HttpStatus=BAD_REQUEST(400) and an error message "Key (email)=(citizen1990@gmail.com) already exists"
            """)
    @MethodSource("reqCitizenDto")
    @ParameterizedTest
    void testEmailAlreadyRegistered(CitizenDto reqCitizenDto) {
        var requestEntity = RequestEntity
                .post(URI.create(CITIZENS_URI))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .body(reqCitizenDto);

        var responseEntity = testRestTemplate.exchange(requestEntity, Map.class);

        assertThat("HttpStatus must be BAD_REQUEST(400)", responseEntity.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));
        assertTrue(responseEntity.getBody().get("errors").toString().contains(DB_ERROR_MESSAGE_EMAIL_ALREADY_REGISTERED),
                "Errors array must contain the message: " + DB_ERROR_MESSAGE_EMAIL_ALREADY_REGISTERED);
    }

    @Order(3)
    @DisplayName("""
              Given a CitizenDto request with an documentNumber already registered \
              When invoke a POST Method on URI "/api/v1/citizens" \
              Then controller response with HttpStatus=BAD_REQUEST(400) and an error message "Key (documentNumber)=(7777777) already exists"
            """)
    @MethodSource("reqCitizenDto")
    @ParameterizedTest
    void testDocumentNumberAlreadyRegistered(CitizenDto reqCitizenDto) {
        reqCitizenDto.setEmail("test@test.com");//change email for wont get error

        var requestEntity = RequestEntity
                .post(URI.create(CITIZENS_URI))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .body(reqCitizenDto);

        var responseEntity = testRestTemplate.exchange(requestEntity, Map.class);

        assertThat("HttpStatus must be BAD_REQUEST(400)", responseEntity.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));
        assertTrue(responseEntity.getBody().get("errors").toString().contains(DB_ERROR_MESSAGE_DOCUMENT_NUMBER_ALREADY_REGISTERED),
                "Errors array must contain the message: " + DB_ERROR_MESSAGE_DOCUMENT_NUMBER_ALREADY_REGISTERED);
    }

    @Order(4)
    @DisplayName("""
              Given a CitizenDto request with email and documentNumber already registered \
              When invoke a POST Method on URI "/api/v1/citizens" \
              Then controller response with HttpStatus=BAD_REQUEST(400) and only an error message with "Key (email)=(citizen1990@gmail.com) already exists"
            """)
    @MethodSource("reqCitizenDto")
    @ParameterizedTest
    void testCitizenAlreadyRegistered(CitizenDto reqCitizenDto) {
        var requestEntity = RequestEntity
                .post(URI.create(CITIZENS_URI))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .body(reqCitizenDto);

        var responseEntity = testRestTemplate.exchange(requestEntity, Map.class);

        assertThat("HttpStatus must be BAD_REQUEST(400)", responseEntity.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));
        assertTrue(responseEntity.getBody().get("errors").toString().contains(DB_ERROR_MESSAGE_EMAIL_ALREADY_REGISTERED),
                "Errors array must contain the message: " + DB_ERROR_MESSAGE_EMAIL_ALREADY_REGISTERED);
        assertFalse(responseEntity.getBody().get("errors").toString().contains(DB_ERROR_MESSAGE_DOCUMENT_NUMBER_ALREADY_REGISTERED),
                "Errors array must contain the message: " + DB_ERROR_MESSAGE_DOCUMENT_NUMBER_ALREADY_REGISTERED);
    }

    private static Stream<CitizenDto> reqCitizenDto() {
        return Stream.of(CitizenDto.builder()
                                   .email("citizen1990@gmail.com")
                                   .password("pass123")
                                   .documentType("L")
                                   .documentNumber("7777777")
                                   .firstName("Juan")
                                   .lastName("Garcia")
                                   .age(20)
                                   .build());
    }
}