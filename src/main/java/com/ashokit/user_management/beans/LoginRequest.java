package com.ashokit.user_management.beans;


import lombok.Data;

@Data
public class LoginRequest {
    String userName;
    String password;
}
