package com.garage.backend.inventory.dto;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Response DTO for stock adjustment history
 * 
 * Used for:
 * - Stock adjustment history/audit trail
 * - After performing stock adjustments
 */
public class StockAdjustmentResponse {

    private UUID id;
    private UUID inventoryId;
    private String itemCode;
    private String itemName;
    private String adjustmentType;
    private Integer quantityBefore;
    private Integer quantityAfter;
    private Integer quantityChanged;
    private String reason;
    private String notes;
    private String performedBy;
    private LocalDateTime createdAt;

    // Constructors
    public StockAdjustmentResponse() {}

    public StockAdjustmentResponse(UUID id, UUID inventoryId, String itemCode, String itemName,
                                  String adjustmentType, Integer quantityBefore, Integer quantityAfter,
                                  Integer quantityChanged, String reason, String notes,
                                  String performedBy, LocalDateTime createdAt) {
        this.id = id;
        this.inventoryId = inventoryId;
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.adjustmentType = adjustmentType;
        this.quantityBefore = quantityBefore;
        this.quantityAfter = quantityAfter;
        this.quantityChanged = quantityChanged;
        this.reason = reason;
        this.notes = notes;
        this.performedBy = performedBy;
        this.createdAt = createdAt;
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

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getAdjustmentType() {
        return adjustmentType;
    }

    public void setAdjustmentType(String adjustmentType) {
        this.adjustmentType = adjustmentType;
    }

    public Integer getQuantityBefore() {
        return quantityBefore;
    }

    public void setQuantityBefore(Integer quantityBefore) {
        this.quantityBefore = quantityBefore;
    }

    public Integer getQuantityAfter() {
        return quantityAfter;
    }

    public void setQuantityAfter(Integer quantityAfter) {
        this.quantityAfter = quantityAfter;
    }

    public Integer getQuantityChanged() {
        return quantityChanged;
    }

    public void setQuantityChanged(Integer quantityChanged) {
        this.quantityChanged = quantityChanged;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getPerformedBy() {
        return performedBy;
    }

    public void setPerformedBy(String performedBy) {
        this.performedBy = performedBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "StockAdjustmentResponse{" +
                "id=" + id +
                ", inventoryId=" + inventoryId +
                ", itemCode='" + itemCode + '\'' +
                ", itemName='" + itemName + '\'' +
                ", adjustmentType='" + adjustmentType + '\'' +
                ", quantityBefore=" + quantityBefore +
                ", quantityAfter=" + quantityAfter +
                ", quantityChanged=" + quantityChanged +
                ", reason='" + reason + '\'' +
                ", notes='" + notes + '\'' +
                ", performedBy='" + performedBy + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
