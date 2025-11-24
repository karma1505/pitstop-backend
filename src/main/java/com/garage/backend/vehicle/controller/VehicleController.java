package com.garage.backend.vehicle.controller;

import com.garage.backend.vehicle.dto.CreateVehicleRequest;
import com.garage.backend.vehicle.dto.UpdateVehicleRequest;
import com.garage.backend.vehicle.dto.VehicleResponse;
import com.garage.backend.vehicle.service.VehicleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * REST Controller for vehicle management operations
 * 
 * Provides endpoints for:
 * - Creating new vehicles
 * - Updating existing vehicles
 * - Retrieving vehicle information
 * - Searching vehicles
 * - Managing vehicle lifecycle
 * 
 * All endpoints follow RESTful conventions and return appropriate HTTP status codes
 */
@RestController
@RequestMapping("/admin/vehicles")
@CrossOrigin(origins = "*")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    /**
     * Create a new vehicle
     * 
     * Scenario: Create New Vehicle via API
     *   Given a garage owner wants to add a new vehicle via API
     *   When they send a POST request with valid vehicle details
     *   Then the system should create a vehicle record
     *   And return 201 Created with vehicle information
     *   But if validation fails
     *   Then the system should return 400 Bad Request
     *   And if registration number already exists
     *   Then the system should return 400 Bad Request
     * 
     * @param request CreateVehicleRequest with vehicle details
     * @return ResponseEntity<VehicleResponse> with created vehicle
     */
    @PostMapping
    public ResponseEntity<VehicleResponse> createVehicle(@Valid @RequestBody CreateVehicleRequest request) {
        try {
            VehicleResponse response = vehicleService.createVehicle(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            // Return bad request for business logic errors (duplicate registration, etc.)
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            // Return internal server error for unexpected errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Update an existing vehicle
     * 
     * Scenario: Update Vehicle via API
     *   Given a garage owner wants to update vehicle details via API
     *   When they send a PATCH request with valid updated information
     *   Then the system should update the vehicle record
     *   And return 200 OK with updated vehicle information
     *   But if vehicle does not exist
     *   Then the system should return 404 Not Found
     *   And if validation fails
     *   Then the system should return 400 Bad Request
     * 
     * @param id Vehicle ID
     * @param request UpdateVehicleRequest with updated details
     * @return ResponseEntity<VehicleResponse> with updated vehicle
     */
    @PatchMapping("/{id}")
    public ResponseEntity<VehicleResponse> updateVehicle(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateVehicleRequest request) {
        try {
            VehicleResponse response = vehicleService.updateVehicle(id, request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            // Check if it's a not found error or validation error
            if (e.getMessage().contains("not found")) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get vehicle by ID
     * 
     * Scenario: Retrieve Vehicle by ID via API
     *   Given a garage owner wants to view vehicle details via API
     *   When they send a GET request with a valid vehicle ID
     *   Then the system should return 200 OK with vehicle information
     *   But if vehicle does not exist
     *   Then the system should return 404 Not Found
     * 
     * @param id Vehicle ID
     * @return ResponseEntity<VehicleResponse> with vehicle details
     */
    @GetMapping("/{id}")
    public ResponseEntity<VehicleResponse> getVehicleById(@PathVariable UUID id) {
        try {
            VehicleResponse response = vehicleService.getVehicleById(id);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get all vehicles with pagination, sorting, and filtering
     * 
     * Scenario: List All Vehicles with Filters via API
     *   Given a garage owner wants to view vehicles with optional filters via API
     *   When they send a GET request with optional pagination and filter parameters
     *   Then the system should return 200 OK with filtered and paginated vehicle list
     *   And include total count and page information
     *   And sort by creation date descending by default
     * 
     * @param page Page number (default: 0)
     * @param size Page size (default: 20)
     * @param sortBy Sort field (default: createdAt)
     * @param sortDir Sort direction (default: desc)
     * @param registrationNumber Vehicle registration number filter (partial match)
     * @param make Vehicle make filter
     * @param model Vehicle model filter
     * @param customerId Customer ID filter
     * @return ResponseEntity<Page<VehicleResponse>> with filtered and paginated vehicles
     */
    @GetMapping
    public ResponseEntity<Page<VehicleResponse>> getAllVehicles(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir,
            @RequestParam(required = false) String registrationNumber,
            @RequestParam(required = false) String make,
            @RequestParam(required = false) String model,
            @RequestParam(required = false) UUID customerId) {
        try {
            // Create sort object
            Sort sort = sortDir.equalsIgnoreCase("desc") 
                ? Sort.by(sortBy).descending() 
                : Sort.by(sortBy).ascending();
            
            Pageable pageable = PageRequest.of(page, size, sort);
            Page<VehicleResponse> response = vehicleService.getAllVehicles(pageable, registrationNumber, 
                    make, model, customerId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Delete vehicle by ID
     * 
     * Scenario: Delete Vehicle via API
     *   Given a garage owner wants to delete a vehicle via API
     *   When they send a DELETE request with a valid vehicle ID
     *   Then the system should delete the vehicle record
     *   And return 200 OK
     *   But if vehicle does not exist
     *   Then the system should return 404 Not Found
     * 
     * @param id Vehicle ID
     * @return ResponseEntity<Void>
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable UUID id) {
        try {
            vehicleService.deleteVehicle(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Health check endpoint
     * 
     * Scenario: Health Check
     *   Given the system needs to verify vehicle service health
     *   When a health check request is made
     *   Then the system should return 200 OK with service status
     * 
     * @return ResponseEntity<String> with health status
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Vehicle API is running - " + System.currentTimeMillis());
    }
}

