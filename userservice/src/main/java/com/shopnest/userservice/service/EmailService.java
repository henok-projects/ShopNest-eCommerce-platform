package com.shopnest.userservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendVerificationEmail(String email, String token) {
        String subject = "Verify Your Email";
        String verificationUrl = "http://yourdomain.com/api/users/verify-email?token=" + token;
        String body = "Please click the link below to verify your email:\n" + verificationUrl;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }

    public void sendPasswordResetEmail(String email, String token) {
        String subject = "Reset Your Password";
        String resetUrl = "http://yourdomain.com/api/users/reset-password?token=" + token;
        String body = "Please click the link below to reset your password:\n" + resetUrl;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }
}
