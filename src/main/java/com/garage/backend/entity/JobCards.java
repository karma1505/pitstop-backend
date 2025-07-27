package com.garage.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "JobCards")
@EntityListeners(AuditingEntityListener.class)
public class JobCards {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "UUID")
    private UUID id;

    @NotNull(message = "Customer is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customers customer;

    @NotNull(message = "Vehicle is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicles vehicle;

    @NotBlank(message = "Job number is required")
    @Size(max = 20, message = "Job number must not exceed 20 characters")
    @Column(name = "job_number", nullable = false, unique = true)
    private String jobNumber;

    @NotBlank(message = "Description is required")
    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @DecimalMin(value = "0.0", inclusive = false, message = "Estimated cost must be positive")
    @Column(name = "estimated_cost", precision = 10, scale = 2)
    private BigDecimal estimatedCost;

    @DecimalMin(value = "0.0", inclusive = false, message = "Actual cost must be positive")
    @Column(name = "actual_cost", precision = 10, scale = 2)
    private BigDecimal actualCost;

    @NotBlank(message = "Status is required")
    @Pattern(regexp = "^(PENDING|IN_PROGRESS|COMPLETED|CANCELLED)$", 
             message = "Status must be PENDING, IN_PROGRESS, COMPLETED, or CANCELLED")
    @Column(name = "status", nullable = false)
    private String status = "PENDING";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_to")
    private Staff assignedTo;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // Constructors
    public JobCards() {}

    public JobCards(Customers customer, Vehicles vehicle, String jobNumber, String description, 
                   BigDecimal estimatedCost, Staff assignedTo) {
        this.customer = customer;
        this.vehicle = vehicle;
        this.jobNumber = jobNumber;
        this.description = description;
        this.estimatedCost = estimatedCost;
        this.assignedTo = assignedTo;
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getEstimatedCost() {
        return estimatedCost;
    }

    public void setEstimatedCost(BigDecimal estimatedCost) {
        this.estimatedCost = estimatedCost;
    }

    public BigDecimal getActualCost() {
        return actualCost;
    }

    public void setActualCost(BigDecimal actualCost) {
        this.actualCost = actualCost;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Staff getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(Staff assignedTo) {
        this.assignedTo = assignedTo;
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
        return "JobCards{" +
                "id=" + id +
                ", customer=" + (customer != null ? customer.getId() : null) +
                ", vehicle=" + (vehicle != null ? vehicle.getId() : null) +
                ", jobNumber='" + jobNumber + '\'' +
                ", description='" + description + '\'' +
                ", estimatedCost=" + estimatedCost +
                ", actualCost=" + actualCost +
                ", status='" + status + '\'' +
                ", assignedTo=" + (assignedTo != null ? assignedTo.getId() : null) +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
} 