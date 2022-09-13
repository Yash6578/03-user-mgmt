package com.ashokit.user_management.repo;

import com.ashokit.user_management.entity.UserMaster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserMasterRepo extends JpaRepository<UserMaster, Long> {
    UserMaster findByEmailAndPassword(String email, String password);

    UserMaster findByEmail(String email);
}
