package com.shopnest.userservice.service;

import com.shopnest.userservice.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User registerUser(User user);

    String loginUser(String email, String password);

    Optional<User> findByEmail(String email);

    List<User> getAllUsers();

    User updateUser(Long id, User user);

    void changePassword(Long id, String newPassword);

    User assignRole(Long id, String role);

    void updateProfilePicture(Long id, String profilePictureUrl);

    void sendVerificationEmail(String email);

    void verifyEmail(String token);

    void requestPasswordReset(String email);

    void resetPassword(String token, String newPassword);

    void deleteUser(Long id);
}
