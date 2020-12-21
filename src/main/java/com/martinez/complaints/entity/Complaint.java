package com.martinez.complaints.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "complaint")
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
    @Column(name = "citizen_id") private int citizenId;

}
