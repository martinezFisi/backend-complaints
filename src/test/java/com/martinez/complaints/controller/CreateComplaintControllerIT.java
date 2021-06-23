package com.martinez.complaints.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CreateComplaintControllerIT extends AbstractIntegrationTest {

    @DisplayName("""    
            Given a valid ComplaintDto request associated with a citizen already registered  \
            When invoke a POST Method on URI "/api/v1/complaints" \
            Then controller response with HttpStatus=CREATED(201) and a Location Header with the ComplaintId generated
            """)
    @Test
    void testSuccessCreateComplaint() {

    }

}
