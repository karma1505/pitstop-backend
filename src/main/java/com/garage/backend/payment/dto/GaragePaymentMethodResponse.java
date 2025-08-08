package com.garage.backend.payment.dto;

import com.garage.backend.shared.enums.Enums;
import java.time.LocalDateTime;
import java.util.UUID;

public class GaragePaymentMethodResponse {
    private UUID id;
    private UUID garageId;
    private Enums.PaymentMethod paymentMethod;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructors
    public GaragePaymentMethodResponse() {}

    public GaragePaymentMethodResponse(UUID id, Enums.PaymentMethod paymentMethod,
                                     Boolean isActive, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.paymentMethod = paymentMethod;
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

    public UUID getGarageId() {
        return garageId;
    }

    public void setGarageId(UUID garageId) {
        this.garageId = garageId;
    }

    public Enums.PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(Enums.PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
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
        return "GaragePaymentMethodResponse{" +
                "id=" + id +
                ", garageId=" + garageId +
                ", paymentMethod=" + paymentMethod +
                ", isActive=" + isActive +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
