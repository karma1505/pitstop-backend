package com.garage.backend.financial.dto;

import com.garage.backend.shared.enums.Enums;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.UUID;

public class CreateIncomeTransactionRequest {

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", inclusive = false, message = "Amount must be positive")
    private BigDecimal amount;

    @NotBlank(message = "Description is required")
    @Size(max = 255, message = "Description must not exceed 255 characters")
    private String description;

    @NotNull(message = "Customer ID is required")
    private UUID customerId;

    private UUID vehicleId;

    private UUID jobCardId;

    @NotNull(message = "Payment method is required")
    private Enums.PaymentMethod paymentMethod;

    @Size(max = 50, message = "Reference number must not exceed 50 characters")
    private String referenceNumber;

    @Size(max = 500, message = "Notes must not exceed 500 characters")
    private String notes;

    // Constructors
    public CreateIncomeTransactionRequest() {}

    public CreateIncomeTransactionRequest(BigDecimal amount, String description, UUID customerId, 
                                        UUID vehicleId, UUID jobCardId, Enums.PaymentMethod paymentMethod, 
                                        String referenceNumber, String notes) {
        this.amount = amount;
        this.description = description;
        this.customerId = customerId;
        this.vehicleId = vehicleId;
        this.jobCardId = jobCardId;
        this.paymentMethod = paymentMethod;
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

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public UUID getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(UUID vehicleId) {
        this.vehicleId = vehicleId;
    }

    public UUID getJobCardId() {
        return jobCardId;
    }

    public void setJobCardId(UUID jobCardId) {
        this.jobCardId = jobCardId;
    }

    public Enums.PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(Enums.PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
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
        return "CreateIncomeTransactionRequest{" +
                "amount=" + amount +
                ", description='" + description + '\'' +
                ", customerId=" + customerId +
                ", vehicleId=" + vehicleId +
                ", jobCardId=" + jobCardId +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", referenceNumber='" + referenceNumber + '\'' +
                ", notes='" + notes + '\'' +
                '}';
    }
} 