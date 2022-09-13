package com.ashokit.user_management.beans;

import lombok.Data;

import java.time.LocalDate;

@Data
public class User {
    String fullName;
    String email;
    long mobileNumber;
    long ssn;
    LocalDate dob;
    Character gender;
}
