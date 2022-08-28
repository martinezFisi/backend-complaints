package com.martinez.complaints.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.martinez.complaints.util.ComplaintType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class ComplaintDto {

    private Long id;
    @NotEmpty(message = "{javax.validation.constraints.NotEmpty.address.message}") private String address;
    @NotNull(message = "{javax.validation.constraints.NotEmpty.latitude.message}") private Double latitude;
    @NotNull(message = "{javax.validation.constraints.NotEmpty.longitude.message}") private Double longitude;
    @NotNull(message = "{javax.validation.constraints.NotEmpty.complaintType.message}") private ComplaintType complaintType;
    @NotEmpty(message = "{javax.validation.constraints.NotEmpty.postalCode.message}") private String postalCode;
    @NotEmpty(message = "{javax.validation.constraints.NotEmpty.locality.message}") private String locality;
    @NotEmpty(message = "{javax.validation.constraints.NotEmpty.country.message}") private String country;
    @NotEmpty(message = "{javax.validation.constraints.NotEmpty.commentary.message}") private String commentary;
    @EqualsAndHashCode.Exclude private String creationTime;
    @NotNull(message = "{javax.validation.constraints.NotEmpty.citizenId.message}") private Long citizenId;//used in request
    @JsonProperty(value = "citizen") private CitizenDto citizenDto;//used in response

}
