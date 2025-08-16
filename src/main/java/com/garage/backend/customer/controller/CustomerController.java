package com.garage.backend.customer.controller;

import com.garage.backend.customer.dto.CreateCustomerRequest;
import com.garage.backend.customer.dto.UpdateCustomerRequest;
import com.garage.backend.customer.dto.CustomerResponse;
import com.garage.backend.customer.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

/**
 * REST Controller for customer management operations
 * 
 * Provides endpoints for:
 * - Creating new customers
 * - Updating existing customers
 * - Retrieving customer information
 * - Searching customers
 * - Managing customer lifecycle
 * 
 * All endpoints follow RESTful conventions and return appropriate HTTP status codes
 */
@RestController
@RequestMapping("/admin/customers")
@CrossOrigin(origins = "*")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    /**
     * Create a new customer
     * 
     * Scenario: Create New Customer via API
     *   Given a garage owner wants to add a new customer via API
     *   When they send a POST request with valid customer details
     *   Then the system should create a customer record
     *   And return 201 Created with customer information
     *   But if validation fails
     *   Then the system should return 400 Bad Request
     *   And if phone number already exists
     *   Then the system should return 400 Bad Request
     * 
     * @param request CreateCustomerRequest with customer details
     * @return ResponseEntity<CustomerResponse> with created customer
     */
    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(@Valid @RequestBody CreateCustomerRequest request) {
        try {
            CustomerResponse response = customerService.createCustomer(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            // Return bad request for business logic errors (duplicate phone, etc.)
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            // Return internal server error for unexpected errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Update an existing customer
     * 
     * Scenario: Update Customer via API
     *   Given a garage owner wants to update customer details via API
     *   When they send a PATCH request with valid updated information
     *   Then the system should update the customer record
     *   And return 200 OK with updated customer information
     *   But if customer does not exist
     *   Then the system should return 404 Not Found
     *   And if validation fails
     *   Then the system should return 400 Bad Request
     * 
     * @param id Customer ID
     * @param request UpdateCustomerRequest with updated details
     * @return ResponseEntity<CustomerResponse> with updated customer
     */
    @PatchMapping("/{id}")
    public ResponseEntity<CustomerResponse> updateCustomer(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateCustomerRequest request) {
        try {
            CustomerResponse response = customerService.updateCustomer(id, request);
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
     * Get customer by ID
     * 
     * Scenario: Retrieve Customer by ID via API
     *   Given a garage owner wants to view customer details via API
     *   When they send a GET request with a valid customer ID
     *   Then the system should return 200 OK with customer information
     *   But if customer does not exist
     *   Then the system should return 404 Not Found
     * 
     * @param id Customer ID
     * @return ResponseEntity<CustomerResponse> with customer details
     */
    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable UUID id) {
        try {
            CustomerResponse response = customerService.getCustomerById(id);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get all customers with pagination, sorting, and filtering
     * 
     * Scenario: List All Customers with Filters via API
     *   Given a garage owner wants to view customers with optional filters via API
     *   When they send a GET request with optional pagination and filter parameters
     *   Then the system should return 200 OK with filtered and paginated customer list
     *   And include total count and page information
     *   And sort by creation date descending by default
     * 
     * @param page Page number (default: 0)
     * @param size Page size (default: 20)
     * @param sortBy Sort field (default: createdAt)
     * @param sortDir Sort direction (default: desc)
     * @param customerName Customer name filter (partial match)
     * @param vehicleRegistrationNumber Vehicle registration number filter (partial match)
     * @param vehicleBrand Vehicle brand/make filter (partial match)
     * @param date Specific date filter (yyyy-mm-dd)
     * @param from Start date filter (yyyy-mm-dd)
     * @param to End date filter (yyyy-mm-dd)
     * @return ResponseEntity<Page<CustomerResponse>> with filtered and paginated customers
     */
    @GetMapping
    public ResponseEntity<Page<CustomerResponse>> getAllCustomers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir,
            @RequestParam(required = false) String customerName,
            @RequestParam(required = false) String vehicleRegistrationNumber,
            @RequestParam(required = false) String vehicleBrand,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        try {
            // Create sort object
            Sort sort = sortDir.equalsIgnoreCase("desc") 
                ? Sort.by(sortBy).descending() 
                : Sort.by(sortBy).ascending();
            
            Pageable pageable = PageRequest.of(page, size, sort);
            Page<CustomerResponse> response = customerService.getAllCustomers(pageable, customerName, 
                    vehicleRegistrationNumber, vehicleBrand, date, from, to);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



    /**
     * Health check endpoint
     * 
     * Scenario: Health Check
     *   Given the system needs to verify customer service health
     *   When a health check request is made
     *   Then the system should return 200 OK with service status
     * 
     * @return ResponseEntity<String> with health status
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Customer API is running - " + System.currentTimeMillis());
    }
}