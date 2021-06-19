package com.martinez.complaints.controller;

import com.martinez.complaints.service.CitizenService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;

import java.net.URI;
import java.util.Map;

import static com.martinez.complaints.util.CitizenDtoFactory.createCitizenDtoWithField;
import static com.martinez.complaints.util.CitizenDtoFactory.createCitizenDtoWithFields;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.MediaType.APPLICATION_JSON;

class CreateCitizenControllerIT extends AbstractIntegrationTest {

    public static final String DOCUMENT_NUMBER_1 = "11111111";
    public static final String DOCUMENT_NUMBER_2 = "11111112";
    public static final String DOCUMENT_NUMBER_3 = "11111113";
    public static final String EMAIL_1 = "jefferson.farfan@gmail.com";
    public static final String EMAIL_2 = "paolo.guerrero@gmail.com";
    public static final String DB_ERROR_MESSAGE_EMAIL_ALREADY_REGISTERED_1 = "Key (email)=(" + EMAIL_1 + ") already exists";
    public static final String DB_ERROR_MESSAGE_EMAIL_ALREADY_REGISTERED_2 = "Key (email)=(" + EMAIL_2 + ") already exists";
    public static final String DB_ERROR_MESSAGE_DOCUMENT_NUMBER_ALREADY_REGISTERED_2 = "Key (document_number)=(" + DOCUMENT_NUMBER_2 + ") already exists";

    @Autowired private CitizenService citizenService;
    @Autowired private TestRestTemplate testRestTemplate;

    @Order(1)
    @DisplayName("""
            Given a valid CitizenDto request \
            When invoke a POST Method on URI "/api/v1/citizens" \
            Then controller response with HttpStatus=CREATED(201) and a Location Header with the CitizenId generated
            """)
    @Test
    void testSuccessCreateCitizen() {
        var requestEntity = RequestEntity
                .post(URI.create(CITIZENS_URI))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .body(createCitizenDtoWithField("documentNumber", DOCUMENT_NUMBER_1));

        var responseEntity = testRestTemplate.exchange(requestEntity, Void.class);

        var expectedCitizenId = citizenService.filterBySearchCriterias("documentNumber=" + DOCUMENT_NUMBER_1)
                                              .get(0)
                                              .getId();
        var expectedLocation = URI.create(CONTEXT + CITIZENS_URI + "/" + expectedCitizenId);

        assertThat("HttpStatus must be CREATED(201)", responseEntity.getStatusCode(), equalTo(HttpStatus.CREATED));
        assertThat("Location URI must be " + expectedLocation, responseEntity.getHeaders()
                                                                             .getLocation(), equalTo(expectedLocation));
    }

    @Order(2)
    @DisplayName("""
            Given a CitizenDto request with an email already registered \
            When invoke a POST Method on URI "/api/v1/citizens" \
            Then controller response with HttpStatus=BAD_REQUEST(400) and an error message "Key (email)=(""" + EMAIL_1 + """
            ) already exists"
            """)
    @Test
    void testEmailAlreadyRegistered() {
        var citizenDto = createCitizenDtoWithField("email", EMAIL_1);
        citizenService.create(citizenDto);

        var requestEntity = RequestEntity
                .post(URI.create(CITIZENS_URI))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .body(createCitizenDtoWithField("email", EMAIL_1));

        var responseEntity = testRestTemplate.exchange(requestEntity, Map.class);

        assertThat("HttpStatus must be BAD_REQUEST(400)", responseEntity.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));
        assertTrue(responseEntity.getBody()
                                 .get("error")
                                 .toString()
                                 .contains(DB_ERROR_MESSAGE_EMAIL_ALREADY_REGISTERED_1),
                "Error message must be " + DB_ERROR_MESSAGE_EMAIL_ALREADY_REGISTERED_1);
    }

    @Order(3)
    @DisplayName("""
            Given a CitizenDto request with an documentNumber already registered \
            When invoke a POST Method on URI "/api/v1/citizens" \
            Then controller response with HttpStatus=BAD_REQUEST(400) and an error message "Key (documentNumber)=(""" + DOCUMENT_NUMBER_2 + """
            ) already exists"
            """)
    @Test
    void testDocumentNumberAlreadyRegistered() {
        var citizenDto = createCitizenDtoWithField("documentNumber", DOCUMENT_NUMBER_2);
        citizenService.create(citizenDto);

        var requestEntity = RequestEntity
                .post(URI.create(CITIZENS_URI))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .body(createCitizenDtoWithField("documentNumber", DOCUMENT_NUMBER_2));

        var responseEntity = testRestTemplate.exchange(requestEntity, Map.class);

        assertThat("HttpStatus must be BAD_REQUEST(400)", responseEntity.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));
        assertTrue(responseEntity.getBody()
                                 .get("error")
                                 .toString()
                                 .contains(DB_ERROR_MESSAGE_DOCUMENT_NUMBER_ALREADY_REGISTERED_2),
                "Error message must be " + DB_ERROR_MESSAGE_DOCUMENT_NUMBER_ALREADY_REGISTERED_2);
    }

    @Order(4)
    @DisplayName("""
            Given a CitizenDto request with email and documentNumber already registered \
            When invoke a POST Method on URI "/api/v1/citizens" \
            Then controller response with HttpStatus=BAD_REQUEST(400) and only an error message with "Key (email)=(""" + EMAIL_2 + """
            ) already exists"
            """)
    @Test
    void testCitizenAlreadyRegistered() {
        var citizenDto = createCitizenDtoWithFields(Map.of("documentNumber", DOCUMENT_NUMBER_3, "email", EMAIL_2));
        citizenService.create(citizenDto);

        var requestEntity = RequestEntity
                .post(URI.create(CITIZENS_URI))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .body(createCitizenDtoWithFields(Map.of("documentNumber", DOCUMENT_NUMBER_3, "email", EMAIL_2)));

        var responseEntity = testRestTemplate.exchange(requestEntity, Map.class);

        assertThat("HttpStatus must be BAD_REQUEST(400)", responseEntity.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));
        assertTrue(responseEntity.getBody()
                                 .get("error")
                                 .toString()
                                 .contains(DB_ERROR_MESSAGE_EMAIL_ALREADY_REGISTERED_2),
                "Error message must be " + DB_ERROR_MESSAGE_EMAIL_ALREADY_REGISTERED_2);
    }

}