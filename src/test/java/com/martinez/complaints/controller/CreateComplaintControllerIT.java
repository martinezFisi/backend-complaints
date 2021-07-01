package com.martinez.complaints.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;

import java.net.URI;
import java.util.Map;

import static com.martinez.complaints.util.CitizenDtoFactory.createCitizenDto;
import static com.martinez.complaints.util.ComplaintDtoFactory.CITIZEN_ID;
import static com.martinez.complaints.util.ComplaintDtoFactory.LATITUDE;
import static com.martinez.complaints.util.ComplaintDtoFactory.LONGITUDE;
import static com.martinez.complaints.util.ComplaintDtoFactory.createComplaintDtoWithField;
import static com.martinez.complaints.util.ComplaintDtoFactory.createComplaintDtoWithFields;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.MediaType.APPLICATION_JSON;

class CreateComplaintControllerIT extends AbstractIntegrationTest {

    public static final double LATITUDE_1 = 20D;
    public static final double LONGITUDE_1 = 40D;
    public static final long CITIZEN_NOT_REGISTERED_ID = 499L;
    public static final String DB_ERROR_MESSAGE_CITIZEN_NOT_REGISTERED = "Key (citizen_id)=(" + CITIZEN_NOT_REGISTERED_ID + ") is not present in table \"citizen\"";

    @DisplayName("""    
            Given a valid ComplaintDto request associated with a citizen already registered  \
            When invoke a POST Method on URI "/api/v1/complaints" \
            Then controller response with HttpStatus=CREATED(201) and a Location Header with the ComplaintId generated
            """)
    @Test
    void testSuccessCreateComplaint() {
        var citizenDto = createCitizenDto();
        var citizenId = citizenService.create(citizenDto);
        var complaintDto = createComplaintDtoWithFields(Map.of(CITIZEN_ID, citizenId, LATITUDE, LATITUDE_1, LONGITUDE, LONGITUDE_1));

        var requestEntity = RequestEntity
                .post(URI.create(COMPLAINTS_URI))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .body(complaintDto);

        var responseEntity = testRestTemplate.exchange(requestEntity, Void.class);

        var expectedComplaintId = complaintService.filterBySearchCriterias(",latitude=" + LATITUDE_1 + ",longitude=" + LONGITUDE_1).get(0).getId();
        var expectedLocation = URI.create(CONTEXT + COMPLAINTS_URI + "/" + expectedComplaintId);

        assertThat("HttpStatus must be CREATED(201)", responseEntity.getStatusCode(), equalTo(HttpStatus.CREATED));
        assertThat("Location URI must be " + expectedLocation, responseEntity.getHeaders()
                                                                             .getLocation(), equalTo(expectedLocation));
    }

    @DisplayName("""    
            Given a ComplaintDto request associated with a citizen not registered  \
            When invoke a POST Method on URI "/api/v1/complaints" \
            Then controller response with HttpStatus=BAD_REQUEST(400) and an error message Key (citizen_id)=(""" + CITIZEN_NOT_REGISTERED_ID + """
            ) is not present in table "citizen"
            """)
    @Test
    void testCreateComplaintWithoutCitizenCreated() {
        var complaintDto = createComplaintDtoWithField(CITIZEN_ID, CITIZEN_NOT_REGISTERED_ID);

        var requestEntity = RequestEntity
                .post(URI.create(COMPLAINTS_URI))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .body(complaintDto);

        var responseEntity = testRestTemplate.exchange(requestEntity, Map.class);

        assertThat("HttpStatus must be BAD_REQUEST(400)", responseEntity.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));
        assertTrue(responseEntity.getBody()
                                 .get("error")
                                 .toString()
                                 .contains(DB_ERROR_MESSAGE_CITIZEN_NOT_REGISTERED),
                "Error message must be " + DB_ERROR_MESSAGE_CITIZEN_NOT_REGISTERED);
    }

}
