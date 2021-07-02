package com.martinez.complaints.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "complaint")
@Table(name = "complaint", schema = "complaints_schema")
public class Complaint {

    @Id @Column(name = "complaint_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COMPLAINT_SEQ")
    @SequenceGenerator(name = "COMPLAINT_SEQ", allocationSize = 5)
    private Long id;
    private String address;
    private Double latitude;
    private Double longitude;
    @Column(name = "complaint_type") private String complaintType;
    private String commentary;
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "citizen_id")
    private Citizen citizen;

}
