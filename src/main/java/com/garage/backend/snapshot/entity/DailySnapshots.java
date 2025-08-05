package com.garage.backend.snapshot.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "Daily_Snapshots")
@EntityListeners(AuditingEntityListener.class)
public class DailySnapshots {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "UUID")
    private UUID id;

    @NotNull(message = "Snapshot date is required")
    @Column(name = "snapshot_date", nullable = false)
    private LocalDate snapshotDate;

    @NotNull(message = "Branch ID is required")
    @Column(name = "branch_id", nullable = false, columnDefinition = "UUID")
    private UUID branchId;

    @NotNull(message = "Total sales is required")
    @Column(name = "total_sales", nullable = false, precision = 15, scale = 2)
    private BigDecimal totalSales = BigDecimal.ZERO;

    @NotNull(message = "Total expenses is required")
    @Column(name = "total_expenses", nullable = false, precision = 15, scale = 2)
    private BigDecimal totalExpenses = BigDecimal.ZERO;

    @NotNull(message = "Total profit is required")
    @Column(name = "total_profit", nullable = false, precision = 15, scale = 2)
    private BigDecimal totalProfit = BigDecimal.ZERO;

    @NotNull(message = "Total invoices is required")
    @Column(name = "total_invoices", nullable = false)
    private Integer totalInvoices = 0;

    @NotNull(message = "Total job cards is required")
    @Column(name = "total_job_cards", nullable = false)
    private Integer totalJobCards = 0;

    @NotNull(message = "Total customers is required")
    @Column(name = "total_customers", nullable = false)
    private Integer totalCustomers = 0;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // Constructors
    public DailySnapshots() {}

    public DailySnapshots(LocalDate snapshotDate, UUID branchId) {
        this.snapshotDate = snapshotDate;
        this.branchId = branchId;
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDate getSnapshotDate() {
        return snapshotDate;
    }

    public void setSnapshotDate(LocalDate snapshotDate) {
        this.snapshotDate = snapshotDate;
    }

    public UUID getBranchId() {
        return branchId;
    }

    public void setBranchId(UUID branchId) {
        this.branchId = branchId;
    }

    public BigDecimal getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(BigDecimal totalSales) {
        this.totalSales = totalSales;
    }

    public BigDecimal getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(BigDecimal totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    public BigDecimal getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(BigDecimal totalProfit) {
        this.totalProfit = totalProfit;
    }

    public Integer getTotalInvoices() {
        return totalInvoices;
    }

    public void setTotalInvoices(Integer totalInvoices) {
        this.totalInvoices = totalInvoices;
    }

    public Integer getTotalJobCards() {
        return totalJobCards;
    }

    public void setTotalJobCards(Integer totalJobCards) {
        this.totalJobCards = totalJobCards;
    }

    public Integer getTotalCustomers() {
        return totalCustomers;
    }

    public void setTotalCustomers(Integer totalCustomers) {
        this.totalCustomers = totalCustomers;
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

    @Override
    public String toString() {
        return "DailySnapshots{" +
                "id=" + id +
                ", snapshotDate=" + snapshotDate +
                ", branchId=" + branchId +
                ", totalSales=" + totalSales +
                ", totalExpenses=" + totalExpenses +
                ", totalProfit=" + totalProfit +
                ", totalInvoices=" + totalInvoices +
                ", totalJobCards=" + totalJobCards +
                ", totalCustomers=" + totalCustomers +
                ", notes='" + notes + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
