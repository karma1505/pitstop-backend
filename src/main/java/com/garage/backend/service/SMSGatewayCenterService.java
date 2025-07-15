package com.garage.backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
public class SMSGatewayCenterService implements SMSService {
    
    @Value("${sms.gateway.api-key:}")
    private String apiKey;
    
    @Value("${sms.gateway.user-id:}")
    private String userId;
    
    @Value("${sms.gateway.password:}")
    private String password;
    
    @Value("${sms.gateway.sender-id:IGARAGE}")
    private String senderId;
    
    @Value("${sms.gateway.base-url:https://www.smsgateway.center/OTPApi/send}")
    private String baseUrl;
    
    private final RestTemplate restTemplate;
    
    public SMSGatewayCenterService() {
        this.restTemplate = new RestTemplate();
    }
    
    @Override
    public boolean sendOTP(String phoneNumber, String message) {
        try {
            // Build the request URL with parameters
            String url = buildRequestUrl(phoneNumber, message);
            
            // Make the HTTP request
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            
            // Check if the response indicates success
            return response.getStatusCode() == HttpStatus.OK && 
                   response.getBody() != null && 
                   response.getBody().contains("status=success");
            
        } catch (Exception e) {
            // Log the error (you might want to use a proper logging framework)
            System.err.println("Error sending SMS: " + e.getMessage());
            return false;
        }
    }
    
    @Override
    public boolean isServiceAvailable() {
        // Check if required configuration is available
        return (apiKey != null && !apiKey.isEmpty()) || 
               (userId != null && !userId.isEmpty() && password != null && !password.isEmpty());
    }
    
    private String buildRequestUrl(String phoneNumber, String message) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl);
        
        // Add authentication parameters
        if (apiKey != null && !apiKey.isEmpty()) {
            builder.queryParam("apiKey", apiKey);
        } else {
            builder.queryParam("userId", userId)
                   .queryParam("password", password);
        }
        
        // Add required parameters
        builder.queryParam("sendMethod", "generate")
               .queryParam("msgType", "text")
               .queryParam("mobile", phoneNumber)
               .queryParam("senderId", senderId)
               .queryParam("msg", URLEncoder.encode(message, StandardCharsets.UTF_8))
               .queryParam("codeExpiry", "120") // 2 minutes
               .queryParam("codeLength", "4")
               .queryParam("codeType", "num")
               .queryParam("retryExpiry", "60") // 1 minute retry limit
               .queryParam("medium", "sms")
               .queryParam("format", "json");
        
        return builder.toUriString();
    }
} 