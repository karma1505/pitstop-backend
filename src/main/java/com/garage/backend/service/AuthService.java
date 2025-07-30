package com.garage.backend.service;

import com.garage.backend.dto.AuthResponse;
import com.garage.backend.dto.LoginRequest;
import com.garage.backend.dto.RegisterRequest;
import com.garage.backend.dto.UpdateProfileRequest;
import com.garage.backend.entity.User;
import com.garage.backend.repository.UserRepository;
import com.garage.backend.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    public AuthResponse register(RegisterRequest request) {
        try {
            // Validate password confirmation
            if (!request.getPassword().equals(request.getConfirmPassword())) {
                return AuthResponse.error("Passwords do not match");
            }

            // Check if user already exists
            if (userRepository.existsByEmail(request.getEmail())) {
                return AuthResponse.error("User with this email already exists");
            }

            // Create new user
            User user = new User();
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setEmail(request.getEmail());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setState(request.getState());
            user.setCity(request.getCity());
            user.setPincode(request.getPincode());
            user.setMobileNumber(request.getMobileNumber());
            user.setGarageName(request.getGarageName());
            user.setAddressLine1(request.getAddressLine1());
            user.setAddressLine2(request.getAddressLine2());
            user.setIsActive(true);

            User savedUser = userRepository.save(user);

            // Generate tokens
            UserDetails userDetails = userDetailsService.loadUserByUsername(savedUser.getEmail());
            String token = jwtUtil.generateToken(userDetails);
            String refreshToken = jwtUtil.generateRefreshToken(userDetails);

            // Create user info
            AuthResponse.UserInfo userInfo = new AuthResponse.UserInfo(
                    savedUser.getId(),
                    savedUser.getFirstName(),
                    savedUser.getLastName(),
                    savedUser.getEmail(),
                    savedUser.getGarageName(),
                    savedUser.getState(),
                    savedUser.getCity(),
                    savedUser.getAddressLine1(),
                    savedUser.getAddressLine2(),
                    savedUser.getMobileNumber(),
                    savedUser.getPincode(),
                    savedUser.getCreatedAt()
            );

            return AuthResponse.success(token, refreshToken, jwtUtil.getExpiration(), userInfo);

        } catch (Exception e) {
            return AuthResponse.error("Registration failed: " + e.getMessage());
        }
    }

    public AuthResponse login(LoginRequest request) {
        try {
            // Authenticate user
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            // Load user details
            UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());

            // Generate tokens
            String token = jwtUtil.generateToken(userDetails);
            String refreshToken = jwtUtil.generateRefreshToken(userDetails);

            // Get user info from database
            User user = userRepository.findByEmail(request.getEmail()).orElse(null);
            if (user == null) {
                return AuthResponse.error("User not found");
            }

            // Create user info
            AuthResponse.UserInfo userInfo = new AuthResponse.UserInfo(
                    user.getId(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getEmail(),
                    user.getGarageName(),
                    user.getState(),
                    user.getCity(),
                    user.getAddressLine1(),
                    user.getAddressLine2(),
                    user.getMobileNumber(),
                    user.getPincode(),
                    user.getCreatedAt()
            );

            return AuthResponse.success(token, refreshToken, jwtUtil.getExpiration(), userInfo);

        } catch (Exception e) {
            return AuthResponse.error("Invalid email or password");
        }
    }

    /**
     * Reset password using email
     */
    public boolean resetPassword(String email, String newPassword) {
        try {
            // Find user by email
            User user = userRepository.findByEmail(email).orElse(null);
            if (user == null) {
                return false;
            }

            // Update password
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            System.err.println("Error resetting password: " + e.getMessage());
            return false;
        }
    }

    /**
     * Login with OTP using email
     */
    public AuthResponse loginWithOTP(String email) {
        try {
            // Find user by email
            User user = userRepository.findByEmail(email).orElse(null);
            if (user == null) {
                return AuthResponse.error("User not found with this email");
            }

            // Load user details
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());

            // Generate tokens
            String token = jwtUtil.generateToken(userDetails);
            String refreshToken = jwtUtil.generateRefreshToken(userDetails);
            // Create user info
            AuthResponse.UserInfo userInfo = new AuthResponse.UserInfo(
                    user.getId(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getEmail(),
                    user.getGarageName(),
                    user.getState(),
                    user.getCity(),
                    user.getAddressLine1(),
                    user.getAddressLine2(),
                    user.getMobileNumber(),
                    user.getPincode(),
                    user.getCreatedAt()
            );

            AuthResponse response = AuthResponse.success(token, refreshToken, jwtUtil.getExpiration(), userInfo);
            return response;

        } catch (Exception e) {
            System.err.println("Error in loginWithOTP: " + e.getMessage());
            e.printStackTrace();
            return AuthResponse.error("Login failed: " + e.getMessage());
        }
    }

    /**
     * Change password for authenticated user
     */
    public boolean changePassword(String userEmail, String currentPassword, String newPassword) {
        try {
            // Find user by email
            User user = userRepository.findByEmail(userEmail).orElse(null);
            if (user == null) {
                return false;
            }

            // Verify current password
            if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
                return false;
            }

            // Update password
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            System.err.println("Error changing password: " + e.getMessage());
            return false;
        }
    }

    /**
     * Update user profile
     */
    public AuthResponse updateProfile(String userEmail, UpdateProfileRequest request) {
        try {
            // Find user by email
            User user = userRepository.findByEmail(userEmail).orElse(null);
            if (user == null) {
                return AuthResponse.error("User not found");
            }

            // Check if email is being changed and if it already exists
            if (!user.getEmail().equals(request.getEmail()) && 
                userRepository.existsByEmail(request.getEmail())) {
                return AuthResponse.error("Email already exists");
            }

            // Update user fields
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setEmail(request.getEmail());
            user.setState(request.getState());
            user.setCity(request.getCity());
            user.setPincode(request.getPincode() != null && !request.getPincode().trim().isEmpty() ? request.getPincode() : null);
            user.setMobileNumber(request.getMobileNumber() != null && !request.getMobileNumber().trim().isEmpty() ? request.getMobileNumber() : null);
            user.setGarageName(request.getGarageName());
            user.setAddressLine1(request.getAddressLine1() != null && !request.getAddressLine1().trim().isEmpty() ? request.getAddressLine1() : null);
            user.setAddressLine2(request.getAddressLine2() != null && !request.getAddressLine2().trim().isEmpty() ? request.getAddressLine2() : null);

            User savedUser = userRepository.save(user);

            // Create updated user info
            AuthResponse.UserInfo userInfo = new AuthResponse.UserInfo(
                    savedUser.getId(),
                    savedUser.getFirstName(),
                    savedUser.getLastName(),
                    savedUser.getEmail(),
                    savedUser.getGarageName(),
                    savedUser.getState(),
                    savedUser.getCity(),
                    savedUser.getAddressLine1(),
                    savedUser.getAddressLine2(),
                    savedUser.getMobileNumber(),
                    savedUser.getPincode(),
                    savedUser.getCreatedAt()
            );

            return AuthResponse.success(null, null, null, userInfo);

        } catch (Exception e) {
            return AuthResponse.error("Profile update failed: " + e.getMessage());
        }
    }
} 