package com.garage.backend.settings.controller;

import com.garage.backend.settings.dto.CreateGarageRequest;
import com.garage.backend.settings.dto.GarageResponse;
import com.garage.backend.settings.service.GarageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin/garage")
@CrossOrigin(origins = "*")
public class GarageController {

    @Autowired
    private GarageService garageService;

    /**
     * Create a new garage
     */
    @PostMapping
    public ResponseEntity<GarageResponse> createGarage(@Valid @RequestBody CreateGarageRequest request) {
        GarageResponse response = garageService.createGarage(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Get garage by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<GarageResponse> getGarageById(@PathVariable UUID id) {
        GarageResponse response = garageService.getGarageById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Get all garages with pagination
     */
    @GetMapping
    public ResponseEntity<Page<GarageResponse>> getAllGarages(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<GarageResponse> response = garageService.getAllGarages(pageable);
        return ResponseEntity.ok(response);
    }

    /**
     * Get all active garages
     */
    @GetMapping("/active")
    public ResponseEntity<List<GarageResponse>> getAllActiveGarages() {
        List<GarageResponse> response = garageService.getAllActiveGarages();
        return ResponseEntity.ok(response);
    }

    /**
     * Update garage
     */
    @PatchMapping("/{id}")
    public ResponseEntity<GarageResponse> updateGarage(
            @PathVariable UUID id,
            @Valid @RequestBody CreateGarageRequest request) {
        GarageResponse response = garageService.updateGarage(id, request);
        return ResponseEntity.ok(response);
    }

    /**
     * Deactivate garage
     */
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<GarageResponse> deactivateGarage(@PathVariable UUID id) {
        GarageResponse response = garageService.deactivateGarage(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Activate garage
     */
    @PatchMapping("/{id}/activate")
    public ResponseEntity<GarageResponse> activateGarage(@PathVariable UUID id) {
        GarageResponse response = garageService.activateGarage(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Health check endpoint
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Garage API is running - " + System.currentTimeMillis());
    }
}
