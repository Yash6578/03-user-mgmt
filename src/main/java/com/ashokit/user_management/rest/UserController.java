package com.ashokit.user_management.rest;

import com.ashokit.user_management.beans.ActivateRequest;
import com.ashokit.user_management.beans.LoginRequest;
import com.ashokit.user_management.beans.RegisterRequest;
import com.ashokit.user_management.service.UserManagementService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class UserController {
    final UserManagementService userManagementService;

    @PostMapping("/register")
    ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        if(userManagementService.registerUser(registerRequest))
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Registration is almost completed.\nFor further process check your mail");
        return ResponseEntity.badRequest().body("Something went wrong sorry for inconvenience");
    }

    @PostMapping("/activate")
    ResponseEntity<String> activate(@RequestBody ActivateRequest activateRequest) {
        if(userManagementService.activeUserAccount(activateRequest))
            return ResponseEntity.ok()
                    .body("Congratulation registration successful");
        return ResponseEntity.badRequest().body("Registration failed");
    }

    @GetMapping("users")
    ResponseEntity<List<RegisterRequest>> getAllUsers() {
        return ResponseEntity.ok(userManagementService.getAllUsers());
    }

    @PostMapping("/login")
    ResponseEntity<String> login(LoginRequest loginRequest) {
        String status = userManagementService.login(loginRequest);
        if(status.equals("Success"))
            return ResponseEntity.ok(status);
        return ResponseEntity.badRequest().body(status);
    }

    @GetMapping("/forget/{email}")
    ResponseEntity<String> forgetPassword(@PathVariable String email) {
        String status = userManagementService.forgetPassword(email);
        if(status.equals("Invalid Email"))
            return ResponseEntity.badRequest().body(status);
        return ResponseEntity.ok(status);
    }

    @GetMapping("/user/{id}")
    ResponseEntity<RegisterRequest> getUserById(@PathVariable Long id) {
        RegisterRequest user = userManagementService.getUserById(id);
        if(null != user)
            return ResponseEntity.ok(user);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(user);
    }
    
    @DeleteMapping("user/{id}")
    ResponseEntity<String> deleteById(@PathVariable Long id) {
        boolean isDeleted = userManagementService.deleteUserById(id);

        if(isDeleted)   return ResponseEntity.ok("User Deleted Successfully");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found");

    }

    @GetMapping("/user/{id}/{status}")
    ResponseEntity<String> changeStatus(@PathVariable Long id, @PathVariable String status) {
        boolean isStatusChanged = userManagementService.changeStatus(id, status);
        if(isStatusChanged) return ResponseEntity.ok("Status changed to " + status);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to changed status.\nUser Id not found");
    }
}