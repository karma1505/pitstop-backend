package com.garage.backend.inventory.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "Inventory_Adjustments")
@EntityListeners(AuditingEntityListener.class)
public class InventoryAdjustments {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "UUID")
    private UUID id;

    @NotNull(message = "Inventory ID is required")
    @Column(name = "inventory_id", nullable = false, columnDefinition = "UUID")
    private UUID inventoryId;

    @NotBlank(message = "Adjustment type is required")
    @Size(max = 20, message = "Adjustment type must not exceed 20 characters")
    @Column(name = "adjustment_type", nullable = false)
    private String adjustmentType; // INCREASE/DECREASE

    @NotNull(message = "Quantity is required")
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @NotBlank(message = "Reason is required")
    @Size(max = 255, message = "Reason must not exceed 255 characters")
    @Column(name = "reason", nullable = false)
    private String reason;

    @Size(max = 50, message = "Reference number must not exceed 50 characters")
    @Column(name = "reference_number")
    private String referenceNumber;

    @NotNull(message = "Adjusted by is required")
    @Column(name = "adjusted_by", nullable = false, columnDefinition = "UUID")
    private UUID adjustedBy;

    @CreatedDate
    @Column(name = "adjusted_at", nullable = false, updatable = false)
    private LocalDateTime adjustedAt;

    // Many-to-One relationship with Inventory
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory_id", insertable = false, updatable = false)
    private Inventory inventory;

    // Constructors
    public InventoryAdjustments() {}

    public InventoryAdjustments(UUID inventoryId, String adjustmentType, Integer quantity, 
                               String reason, UUID adjustedBy) {
        this.inventoryId = inventoryId;
        this.adjustmentType = adjustmentType;
        this.quantity = quantity;
        this.reason = reason;
        this.adjustedBy = adjustedBy;
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(UUID inventoryId) {
        this.inventoryId = inventoryId;
    }

    public String getAdjustmentType() {
        return adjustmentType;
    }

    public void setAdjustmentType(String adjustmentType) {
        this.adjustmentType = adjustmentType;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public UUID getAdjustedBy() {
        return adjustedBy;
    }

    public void setAdjustedBy(UUID adjustedBy) {
        this.adjustedBy = adjustedBy;
    }

    public LocalDateTime getAdjustedAt() {
        return adjustedAt;
    }

    public void setAdjustedAt(LocalDateTime adjustedAt) {
        this.adjustedAt = adjustedAt;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    @Override
    public String toString() {
        return "InventoryAdjustments{" +
                "id=" + id +
                ", inventoryId=" + inventoryId +
                ", adjustmentType='" + adjustmentType + '\'' +
                ", quantity=" + quantity +
                ", reason='" + reason + '\'' +
                ", referenceNumber='" + referenceNumber + '\'' +
                ", adjustedBy=" + adjustedBy +
                ", adjustedAt=" + adjustedAt +
                '}';
    }
}
