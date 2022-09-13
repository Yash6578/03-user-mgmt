package com.ashokit.user_management.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "USER_MASTER")
@Data
public class UserMaster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "FULL_NAME")
    String fullName;

    @Column(name = "EMAIL", unique = true)
    String email;

    @Column(name = "MOBILE_NUMBER")
    long mobileNumber;

    @Column(name = "SSN")
    long ssn;

    @Column(name = "DOB")
    LocalDate dob;

    @Column(name = "GENDER")
    Character gender;

    @Column(name = "PASSWORD")
    String password;

    @Column(name = "STATUS")
    String status;

    @Column(name = "CREATED_BY")
    String createdBy;

    @Column(name = "UPDATED_BY")
    String updatedBy;


    @Column(name = "CREATED_DATE", updatable = false)
    @CreationTimestamp
    LocalDateTime createdDate;

    @Column(name = "UPDATED_DATE", insertable = false)
    @UpdateTimestamp
    LocalDateTime updatedDate;
}
