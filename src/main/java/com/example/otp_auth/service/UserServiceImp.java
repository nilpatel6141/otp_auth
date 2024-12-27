package com.example.otp_auth.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.otp_auth.entity.Token;
import com.example.otp_auth.entity.User;
import com.example.otp_auth.repository.TokenRepository;
import com.example.otp_auth.repository.UserRepository;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    TokenRepository tokenRepository;
    @Autowired
    JavaMailSender mailSender;

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void sendOtp(User user) {
        String otp = String.format("%06d", new Random().nextInt(999999));
        // generating otp
        Token token = new Token();
        token.setUser(user);
        token.setOtp(otp);
        token.setCreatedAt(LocalDateTime.now());

        // storing otp in DB
        tokenRepository.save(token);

        // sending otp to the user mail
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Your OTP code is");
        message.setText("Use this OTP to verify: " + otp);

        try {
            mailSender.send(message);
            System.out.println("OTP email sent successfully to: " + user.getEmail());
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception to check for errors
        }
    }

    @Override
    public boolean verifyOtp(String otp) {
        // fetching the token from DB
        Token token = tokenRepository.findByOtp(otp);
        if (token == null) {
            return false; // OTP not found in DB
        }
        if (ChronoUnit.MINUTES.between(token.getCreatedAt(), LocalDateTime.now()) > 1) {
            tokenRepository.delete(token); // Delete expired OTP from DB
            return false; // OTP expired
        }
        return true; // OTP is valid
    }

}
