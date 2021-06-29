package com.martinez.complaints.controller;

import com.martinez.complaints.service.CitizenService;
import com.martinez.complaints.service.ComplaintService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public abstract class AbstractIntegrationTest {

    public final String CONTEXT = "/complaints";
    public final String CITIZENS_URI = "/api/v1/citizens";
    public final String COMPLAINTS_URI = "/api/v1/complaints";

    @Autowired protected CitizenService citizenService;
    @Autowired protected ComplaintService complaintService;
    @Autowired protected TestRestTemplate testRestTemplate;

    public static final JdbcDatabaseContainer postgreSQLContainer = new PostgreSQLContainer("postgres:9.4")
            .withInitScript("scripts/complaints-scripts-all-in-one.sql");

    static {
        postgreSQLContainer.start();
    }

    @DynamicPropertySource
    static void postgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

}
