package com.ashokit.user_management.service.impl;

import com.ashokit.user_management.beans.ActivateRequest;
import com.ashokit.user_management.beans.LoginRequest;
import com.ashokit.user_management.beans.User;
import com.ashokit.user_management.entity.UserMaster;
import com.ashokit.user_management.repo.UserMasterRepo;
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
    @Override
    public boolean save(User user) {
        UserMaster userMaster = new UserMaster();
        BeanUtils.copyProperties(user, userMaster);

        userMaster.setPassword(passwordGeneratorService.generate());
        userMaster.setStatus("In-Active");
        userMaster.setCreatedBy(user.getEmail());

        //Todo: sent email

        return userMasterRepo.save(userMaster).getId() != null;
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
    public List<User> getAllUsers() {

        List<User> users = new ArrayList<>();

        userMasterRepo.findAll().stream().forEach(userMaster -> {
            User user = new User();
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
    public User getUserById(Long id) {
        UserMaster userMaster = userMasterRepo.findById(id).get();
        User user =  null;

        if(userMaster != null) {
            user = new User();
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

        if(user != null) {
            //Todo: sent email
        }

        return "Invalid Email";
    }
}
