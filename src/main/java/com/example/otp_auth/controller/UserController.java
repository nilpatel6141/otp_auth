package com.example.otp_auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.otp_auth.entity.User;
import com.example.otp_auth.service.UserService;

@Controller
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String home() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model) {
        User user = userService.findUserByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            userService.sendOtp(user);
            model.addAttribute("username", username);
            return "verify-otp";
        }
        model.addAttribute("error", "Invalid credentials!");
        return "login";
    }

    @PostMapping("/verify-otp")
    public String verifyOtp(@RequestParam String otp, Model model) {
        boolean result = userService.verifyOtp(otp);
        if (result == true) {
            model.addAttribute("message", "OTP verifyed suceessfully Welcome to the dashboard");
            return "dashboard";
        }
        model.addAttribute("error", "Invalid or expired OTP");
        return "verify-otp";
    }

}
