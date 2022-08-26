package com.martinez.complaints.dto;

import com.martinez.complaints.util.DocumentType;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Builder
@Data
public class CitizenDto {

    private Long id;
    @NotEmpty(message = "Please provide a email") private String email;
    private String phoneNumber;
    private String password;
    private DocumentType documentType;
    private String documentNumber;
    @NotEmpty(message = "Please provide a firstName") private String firstName;
    @NotEmpty(message = "Please provide a lastName") private String lastName;
    private Integer age;
}
