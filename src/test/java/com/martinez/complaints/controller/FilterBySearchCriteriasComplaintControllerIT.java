package com.martinez.complaints.controller;

import com.martinez.complaints.dto.CitizenDto;
import com.martinez.complaints.dto.ComplaintDto;
import com.martinez.complaints.util.ComplaintType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;

import java.net.URI;
import java.util.List;
import java.util.Map;

import static com.martinez.complaints.util.CitizenDtoFactory.createCitizenDto;
import static com.martinez.complaints.util.ComplaintDtoFactory.ADDRESS;
import static com.martinez.complaints.util.ComplaintDtoFactory.CITIZEN_ID;
import static com.martinez.complaints.util.ComplaintDtoFactory.COMMENTARY;
import static com.martinez.complaints.util.ComplaintDtoFactory.COMPLAINT_TYPE;
import static com.martinez.complaints.util.ComplaintDtoFactory.LATITUDE;
import static com.martinez.complaints.util.ComplaintDtoFactory.LONGITUDE;
import static com.martinez.complaints.util.ComplaintDtoFactory.createComplaintDtoWithFields;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.MediaType.APPLICATION_JSON;

class FilterBySearchCriteriasComplaintControllerIT extends AbstractIntegrationTest {

    private static final String ALL_ADDRESS_VALUE = "Amadeo Avogadro";
    private static final String FILTER_ADDRESS_VALUE = "Avogadro";
    private static final String ALL_COMMENTARY_VALUE = "Robbery by 2 men";
    private static final String FILTER_COMMENTARY_VALUE = "2+men";// encoding the space with +
    private static final String COMPLAINT_TYPE_VALUE = "ROBBERY_TO_VEHICLE";
    private static final double LATITUDE_VALUE = 25.112312;
    private static final double FILTER_LATITUDE_VALUE = 20.00;
    private static final double LONGITUDE_VALUE = 35.123122;
    private static final double FILTER_LONGITUDE_VALUE = 40.8765251212;

    @DisplayName("""
            Given a valid Complaint already registered \
            When invoke a GET Method on URI "/api/v1/complaints?searchCriterias=address:""" + FILTER_ADDRESS_VALUE + """
            Then controller response with HttpStatus=OK(200) and the Complaint already registered
            """)
    @Test
    void testFilterLikeByAddress() {
        //given
        var citizenDto = createCitizenDto();
        var citizenId = citizenService.create(citizenDto);
        citizenDto.setId(citizenId);

        var complaintDto = createComplaintDtoWithFields(Map.of(CITIZEN_ID, citizenId, ADDRESS, ALL_ADDRESS_VALUE));
        var complaintId = complaintService.create(complaintDto);
        complaintDto.setId(complaintId);
        complaintDto.setCitizenId(null);//In response citizenId must be null
        complaintDto.setCitizenDto(citizenDto);

        //when
        var requestEntity = RequestEntity
                .get(URI.create(COMPLAINTS_URI.concat("?searchCriterias=address:" + FILTER_ADDRESS_VALUE)))
                .accept(APPLICATION_JSON)
                .build();

        var responseEntity = testRestTemplate.exchange(requestEntity, new ParameterizedTypeReference<List<ComplaintDto>>() {
        });

        //then
        assertThat("HttpStatus must be OK(200)", responseEntity.getStatusCode(), equalTo(HttpStatus.OK));
        assertTrue(responseEntity.getBody().contains(complaintDto),
                "Citizen list found must contains to citizen registered");
    }

    @DisplayName("""
            Given a valid Complaint already registered \
            When invoke a GET Method on URI "/api/v1/complaints?searchCriterias=commentary:""" + FILTER_COMMENTARY_VALUE + """
            Then controller response with HttpStatus=OK(200) and the Complaint already registered
            """)
    @Test
    void testFilterLikeByCommentary() {
        //given
        var citizenDto = createCitizenDto();
        var citizenId = citizenService.create(citizenDto);
        citizenDto.setId(citizenId);

        var complaintDto = createComplaintDtoWithFields(Map.of(CITIZEN_ID, citizenId, COMMENTARY, ALL_COMMENTARY_VALUE));
        var complaintId = complaintService.create(complaintDto);
        complaintDto.setId(complaintId);
        complaintDto.setCitizenId(null);//In response citizenId must be null
        complaintDto.setCitizenDto(citizenDto);

        //when
        var requestEntity = RequestEntity
                .get(URI.create(COMPLAINTS_URI.concat("?searchCriterias=commentary:" + FILTER_COMMENTARY_VALUE)))
                .accept(APPLICATION_JSON)
                .build();

        var responseEntity = testRestTemplate.exchange(requestEntity, new ParameterizedTypeReference<List<ComplaintDto>>() {
        });

        //then
        assertThat("HttpStatus must be OK(200)", responseEntity.getStatusCode(), equalTo(HttpStatus.OK));
        assertTrue(responseEntity.getBody().contains(complaintDto),
                "Citizen list found must contains to citizen registered");
    }

    @DisplayName("""
            Given a valid Complaint already registered \
            When invoke a GET Method on URI "/api/v1/complaints?searchCriterias=complaintType=""" + COMPLAINT_TYPE_VALUE + """
            Then controller response with HttpStatus=OK(200) and the Complaint already registered
            """)
    @Test
    void testFilterByComplaintType() {
        //given
        var citizenDto = createCitizenDto();
        var citizenId = citizenService.create(citizenDto);
        citizenDto.setId(citizenId);

        var complaintDto = createComplaintDtoWithFields(Map.of(CITIZEN_ID, citizenId, COMPLAINT_TYPE, COMPLAINT_TYPE_VALUE));
        var complaintId = complaintService.create(complaintDto);
        complaintDto.setId(complaintId);
        complaintDto.setCitizenId(null);//In response citizenId must be null
        complaintDto.setCitizenDto(citizenDto);

        //when
        var requestEntity = RequestEntity
                .get(URI.create(COMPLAINTS_URI.concat("?searchCriterias=complaintType=" + COMPLAINT_TYPE_VALUE)))
                .accept(APPLICATION_JSON)
                .build();

        var responseEntity = testRestTemplate.exchange(requestEntity, new ParameterizedTypeReference<List<ComplaintDto>>() {
        });

        //then
        assertThat("HttpStatus must be OK(200)", responseEntity.getStatusCode(), equalTo(HttpStatus.OK));
        assertTrue(responseEntity.getBody().contains(complaintDto),
                "Citizen list found must contains to citizen registered");
    }

    @DisplayName("""
            Given a valid Complaint already registered \
            When invoke a GET Method on URI "/api/v1/complaints?searchCriterias=latitude>""" + FILTER_LATITUDE_VALUE + """
            ,longitude<""" + FILTER_LONGITUDE_VALUE + """
            Then controller response with HttpStatus=OK(200) and the Complaint already registered
            """)
    @Test
    void testFilterByLatitudeAndLongitude() {
        //given
        var citizenDto = createCitizenDto();
        var citizenId = citizenService.create(citizenDto);
        citizenDto.setId(citizenId);

        var complaintDto = createComplaintDtoWithFields(Map.of(CITIZEN_ID, citizenId, LATITUDE, LATITUDE_VALUE, LONGITUDE, LONGITUDE_VALUE));
        var complaintId = complaintService.create(complaintDto);
        complaintDto.setId(complaintId);
        complaintDto.setCitizenId(null);//In response citizenId must be null
        complaintDto.setCitizenDto(citizenDto);

        //when
        var requestEntity = RequestEntity
                .get(URI.create(COMPLAINTS_URI.concat("?searchCriterias=latitude%3E" + FILTER_LATITUDE_VALUE + ",latitude%3C" + FILTER_LONGITUDE_VALUE)))
                .accept(APPLICATION_JSON)
                .build();

        var responseEntity = testRestTemplate.exchange(requestEntity, new ParameterizedTypeReference<List<ComplaintDto>>() {
        });

        //then
        assertThat("HttpStatus must be OK(200)", responseEntity.getStatusCode(), equalTo(HttpStatus.OK));
        assertTrue(responseEntity.getBody().contains(complaintDto),
                "Citizen list found must contains to citizen registered");
    }

}
