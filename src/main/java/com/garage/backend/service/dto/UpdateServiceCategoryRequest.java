package com.garage.backend.service.dto;

import jakarta.validation.constraints.*;

/**
 * DTO for updating an existing service category
 * 
 * Scenario: Update Service Category via API
 *   Given a garage owner wants to update service category details
 *   When they provide updated information
 *   Then the system should update the category
 *   And return 200 OK with updated details
 *   But the category code cannot be changed
 */
public class UpdateServiceCategoryRequest {

    @NotBlank(message = "Category name is required")
    @Size(min = 2, max = 100, message = "Category name must be between 2 and 100 characters")
    private String categoryName;

    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private String description;

    @Min(value = 0, message = "Estimated time cannot be negative")
    @Max(value = 1440, message = "Estimated time cannot exceed 1440 minutes (24 hours)")
    private Integer estimatedTime; // in minutes

    @NotNull(message = "Active status is required")
    private Boolean isActive;

    // Constructors
    public UpdateServiceCategoryRequest() {}

    public UpdateServiceCategoryRequest(String categoryName, String description, Integer estimatedTime, Boolean isActive) {
        this.categoryName = categoryName;
        this.description = description;
        this.estimatedTime = estimatedTime;
        this.isActive = isActive;
    }

    // Getters and Setters
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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

    @Override
    public String toString() {
        return "UpdateServiceCategoryRequest{" +
                "categoryName='" + categoryName + '\'' +
                ", description='" + description + '\'' +
                ", estimatedTime=" + estimatedTime +
                ", isActive=" + isActive +
                '}';
    }
}
