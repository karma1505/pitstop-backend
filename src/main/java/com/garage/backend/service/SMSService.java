package com.garage.backend.service;

public interface SMSService {
    
    /**
     * Send OTP via SMS
     * @param phoneNumber The phone number to send OTP to
     * @param message The message template with OTP placeholder
     * @return true if SMS sent successfully, false otherwise
     */
    boolean sendOTP(String phoneNumber, String message);
    
    /**
     * Verify if the service is available
     * @return true if service is available, false otherwise
     */
    boolean isServiceAvailable();
} 