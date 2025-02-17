package com.shopnest.userservice.controller;

import com.shopnest.userservice.model.User;
import com.shopnest.userservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User Management", description = "APIs for managing users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Creates a new user account.")
    @ApiResponse(responseCode = "200", description = "User registered successfully")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        User registeredUser = userService.registerUser(user);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    @Operation(summary = "Login a user", description = "Authenticates a user and returns a JWT token.")
    @ApiResponse(responseCode = "200", description = "Login successful")
    public ResponseEntity<String> loginUser(@RequestBody User user) {
        String token = userService.loginUser(user.getEmail(), user.getPassword());
        return ResponseEntity.ok(token);
    }

    @GetMapping("/{email}")
    @Operation(summary = "Get user by email", description = "Retrieves user details by email.")
    @ApiResponse(responseCode = "200", description = "User found")
    public ResponseEntity<User> getUserByEmail(
        @Parameter(description = "Email of the user", example = "user@example.com")
        @PathVariable String email) {
        return userService.findByEmail(email)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update user profile", description = "Updates the profile of an existing user.")
    @ApiResponse(responseCode = "200", description = "User profile updated successfully")
    public ResponseEntity<User> updateUser(
        @Parameter(description = "ID of the user", example = "1")
        @PathVariable Long id,
        @RequestBody User user) {
        User updatedUser = userService.updateUser(id, user);
        return ResponseEntity.ok(updatedUser);
    }

    @PostMapping("/{id}/update-profile-picture")
    @Operation(summary = "Update profile picture", description = "Updates the profile picture of a user.")
    @ApiResponse(responseCode = "200", description = "Profile picture updated successfully")
    public ResponseEntity<Void> updateProfilePicture(
        @Parameter(description = "ID of the user", example = "1")
        @PathVariable Long id,
        @Parameter(description = "URL of the new profile picture", example = "https://example.com/profile.jpg")
        @RequestParam String profilePictureUrl) {
        userService.updateProfilePicture(id, profilePictureUrl);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a user", description = "Deletes a user by their ID.")
    @ApiResponse(responseCode = "204", description = "User deleted successfully")
    public ResponseEntity<Void> deleteUser(
        @Parameter(description = "ID of the user", example = "1")
        @PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/change-password")
    @Operation(summary = "Change user password", description = "Changes the password of an existing user.")
    @ApiResponse(responseCode = "200", description = "Password changed successfully")
    public ResponseEntity<Void> changePassword(
        @Parameter(description = "ID of the user", example = "1")
        @PathVariable Long id,
        @RequestBody String newPassword) {
        userService.changePassword(id, newPassword);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/all")
    @Operation(summary = "Fetch all users", description = "Retrieves a list of all users (Admin only).")
    @ApiResponse(responseCode = "200", description = "List of users retrieved successfully")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/{id}/assign-role")
    @Operation(summary = "Assign role to a user", description = "Assigns a role (e.g., ADMIN) to a user (Admin only).")
    @ApiResponse(responseCode = "200", description = "Role assigned successfully")
    public ResponseEntity<User> assignRole(
        @Parameter(description = "ID of the user", example = "1")
        @PathVariable Long id,
        @RequestBody String role) {
        User user = userService.assignRole(id, role);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/verify-email")
    @Operation(summary = "Verify email", description = "Verifies a user's email address using a token.")
    @ApiResponse(responseCode = "200", description = "Email verified successfully")
    public ResponseEntity<String> verifyEmail(
        @Parameter(description = "Email verification token", example = "abc123")
        @RequestParam String token) {
        userService.verifyEmail(token);
        return ResponseEntity.ok("Email verified successfully");
    }

    @PostMapping("/send-verification-email")
    @Operation(summary = "Send verification email", description = "Sends a verification email to the user.")
    @ApiResponse(responseCode = "200", description = "Verification email sent successfully")
    public ResponseEntity<Void> sendVerificationEmail(
        @Parameter(description = "Email of the user", example = "user@example.com")
        @RequestParam String email) {
        userService.sendVerificationEmail(email);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/request-password-reset")
    @Operation(summary = "Request password reset", description = "Sends a password reset email to the user.")
    @ApiResponse(responseCode = "200", description = "Password reset email sent")
    public ResponseEntity<String> requestPasswordReset(
        @Parameter(description = "Email of the user", example = "user@example.com")
        @RequestParam String email) {
        userService.requestPasswordReset(email);
        return ResponseEntity.ok("Password reset email sent");
    }

    @PostMapping("/reset-password")
    @Operation(summary = "Reset password", description = "Resets the user's password using a token.")
    @ApiResponse(responseCode = "200", description = "Password reset successfully")
    public ResponseEntity<String> resetPassword(
        @Parameter(description = "Password reset token", example = "abc123")
        @RequestParam String token,
        @Parameter(description = "New password", example = "newPassword123")
        @RequestParam String newPassword) {
        userService.resetPassword(token, newPassword);
        return ResponseEntity.ok("Password reset successfully");
    }
}
