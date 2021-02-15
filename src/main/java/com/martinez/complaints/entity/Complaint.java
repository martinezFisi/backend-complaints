package com.martinez.complaints.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Builder
@Data
@Entity(name = "complaint")
@Table(name = "complaint", schema = "complaints")
public class Complaint {

    @Id @Column(name = "complaint_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COMPLAINT_SEQ")
    @SequenceGenerator(name = "COMPLAINT_SEQ", allocationSize = 5)
    private int id;
    private String address;
    private String latitude;
    private String longitude;
    @Column(name = "complaint_type") private String complaintType;
    private String commentary;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "citizen_id")
    private Citizen citizen;

}
