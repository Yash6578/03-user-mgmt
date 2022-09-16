package com.ashokit.user_management.service;

import com.ashokit.user_management.beans.ActivateRequest;
import com.ashokit.user_management.beans.LoginRequest;
import com.ashokit.user_management.beans.RegisterRequest;
import com.ashokit.user_management.entity.UserMaster;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserManagementService {

    boolean activeUserAccount(ActivateRequest activateRequest);

    List<RegisterRequest> getAllUsers();

    boolean changeStatus(Long id, String status);

    String login(LoginRequest loginRequest);

    RegisterRequest getUserById(Long id);

    boolean deleteUserById(Long id);

    String forgetPassword(String email);

    boolean registerUser(RegisterRequest registerRequest);
}
