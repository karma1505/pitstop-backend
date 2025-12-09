package com.garage.backend.service.controller;

import com.garage.backend.service.dto.*;
import com.garage.backend.service.service.ServiceCategoryService;
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
 * REST Controller for Service Category Management
 * Provides endpoints for CRUD operations on service categories
 */
@RestController
@RequestMapping("/api/v1/admin/service-categories")
public class ServiceCategoryController {

    @Autowired
    private ServiceCategoryService serviceCategoryService;

    /**
     * Health check endpoint
     * 
     * @return String indicating service is running
     */
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Service Category API is running");
    }

    /**
     * Create a new service category
     * 
     * Scenario: Create Service Category via API
     *   Given a garage owner wants to add a new service category
     *   When they send a POST request with valid category information
     *   Then the system should create the service category
     *   And return 201 Created with the category details
     *   But if the category code already exists
     *   Then the system should return 400 Bad Request
     *   And if validation fails
     *   Then the system should return 400 Bad Request
     * 
     * @param request CreateServiceCategoryRequest with category details
     * @return ResponseEntity<ServiceCategoryResponse> with created category
     */
    @PostMapping
    public ResponseEntity<ServiceCategoryResponse> createServiceCategory(
            @Valid @RequestBody CreateServiceCategoryRequest request) {
        try {
            ServiceCategoryResponse response = serviceCategoryService.createServiceCategory(request);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Update an existing service category
     * 
     * Scenario: Update Service Category via API
     *   Given a garage owner wants to update service category details
     *   When they send a PATCH request with valid updated information
     *   Then the system should update the service category
     *   And return 200 OK with updated category information
     *   But if category does not exist
     *   Then the system should return 404 Not Found
     *   And if validation fails
     *   Then the system should return 400 Bad Request
     * 
     * @param id Category ID
     * @param request UpdateServiceCategoryRequest with updated details
     * @return ResponseEntity<ServiceCategoryResponse> with updated category
     */
    @PatchMapping("/{id}")
    public ResponseEntity<ServiceCategoryResponse> updateServiceCategory(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateServiceCategoryRequest request) {
        try {
            ServiceCategoryResponse response = serviceCategoryService.updateServiceCategory(id, request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found")) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get service category by ID
     * 
     * Scenario: Get Service Category Details via API
     *   Given a garage owner wants to view service category details
     *   When they send a GET request with a valid category ID
     *   Then the system should return the category details
     *   And return 200 OK
     *   But if category does not exist
     *   Then the system should return 404 Not Found
     * 
     * @param id Category ID
     * @return ResponseEntity<ServiceCategoryResponse> with category details
     */
    @GetMapping("/{id}")
    public ResponseEntity<ServiceCategoryResponse> getServiceCategoryById(@PathVariable UUID id) {
        try {
            ServiceCategoryResponse response = serviceCategoryService.getServiceCategoryById(id);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get all service categories with pagination and filtering
     * 
     * Scenario: List Service Categories via API
     *   Given a garage owner wants to view all service categories
     *   When they send a GET request with optional filters
     *   Then the system should return a paginated list of categories
     *   And return 200 OK
     *   And support filtering by name, code, and active status
     *   And support pagination and sorting
     * 
     * @param categoryName Filter by category name (optional)
     * @param categoryCode Filter by category code (optional)
     * @param isActive Filter by active status (optional)
     * @param page Page number (default: 0)
     * @param size Page size (default: 20)
     * @param sortBy Sort field (default: categoryName)
     * @param sortDir Sort direction (default: asc)
     * @return ResponseEntity<Page<ServiceCategoryListResponse>> with paginated categories
     */
    @GetMapping
    public ResponseEntity<Page<ServiceCategoryListResponse>> getAllServiceCategories(
            @RequestParam(required = false) String categoryName,
            @RequestParam(required = false) String categoryCode,
            @RequestParam(required = false) Boolean isActive,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "categoryName") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        try {
            Sort.Direction direction = sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
            Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

            Page<ServiceCategoryListResponse> response = serviceCategoryService.getAllServiceCategories(
                    categoryName, categoryCode, isActive, pageable);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deactivate a service category
     * 
     * Scenario: Deactivate Service Category via API
     *   Given a garage owner wants to deactivate a service category
     *   When they send a PATCH request to the deactivate endpoint
     *   Then the system should set isActive to false
     *   And return 200 OK with updated category
     *   But if category does not exist
     *   Then the system should return 404 Not Found
     * 
     * @param id Category ID
     * @return ResponseEntity<ServiceCategoryResponse> with updated category
     */
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<ServiceCategoryResponse> deactivateServiceCategory(@PathVariable UUID id) {
        try {
            ServiceCategoryResponse response = serviceCategoryService.deactivateServiceCategory(id);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Reactivate a service category
     * 
     * Scenario: Reactivate Service Category via API
     *   Given a garage owner wants to reactivate a service category
     *   When they send a PATCH request to the reactivate endpoint
     *   Then the system should set isActive to true
     *   And return 200 OK with updated category
     *   But if category does not exist
     *   Then the system should return 404 Not Found
     * 
     * @param id Category ID
     * @return ResponseEntity<ServiceCategoryResponse> with updated category
     */
    @PatchMapping("/{id}/reactivate")
    public ResponseEntity<ServiceCategoryResponse> reactivateServiceCategory(@PathVariable UUID id) {
        try {
            ServiceCategoryResponse response = serviceCategoryService.reactivateServiceCategory(id);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
