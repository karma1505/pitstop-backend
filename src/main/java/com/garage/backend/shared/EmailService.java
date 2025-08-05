package com.garage.backend.shared;

public interface EmailService {
    
    /**
     * Send OTP via Email
     * @param email The email address to send OTP to
     * @param message The message template with OTP placeholder
     * @return true if email sent successfully, false otherwise
     */
    boolean sendOTP(String email, String message);
    
    /**
     * Verify if the service is available
     * @return true if service is available, false otherwise
     */
    boolean isServiceAvailable();
} 