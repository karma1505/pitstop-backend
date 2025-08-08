package com.garage.backend.settings.dto;

import jakarta.validation.constraints.*;

public class CreateGarageRequest {
    @NotBlank(message = "Garage name is required")
    @Size(max = 255, message = "Garage name must not exceed 255 characters")
    private String garageName;

    @NotBlank(message = "Business registration number is required")
    @Size(max = 100, message = "Business registration number must not exceed 100 characters")
    private String businessRegistrationNumber;

    @Size(max = 50, message = "GST number must not exceed 50 characters")
    private String gstNumber;

    @Size(max = 255, message = "Logo URL must not exceed 255 characters")
    private String logoUrl;

    @Size(max = 255, message = "Website URL must not exceed 255 characters")
    private String websiteUrl;

    @NotBlank(message = "Business hours are required")
    private String businessHours;

    private Boolean hasBranch = false;

    // Constructors
    public CreateGarageRequest() {}

    public CreateGarageRequest(String garageName, String businessRegistrationNumber, String gstNumber,
                             String businessHours) {
        this.garageName = garageName;
        this.businessRegistrationNumber = businessRegistrationNumber;
        this.gstNumber = gstNumber;
        this.businessHours = businessHours;
    }

    // Getters and Setters
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

    @Override
    public String toString() {
        return "CreateGarageRequest{" +
                "garageName='" + garageName + '\'' +
                ", businessRegistrationNumber='" + businessRegistrationNumber + '\'' +
                ", gstNumber='" + gstNumber + '\'' +
                ", logoUrl='" + logoUrl + '\'' +
                ", websiteUrl='" + websiteUrl + '\'' +
                ", businessHours='" + businessHours + '\'' +
                ", hasBranch=" + hasBranch +
                '}';
    }
}
