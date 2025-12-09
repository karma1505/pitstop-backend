package com.garage.backend.service.dto;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for service category response with full details
 */
public class ServiceCategoryResponse {

    private UUID id;
    private String categoryName;
    private String categoryCode;
    private String description;
    private Integer estimatedTime; // in minutes
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructors
    public ServiceCategoryResponse() {}

    public ServiceCategoryResponse(UUID id, String categoryName, String categoryCode, String description,
                                    Integer estimatedTime, Boolean isActive, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.categoryName = categoryName;
        this.categoryCode = categoryCode;
        this.description = description;
        this.estimatedTime = estimatedTime;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        return "ServiceCategoryResponse{" +
                "id=" + id +
                ", categoryName='" + categoryName + '\'' +
                ", categoryCode='" + categoryCode + '\'' +
                ", description='" + description + '\'' +
                ", estimatedTime=" + estimatedTime +
                ", isActive=" + isActive +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
