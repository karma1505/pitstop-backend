package com.garage.backend.staff.dto;

import com.garage.backend.shared.enums.Enums;
import jakarta.validation.constraints.*;
import java.util.UUID;

public class CreateStaffRequest {

    // garageId will be extracted from user's garage automatically

    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName;

    @Size(max = 50, message = "Last name must not exceed 50 characters")
    private String lastName;

    @NotBlank(message = "Mobile number is required")
    @Pattern(regexp = "^[0-9]{10,11}$", message = "Mobile number must be 10-11 digits")
    private String mobileNumber;

    @Size(max = 12, message = "Aadhar number must not exceed 12 characters")
    private String aadharNumber;

    @NotNull(message = "Role is required")
    private Enums.StaffRole role;

    // Constructors
    public CreateStaffRequest() {}

    public CreateStaffRequest(String firstName, String lastName, String mobileNumber, String aadharNumber, Enums.StaffRole role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobileNumber = mobileNumber;
        this.aadharNumber = aadharNumber;
        this.role = role;
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

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getAadharNumber() {
        return aadharNumber;
    }

    public void setAadharNumber(String aadharNumber) {
        this.aadharNumber = aadharNumber;
    }

    public Enums.StaffRole getRole() {
        return role;
    }

    public void setRole(Enums.StaffRole role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "CreateStaffRequest{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", aadharNumber='" + aadharNumber + '\'' +
                ", role=" + role +
                '}';
    }
}
