package com.garage.backend.staff.controller;

import com.garage.backend.staff.dto.CreateStaffRequest;
import com.garage.backend.staff.dto.StaffResponse;
import com.garage.backend.staff.service.StaffService;
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
@RequestMapping("/admin/staff")
@CrossOrigin(origins = "*")
public class StaffController {

    @Autowired
    private StaffService staffService;

    /**
     * Create a new staff member
     */
    @PostMapping
    public ResponseEntity<StaffResponse> createStaff(@Valid @RequestBody CreateStaffRequest request) {
        try {
            StaffResponse response = staffService.createStaff(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get staff by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<StaffResponse> getStaffById(@PathVariable UUID id) {
        try {
            StaffResponse response = staffService.getStaffById(id);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get all staff with pagination and optional role filtering
     */
    @GetMapping
    public ResponseEntity<Page<StaffResponse>> getAllStaff(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String role) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<StaffResponse> response = staffService.getAllStaff(pageable, role);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get all active staff
     */
    @GetMapping("/active")
    public ResponseEntity<List<StaffResponse>> getAllActiveStaff() {
        try {
            List<StaffResponse> response = staffService.getAllActiveStaff();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



    /**
     * Get staff for current user's garage
     */
    @GetMapping("/my-staff")
    public ResponseEntity<List<StaffResponse>> getCurrentUserStaff() {
        try {
            List<StaffResponse> response = staffService.getCurrentUserStaff();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Update staff
     */
    @PatchMapping("/{id}")
    public ResponseEntity<StaffResponse> updateStaff(
            @PathVariable UUID id,
            @Valid @RequestBody CreateStaffRequest request,
            @RequestParam(required = false) String status) {
        try {
            StaffResponse response;
            if (status != null) {
                response = staffService.updateStaffStatus(id, status);
            } else {
                response = staffService.updateStaff(id, request);
            }
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Health check endpoint
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Staff API is running - " + System.currentTimeMillis());
    }
}
