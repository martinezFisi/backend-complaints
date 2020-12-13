package com.martinez.complaints.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "user_complaint")
public class UserComplaintEntity {

    @Id @GeneratedValue(strategy = GenerationType.AUTO) @Column(name = "complaint_id") private int id;
    private String address;
    private String latitude;
    private String longitude;
    @Column(name = "complaint_type") private String complaintType;
    private String commentary;
    @Column(name = "user_id") private int userId;

}
