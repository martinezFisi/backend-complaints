package com.martinez.complaints.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Builder
@Data
public class CitizenDto {

    private Long id;
    @NotEmpty(message = "Please provide a email") private String email;
    @NotEmpty(message = "Please provide a password") private String password;
    @NotEmpty(message = "Please provide a documentType") private String documentType;
    @NotEmpty(message = "Please provide a documentNumber") private String documentNumber;
    @NotEmpty(message = "Please provide a firstName") private String firstName;
    @NotEmpty(message = "Please provide a lastName") private String lastName;
    @Min(message = "Please provide a age greater than or equal to 18", value = 18) private Integer age;
}
