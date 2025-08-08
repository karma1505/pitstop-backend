package com.garage.backend.settings.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class GarageResponse {
    private UUID id;
    private String garageName;
    private String businessRegistrationNumber;
    private String gstNumber;

    private String logoUrl;
    private String websiteUrl;
    private String businessHours;
    private Boolean hasBranch;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructors
    public GarageResponse() {}

    public GarageResponse(UUID id, String garageName, String businessRegistrationNumber, String gstNumber,
                         String logoUrl, String websiteUrl, String businessHours,
                         Boolean hasBranch, Boolean isActive, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.garageName = garageName;
        this.businessRegistrationNumber = businessRegistrationNumber;
        this.gstNumber = gstNumber;
        this.logoUrl = logoUrl;
        this.websiteUrl = websiteUrl;
        this.businessHours = businessHours;
        this.hasBranch = hasBranch;
        this.isActive = isActive;
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

    public String getGarageName() {
        return garageName;
    }

    public void setGarageName(String garageName) {
        this.garageName = garageName;
    }

    public String getBusinessRegistrationNumber() {
        return businessRegistrationNumber;
    }

    public void setBusinessRegistrationNumber(String businessRegistrationNumber) {
        this.businessRegistrationNumber = businessRegistrationNumber;
    }

    public String getGstNumber() {
        return gstNumber;
    }

    public void setGstNumber(String gstNumber) {
        this.gstNumber = gstNumber;
    }



    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public String getBusinessHours() {
        return businessHours;
    }

    public void setBusinessHours(String businessHours) {
        this.businessHours = businessHours;
    }

    public Boolean getHasBranch() {
        return hasBranch;
    }

    public void setHasBranch(Boolean hasBranch) {
        this.hasBranch = hasBranch;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
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
        return "GarageResponse{" +
                "id=" + id +
                ", garageName='" + garageName + '\'' +
                ", businessRegistrationNumber='" + businessRegistrationNumber + '\'' +
                ", gstNumber='" + gstNumber + '\'' +
                ", logoUrl='" + logoUrl + '\'' +
                ", websiteUrl='" + websiteUrl + '\'' +
                ", businessHours='" + businessHours + '\'' +
                ", hasBranch=" + hasBranch +
                ", isActive=" + isActive +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
