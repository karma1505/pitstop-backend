package com.garage.backend.inventory.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "Product_Inventory")
@EntityListeners(AuditingEntityListener.class)
public class ProductInventory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "UUID")
    private UUID id;

    @NotNull(message = "Inventory ID is required")
    @Column(name = "inventory_id", nullable = false, columnDefinition = "UUID")
    private UUID inventoryId;

    @NotNull(message = "Quantity is required")
    @Column(name = "quantity", nullable = false)
    private Integer quantity = 0;

    @NotNull(message = "Reserved quantity is required")
    @Column(name = "reserved_quantity", nullable = false)
    private Integer reservedQuantity = 0;

    @CreatedDate
    @Column(name = "last_updated", nullable = false, updatable = false)
    private LocalDateTime lastUpdated;

    // Many-to-One relationship with Inventory
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory_id", insertable = false, updatable = false)
    private Inventory inventory;

    // Constructors
    public ProductInventory() {}

    public ProductInventory(UUID inventoryId, Integer quantity, Integer reservedQuantity) {
        this.inventoryId = inventoryId;
        this.quantity = quantity;
        this.reservedQuantity = reservedQuantity;
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getReservedQuantity() {
        return reservedQuantity;
    }

    public void setReservedQuantity(Integer reservedQuantity) {
        this.reservedQuantity = reservedQuantity;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    // Helper method to get available quantity
    public Integer getAvailableQuantity() {
        return quantity - reservedQuantity;
    }

    @Override
    public String toString() {
        return "ProductInventory{" +
                "id=" + id +
                ", inventoryId=" + inventoryId +
                ", quantity=" + quantity +
                ", reservedQuantity=" + reservedQuantity +
                ", lastUpdated=" + lastUpdated +
                '}';
    }
}
