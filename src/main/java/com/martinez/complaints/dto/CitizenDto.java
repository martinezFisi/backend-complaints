package com.martinez.complaints.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CitizenDto {

    private Long id;
    private String email;
    private String password;
    private String documentType;
    private String documentNumber;
    private String firstName;
    private String lastName;
    private int age;

}
