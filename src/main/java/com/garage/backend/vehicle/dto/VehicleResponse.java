package com.garage.backend.vehicle.dto;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Response DTO for vehicle operations
 * 
 * Scenario: Return Vehicle Information
 *   Given a vehicle operation is performed
 *   When the operation is successful
 *   Then the system should return vehicle details
 *   And include creation and update timestamps
 */
public class VehicleResponse {

    private UUID id;
    private String registrationNumber;
    private String make;
    private String model;
    private Integer year;
    private String vinNumber;
    private String engineNumber;
    private String fuelType;
    private String transmissionType;
    private String color;
    private UUID customerId;
    private String customerName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructors
    public VehicleResponse() {}

    public VehicleResponse(UUID id, String registrationNumber, String make, String model, Integer year,
                          String vinNumber, String engineNumber, String fuelType, String transmissionType,
                          String color, UUID customerId, String customerName,
                          LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.registrationNumber = registrationNumber;
        this.make = make;
        this.model = model;
        this.year = year;
        this.vinNumber = vinNumber;
        this.engineNumber = engineNumber;
        this.fuelType = fuelType;
        this.transmissionType = transmissionType;
        this.color = color;
        this.customerId = customerId;
        this.customerName = customerName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getVinNumber() {
        return vinNumber;
    }

    public void setVinNumber(String vinNumber) {
        this.vinNumber = vinNumber;
    }

    public String getEngineNumber() {
        return engineNumber;
    }

    public void setEngineNumber(String engineNumber) {
        this.engineNumber = engineNumber;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getTransmissionType() {
        return transmissionType;
    }

    public void setTransmissionType(String transmissionType) {
        this.transmissionType = transmissionType;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "VehicleResponse{" +
                "id=" + id +
                ", registrationNumber='" + registrationNumber + '\'' +
                ", make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                ", vinNumber='" + vinNumber + '\'' +
                ", engineNumber='" + engineNumber + '\'' +
                ", fuelType='" + fuelType + '\'' +
                ", transmissionType='" + transmissionType + '\'' +
                ", color='" + color + '\'' +
                ", customerId=" + customerId +
                ", customerName='" + customerName + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}

