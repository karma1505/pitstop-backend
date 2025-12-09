package com.garage.backend.service.dto;

import jakarta.validation.constraints.*;

/**
 * DTO for creating a new service category
 * 
 * Scenario: Create Service Category via API
 *   Given a garage owner wants to add a new service category
 *   When they provide category name, code, and optional details
 *   Then the system should create the category
 *   And return 201 Created with the category details
 *   But if the category code already exists
 *   Then the system should return 400 Bad Request
 */
public class CreateServiceCategoryRequest {

    @NotBlank(message = "Category name is required")
    @Size(min = 2, max = 100, message = "Category name must be between 2 and 100 characters")
    private String categoryName;

    @NotBlank(message = "Category code is required")
    @Size(min = 2, max = 20, message = "Category code must be between 2 and 20 characters")
    @Pattern(regexp = "^[A-Z0-9-]+$", message = "Category code must contain only uppercase letters, numbers, and hyphens")
    private String categoryCode;

    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private String description;

    @Min(value = 0, message = "Estimated time cannot be negative")
    @Max(value = 1440, message = "Estimated time cannot exceed 1440 minutes (24 hours)")
    private Integer estimatedTime; // in minutes

    // Constructors
    public CreateServiceCategoryRequest() {}

    public CreateServiceCategoryRequest(String categoryName, String categoryCode, String description, Integer estimatedTime) {
        this.categoryName = categoryName;
        this.categoryCode = categoryCode;
        this.description = description;
        this.estimatedTime = estimatedTime;
    }

    // Getters and Setters
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

    @Override
    public String toString() {
        return "CreateServiceCategoryRequest{" +
                "categoryName='" + categoryName + '\'' +
                ", categoryCode='" + categoryCode + '\'' +
                ", description='" + description + '\'' +
                ", estimatedTime=" + estimatedTime +
                '}';
    }
}
