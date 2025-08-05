package com.garage.backend.inventory.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "Inventory_Transfer")
@EntityListeners(AuditingEntityListener.class)
public class InventoryTransfer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "UUID")
    private UUID id;

    @NotBlank(message = "Transfer number is required")
    @Size(max = 50, message = "Transfer number must not exceed 50 characters")
    @Column(name = "transfer_number", nullable = false, unique = true)
    private String transferNumber;

    @NotNull(message = "Inventory ID is required")
    @Column(name = "inventory_id", nullable = false, columnDefinition = "UUID")
    private UUID inventoryId;

    @NotNull(message = "From branch ID is required")
    @Column(name = "from_branch_id", nullable = false, columnDefinition = "UUID")
    private UUID fromBranchId;

    @NotNull(message = "To branch ID is required")
    @Column(name = "to_branch_id", nullable = false, columnDefinition = "UUID")
    private UUID toBranchId;

    @NotNull(message = "Quantity is required")
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @NotNull(message = "Transfer date is required")
    @Column(name = "transfer_date", nullable = false)
    private LocalDate transferDate;

    @NotBlank(message = "Status is required")
    @Size(max = 20, message = "Status must not exceed 20 characters")
    @Column(name = "status", nullable = false)
    private String status = "PENDING"; // PENDING, IN_TRANSIT, COMPLETED, CANCELLED

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @NotNull(message = "Transferred by is required")
    @Column(name = "transferred_by", nullable = false, columnDefinition = "UUID")
    private UUID transferredBy;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // Many-to-One relationship with Inventory
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory_id", insertable = false, updatable = false)
    private Inventory inventory;

    // Constructors
    public InventoryTransfer() {}

    public InventoryTransfer(String transferNumber, UUID inventoryId, UUID fromBranchId, 
                           UUID toBranchId, Integer quantity, LocalDate transferDate, UUID transferredBy) {
        this.transferNumber = transferNumber;
        this.inventoryId = inventoryId;
        this.fromBranchId = fromBranchId;
        this.toBranchId = toBranchId;
        this.quantity = quantity;
        this.transferDate = transferDate;
        this.transferredBy = transferredBy;
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTransferNumber() {
        return transferNumber;
    }

    public void setTransferNumber(String transferNumber) {
        this.transferNumber = transferNumber;
    }

    public UUID getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(UUID inventoryId) {
        this.inventoryId = inventoryId;
    }

    public UUID getFromBranchId() {
        return fromBranchId;
    }

    public void setFromBranchId(UUID fromBranchId) {
        this.fromBranchId = fromBranchId;
    }

    public UUID getToBranchId() {
        return toBranchId;
    }

    public void setToBranchId(UUID toBranchId) {
        this.toBranchId = toBranchId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public LocalDate getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(LocalDate transferDate) {
        this.transferDate = transferDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public UUID getTransferredBy() {
        return transferredBy;
    }

    public void setTransferredBy(UUID transferredBy) {
        this.transferredBy = transferredBy;
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

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    @Override
    public String toString() {
        return "InventoryTransfer{" +
                "id=" + id +
                ", transferNumber='" + transferNumber + '\'' +
                ", inventoryId=" + inventoryId +
                ", fromBranchId=" + fromBranchId +
                ", toBranchId=" + toBranchId +
                ", quantity=" + quantity +
                ", transferDate=" + transferDate +
                ", status='" + status + '\'' +
                ", notes='" + notes + '\'' +
                ", transferredBy=" + transferredBy +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
