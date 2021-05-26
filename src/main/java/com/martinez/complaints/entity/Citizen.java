package com.martinez.complaints.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "citizen")
@Table(name = "citizen", schema = "complaints_schema")
public class Citizen {

    @Id @Column(name = "citizen_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CITIZEN_SEQ")
    @SequenceGenerator(name = "CITIZEN_SEQ", allocationSize = 5)
    private Long id;
    private String email;
    private String password;
    @Column(name = "document_type") private String documentType;
    @Column(name = "document_number") private String documentNumber;
    @Column(name = "first_name") private String firstName;
    @Column(name = "last_name") private String lastName;
    private Integer age;


}
