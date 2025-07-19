package com.garage.backend.service;

import com.garage.backend.entity.OTPCode;
import com.garage.backend.repository.OTPRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class OTPService {
    
    @Autowired
    private OTPRepository otpRepository;
    
    @Autowired
    private SMSService smsService;
    
    private static final String OTP_MESSAGE_TEMPLATE = "Your PitStop verification code is $otp$. Valid for 2 minutes.";
    private static final int OTP_LENGTH = 4;
    private static final int OTP_EXPIRY_MINUTES = 2;
    private static final int RATE_LIMIT_REQUESTS = 5;
    private static final int RATE_LIMIT_HOURS = 1;
    
    /**
     * Generate and send OTP for forgot password
     */
    public boolean sendForgotPasswordOTP(String phoneNumber) {
        return sendOTP(phoneNumber, "FORGOT_PASSWORD");
    }
    
    /**
     * Generate and send OTP for login
     */
    public boolean sendLoginOTP(String phoneNumber) {
        return sendOTP(phoneNumber, "LOGIN_OTP");
    }
    
    /**
     * Verify OTP code
     */
    public boolean verifyOTP(String phoneNumber, String otpCode, String type) {
        try {
            Optional<OTPCode> otpOptional = otpRepository.findValidOTPByPhoneAndType(
                phoneNumber, type, LocalDateTime.now());
            
            if (otpOptional.isPresent()) {
                OTPCode otp = otpOptional.get();
                
                // Check if OTP matches and is not used
                if (otp.getOtpCode().equals(otpCode) && !otp.getIsUsed()) {
                    // Mark OTP as used
                    otp.setIsUsed(true);
                    otpRepository.save(otp);
                    return true;
                }
            }
            
            return false;
        } catch (Exception e) {
            System.err.println("Error verifying OTP: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Check if user can request OTP (rate limiting)
     */
    public boolean canRequestOTP(String phoneNumber, String type) {
        try {
            LocalDateTime since = LocalDateTime.now().minusHours(RATE_LIMIT_HOURS);
            long recentRequests = otpRepository.countRecentOTPRequests(phoneNumber, type, since);
            return recentRequests < RATE_LIMIT_REQUESTS;
        } catch (Exception e) {
            System.err.println("Error checking rate limit: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Generate and send OTP
     */
    private boolean sendOTP(String phoneNumber, String type) {
        try {
            // Check rate limiting
            if (!canRequestOTP(phoneNumber, type)) {
                System.err.println("Rate limit exceeded for phone: " + phoneNumber);
                return false;
            }
            
            // Check if SMS service is available
            if (!smsService.isServiceAvailable()) {
                System.err.println("SMS service not available");
                return false;
            }
            
            // Generate OTP
            String otpCode = generateOTP();
            LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(OTP_EXPIRY_MINUTES);
            
            // Save OTP to database
            OTPCode otp = new OTPCode(phoneNumber, otpCode, type, expiresAt);
            otpRepository.save(otp);
            
            // Send SMS
            String message = OTP_MESSAGE_TEMPLATE.replace("$otp$", otpCode);
            boolean smsSent = smsService.sendOTP(phoneNumber, message);
            
            if (!smsSent) {
                // If SMS failed, delete the OTP from database
                otpRepository.delete(otp);
                return false;
            }
            
            return true;
        } catch (Exception e) {
            System.err.println("Error sending OTP: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Generate random 4-digit OTP
     */
    private String generateOTP() {
        Random random = new Random();
        int otp = 1000 + random.nextInt(9000); // 1000 to 9999
        return String.valueOf(otp);
    }
    
    /**
     * Clean up expired OTPs (scheduled task)
     */
    @Scheduled(fixedRate = 300000) // Run every 5 minutes
    public void cleanupExpiredOTPs() {
        try {
            LocalDateTime now = LocalDateTime.now();
            otpRepository.deleteExpiredOTPs(now);
        } catch (Exception e) {
            System.err.println("Error cleaning up expired OTPs: " + e.getMessage());
        }
    }
} 