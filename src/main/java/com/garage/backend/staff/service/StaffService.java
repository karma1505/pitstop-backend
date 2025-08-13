package com.garage.backend.staff.service;

import com.garage.backend.shared.enums.Enums;
import com.garage.backend.staff.dto.CreateStaffRequest;
import com.garage.backend.staff.dto.StaffResponse;
import com.garage.backend.staff.entity.Staff;
import com.garage.backend.staff.repository.StaffRepository;
import com.garage.backend.shared.service.GarageContextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class StaffService {

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private GarageContextService garageContextService;

    /**
     * Create a new staff member
     */
    public StaffResponse createStaff(CreateStaffRequest request) {
        // Get current user's garage ID
        UUID garageId = garageContextService.getCurrentUserGarageId();
        
        // Check if staff with same mobile number already exists
        if (staffRepository.existsByMobileNumber(request.getMobileNumber())) {
            throw new RuntimeException("Staff with this mobile number already exists");
        }

        Staff staff = new Staff();
        staff.setGarageId(garageId);
        staff.setFirstName(request.getFirstName());
        staff.setLastName(request.getLastName());
        staff.setMobileNumber(request.getMobileNumber());
        staff.setAadharNumber(request.getAadharNumber());
        staff.setRole(request.getRole());
        staff.setJobsCompleted(0); // Default value for new staff
        staff.setIsActive(true);

        Staff savedStaff = staffRepository.save(staff);
        return convertToResponse(savedStaff);
    }

    /**
     * Get staff by ID
     */
    public StaffResponse getStaffById(UUID id) {
        Optional<Staff> staffOptional = staffRepository.findById(id);
        if (staffOptional.isPresent()) {
            return convertToResponse(staffOptional.get());
        } else {
            throw new RuntimeException("Staff not found with ID: " + id);
        }
    }

    /**
     * Get all staff with pagination and optional role filtering
     */
    public Page<StaffResponse> getAllStaff(Pageable pageable, String role) {
        UUID garageId = garageContextService.getCurrentUserGarageId();
        Page<Staff> staffList;
        
        if (role != null && !role.trim().isEmpty()) {
            // Handle multiple roles separated by commas
            String[] roles = role.split(",");
            if (roles.length == 1) {
                try {
                    Enums.StaffRole staffRole = Enums.StaffRole.valueOf(roles[0].trim().toUpperCase());
                    staffList = staffRepository.findByGarageIdAndRole(garageId, staffRole, pageable);
                } catch (IllegalArgumentException e) {
                    throw new RuntimeException("Invalid staff role: " + roles[0]);
                }
            } else {
                // Multiple roles - we'll need to implement this in repository
                List<Enums.StaffRole> staffRoles = new ArrayList<>();
                for (String roleStr : roles) {
                    try {
                        staffRoles.add(Enums.StaffRole.valueOf(roleStr.trim().toUpperCase()));
                    } catch (IllegalArgumentException e) {
                        throw new RuntimeException("Invalid staff role: " + roleStr);
                    }
                }
                staffList = staffRepository.findByGarageIdAndRoleIn(garageId, staffRoles, pageable);
            }
        } else {
            staffList = staffRepository.findByGarageId(garageId, pageable);
        }
        return staffList.map(this::convertToResponse);
    }

    /**
     * Get all active staff
     */
    public List<StaffResponse> getAllActiveStaff() {
        List<Staff> staffList = staffRepository.findByIsActiveTrue();
        return staffList.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Get staff by role
     */
    public List<StaffResponse> getStaffByRole(String role) {
        try {
            Enums.StaffRole staffRole = Enums.StaffRole.valueOf(role.toUpperCase());
            List<Staff> staffList = staffRepository.findActiveByRole(staffRole);
            return staffList.stream()
                    .map(this::convertToResponse)
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid staff role: " + role);
        }
    }

    /**
     * Get staff for current user's garage
     */
    public List<StaffResponse> getCurrentUserStaff() {
        UUID garageId = garageContextService.getCurrentUserGarageId();
        List<Staff> staffList = staffRepository.findByGarageId(garageId);
        return staffList.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Update staff
     */
    public StaffResponse updateStaff(UUID id, CreateStaffRequest request) {
        Optional<Staff> staffOptional = staffRepository.findById(id);
        if (staffOptional.isEmpty()) {
            throw new RuntimeException("Staff not found with ID: " + id);
        }

        Staff staff = staffOptional.get();

        // Check if mobile number is being changed and if it already exists
        if (!staff.getMobileNumber().equals(request.getMobileNumber()) && 
            staffRepository.existsByMobileNumber(request.getMobileNumber())) {
            throw new RuntimeException("Staff with this mobile number already exists");
        }

        // Get current user's garage ID
        UUID garageId = garageContextService.getCurrentUserGarageId();
        
        // Verify the staff belongs to current user's garage
        if (!staff.getGarageId().equals(garageId)) {
            throw new RuntimeException("Staff not found or access denied");
        }
        
        staff.setGarageId(garageId);
        staff.setFirstName(request.getFirstName());
        staff.setLastName(request.getLastName());
        staff.setMobileNumber(request.getMobileNumber());
        staff.setAadharNumber(request.getAadharNumber());
        staff.setRole(request.getRole());

        Staff savedStaff = staffRepository.save(staff);
        return convertToResponse(savedStaff);
    }

    /**
     * Deactivate staff
     */
    public StaffResponse deactivateStaff(UUID id) {
        Optional<Staff> staffOptional = staffRepository.findById(id);
        if (staffOptional.isEmpty()) {
            throw new RuntimeException("Staff not found with ID: " + id);
        }

        Staff staff = staffOptional.get();
        staff.setIsActive(false);
        Staff savedStaff = staffRepository.save(staff);
        return convertToResponse(savedStaff);
    }

    /**
     * Update staff status (activate/deactivate)
     */
    public StaffResponse updateStaffStatus(UUID id, String status) {
        Optional<Staff> staffOptional = staffRepository.findById(id);
        if (staffOptional.isEmpty()) {
            throw new RuntimeException("Staff not found with ID: " + id);
        }

        Staff staff = staffOptional.get();
        
        // Verify the staff belongs to current user's garage
        UUID garageId = garageContextService.getCurrentUserGarageId();
        if (!staff.getGarageId().equals(garageId)) {
            throw new RuntimeException("Staff not found or access denied");
        }
        
        if ("ACTIVATE".equalsIgnoreCase(status)) {
            staff.setIsActive(true);
        } else if ("DEACTIVATE".equalsIgnoreCase(status)) {
            staff.setIsActive(false);
        } else {
            throw new RuntimeException("Invalid status. Use 'ACTIVATE' or 'DEACTIVATE'");
        }
        
        Staff savedStaff = staffRepository.save(staff);
        return convertToResponse(savedStaff);
    }

    /**
     * Convert Staff entity to StaffResponse
     */
    private StaffResponse convertToResponse(Staff staff) {
        // Only include jobsCompleted for MECHANIC and MANAGER roles
        Integer jobsCompleted = null;
        if (staff.getRole() == Enums.StaffRole.MECHANIC || staff.getRole() == Enums.StaffRole.MANAGER) {
            jobsCompleted = staff.getJobsCompleted();
        }
        
        return new StaffResponse(
                staff.getId(),
                staff.getFirstName(),
                staff.getLastName(),
                staff.getMobileNumber(),
                staff.getAadharNumber(),
                staff.getRole(),
                staff.getIsActive(),
                jobsCompleted,
                staff.getCreatedAt(),
                staff.getUpdatedAt()
        );
    }
}
