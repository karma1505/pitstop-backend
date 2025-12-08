package com.garage.backend.inventory.dto;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Simplified response DTO for inventory item lists
 * 
 * Used for:
 * - List views with pagination
 * - Search results
 * - Quick lookups
 * 
 * Contains only essential information for display in lists
 */
public class InventoryListResponse {

    private UUID id;
    private String itemCode;
    private String itemName;
    private String category;
    private String unit;
    private BigDecimal sellingPrice;
    private Integer currentQuantity;
    private Integer availableQuantity;
    private Boolean isLowStock;
    private Boolean isActive;

    // Constructors
    public InventoryListResponse() {}

    public InventoryListResponse(UUID id, String itemCode, String itemName, String category, String unit,
                                BigDecimal sellingPrice, Integer currentQuantity, Integer availableQuantity,
                                Boolean isLowStock, Boolean isActive) {
        this.id = id;
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.category = category;
        this.unit = unit;
        this.sellingPrice = sellingPrice;
        this.currentQuantity = currentQuantity;
        this.availableQuantity = availableQuantity;
        this.isLowStock = isLowStock;
        this.isActive = isActive;
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public Integer getCurrentQuantity() {
        return currentQuantity;
    }

    public void setCurrentQuantity(Integer currentQuantity) {
        this.currentQuantity = currentQuantity;
    }

    public Integer getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(Integer availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public Boolean getIsLowStock() {
        return isLowStock;
    }

    public void setIsLowStock(Boolean isLowStock) {
        this.isLowStock = isLowStock;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public String toString() {
        return "InventoryListResponse{" +
                "id=" + id +
                ", itemCode='" + itemCode + '\'' +
                ", itemName='" + itemName + '\'' +
                ", category='" + category + '\'' +
                ", unit='" + unit + '\'' +
                ", sellingPrice=" + sellingPrice +
                ", currentQuantity=" + currentQuantity +
                ", availableQuantity=" + availableQuantity +
                ", isLowStock=" + isLowStock +
                ", isActive=" + isActive +
                '}';
    }
}
