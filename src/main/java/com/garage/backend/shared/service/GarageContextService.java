package com.garage.backend.shared.service;

import com.garage.backend.authentication.entity.User;
import com.garage.backend.authentication.repository.UserRepository;
import com.garage.backend.settings.entity.Garage;
import com.garage.backend.settings.repository.GarageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GarageContextService {

    @Autowired
    private GarageRepository garageRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Get the garage ID for the currently authenticated user
     */
    public UUID getCurrentUserGarageId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("User not authenticated");
        }

        String userEmail = authentication.getName();
        Garage garage = garageRepository.findByUserEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("No garage found for user: " + userEmail));

        return garage.getId();
    }

    /**
     * Get the current user ID from authentication context
     */
    public UUID getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("User not authenticated");
        }

        String userEmail = authentication.getName();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found: " + userEmail));
        
        return user.getId();
    }

    /**
     * Get the garage for the currently authenticated user
     */
    public Garage getCurrentUserGarage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("User not authenticated");
        }

        String userEmail = authentication.getName();
        return garageRepository.findByUserEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("No garage found for user: " + userEmail));
    }
}
