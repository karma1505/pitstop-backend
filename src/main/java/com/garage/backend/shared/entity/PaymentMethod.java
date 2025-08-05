package com.garage.backend.shared.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "Payment_Method")
@EntityListeners(AuditingEntityListener.class)
public class PaymentMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "UUID")
    private UUID id;

    @NotBlank(message = "Method name is required")
    @Size(max = 100, message = "Method name must not exceed 100 characters")
    @Column(name = "method_name", nullable = false, unique = true)
    private String methodName;

    @Size(max = 255, message = "Description must not exceed 255 characters")
    @Column(name = "description")
    private String description;

    @NotBlank(message = "Method type is required")
    @Size(max = 50, message = "Method type must not exceed 50 characters")
    @Column(name = "method_type", nullable = false)
    private String methodType; // CASH, CARD, UPI, BANK_TRANSFER, etc.

    @Column(name = "processing_fee_percentage")
    private Double processingFeePercentage = 0.0;

    @Column(name = "processing_fee_fixed")
    private Double processingFeeFixed = 0.0;

    @NotNull(message = "Active status is required")
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // Constructors
    public PaymentMethod() {}

    public PaymentMethod(String methodName, String methodType) {
        this.methodName = methodName;
        this.methodType = methodType;
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMethodType() {
        return methodType;
    }

    public void setMethodType(String methodType) {
        this.methodType = methodType;
    }

    public Double getProcessingFeePercentage() {
        return processingFeePercentage;
    }

    public void setProcessingFeePercentage(Double processingFeePercentage) {
        this.processingFeePercentage = processingFeePercentage;
    }

    public Double getProcessingFeeFixed() {
        return processingFeeFixed;
    }

    public void setProcessingFeeFixed(Double processingFeeFixed) {
        this.processingFeeFixed = processingFeeFixed;
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
        return "PaymentMethod{" +
                "id=" + id +
                ", methodName='" + methodName + '\'' +
                ", description='" + description + '\'' +
                ", methodType='" + methodType + '\'' +
                ", processingFeePercentage=" + processingFeePercentage +
                ", processingFeeFixed=" + processingFeeFixed +
                ", isActive=" + isActive +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
