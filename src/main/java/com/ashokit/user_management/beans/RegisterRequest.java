package com.ashokit.user_management.beans;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RegisterRequest {
    String fullName;
    String email;
    long mobileNumber;
    long ssn;
    LocalDate dob;
    String gender;
}
