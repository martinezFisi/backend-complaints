package com.martinez.complaints.dto;

import lombok.Data;

@Data
public class CitizenDto {

    private Integer id;
    private String email;
    private String password;
    private String documentType;
    private String documentNumber;
    private String firstName;
    private String lastName;
    private int age;

}
