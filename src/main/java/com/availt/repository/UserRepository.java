package com.availt.repository;

import com.availt.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByPhone(String phone);
    User findByEmail(String email);
}