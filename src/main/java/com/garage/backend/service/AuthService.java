package com.garage.backend.service;

import com.garage.backend.dto.AuthResponse;
import com.garage.backend.dto.LoginRequest;
import com.garage.backend.dto.RegisterRequest;
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
                    user.getCreatedAt()
            );

            return AuthResponse.success(token, refreshToken, jwtUtil.getExpiration(), userInfo);

        } catch (Exception e) {
            return AuthResponse.error("Invalid email or password");
        }
    }
} 