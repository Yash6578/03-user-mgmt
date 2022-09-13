package com.ashokit.user_management.beans;

import lombok.Data;

@Data
public class ActivateRequest {
    String email;
    String tempPassword;
    String newPassword;
    String confirmPassword;
}
