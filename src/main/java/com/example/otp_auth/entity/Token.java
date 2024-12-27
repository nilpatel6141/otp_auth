package com.example.otp_auth.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tokens")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Automatically maps to the "id" column

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // Relationship with the User entity

    @Column(nullable = false, length = 10)
    private String otp; // Maps to the "otp" column

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public Token() {
    }

    public Token(Long id, User user, String otp, LocalDateTime createdAt) {
        this.id = id;
        this.user = user;
        this.otp = otp;
        this.createdAt = createdAt;
    }

    public Token(User user, String otp, LocalDateTime createdAt) {
        this.user = user;
        this.otp = otp;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

}
