package com.martinez.complaints.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
class CitizenControllerIT {

    @Container
    public static final JdbcDatabaseContainer postgreSQLContainer = (JdbcDatabaseContainer) new PostgreSQLContainer("postgres:9.4")
            .withInitScript("scripts/complaints-scripts-all-in-one.sql")
            .withExposedPorts(5432);

    public static final String CITIZEN_BASE_URI = "/api/v1/citizens";

    @Autowired private MockMvc mockMvc;

    @DynamicPropertySource
    static void postgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @DisplayName("""
            Given a valid CitizenDto request \
            When invoke a POST Method on URI "/api/v1/citizens" \
            Then controller response a CitizenDto with Id generated
            """)
    @MethodSource("citizenDtoReqAndRes")
    @ParameterizedTest
    void testSuccessCreateCitizen(String reqCitizenDto, String resCitizenDto) throws Exception {
        mockMvc.perform(post(CITIZEN_BASE_URI)
                .contentType(APPLICATION_JSON)
                .content(reqCitizenDto))
               .andExpect(content().contentType(APPLICATION_JSON))
               .andExpect(status().isCreated())
               .andExpect(header().string("location", CITIZEN_BASE_URI.concat("/1")))
               .andExpect(content().json(resCitizenDto));
    }

    private static Stream<Arguments> citizenDtoReqAndRes() throws JSONException {
        var reqMap = Map.of("email", "citizen1990@gmail.com",
                "password", "citizen1990",
                "documentType", "L",
                "documentNumber", "7777777",
                "firstName", "Juan",
                "lastName", "Garcia",
                "age", 20);
        var reqCitizenDto = new JSONObject(reqMap);

        var resMap = new HashMap<>(reqMap);
        resMap.put("id", 1);
        var resCitizenDto = new JSONObject(resMap);

        return Stream.of(
                Arguments.of(reqCitizenDto.toString(), resCitizenDto.toString())
        );
    }
}