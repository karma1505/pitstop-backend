package com.garage.backend.settings.service;

import com.garage.backend.settings.dto.CreateGarageRequest;
import com.garage.backend.settings.dto.GarageResponse;
import com.garage.backend.settings.entity.Garage;
import com.garage.backend.settings.repository.GarageRepository;
import com.garage.backend.shared.service.GarageContextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class GarageService {

    @Autowired
    private GarageRepository garageRepository;

    @Autowired
    private GarageContextService garageContextService;

    /**
     * Create a new garage
     */
    public GarageResponse createGarage(CreateGarageRequest request) {
        // Check if garage with same business registration number already exists
        if (garageRepository.existsByBusinessRegistrationNumber(request.getBusinessRegistrationNumber())) {
            throw new RuntimeException("Garage with this business registration number already exists");
        }

        // Check if garage with same GST number already exists
        if (garageRepository.existsByGstNumber(request.getGstNumber())) {
            throw new RuntimeException("Garage with this GST number already exists");
        }

        // Get current user ID from authentication context
        UUID currentUserId = garageContextService.getCurrentUserId();

        Garage garage = new Garage();
        garage.setCreatedBy(currentUserId);
        garage.setGarageName(request.getGarageName());
        garage.setBusinessRegistrationNumber(request.getBusinessRegistrationNumber());
        garage.setGstNumber(request.getGstNumber());
        garage.setLogoUrl(request.getLogoUrl());
        garage.setWebsiteUrl(request.getWebsiteUrl());
        garage.setBusinessHours(request.getBusinessHours());
        garage.setHasBranch(request.getHasBranch());
        garage.setIsActive(true);

        Garage savedGarage = garageRepository.save(garage);
        return convertToResponse(savedGarage);
    }

    /**
     * Get garage by ID
     */
    public GarageResponse getGarageById(UUID id) {
        Optional<Garage> garageOptional = garageRepository.findById(id);
        if (garageOptional.isPresent()) {
            return convertToResponse(garageOptional.get());
        } else {
            throw new RuntimeException("Garage not found with ID: " + id);
        }
    }

    /**
     * Get all garages with pagination
     */
    public Page<GarageResponse> getAllGarages(Pageable pageable) {
        Page<Garage> garages = garageRepository.findAll(pageable);
        return garages.map(this::convertToResponse);
    }

    /**
     * Get all active garages
     */
    public List<GarageResponse> getAllActiveGarages() {
        List<Garage> garages = garageRepository.findByIsActiveTrue();
        return garages.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Update garage
     */
    public GarageResponse updateGarage(UUID id, CreateGarageRequest request) {
        Optional<Garage> garageOptional = garageRepository.findById(id);
        if (garageOptional.isEmpty()) {
            throw new RuntimeException("Garage not found with ID: " + id);
        }

        Garage garage = garageOptional.get();

        // Check if business registration number is being changed and if it already exists
        if (!garage.getBusinessRegistrationNumber().equals(request.getBusinessRegistrationNumber()) &&
            garageRepository.existsByBusinessRegistrationNumber(request.getBusinessRegistrationNumber())) {
            throw new RuntimeException("Garage with this business registration number already exists");
        }

        // Check if GST number is being changed and if it already exists
        if (!garage.getGstNumber().equals(request.getGstNumber()) &&
            garageRepository.existsByGstNumber(request.getGstNumber())) {
            throw new RuntimeException("Garage with this GST number already exists");
        }

        garage.setGarageName(request.getGarageName());
        garage.setBusinessRegistrationNumber(request.getBusinessRegistrationNumber());
        garage.setGstNumber(request.getGstNumber());
        garage.setLogoUrl(request.getLogoUrl());
        garage.setWebsiteUrl(request.getWebsiteUrl());
        garage.setBusinessHours(request.getBusinessHours());
        garage.setHasBranch(request.getHasBranch());

        Garage savedGarage = garageRepository.save(garage);
        return convertToResponse(savedGarage);
    }

    /**
     * Deactivate garage
     */
    public GarageResponse deactivateGarage(UUID id) {
        Optional<Garage> garageOptional = garageRepository.findById(id);
        if (garageOptional.isEmpty()) {
            throw new RuntimeException("Garage not found with ID: " + id);
        }

        Garage garage = garageOptional.get();
        garage.setIsActive(false);
        Garage savedGarage = garageRepository.save(garage);
        return convertToResponse(savedGarage);
    }

    /**
     * Activate garage
     */
    public GarageResponse activateGarage(UUID id) {
        Optional<Garage> garageOptional = garageRepository.findById(id);
        if (garageOptional.isEmpty()) {
            throw new RuntimeException("Garage not found with ID: " + id);
        }

        Garage garage = garageOptional.get();
        garage.setIsActive(true);
        Garage savedGarage = garageRepository.save(garage);
        return convertToResponse(savedGarage);
    }

    /**
     * Convert Garage entity to GarageResponse
     */
    private GarageResponse convertToResponse(Garage garage) {
        return new GarageResponse(
                garage.getId(),
                garage.getGarageName(),
                garage.getBusinessRegistrationNumber(),
                garage.getGstNumber(),
                garage.getLogoUrl(),
                garage.getWebsiteUrl(),
                garage.getBusinessHours(),
                garage.getHasBranch(),
                garage.getIsActive(),
                garage.getCreatedAt(),
                garage.getUpdatedAt()
        );
    }
}
