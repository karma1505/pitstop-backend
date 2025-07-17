package com.garage.backend.dto;

import jakarta.validation.constraints.*;
import java.util.UUID;

public class UpdateProfileRequest {
    
    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName;
    
    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    private String lastName;
    
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;
    
    @NotBlank(message = "State is required")
    @Size(max = 50, message = "State must not exceed 50 characters")
    private String state;
    
    @NotBlank(message = "City is required")
    @Size(max = 50, message = "City must not exceed 50 characters")
    private String city;
    
    @Pattern(regexp = "^$|^[0-9]{6}$", message = "Pincode must be exactly 6 digits")
    private String pincode;
    
    @Pattern(regexp = "^$|^[0-9]{10,11}$", message = "Mobile number must be 10-11 digits")
    private String mobileNumber;
    
    @NotBlank(message = "Garage name is required")
    @Size(max = 100, message = "Garage name must not exceed 100 characters")
    private String garageName;
    
    @Size(max = 255, message = "Address line 1 must not exceed 255 characters")
    private String addressLine1;
    
    @Size(max = 255, message = "Address line 2 must not exceed 255 characters")
    private String addressLine2;
    
    // Constructors
    public UpdateProfileRequest() {}
    
    public UpdateProfileRequest(String firstName, String lastName, String email, 
                              String state, String city, String pincode, 
                              String mobileNumber, String garageName, 
                              String addressLine1, String addressLine2) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.state = state;
        this.city = city;
        this.pincode = pincode;
        this.mobileNumber = mobileNumber;
        this.garageName = garageName;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
    }
    
    // Getters and Setters
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getState() {
        return state;
    }
    
    public void setState(String state) {
        this.state = state;
    }
    
    public String getCity() {
        return city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    public String getPincode() {
        return pincode;
    }
    
    public void setPincode(String pincode) {
        this.pincode = pincode;
    }
    
    public String getMobileNumber() {
        return mobileNumber;
    }
    
    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
    
    public String getGarageName() {
        return garageName;
    }
    
    public void setGarageName(String garageName) {
        this.garageName = garageName;
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
} 