package com.garage.backend.payment.entity;

import com.garage.backend.shared.enums.Enums;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "Garage_Payment_Methods")
@EntityListeners(AuditingEntityListener.class)
public class GaragePaymentMethods {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "UUID")
    private UUID id;

    @NotNull(message = "Garage ID is required")
    @Column(name = "garage_id", nullable = false, columnDefinition = "UUID")
    private UUID garageId;

    @NotNull(message = "Payment method is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false)
    private Enums.PaymentMethod paymentMethod;

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
    public GaragePaymentMethods() {}

    public GaragePaymentMethods(UUID garageId, Enums.PaymentMethod paymentMethod) {
        this.garageId = garageId;
        this.paymentMethod = paymentMethod;
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
        return "GaragePaymentMethods{" +
                "id=" + id +
                ", garageId=" + garageId +
                ", paymentMethod=" + paymentMethod +
                ", isActive=" + isActive +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
