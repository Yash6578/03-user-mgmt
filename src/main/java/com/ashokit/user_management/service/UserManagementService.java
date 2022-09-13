package com.ashokit.user_management.service;

import com.ashokit.user_management.beans.ActivateRequest;
import com.ashokit.user_management.beans.LoginRequest;
import com.ashokit.user_management.beans.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserManagementService {
    boolean save(User user);

    boolean activeUserAccount(ActivateRequest activateRequest);

    List<User> getAllUsers();

    boolean changeStatus(Long id, String status);

    String login(LoginRequest loginRequest);

    User getUserById(Long id);

    boolean deleteUserById(Long id);

    String forgetPassword(String email);
}
