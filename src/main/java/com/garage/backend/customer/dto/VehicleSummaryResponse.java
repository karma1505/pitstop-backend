package com.garage.backend.customer.dto;

import java.util.UUID;

/**
 * Summary response DTO for vehicle information in customer responses
 * 
 * Scenario: Return Vehicle Summary with Customer
 *   Given a customer has associated vehicles
 *   When customer information is requested
 *   Then the system should return basic vehicle details
 *   And include essential vehicle information
 */
public class VehicleSummaryResponse {

    private UUID id;
    private String registrationNumber;
    private String make;
    private String model;
    private Integer year;

    // Constructors
    public VehicleSummaryResponse() {}

    public VehicleSummaryResponse(UUID id, String registrationNumber, String make, String model, Integer year) {
        this.id = id;
        this.registrationNumber = registrationNumber;
        this.make = make;
        this.model = model;
        this.year = year;
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

    @Override
    public String toString() {
        return "VehicleSummaryResponse{" +
                "id=" + id +
                ", registrationNumber='" + registrationNumber + '\'' +
                ", make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                '}';
    }
}