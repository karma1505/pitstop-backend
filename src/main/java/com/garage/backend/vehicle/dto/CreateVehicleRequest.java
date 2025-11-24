package com.garage.backend.vehicle.dto;

import jakarta.validation.constraints.*;

/**
 * Request DTO for creating a new vehicle
 * 
 * Scenario: Create Vehicle
 *   Given a garage owner wants to add a new vehicle
 *   When they provide vehicle details
 *   Then the system should create a vehicle record
 *   And return the vehicle information
 */
public class CreateVehicleRequest {

    @NotBlank(message = "Registration number is required")
    @Size(max = 20, message = "Registration number must not exceed 20 characters")
    private String registrationNumber;

    @Size(max = 50, message = "Make must not exceed 50 characters")
    private String make;

    @Size(max = 50, message = "Model must not exceed 50 characters")
    private String model;

    @Min(value = 1900, message = "Year must be at least 1900")
    @Max(value = 2100, message = "Year must not exceed 2100")
    private Integer year;

    @Size(max = 17, message = "VIN must not exceed 17 characters")
    private String vinNumber;

    @Size(max = 20, message = "Engine number must not exceed 20 characters")
    private String engineNumber;

    @Size(max = 20, message = "Fuel type must not exceed 20 characters")
    private String fuelType;

    @Size(max = 20, message = "Transmission type must not exceed 20 characters")
    private String transmissionType;

    @Size(max = 50, message = "Color must not exceed 50 characters")
    private String color;

    @NotNull(message = "Customer ID is required")
    private java.util.UUID customerId;

    // Constructors
    public CreateVehicleRequest() {}

    public CreateVehicleRequest(String registrationNumber, String make, String model, Integer year,
                               String vinNumber, String engineNumber, String fuelType, String transmissionType,
                               String color, java.util.UUID customerId) {
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
    }

    // Getters and Setters
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

    public java.util.UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(java.util.UUID customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "CreateVehicleRequest{" +
                "registrationNumber='" + registrationNumber + '\'' +
                ", make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                ", vinNumber='" + vinNumber + '\'' +
                ", engineNumber='" + engineNumber + '\'' +
                ", fuelType='" + fuelType + '\'' +
                ", transmissionType='" + transmissionType + '\'' +
                ", color='" + color + '\'' +
                ", customerId=" + customerId +
                '}';
    }
}

