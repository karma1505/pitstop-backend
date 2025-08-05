package com.garage.backend.audit.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "History")
@EntityListeners(AuditingEntityListener.class)
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "UUID")
    private UUID id;

    @NotBlank(message = "Table name is required")
    @Size(max = 100, message = "Table name must not exceed 100 characters")
    @Column(name = "table_name", nullable = false)
    private String tableName;

    @NotNull(message = "Record ID is required")
    @Column(name = "record_id", nullable = false, columnDefinition = "UUID")
    private UUID recordId;

    @NotBlank(message = "Action is required")
    @Size(max = 20, message = "Action must not exceed 20 characters")
    @Column(name = "action", nullable = false)
    private String action; // INSERT, UPDATE, DELETE

    @Column(name = "old_values", columnDefinition = "JSONB")
    private String oldValues; // JSON string of old values

    @Column(name = "new_values", columnDefinition = "JSONB")
    private String newValues; // JSON string of new values

    @NotNull(message = "User ID is required")
    @Column(name = "user_id", nullable = false, columnDefinition = "UUID")
    private UUID userId;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // Constructors
    public History() {}

    public History(String tableName, UUID recordId, String action, UUID userId) {
        this.tableName = tableName;
        this.recordId = recordId;
        this.action = action;
        this.userId = userId;
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public UUID getRecordId() {
        return recordId;
    }

    public void setRecordId(UUID recordId) {
        this.recordId = recordId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getOldValues() {
        return oldValues;
    }

    public void setOldValues(String oldValues) {
        this.oldValues = oldValues;
    }

    public String getNewValues() {
        return newValues;
    }

    public void setNewValues(String newValues) {
        this.newValues = newValues;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "History{" +
                "id=" + id +
                ", tableName='" + tableName + '\'' +
                ", recordId=" + recordId +
                ", action='" + action + '\'' +
                ", oldValues='" + oldValues + '\'' +
                ", newValues='" + newValues + '\'' +
                ", userId=" + userId +
                ", createdAt=" + createdAt +
                '}';
    }
}
