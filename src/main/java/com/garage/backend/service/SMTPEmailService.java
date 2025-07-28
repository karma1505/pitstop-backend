package com.garage.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SMTPEmailService implements EmailService {
    
    @Autowired
    private JavaMailSender mailSender;
    
    @Value("${spring.mail.username}")
    private String fromEmail;
    
    @Value("${spring.mail.password}")
    private String emailPassword;
    
    private static final String EMAIL_SUBJECT = "PitStop Verification Code";
    private static final String EMAIL_BODY_TEMPLATE = 
        "Your PitStop verification code is: %s\n\n" +
        "This code is valid for 2 minutes.\n\n" +
        "If you didn't request this code, please ignore this email.\n\n" +
        "Best regards,\nPitStop Team";
    
    @Override
    public boolean sendOTP(String email, String message) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(fromEmail);
            mailMessage.setTo(email);
            mailMessage.setSubject(EMAIL_SUBJECT);
            mailMessage.setText(String.format(EMAIL_BODY_TEMPLATE, message));
            
            mailSender.send(mailMessage);
            return true;
            
        } catch (Exception e) {
            System.err.println("Error sending email: " + e.getMessage());
            return false;
        }
    }
    
    @Override
    public boolean isServiceAvailable() {
        return fromEmail != null && !fromEmail.isEmpty() && 
               emailPassword != null && !emailPassword.isEmpty();
    }
} 