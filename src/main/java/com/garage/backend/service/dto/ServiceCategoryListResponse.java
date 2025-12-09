package com.garage.backend.service.dto;

import java.util.UUID;

/**
 * DTO for service category list response (simplified for list views)
 */
public class ServiceCategoryListResponse {

    private UUID id;
    private String categoryName;
    private String categoryCode;
    private Integer estimatedTime; // in minutes
    private Boolean isActive;

    // Constructors
    public ServiceCategoryListResponse() {}

    public ServiceCategoryListResponse(UUID id, String categoryName, String categoryCode, Integer estimatedTime, Boolean isActive) {
        this.id = id;
        this.categoryName = categoryName;
        this.categoryCode = categoryCode;
        this.estimatedTime = estimatedTime;
        this.isActive = isActive;
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public Integer getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(Integer estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public String toString() {
        return "ServiceCategoryListResponse{" +
                "id=" + id +
                ", categoryName='" + categoryName + '\'' +
                ", categoryCode='" + categoryCode + '\'' +
                ", estimatedTime=" + estimatedTime +
                ", isActive=" + isActive +
                '}';
    }
}
