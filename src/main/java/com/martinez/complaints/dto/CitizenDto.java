package com.martinez.complaints.dto;

import com.martinez.complaints.util.DocumentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class CitizenDto {

    private Long id;
    @NotEmpty(message = "{javax.validation.constraints.NotEmpty.email.message}") private String email;
    private String phoneNumber;
    private String password;
    private DocumentType documentType;
    private String documentNumber;
    @NotEmpty(message = "{javax.validation.constraints.NotEmpty.firstName.message}") private String firstName;
    @NotEmpty(message = "{javax.validation.constraints.NotEmpty.lastName.message}") private String lastName;
    private Integer age;

}
