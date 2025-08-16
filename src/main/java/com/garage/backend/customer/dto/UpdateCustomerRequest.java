package com.garage.backend.customer.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

/**
 * Request DTO for updating an existing customer
 * 
 * Scenario: Update Customer Information
 *   Given a garage owner wants to update customer details
 *   When they provide updated customer information
 *   Then the system should update the customer record
 *   And return the updated customer information
 */
public class UpdateCustomerRequest {

    @NotBlank(message = "Customer name is required")
    @Size(min = 2, max = 100, message = "Customer name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[0-9]{10,11}$", message = "Phone number must be 10-11 digits")
    private String phone;

    @Email(message = "Email should be valid")
    private String email;

    @Size(max = 255, message = "Address line 1 must not exceed 255 characters")
    private String addressLine1;

    @Size(max = 255, message = "Address line 2 must not exceed 255 characters")
    private String addressLine2;

    @Size(max = 100, message = "City must not exceed 100 characters")
    private String city;

    @Size(max = 100, message = "State must not exceed 100 characters")
    private String state;

    @Valid
    private VehicleInfoRequest vehicleInfo;

    // Constructors
    public UpdateCustomerRequest() {}

    public UpdateCustomerRequest(String name, String phone, String email, String addressLine1, 
                               String addressLine2, String city, String state, VehicleInfoRequest vehicleInfo) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.state = state;
        this.vehicleInfo = vehicleInfo;
    }

    // Getters and Setters
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

    public VehicleInfoRequest getVehicleInfo() {
        return vehicleInfo;
    }

    public void setVehicleInfo(VehicleInfoRequest vehicleInfo) {
        this.vehicleInfo = vehicleInfo;
    }

    @Override
    public String toString() {
        return "UpdateCustomerRequest{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", addressLine1='" + addressLine1 + '\'' +
                ", addressLine2='" + addressLine2 + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", vehicleInfo=" + vehicleInfo +
                '}';
    }
}