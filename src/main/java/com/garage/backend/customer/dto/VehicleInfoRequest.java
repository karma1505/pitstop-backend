package com.garage.backend.customer.dto;

import jakarta.validation.constraints.*;

/**
 * Request DTO for vehicle information when creating/updating customers
 * 
 * Scenario: Add Vehicle Information with Customer
 *   Given a garage owner wants to add vehicle details with customer
 *   When they provide vehicle information
 *   Then the system should validate and store vehicle data
 *   And link it to the customer record
 */
public class VehicleInfoRequest {

    @NotBlank(message = "Vehicle registration number is required")
    @Size(max = 20, message = "Registration number must not exceed 20 characters")
    private String registrationNumber;

    @NotBlank(message = "Vehicle make is required")
    @Size(max = 50, message = "Make must not exceed 50 characters")
    private String make;

    @NotBlank(message = "Vehicle model is required")
    @Size(max = 50, message = "Model must not exceed 50 characters")
    private String model;

    @NotNull(message = "Vehicle year is required")
    @Min(value = 1970, message = "Year must be at least 1970")
    @Max(value = 2050, message = "Year must not exceed 2050")
    private Integer year;

    // Constructors
    public VehicleInfoRequest() {}

    public VehicleInfoRequest(String registrationNumber, String make, String model, Integer year) {
        this.registrationNumber = registrationNumber;
        this.make = make;
        this.model = model;
        this.year = year;
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

    @Override
    public String toString() {
        return "VehicleInfoRequest{" +
                "registrationNumber='" + registrationNumber + '\'' +
                ", make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                '}';
    }
}