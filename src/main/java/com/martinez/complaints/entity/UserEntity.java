package com.martinez.complaints.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "user")
public class UserEntity {

    @Id @GeneratedValue(strategy = GenerationType.AUTO) @Column(name = "user_id") private int id;
    private String email;
    private String password;
    @Column(name = "document_type") private String documentType;
    @Column(name = "document_number") private String documentNumber;
    @Column(name = "first_name") private String firstName;
    @Column(name = "last_name") private String lastName;
    private int age;


}
