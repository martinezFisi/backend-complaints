package com.martinez.complaints.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Builder
@Data
public class ComplaintDto {

    private Long id;
    @NotEmpty(message = "Please provide a address") private String address;
    @NotEmpty(message = "Please provide a latitude") private String latitude;
    @NotEmpty(message = "Please provide a longitude") private String longitude;
    @NotEmpty(message = "Please provide a complaintType") private String complaintType;
    @NotEmpty(message = "Please provide a commentary") private String commentary;
    @NotNull(message = "Please provide a citizenId") private Long citizenId;//used in request
    @JsonProperty(value = "citizen") private CitizenDto citizenDto;//used in response

}
