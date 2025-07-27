package com.garage.backend.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class FinancialTransactionResponse {

    private UUID id;
    private BigDecimal amount;
    private String transactionType;
    private LocalDateTime transactionDate;
    private String description;
    private String status;

    // Income fields
    private CustomerSummary customer;
    private VehicleSummary vehicle;
    private JobCardSummary jobCard;
    private String paymentMethod;

    // Expense fields
    private String expenseCategory;
    private String vendorName;
    private String vendorContact;

    // Common fields
    private String referenceNumber;
    private String notes;
    private LocalDateTime createdAt;
    private StaffSummary createdBy;

    // Constructors
    public FinancialTransactionResponse() {}

    public FinancialTransactionResponse(UUID id, BigDecimal amount, String transactionType, 
                                      LocalDateTime transactionDate, String description, String status,
                                      CustomerSummary customer, VehicleSummary vehicle, JobCardSummary jobCard,
                                      String paymentMethod, String expenseCategory, String vendorName,
                                      String vendorContact, String referenceNumber, String notes,
                                      LocalDateTime createdAt, StaffSummary createdBy) {
        this.id = id;
        this.amount = amount;
        this.transactionType = transactionType;
        this.transactionDate = transactionDate;
        this.description = description;
        this.status = status;
        this.customer = customer;
        this.vehicle = vehicle;
        this.jobCard = jobCard;
        this.paymentMethod = paymentMethod;
        this.expenseCategory = expenseCategory;
        this.vendorName = vendorName;
        this.vendorContact = vendorContact;
        this.referenceNumber = referenceNumber;
        this.notes = notes;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public CustomerSummary getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerSummary customer) {
        this.customer = customer;
    }

    public VehicleSummary getVehicle() {
        return vehicle;
    }

    public void setVehicle(VehicleSummary vehicle) {
        this.vehicle = vehicle;
    }

    public JobCardSummary getJobCard() {
        return jobCard;
    }

    public void setJobCard(JobCardSummary jobCard) {
        this.jobCard = jobCard;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public StaffSummary getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(StaffSummary createdBy) {
        this.createdBy = createdBy;
    }

    // Inner classes for summary objects
    public static class CustomerSummary {
        private UUID id;
        private String name;
        private String phone;

        public CustomerSummary() {}

        public CustomerSummary(UUID id, String name, String phone) {
            this.id = id;
            this.name = name;
            this.phone = phone;
        }

        public UUID getId() { return id; }
        public void setId(UUID id) { this.id = id; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }
    }

    public static class VehicleSummary {
        private UUID id;
        private String registrationNumber;
        private String make;
        private String model;

        public VehicleSummary() {}

        public VehicleSummary(UUID id, String registrationNumber, String make, String model) {
            this.id = id;
            this.registrationNumber = registrationNumber;
            this.make = make;
            this.model = model;
        }

        public UUID getId() { return id; }
        public void setId(UUID id) { this.id = id; }
        public String getRegistrationNumber() { return registrationNumber; }
        public void setRegistrationNumber(String registrationNumber) { this.registrationNumber = registrationNumber; }
        public String getMake() { return make; }
        public void setMake(String make) { this.make = make; }
        public String getModel() { return model; }
        public void setModel(String model) { this.model = model; }
    }

    public static class JobCardSummary {
        private UUID id;
        private String jobNumber;
        private String status;

        public JobCardSummary() {}

        public JobCardSummary(UUID id, String jobNumber, String status) {
            this.id = id;
            this.jobNumber = jobNumber;
            this.status = status;
        }

        public UUID getId() { return id; }
        public void setId(UUID id) { this.id = id; }
        public String getJobNumber() { return jobNumber; }
        public void setJobNumber(String jobNumber) { this.jobNumber = jobNumber; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
    }

    public static class StaffSummary {
        private UUID id;
        private String name;
        private String role;

        public StaffSummary() {}

        public StaffSummary(UUID id, String name, String role) {
            this.id = id;
            this.name = name;
            this.role = role;
        }

        public UUID getId() { return id; }
        public void setId(UUID id) { this.id = id; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getRole() { return role; }
        public void setRole(String role) { this.role = role; }
    }

    @Override
    public String toString() {
        return "FinancialTransactionResponse{" +
                "id=" + id +
                ", amount=" + amount +
                ", transactionType='" + transactionType + '\'' +
                ", transactionDate=" + transactionDate +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", customer=" + customer +
                ", vehicle=" + vehicle +
                ", jobCard=" + jobCard +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", expenseCategory='" + expenseCategory + '\'' +
                ", vendorName='" + vendorName + '\'' +
                ", vendorContact='" + vendorContact + '\'' +
                ", referenceNumber='" + referenceNumber + '\'' +
                ", notes='" + notes + '\'' +
                ", createdAt=" + createdAt +
                ", createdBy=" + createdBy +
                '}';
    }
} 