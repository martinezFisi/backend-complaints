package com.martinez.complaints.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ComplaintDto {

    private int id;
    private String address;
    private String latitude;
    private String longitude;
    private String complaintType;
    private String commentary;
    @JsonProperty(value = "citizen") private CitizenDto citizenDto;

}
