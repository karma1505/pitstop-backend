package com.garage.backend.inventory.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

/**
 * Request DTO for creating a new inventory item
 * 
 * Scenario: Create Inventory Item
 *   Given a garage owner wants to add a new part/item to inventory
 *   When they provide item details with initial stock quantity
 *   Then the system should create an inventory record
 *   And initialize stock levels
 *   And return the item information
 */
public class CreateInventoryItemRequest {

    @NotBlank(message = "Item code is required")
    @Size(min = 1, max = 50, message = "Item code must be between 1 and 50 characters")
    @Pattern(regexp = "^[A-Z0-9-]+$", message = "Item code must contain only uppercase letters, numbers, and hyphens")
    private String itemCode;

    @NotBlank(message = "Item name is required")
    @Size(min = 2, max = 255, message = "Item name must be between 2 and 255 characters")
    private String itemName;

    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private String description;

    @Size(max = 100, message = "Category must not exceed 100 characters")
    private String category;

    @NotBlank(message = "Unit is required")
    @Size(max = 20, message = "Unit must not exceed 20 characters")
    private String unit;

    @NotNull(message = "Cost price is required")
    @DecimalMin(value = "0.01", message = "Cost price must be greater than 0")
    @Digits(integer = 10, fraction = 2, message = "Cost price must have at most 10 digits and 2 decimal places")
    private BigDecimal costPrice;

    @NotNull(message = "Selling price is required")
    @DecimalMin(value = "0.01", message = "Selling price must be greater than 0")
    @Digits(integer = 10, fraction = 2, message = "Selling price must have at most 10 digits and 2 decimal places")
    private BigDecimal sellingPrice;

    @Min(value = 0, message = "Minimum stock level cannot be negative")
    private Integer minStockLevel = 0;

    @Min(value = 0, message = "Maximum stock level cannot be negative")
    private Integer maxStockLevel;

    @NotNull(message = "Initial quantity is required")
    @Min(value = 0, message = "Initial quantity cannot be negative")
    private Integer initialQuantity = 0;

    // Constructors
    public CreateInventoryItemRequest() {}

    public CreateInventoryItemRequest(String itemCode, String itemName, String description, String category,
                                     String unit, BigDecimal costPrice, BigDecimal sellingPrice,
                                     Integer minStockLevel, Integer maxStockLevel, Integer initialQuantity) {
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.description = description;
        this.category = category;
        this.unit = unit;
        this.costPrice = costPrice;
        this.sellingPrice = sellingPrice;
        this.minStockLevel = minStockLevel;
        this.maxStockLevel = maxStockLevel;
        this.initialQuantity = initialQuantity;
    }

    // Getters and Setters
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

    public Integer getInitialQuantity() {
        return initialQuantity;
    }

    public void setInitialQuantity(Integer initialQuantity) {
        this.initialQuantity = initialQuantity;
    }

    @Override
    public String toString() {
        return "CreateInventoryItemRequest{" +
                "itemCode='" + itemCode + '\'' +
                ", itemName='" + itemName + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", unit='" + unit + '\'' +
                ", costPrice=" + costPrice +
                ", sellingPrice=" + sellingPrice +
                ", minStockLevel=" + minStockLevel +
                ", maxStockLevel=" + maxStockLevel +
                ", initialQuantity=" + initialQuantity +
                '}';
    }
}
