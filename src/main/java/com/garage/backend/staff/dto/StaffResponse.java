package com.garage.backend.staff.dto;

import com.garage.backend.shared.enums.Enums;
import java.time.LocalDateTime;
import java.util.UUID;

public class StaffResponse {
    private UUID id;
    private UUID garageId;
    private String firstName;
    private String lastName;
    private String mobileNumber;
    private String aadharNumber;
    private Enums.StaffRole role;
    private Boolean isActive;
    private Integer jobsCompleted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructors
    public StaffResponse() {}

    public StaffResponse(UUID id, String firstName, String lastName, String mobileNumber, String aadharNumber,
                        Enums.StaffRole role, Boolean isActive, Integer jobsCompleted, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobileNumber = mobileNumber;
        this.aadharNumber = aadharNumber;
        this.role = role;
        this.isActive = isActive;
        this.jobsCompleted = jobsCompleted;
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

    public UUID getGarageId() {
        return garageId;
    }

    public void setGarageId(UUID garageId) {
        this.garageId = garageId;
    }

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

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Integer getJobsCompleted() {
        return jobsCompleted;
    }

    public void setJobsCompleted(Integer jobsCompleted) {
        this.jobsCompleted = jobsCompleted;
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
        return "StaffResponse{" +
                "id=" + id +
                ", garageId=" + garageId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", aadharNumber='" + aadharNumber + '\'' +
                ", role=" + role +
                ", isActive=" + isActive +
                ", jobsCompleted=" + jobsCompleted +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
