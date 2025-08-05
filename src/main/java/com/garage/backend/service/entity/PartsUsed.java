package com.garage.backend.service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "Parts_Used")
@EntityListeners(AuditingEntityListener.class)
public class PartsUsed {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "UUID")
    private UUID id;

    @NotNull(message = "Job card ID is required")
    @Column(name = "jobcard_id", nullable = false, columnDefinition = "UUID")
    private UUID jobcardId;

    @NotNull(message = "Inventory ID is required")
    @Column(name = "inventory_id", nullable = false, columnDefinition = "UUID")
    private UUID inventoryId;

    @NotNull(message = "Quantity used is required")
    @Column(name = "quantity_used", nullable = false)
    private Integer quantityUsed;

    @NotNull(message = "Unit price is required")
    @Column(name = "unit_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;

    @NotNull(message = "Total price is required")
    @Column(name = "total_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPrice;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // Constructors
    public PartsUsed() {}

    public PartsUsed(UUID jobcardId, UUID inventoryId, Integer quantityUsed, 
                    BigDecimal unitPrice, BigDecimal totalPrice) {
        this.jobcardId = jobcardId;
        this.inventoryId = inventoryId;
        this.quantityUsed = quantityUsed;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getJobcardId() {
        return jobcardId;
    }

    public void setJobcardId(UUID jobcardId) {
        this.jobcardId = jobcardId;
    }

    public UUID getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(UUID inventoryId) {
        this.inventoryId = inventoryId;
    }

    public Integer getQuantityUsed() {
        return quantityUsed;
    }

    public void setQuantityUsed(Integer quantityUsed) {
        this.quantityUsed = quantityUsed;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "PartsUsed{" +
                "id=" + id +
                ", jobcardId=" + jobcardId +
                ", inventoryId=" + inventoryId +
                ", quantityUsed=" + quantityUsed +
                ", unitPrice=" + unitPrice +
                ", totalPrice=" + totalPrice +
                ", notes='" + notes + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
