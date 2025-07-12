package com.garage.backend.dto;

import java.time.LocalDateTime;

public class AuthResponse {

    private String token;
    private String refreshToken;
    private String tokenType = "Bearer";
    private Long expiresIn;
    private UserInfo userInfo;
    private String message;
    private boolean success;

    // Constructors
    public AuthResponse() {}

    public AuthResponse(String token, String refreshToken, Long expiresIn, UserInfo userInfo, String message, boolean success) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.expiresIn = expiresIn;
        this.userInfo = userInfo;
        this.message = message;
        this.success = success;
    }

    // Static factory methods
    public static AuthResponse success(String token, String refreshToken, Long expiresIn, UserInfo userInfo) {
        return new AuthResponse(token, refreshToken, expiresIn, userInfo, "Authentication successful", true);
    }

    public static AuthResponse error(String message) {
        AuthResponse response = new AuthResponse();
        response.setMessage(message);
        response.setSuccess(false);
        return response;
    }

    // Getters and Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    // Inner class for user information
    public static class UserInfo {
        private Long id;
        private String firstName;
        private String lastName;
        private String email;
        private String garageName;
        private String state;
        private String city;
        private LocalDateTime createdAt;

        // Constructors
        public UserInfo() {}

        public UserInfo(Long id, String firstName, String lastName, String email, 
                      String garageName, String state, String city, LocalDateTime createdAt) {
            this.id = id;
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
            this.garageName = garageName;
            this.state = state;
            this.city = city;
            this.createdAt = createdAt;
        }

        // Getters and Setters
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getGarageName() {
            return garageName;
        }

        public void setGarageName(String garageName) {
            this.garageName = garageName;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public LocalDateTime getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
        }

        @Override
        public String toString() {
            return "UserInfo{" +
                    "id=" + id +
                    ", firstName='" + firstName + '\'' +
                    ", lastName='" + lastName + '\'' +
                    ", email='" + email + '\'' +
                    ", garageName='" + garageName + '\'' +
                    ", state='" + state + '\'' +
                    ", city='" + city + '\'' +
                    ", createdAt=" + createdAt +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "AuthResponse{" +
                "token='" + token + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                ", tokenType='" + tokenType + '\'' +
                ", expiresIn=" + expiresIn +
                ", userInfo=" + userInfo +
                ", message='" + message + '\'' +
                ", success=" + success +
                '}';
    }
} 