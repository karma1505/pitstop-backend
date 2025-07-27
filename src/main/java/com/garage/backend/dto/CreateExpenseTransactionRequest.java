package com.garage.backend.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class CreateExpenseTransactionRequest {

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", inclusive = false, message = "Amount must be positive")
    private BigDecimal amount;

    @NotBlank(message = "Description is required")
    @Size(max = 255, message = "Description must not exceed 255 characters")
    private String description;

    @NotBlank(message = "Expense category is required")
    @Pattern(regexp = "^(UTILITIES|SALARY|EQUIPMENT|SUPPLIES|RENT|INSURANCE|MAINTENANCE|MARKETING|OFFICE_SUPPLIES|OTHER)$", 
             message = "Expense category must be valid")
    private String expenseCategory;

    @Size(max = 100, message = "Vendor name must not exceed 100 characters")
    private String vendorName;

    @Size(max = 50, message = "Vendor contact must not exceed 50 characters")
    private String vendorContact;

    @Size(max = 50, message = "Reference number must not exceed 50 characters")
    private String referenceNumber;

    @Size(max = 500, message = "Notes must not exceed 500 characters")
    private String notes;

    // Constructors
    public CreateExpenseTransactionRequest() {}

    public CreateExpenseTransactionRequest(BigDecimal amount, String description, String expenseCategory,
                                         String vendorName, String vendorContact, String referenceNumber, String notes) {
        this.amount = amount;
        this.description = description;
        this.expenseCategory = expenseCategory;
        this.vendorName = vendorName;
        this.vendorContact = vendorContact;
        this.referenceNumber = referenceNumber;
        this.notes = notes;
    }

    // Getters and Setters
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExpenseCategory() {
        return expenseCategory;
    }

    public void setExpenseCategory(String expenseCategory) {
        this.expenseCategory = expenseCategory;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getVendorContact() {
        return vendorContact;
    }

    public void setVendorContact(String vendorContact) {
        this.vendorContact = vendorContact;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "CreateExpenseTransactionRequest{" +
                "amount=" + amount +
                ", description='" + description + '\'' +
                ", expenseCategory='" + expenseCategory + '\'' +
                ", vendorName='" + vendorName + '\'' +
                ", vendorContact='" + vendorContact + '\'' +
                ", referenceNumber='" + referenceNumber + '\'' +
                ", notes='" + notes + '\'' +
                '}';
    }
} 