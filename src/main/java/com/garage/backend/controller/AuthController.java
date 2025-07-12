package com.garage.backend.controller;

import com.garage.backend.dto.AuthResponse;
import com.garage.backend.dto.LoginRequest;
import com.garage.backend.dto.RegisterRequest;
import com.garage.backend.dto.ForgotPasswordRequest;
import com.garage.backend.dto.OTPVerificationRequest;
import com.garage.backend.dto.ResetPasswordRequest;
import com.garage.backend.dto.ChangePasswordRequest;
import com.garage.backend.service.AuthService;
import com.garage.backend.service.OTPService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private OTPService otpService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        AuthResponse response = authService.register(request);
        
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Map<String, Object>> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        try {
            boolean otpSent = otpService.sendForgotPasswordOTP(request.getPhoneNumber());
            
            Map<String, Object> response = new HashMap<>();
            if (otpSent) {
                response.put("success", true);
                response.put("message", "OTP sent successfully to your phone number");
            } else {
                response.put("success", false);
                response.put("message", "Failed to send OTP. Please try again later.");
            }
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "An error occurred while processing your request");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<Map<String, Object>> verifyOTP(@Valid @RequestBody OTPVerificationRequest request) {
        try {
            boolean isValid = otpService.verifyOTP(request.getPhoneNumber(), request.getOtpCode(), request.getType());
            
            Map<String, Object> response = new HashMap<>();
            if (isValid) {
                response.put("success", true);
                response.put("message", "OTP verified successfully");
            } else {
                response.put("success", false);
                response.put("message", "Invalid OTP or OTP has expired");
            }
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "An error occurred while verifying OTP");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Map<String, Object>> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        try {
            // First verify the OTP
            boolean otpValid = otpService.verifyOTP(request.getPhoneNumber(), request.getOtpCode(), "FORGOT_PASSWORD");
            
            if (!otpValid) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "Invalid OTP or OTP has expired");
                return ResponseEntity.badRequest().body(response);
            }
            
            // Check if passwords match
            if (!request.getNewPassword().equals(request.getConfirmPassword())) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "Passwords do not match");
                return ResponseEntity.badRequest().body(response);
            }
            
            // Reset password using AuthService
            boolean passwordReset = authService.resetPassword(request.getPhoneNumber(), request.getNewPassword());
            
            Map<String, Object> response = new HashMap<>();
            if (passwordReset) {
                response.put("success", true);
                response.put("message", "Password reset successfully");
            } else {
                response.put("success", false);
                response.put("message", "Failed to reset password. User not found.");
            }
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "An error occurred while resetting password");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/send-login-otp")
    public ResponseEntity<Map<String, Object>> sendLoginOTP(@Valid @RequestBody ForgotPasswordRequest request) {
        try {
            boolean otpSent = otpService.sendLoginOTP(request.getPhoneNumber());
            
            Map<String, Object> response = new HashMap<>();
            if (otpSent) {
                response.put("success", true);
                response.put("message", "Login OTP sent successfully to your phone number");
            } else {
                response.put("success", false);
                response.put("message", "Failed to send OTP. Please try again later.");
            }
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "An error occurred while processing your request");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/login-with-otp")
    public ResponseEntity<AuthResponse> loginWithOTP(@Valid @RequestBody OTPVerificationRequest request) {
        try {
            // Verify OTP first
            boolean otpValid = otpService.verifyOTP(request.getPhoneNumber(), request.getOtpCode(), "LOGIN_OTP");
            
            if (!otpValid) {
                return ResponseEntity.badRequest().body(AuthResponse.error("Invalid OTP or OTP has expired"));
            }
            
            // Login with OTP using AuthService
            AuthResponse response = authService.loginWithOTP(request.getPhoneNumber());
            
            if (response.isSuccess()) {
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(AuthResponse.error("An error occurred during OTP login"));
        }
    }

    @PostMapping("/change-password")
    public ResponseEntity<Map<String, Object>> changePassword(@Valid @RequestBody ChangePasswordRequest request) {
        try {
            // Get current user from authentication context
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userEmail = authentication.getName();
            
            // Validate password confirmation
            if (!request.getNewPassword().equals(request.getConfirmPassword())) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "New password and confirm password do not match");
                return ResponseEntity.badRequest().body(response);
            }
            
            // Change password
            boolean passwordChanged = authService.changePassword(userEmail, request.getCurrentPassword(), request.getNewPassword());
            
            Map<String, Object> response = new HashMap<>();
            if (passwordChanged) {
                response.put("success", true);
                response.put("message", "Password changed successfully");
            } else {
                response.put("success", false);
                response.put("message", "Current password is incorrect");
            }
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "An error occurred while changing password");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Auth service is running");
    }
} 