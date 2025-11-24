package com.garage.backend.vehicle.service;

import com.garage.backend.customer.entity.Customers;
import com.garage.backend.customer.repository.CustomersRepository;
import com.garage.backend.vehicle.dto.CreateVehicleRequest;
import com.garage.backend.vehicle.dto.UpdateVehicleRequest;
import com.garage.backend.vehicle.dto.VehicleResponse;
import com.garage.backend.vehicle.entity.Vehicles;
import com.garage.backend.vehicle.repository.VehiclesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service class for vehicle management operations
 * 
 * Handles all business logic related to vehicle operations including:
 * - Creating new vehicles
 * - Updating existing vehicles
 * - Retrieving vehicle information
 * - Searching vehicles
 * - Managing vehicle lifecycle
 */
@Service
@Transactional
public class VehicleService {

    @Autowired
    private VehiclesRepository vehiclesRepository;

    @Autowired
    private CustomersRepository customersRepository;

    /**
     * Create a new vehicle
     * 
     * Scenario: Create New Vehicle
     *   Given a garage owner wants to add a new vehicle
     *   When they provide valid vehicle details
     *   Then the system should create a vehicle record
     *   And return the vehicle information
     *   But if registration number already exists
     *   Then the system should throw an exception
     */
    public VehicleResponse createVehicle(CreateVehicleRequest request) {
        // Check if vehicle with registration number already exists
        if (vehiclesRepository.existsByRegistrationNumber(request.getRegistrationNumber())) {
            throw new RuntimeException("Vehicle with registration number " + request.getRegistrationNumber() + " already exists");
        }

        // Get customer
        Customers customer = customersRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + request.getCustomerId()));

        // Create new vehicle entity
        Vehicles vehicle = new Vehicles();
        vehicle.setCustomer(customer);
        vehicle.setRegistrationNumber(request.getRegistrationNumber());
        vehicle.setMake(request.getMake());
        vehicle.setModel(request.getModel());
        vehicle.setYear(request.getYear());
        vehicle.setVin(request.getVinNumber());
        vehicle.setEngineNumber(request.getEngineNumber());
        vehicle.setFuelType(request.getFuelType());
        vehicle.setTransmissionType(request.getTransmissionType());

        Vehicles savedVehicle = vehiclesRepository.save(vehicle);
        return convertToResponse(savedVehicle);
    }

    /**
     * Update an existing vehicle
     * 
     * Scenario: Update Vehicle
     *   Given a garage owner wants to update vehicle details
     *   When they provide updated vehicle information
     *   Then the system should update the vehicle record
     *   And return the updated vehicle information
     *   But if vehicle does not exist
     *   Then the system should throw an exception
     */
    public VehicleResponse updateVehicle(UUID id, UpdateVehicleRequest request) {
        Vehicles vehicle = vehiclesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found with ID: " + id));

        // Check if registration number is being changed and if it conflicts
        if (!vehicle.getRegistrationNumber().equals(request.getRegistrationNumber())) {
            if (vehiclesRepository.existsByRegistrationNumber(request.getRegistrationNumber())) {
                throw new RuntimeException("Vehicle with registration number " + request.getRegistrationNumber() + " already exists");
            }
        }

        // Update customer if provided
        if (request.getCustomerId() != null) {
            Customers customer = customersRepository.findById(request.getCustomerId())
                    .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + request.getCustomerId()));
            vehicle.setCustomer(customer);
        }

        // Update vehicle fields
        vehicle.setRegistrationNumber(request.getRegistrationNumber());
        vehicle.setMake(request.getMake());
        vehicle.setModel(request.getModel());
        vehicle.setYear(request.getYear());
        vehicle.setVin(request.getVinNumber());
        vehicle.setEngineNumber(request.getEngineNumber());
        vehicle.setFuelType(request.getFuelType());
        vehicle.setTransmissionType(request.getTransmissionType());

        Vehicles updatedVehicle = vehiclesRepository.save(vehicle);
        return convertToResponse(updatedVehicle);
    }

    /**
     * Get vehicle by ID
     * 
     * Scenario: Get Vehicle by ID
     *   Given a garage owner wants to view vehicle details
     *   When they provide a valid vehicle ID
     *   Then the system should return vehicle information
     *   But if vehicle does not exist
     *   Then the system should throw an exception
     */
    @Transactional(readOnly = true)
    public VehicleResponse getVehicleById(UUID id) {
        Vehicles vehicle = vehiclesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found with ID: " + id));
        return convertToResponse(vehicle);
    }

    /**
     * Get all vehicles with pagination, sorting, and filtering
     * 
     * Scenario: List All Vehicles with Filters
     *   Given a garage owner wants to view vehicles with optional filters
     *   When they request vehicle list with optional filters and pagination
     *   Then the system should return filtered and paginated vehicle list
     *   And include total count and page information
     */
    @Transactional(readOnly = true)
    public Page<VehicleResponse> getAllVehicles(Pageable pageable, String registrationNumber,
                                               String make, String model, UUID customerId) {
        List<Vehicles> allVehicles;

        // Apply filters if any are provided
        if (registrationNumber != null && !registrationNumber.trim().isEmpty()) {
            allVehicles = vehiclesRepository.findByRegistrationNumberContainingIgnoreCase(registrationNumber);
        } else if (make != null && !make.trim().isEmpty() && model != null && !model.trim().isEmpty()) {
            allVehicles = vehiclesRepository.findByMakeAndModel(make, model);
        } else if (make != null && !make.trim().isEmpty()) {
            allVehicles = vehiclesRepository.findByMake(make);
        } else if (customerId != null) {
            allVehicles = vehiclesRepository.findByCustomerId(customerId);
        } else {
            // No filters, use repository pagination directly
            Page<Vehicles> vehiclesPage = vehiclesRepository.findAll(pageable);
            List<VehicleResponse> vehicleResponses = vehiclesPage.getContent().stream()
                    .map(this::convertToResponse)
                    .collect(Collectors.toList());
            return new PageImpl<>(vehicleResponses, pageable, vehiclesPage.getTotalElements());
        }

        // Apply additional customerId filter if needed (when not already filtered by customerId)
        // Only apply if we used registrationNumber, make, or model filters (not customerId)
        boolean usedCustomerFilter = (registrationNumber == null || registrationNumber.trim().isEmpty()) &&
                                     (make == null || make.trim().isEmpty()) &&
                                     (model == null || model.trim().isEmpty());
        
        if (customerId != null && !usedCustomerFilter) {
            allVehicles = allVehicles.stream()
                    .filter(v -> v.getCustomer() != null && v.getCustomer().getId().equals(customerId))
                    .collect(Collectors.toList());
        }

        // Convert to response DTOs
        List<VehicleResponse> allVehicleResponses = allVehicles.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());

        // Apply manual pagination for filtered results
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), allVehicleResponses.size());
        List<VehicleResponse> pageContent = allVehicleResponses.subList(start, end);

        return new PageImpl<>(pageContent, pageable, allVehicleResponses.size());
    }

    /**
     * Delete vehicle by ID
     * 
     * Scenario: Delete Vehicle
     *   Given a garage owner wants to delete a vehicle
     *   When they provide a valid vehicle ID
     *   Then the system should delete the vehicle record
     *   But if vehicle does not exist
     *   Then the system should throw an exception
     */
    public void deleteVehicle(UUID id) {
        if (!vehiclesRepository.existsById(id)) {
            throw new RuntimeException("Vehicle not found with ID: " + id);
        }
        vehiclesRepository.deleteById(id);
    }

    /**
     * Convert Vehicle entity to VehicleResponse DTO
     * 
     * @param vehicle Vehicle entity
     * @return VehicleResponse DTO
     */
    private VehicleResponse convertToResponse(Vehicles vehicle) {
        String customerName = null;
        if (vehicle.getCustomer() != null) {
            customerName = vehicle.getCustomer().getName();
        }

        return new VehicleResponse(
                vehicle.getId(),
                vehicle.getRegistrationNumber(),
                vehicle.getMake(),
                vehicle.getModel(),
                vehicle.getYear(),
                vehicle.getVin(),
                vehicle.getEngineNumber(),
                vehicle.getFuelType(),
                vehicle.getTransmissionType(),
                null, // color - not in entity yet
                vehicle.getCustomer() != null ? vehicle.getCustomer().getId() : null,
                customerName,
                vehicle.getCreatedAt(),
                vehicle.getUpdatedAt()
        );
    }
}

