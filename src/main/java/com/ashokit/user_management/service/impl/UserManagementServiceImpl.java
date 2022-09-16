package com.ashokit.user_management.service.impl;

import com.ashokit.user_management.beans.ActivateRequest;
import com.ashokit.user_management.beans.LoginRequest;
import com.ashokit.user_management.beans.RegisterRequest;
import com.ashokit.user_management.entity.UserMaster;
import com.ashokit.user_management.repo.UserMasterRepo;
import com.ashokit.user_management.service.EmailSenderService;
import com.ashokit.user_management.service.PasswordGeneratorService;
import com.ashokit.user_management.service.UserManagementService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class UserManagementServiceImpl implements UserManagementService {

    final PasswordGeneratorService passwordGeneratorService;
    final UserMasterRepo userMasterRepo;
    final EmailSenderService emailSenderService;

    boolean save(UserMaster user) {
        return userMasterRepo.save(user).getId() != null;
    }

    public boolean registerUser(RegisterRequest registerRequest) {
        boolean isUserRegistered = false;
        UserMaster user = new UserMaster();
        BeanUtils.copyProperties(registerRequest, user);

        user.setPassword(passwordGeneratorService.generate());
        user.setStatus("In-Active");

        user.setCreatedBy(registerRequest.getEmail());

        if(save(user)) {
            String body = emailSenderService.getActivationMailBody(user.getFullName(), user.getPassword());
            isUserRegistered =
                    emailSenderService.sendEmail(user.getEmail(), "Your Registration is successful", body);
        }

        return isUserRegistered;
    }

    public boolean activeUserAccount(ActivateRequest activateRequest) {
        UserMaster user =
                userMasterRepo.findByEmailAndPassword(activateRequest.getEmail(), activateRequest.getTempPassword());
        if(user != null) {
            user.setStatus("Active");
            user.setPassword(activateRequest.getNewPassword());
            userMasterRepo.save(user);
        }

        return user != null;
    }

    @Override
    public List<RegisterRequest> getAllUsers() {

        List<RegisterRequest> users = new ArrayList<>();

        userMasterRepo.findAll().stream().forEach(userMaster -> {
            RegisterRequest user = new RegisterRequest();
            BeanUtils.copyProperties(userMaster, user);
            users.add(user);
        });

        return users;
    }

    @Override
    public boolean changeStatus(Long id, String status) {
        UserMaster user = userMasterRepo.findById(id).get();

        if(user != null) {
            user.setStatus(status);
            userMasterRepo.save(user);
            //Todo: updatedBy = ?
        }

        return user != null;
    }

    @Override
    public String login(LoginRequest loginRequest) {
        UserMaster user = userMasterRepo.findByEmailAndPassword(loginRequest.getUserName(), loginRequest.getPassword());
        if(user == null)    return "Invalid Credentials";
        return user.getStatus().equals("Active") ? "Success" : "InActive Account";
    }

    @Override
    public RegisterRequest getUserById(Long id) {
        UserMaster userMaster = userMasterRepo.findById(id).get();
        RegisterRequest user =  null;

        if(userMaster != null) {
            user = new RegisterRequest();
            BeanUtils.copyProperties(userMaster, user);
        }

        return user;
    }

    @Override
    public boolean deleteUserById(Long id) {

        try {
            userMasterRepo.deleteById(id);
            return true;
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public String forgetPassword(String email) {
        UserMaster user = userMasterRepo.findByEmail(email);
        String status = "Invalid Email";

        if(user != null) {
            String body = emailSenderService.getForgetPasswordMailBody(user.getFullName(), user.getPassword());
            emailSenderService.sendEmail(user.getEmail(), "Your Password for {APP}", body);
            status = "Password sent to your registered email";
        }

        return status;
    }
}
