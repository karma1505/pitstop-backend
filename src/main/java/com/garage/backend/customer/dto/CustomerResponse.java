package com.garage.backend.customer.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Response DTO for customer operations
 * 
 * Scenario: Return Customer Information
 *   Given a customer operation is performed
 *   When the operation is successful
 *   Then the system should return customer details
 *   And include creation and update timestamps
 */
public class CustomerResponse {

    private UUID id;
    private String name;
    private String phone;
    private String email;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private Boolean isRegularCustomer;
    private List<VehicleSummaryResponse> vehicles;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructors
    public CustomerResponse() {}

    public CustomerResponse(UUID id, String name, String phone, String email, String addressLine1, 
                           String addressLine2, String city, String state, Boolean isRegularCustomer,
                           List<VehicleSummaryResponse> vehicles, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.state = state;
        this.isRegularCustomer = isRegularCustomer;
        this.vehicles = vehicles;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Boolean getIsRegularCustomer() {
        return isRegularCustomer;
    }

    public void setIsRegularCustomer(Boolean isRegularCustomer) {
        this.isRegularCustomer = isRegularCustomer;
    }

    public List<VehicleSummaryResponse> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<VehicleSummaryResponse> vehicles) {
        this.vehicles = vehicles;
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
        return "CustomerResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", addressLine1='" + addressLine1 + '\'' +
                ", addressLine2='" + addressLine2 + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", isRegularCustomer=" + isRegularCustomer +
                ", vehicles=" + vehicles +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}