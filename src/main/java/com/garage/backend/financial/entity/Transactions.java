package com.garage.backend.financial.entity;

import com.garage.backend.customer.entity.Customers;
import com.garage.backend.jobcard.entity.JobCards;
import com.garage.backend.shared.enums.Enums;
import com.garage.backend.staff.entity.Staff;
import com.garage.backend.vehicle.entity.Vehicles;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "Transactions")
@EntityListeners(AuditingEntityListener.class)
public class Transactions {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "UUID")
    private UUID id;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", inclusive = false, message = "Amount must be positive")
    @Column(name = "amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @NotNull(message = "Transaction type is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", nullable = false)
    private Enums.TransactionType transactionType;

    @NotNull(message = "Transaction date is required")
    @Column(name = "transaction_date", nullable = false)
    private LocalDateTime transactionDate;

    @NotNull(message = "Transaction date only is required")
    @Column(name = "transaction_date_only", nullable = false)
    private LocalDate transactionDateOnly;

    @NotBlank(message = "Description is required")
    @Size(max = 255, message = "Description must not exceed 255 characters")
    @Column(name = "description", nullable = false)
    private String description;

    @NotBlank(message = "Status is required")
    @Pattern(regexp = "^(PENDING|COMPLETED|CANCELLED|REFUNDED)$", 
             message = "Status must be PENDING, COMPLETED, CANCELLED, or REFUNDED")
    @Column(name = "status", nullable = false)
    private String status = "COMPLETED";

    // Income-specific fields (NULL for expenses)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customers customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id")
    private Vehicles vehicle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_card_id")
    private JobCards jobCard;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    private Enums.PaymentMethod paymentMethod;

    // Expense-specific fields (NULL for income)
    @Enumerated(EnumType.STRING)
    @Column(name = "expense_category")
    private Enums.ExpenseCategory expenseCategory;

    @Size(max = 100, message = "Vendor name must not exceed 100 characters")
    @Column(name = "vendor_name")
    private String vendorName;

    @Size(max = 50, message = "Vendor contact must not exceed 50 characters")
    @Column(name = "vendor_contact")
    private String vendorContact;

    // Common fields
    @Size(max = 50, message = "Reference number must not exceed 50 characters")
    @Column(name = "reference_number")
    private String referenceNumber;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private Staff createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by")
    private Staff updatedBy;

    // Constructors
    public Transactions() {}

        // Income transaction constructor
    public Transactions(BigDecimal amount, String description, Customers customer,
                       Vehicles vehicle, JobCards jobCard, Enums.PaymentMethod paymentMethod,
                       String referenceNumber) {
        this.amount = amount;
        this.transactionType = Enums.TransactionType.INCOME;
        this.transactionDate = LocalDateTime.now();
        this.transactionDateOnly = LocalDate.now();
        this.description = description;
        this.customer = customer;
        this.vehicle = vehicle;
        this.jobCard = jobCard;
        this.paymentMethod = paymentMethod;
        this.referenceNumber = referenceNumber;
    }

    // Expense transaction constructor
    public Transactions(BigDecimal amount, String description, Enums.ExpenseCategory expenseCategory,
                       String vendorName, String vendorContact, String referenceNumber,
                       String notes) {
        this.amount = amount;
        this.transactionType = Enums.TransactionType.EXPENSE;
        this.transactionDate = LocalDateTime.now();
        this.transactionDateOnly = LocalDate.now();
        this.description = description;
        this.expenseCategory = expenseCategory;
        this.vendorName = vendorName;
        this.vendorContact = vendorContact;
        this.referenceNumber = referenceNumber;
        this.notes = notes;
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

    public Enums.TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(Enums.TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public LocalDate getTransactionDateOnly() {
        return transactionDateOnly;
    }

    public void setTransactionDateOnly(LocalDate transactionDateOnly) {
        this.transactionDateOnly = transactionDateOnly;
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

    public Customers getCustomer() {
        return customer;
    }

    public void setCustomer(Customers customer) {
        this.customer = customer;
    }

    public Vehicles getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicles vehicle) {
        this.vehicle = vehicle;
    }

    public JobCards getJobCard() {
        return jobCard;
    }

    public void setJobCard(JobCards jobCard) {
        this.jobCard = jobCard;
    }

    public Enums.PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(Enums.PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Enums.ExpenseCategory getExpenseCategory() {
        return expenseCategory;
    }

    public void setExpenseCategory(Enums.ExpenseCategory expenseCategory) {
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

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Staff getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Staff createdBy) {
        this.createdBy = createdBy;
    }

    public Staff getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Staff updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Override
    public String toString() {
        return "Transactions{" +
                "id=" + id +
                ", amount=" + amount +
                ", transactionType='" + transactionType + '\'' +
                ", transactionDate=" + transactionDate +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", customer=" + (customer != null ? customer.getId() : null) +
                ", vehicle=" + (vehicle != null ? vehicle.getId() : null) +
                ", jobCard=" + (jobCard != null ? jobCard.getId() : null) +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", expenseCategory='" + expenseCategory + '\'' +
                ", vendorName='" + vendorName + '\'' +
                ", vendorContact='" + vendorContact + '\'' +
                ", referenceNumber='" + referenceNumber + '\'' +
                ", notes='" + notes + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", createdBy=" + (createdBy != null ? createdBy.getId() : null) +
                ", updatedBy=" + (updatedBy != null ? updatedBy.getId() : null) +
                '}';
    }
} 