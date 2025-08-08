package com.garage.backend.settings.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class SettingResponse {

    private UUID id;
    private String settingKey;
    private String settingValue;
    private String description;
    private String category;
    private Boolean isEditable;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructors
    public SettingResponse() {}

    public SettingResponse(UUID id, String settingKey, String settingValue, String description,
                          String category, Boolean isEditable, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.settingKey = settingKey;
        this.settingValue = settingValue;
        this.description = description;
        this.category = category;
        this.isEditable = isEditable;
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
        return "SettingResponse{" +
                "id=" + id +
                ", settingKey='" + settingKey + '\'' +
                ", settingValue='" + settingValue + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", isEditable=" + isEditable +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
