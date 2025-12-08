package com.garage.backend.inventory.dto;

import jakarta.validation.constraints.*;

/**
 * Request DTO for adjusting stock levels
 * 
 * Scenario: Adjust Stock
 *   Given a garage owner wants to adjust stock levels
 *   When they specify adjustment type and quantity
 *   Then the system should update stock levels
 *   And record the adjustment in history
 *   And return updated stock information
 */
public class AdjustStockRequest {

    public enum AdjustmentType {
        ADD,      // Purchase/Restock - adds to current quantity
        REMOVE,   // Damage/Loss/Usage - removes from current quantity
        SET       // Manual correction - sets to exact quantity
    }

    @NotNull(message = "Adjustment type is required")
    private AdjustmentType adjustmentType;

    @NotNull(message = "Quantity is required")
    @Min(value = 0, message = "Quantity cannot be negative")
    private Integer quantity;

    @NotBlank(message = "Reason is required")
    @Size(min = 3, max = 255, message = "Reason must be between 3 and 255 characters")
    private String reason;

    @Size(max = 1000, message = "Notes must not exceed 1000 characters")
    private String notes;

    // Constructors
    public AdjustStockRequest() {}

    public AdjustStockRequest(AdjustmentType adjustmentType, Integer quantity, String reason, String notes) {
        this.adjustmentType = adjustmentType;
        this.quantity = quantity;
        this.reason = reason;
        this.notes = notes;
    }

    // Getters and Setters
    public AdjustmentType getAdjustmentType() {
        return adjustmentType;
    }

    public void setAdjustmentType(AdjustmentType adjustmentType) {
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "AdjustStockRequest{" +
                "adjustmentType=" + adjustmentType +
                ", quantity=" + quantity +
                ", reason='" + reason + '\'' +
                ", notes='" + notes + '\'' +
                '}';
    }
}
