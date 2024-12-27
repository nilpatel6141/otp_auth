package com.example.otp_auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.otp_auth.entity.User;
@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
    User findByUsername(String name);
}
