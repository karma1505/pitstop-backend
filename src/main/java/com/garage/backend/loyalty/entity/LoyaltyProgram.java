package com.garage.backend.loyalty.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "Loyalty_Program")
@EntityListeners(AuditingEntityListener.class)
public class LoyaltyProgram {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "UUID")
    private UUID id;

    @NotNull(message = "Customer ID is required")
    @Column(name = "customer_id", nullable = false, columnDefinition = "UUID")
    private UUID customerId;

    @NotBlank(message = "Program name is required")
    @Size(max = 100, message = "Program name must not exceed 100 characters")
    @Column(name = "program_name", nullable = false)
    private String programName;

    @NotNull(message = "Points balance is required")
    @Column(name = "points_balance", nullable = false)
    private Integer pointsBalance = 0;

    @NotNull(message = "Total points earned is required")
    @Column(name = "total_points_earned", nullable = false)
    private Integer totalPointsEarned = 0;

    @NotNull(message = "Total points redeemed is required")
    @Column(name = "total_points_redeemed", nullable = false)
    private Integer totalPointsRedeemed = 0;

    @NotNull(message = "Points to currency ratio is required")
    @Column(name = "points_to_currency_ratio", nullable = false, precision = 10, scale = 4)
    private BigDecimal pointsToCurrencyRatio = BigDecimal.ONE;

    @NotNull(message = "Active status is required")
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "last_activity_date")
    private LocalDateTime lastActivityDate;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // Constructors
    public LoyaltyProgram() {}

    public LoyaltyProgram(UUID customerId, String programName) {
        this.customerId = customerId;
        this.programName = programName;
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public Integer getPointsBalance() {
        return pointsBalance;
    }

    public void setPointsBalance(Integer pointsBalance) {
        this.pointsBalance = pointsBalance;
    }

    public Integer getTotalPointsEarned() {
        return totalPointsEarned;
    }

    public void setTotalPointsEarned(Integer totalPointsEarned) {
        this.totalPointsEarned = totalPointsEarned;
    }

    public Integer getTotalPointsRedeemed() {
        return totalPointsRedeemed;
    }

    public void setTotalPointsRedeemed(Integer totalPointsRedeemed) {
        this.totalPointsRedeemed = totalPointsRedeemed;
    }

    public BigDecimal getPointsToCurrencyRatio() {
        return pointsToCurrencyRatio;
    }

    public void setPointsToCurrencyRatio(BigDecimal pointsToCurrencyRatio) {
        this.pointsToCurrencyRatio = pointsToCurrencyRatio;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public LocalDateTime getLastActivityDate() {
        return lastActivityDate;
    }

    public void setLastActivityDate(LocalDateTime lastActivityDate) {
        this.lastActivityDate = lastActivityDate;
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
        return "LoyaltyProgram{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", programName='" + programName + '\'' +
                ", pointsBalance=" + pointsBalance +
                ", totalPointsEarned=" + totalPointsEarned +
                ", totalPointsRedeemed=" + totalPointsRedeemed +
                ", pointsToCurrencyRatio=" + pointsToCurrencyRatio +
                ", isActive=" + isActive +
                ", lastActivityDate=" + lastActivityDate +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
