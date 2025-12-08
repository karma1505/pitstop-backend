package com.garage.backend.inventory.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Response DTO for inventory item with full details including stock levels
 * 
 * Used for:
 * - Single item retrieval
 * - After create/update operations
 * - Detailed view
 */
public class InventoryItemResponse {

    private UUID id;
    private String itemCode;
    private String itemName;
    private String description;
    private String category;
    private String unit;
    private BigDecimal costPrice;
    private BigDecimal sellingPrice;
    private UUID supplierId;
    private UUID branchId;
    private Integer minStockLevel;
    private Integer maxStockLevel;
    private Boolean isActive;
    
    // Stock information from ProductInventory
    private Integer currentQuantity;
    private Integer reservedQuantity;
    private Integer availableQuantity;
    private Boolean isLowStock;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructors
    public InventoryItemResponse() {}

    public InventoryItemResponse(UUID id, String itemCode, String itemName, String description, String category,
                                String unit, BigDecimal costPrice, BigDecimal sellingPrice, UUID supplierId,
                                UUID branchId, Integer minStockLevel, Integer maxStockLevel, Boolean isActive,
                                Integer currentQuantity, Integer reservedQuantity, Integer availableQuantity,
                                Boolean isLowStock, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.description = description;
        this.category = category;
        this.unit = unit;
        this.costPrice = costPrice;
        this.sellingPrice = sellingPrice;
        this.supplierId = supplierId;
        this.branchId = branchId;
        this.minStockLevel = minStockLevel;
        this.maxStockLevel = maxStockLevel;
        this.isActive = isActive;
        this.currentQuantity = currentQuantity;
        this.reservedQuantity = reservedQuantity;
        this.availableQuantity = availableQuantity;
        this.isLowStock = isLowStock;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public BigDecimal getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }

    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public UUID getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(UUID supplierId) {
        this.supplierId = supplierId;
    }

    public UUID getBranchId() {
        return branchId;
    }

    public void setBranchId(UUID branchId) {
        this.branchId = branchId;
    }

    public Integer getMinStockLevel() {
        return minStockLevel;
    }

    public void setMinStockLevel(Integer minStockLevel) {
        this.minStockLevel = minStockLevel;
    }

    public Integer getMaxStockLevel() {
        return maxStockLevel;
    }

    public void setMaxStockLevel(Integer maxStockLevel) {
        this.maxStockLevel = maxStockLevel;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Integer getCurrentQuantity() {
        return currentQuantity;
    }

    public void setCurrentQuantity(Integer currentQuantity) {
        this.currentQuantity = currentQuantity;
    }

    public Integer getReservedQuantity() {
        return reservedQuantity;
    }

    public void setReservedQuantity(Integer reservedQuantity) {
        this.reservedQuantity = reservedQuantity;
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
        return "InventoryItemResponse{" +
                "id=" + id +
                ", itemCode='" + itemCode + '\'' +
                ", itemName='" + itemName + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", unit='" + unit + '\'' +
                ", costPrice=" + costPrice +
                ", sellingPrice=" + sellingPrice +
                ", supplierId=" + supplierId +
                ", branchId=" + branchId +
                ", minStockLevel=" + minStockLevel +
                ", maxStockLevel=" + maxStockLevel +
                ", isActive=" + isActive +
                ", currentQuantity=" + currentQuantity +
                ", reservedQuantity=" + reservedQuantity +
                ", availableQuantity=" + availableQuantity +
                ", isLowStock=" + isLowStock +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
