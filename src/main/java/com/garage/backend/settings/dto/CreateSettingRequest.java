package com.garage.backend.settings.dto;

import jakarta.validation.constraints.*;

public class CreateSettingRequest {

    @NotBlank(message = "Setting key is required")
    @Size(max = 100, message = "Setting key must not exceed 100 characters")
    private String settingKey;

    @NotBlank(message = "Setting value is required")
    private String settingValue;

    @Size(max = 255, message = "Description must not exceed 255 characters")
    private String description;

    @NotBlank(message = "Category is required")
    @Size(max = 50, message = "Category must not exceed 50 characters")
    private String category;

    private Boolean isEditable = true;

    // Constructors
    public CreateSettingRequest() {}

    public CreateSettingRequest(String settingKey, String settingValue, String category) {
        this.settingKey = settingKey;
        this.settingValue = settingValue;
        this.category = category;
    }

    // Getters and Setters
    public String getSettingKey() {
        return settingKey;
    }

    public void setSettingKey(String settingKey) {
        this.settingKey = settingKey;
    }

    public String getSettingValue() {
        return settingValue;
    }

    public void setSettingValue(String settingValue) {
        this.settingValue = settingValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Boolean getIsEditable() {
        return isEditable;
    }

    public void setIsEditable(Boolean isEditable) {
        this.isEditable = isEditable;
    }

    @Override
    public String toString() {
        return "CreateSettingRequest{" +
                "settingKey='" + settingKey + '\'' +
                ", settingValue='" + settingValue + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", isEditable=" + isEditable +
                '}';
    }
}
