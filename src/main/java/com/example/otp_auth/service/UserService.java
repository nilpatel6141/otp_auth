package com.example.otp_auth.service;

import com.example.otp_auth.entity.User;

public interface UserService {
  public User findUserByUsername(String username);
  public void sendOtp(User user);
  public boolean verifyOtp(String otp);
}
