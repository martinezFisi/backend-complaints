package com.martinez.complaints.controller;

import com.martinez.complaints.dto.CitizenDto;
import com.martinez.complaints.service.CitizenService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.net.URI;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Testcontainers
@SpringBootTest(webEnvironment = RANDOM_PORT)
class CitizenControllerIT extends AbstractIntegrationTest {

    public static final String CONTEXT = "/complaints";
    public static final String CITIZENS_URI = "/api/v1/citizens";

    @Autowired private CitizenService citizenService;
    @Autowired private TestRestTemplate testRestTemplate;

    @DisplayName("""
            Given a valid CitizenDto request \
            When invoke a POST Method on URI "/api/v1/citizens" \
            Then controller response with HttpStatus=CREATED and a Location Header with the CitizenId generated
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
        assertThat("Location URI must be " + expectedLocation, responseEntity.getHeaders().getLocation(), equalTo(expectedLocation));
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