package com.shopnest.userservice.model;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "_users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role; // e.g., ROLE_USER, ROLE_ADMIN

    @Column(nullable = false)
    private boolean verified = false;

    private String emailVerificationToken;

    private String passwordResetToken;

    private LocalDateTime passwordResetTokenExpiry;

    private String profilePictureUrl;
    public User() {}

    public User(Long id, String email, String password, String role, boolean verified,
                String emailVerificationToken, String passwordResetToken,
                LocalDateTime passwordResetTokenExpiry, String profilePictureUrl) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
        this.verified = verified;
        this.emailVerificationToken = emailVerificationToken;
        this.passwordResetToken = passwordResetToken;
        this.passwordResetTokenExpiry = passwordResetTokenExpiry;
        this.profilePictureUrl = profilePictureUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public String getEmailVerificationToken() {
        return emailVerificationToken;
    }

    public void setEmailVerificationToken(String emailVerificationToken) {
        this.emailVerificationToken = emailVerificationToken;
    }

    public String getPasswordResetToken() {
        return passwordResetToken;
    }

    public void setPasswordResetToken(String passwordResetToken) {
        this.passwordResetToken = passwordResetToken;
    }

    public LocalDateTime getPasswordResetTokenExpiry() {
        return passwordResetTokenExpiry;
    }

    public void setPasswordResetTokenExpiry(LocalDateTime passwordResetTokenExpiry) {
        this.passwordResetTokenExpiry = passwordResetTokenExpiry;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }
}
