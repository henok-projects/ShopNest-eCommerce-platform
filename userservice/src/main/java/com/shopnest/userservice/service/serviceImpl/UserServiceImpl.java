package com.shopnest.userservice.service.serviceImpl;

import com.shopnest.userservice.config.JwtUtil;
import com.shopnest.userservice.model.User;
import com.shopnest.userservice.repository.UserRepository;
import com.shopnest.userservice.service.EmailService;
import com.shopnest.userservice.service.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    private EmailService emailService;

    @Override
    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
    @Override
    public String loginUser(String email, String password) {
        Optional<User> existingUser = userRepository.findByEmail(email);
        if (existingUser.isPresent() && passwordEncoder.matches(password, existingUser.get().getPassword())) {
            return jwtUtil.generateToken(new org.springframework.security.core.userdetails.User(
                existingUser.get().getEmail(),
                existingUser.get().getPassword(),
                new ArrayList<>()
            ));
        }
        throw new RuntimeException("Invalid credentials");
    }
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    @Override
    public User updateUser(Long id, User user) {
        User existingUser = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(existingUser);
    }
    @Override
    public void changePassword(Long id, String newPassword) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
    @Override
    public User assignRole(Long id, String role) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));
        user.setRole(role);
        return userRepository.save(user);
    }

    // Update Profile Picture
    @Override
    public void updateProfilePicture(Long id, String profilePictureUrl) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));
        user.setProfilePictureUrl(profilePictureUrl);
        userRepository.save(user);
    }

    @Transactional
    @Override
    public void sendVerificationEmail(String email) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));

        // Generate a unique token
        String token = UUID.randomUUID().toString();
        user.setEmailVerificationToken(token);
        userRepository.save(user);

        // Send verification email
        emailService.sendVerificationEmail(email, token);
    }

    @Transactional
    @Override
    public void verifyEmail(String token) {
        User user = userRepository.findByEmailVerificationToken(token)
            .orElseThrow(() -> new RuntimeException("Invalid token"));

        user.setVerified(true);
        user.setEmailVerificationToken(null); // Clear the token after verification
        userRepository.save(user);
    }

    @Transactional
    @Override
    public void requestPasswordReset(String email) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));

        // Generate a unique token
        String token = UUID.randomUUID().toString();
        user.setPasswordResetToken(token);
        user.setPasswordResetTokenExpiry(LocalDateTime.now().plusHours(1)); // Token expires in 1 hour
        userRepository.save(user);

        // Send password reset email
        emailService.sendPasswordResetEmail(email, token);
    }

    @Transactional
    @Override
    public void resetPassword(String token, String newPassword) {
        User user = userRepository.findByPasswordResetToken(token)
            .orElseThrow(() -> new RuntimeException("Invalid token"));

        // Check if the token has expired
        if (user.getPasswordResetTokenExpiry().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token has expired");
        }

        // Reset the password
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setPasswordResetToken(null); // Clear the token
        user.setPasswordResetTokenExpiry(null); // Clear the expiry
        userRepository.save(user);
    }
    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
