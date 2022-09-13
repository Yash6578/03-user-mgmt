package com.ashokit.user_management.rest;

import com.ashokit.user_management.beans.User;
import com.ashokit.user_management.service.UserManagementService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UserController {
    final UserManagementService userManagementService;

    @GetMapping("/")
    ResponseEntity<String> test() {
        boolean res = userManagementService.save(new User());
        return ResponseEntity.ok().body("ok");
    }
}