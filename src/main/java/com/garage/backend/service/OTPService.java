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
    private EmailService emailService;
    
    private static final String OTP_MESSAGE_TEMPLATE = "Your PitStop verification code is %s. Valid for 2 minutes.";
    private static final int OTP_LENGTH = 4;
    private static final int OTP_EXPIRY_MINUTES = 2;
    // Removed rate limiting constants for testing phase
    // private static final int RATE_LIMIT_REQUESTS = 5;
    // private static final int RATE_LIMIT_HOURS = 1;
    
    /**
     * Generate and send OTP for forgot password
     */
    public boolean sendForgotPasswordOTP(String email) {
        return sendOTP(email, "FORGOT_PASSWORD");
    }
    
    /**x
     * Generate and send OTP for login
     */
    public boolean sendLoginOTP(String email) {
        return sendOTP(email, "LOGIN_OTP");
    }
    
    /**
     * Verify OTP
     */
    public boolean verifyOTP(String email, String otpCode, String type) {
        try {
            Optional<OTPCode> otpOptional = otpRepository.findValidOTPByEmailAndType(
                email, type, LocalDateTime.now());
            
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
            e.printStackTrace();
            return false;
        }
    }
    
    // Removed canRequestOTP method for testing phase
    // /**
    //  * Check if user can request OTP (rate limiting)
    //  */
    // public boolean canRequestOTP(String email, String type) {
    //     try {
    //         LocalDateTime since = LocalDateTime.now().minusHours(RATE_LIMIT_HOURS);
    //         long recentRequests = otpRepository.countRecentEmailOTPRequests(email, type, since);
    //         return recentRequests < RATE_LIMIT_REQUESTS;
    //     } catch (Exception e) {
    //         System.err.println("Error checking rate limit: " + e.getMessage());
    //         return false;
    //     }
    // }
    
    /**
     * Generate and send OTP
     */
    private boolean sendOTP(String email, String type) {
        try {
            // Removed rate limiting check for testing phase
            // if (!canRequestOTP(email, type)) {
            //     System.err.println("Rate limit exceeded for email: " + email);
            //     return false;
            // }
            
            // Check if email service is available
            if (!emailService.isServiceAvailable()) {
                System.err.println("Email service not available");
                return false;
            }
            
            // Generate OTP
            String otpCode = generateOTP();
            LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(OTP_EXPIRY_MINUTES);
            
            // Save OTP to database
            OTPCode otp = new OTPCode(email, otpCode, type, expiresAt);
            otpRepository.save(otp);
            
            // Send email
            String message = String.format(OTP_MESSAGE_TEMPLATE, otpCode);
            boolean emailSent = emailService.sendOTP(email, message);
            
            if (!emailSent) {
                // If email failed, delete the OTP from database
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