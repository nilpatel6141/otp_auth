package com.example.otp_auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.otp_auth.entity.Token;
@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {
    Token findByOtp(String otp);

}
