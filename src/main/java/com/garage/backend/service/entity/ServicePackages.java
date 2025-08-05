package com.garage.backend.service.entity;

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
@Table(name = "Service_Packages")
@EntityListeners(AuditingEntityListener.class)
public class ServicePackages {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "UUID")
    private UUID id;

    @NotBlank(message = "Package name is required")
    @Size(max = 255, message = "Package name must not exceed 255 characters")
    @Column(name = "package_name", nullable = false)
    private String packageName;

    @NotNull(message = "Service category ID is required")
    @Column(name = "service_category_id", nullable = false, columnDefinition = "UUID")
    private UUID serviceCategoryId;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "services_included", columnDefinition = "JSONB")
    private String servicesIncluded; // JSON string of services included

    @NotNull(message = "Estimated cost is required")
    @Column(name = "estimated_cost", nullable = false, precision = 10, scale = 2)
    private BigDecimal estimatedCost;

    @Column(name = "estimated_time")
    private Integer estimatedTime; // in minutes

    @NotNull(message = "Custom flag is required")
    @Column(name = "is_custom", nullable = false)
    private Boolean isCustom = false;

    @Column(name = "created_by", columnDefinition = "UUID")
    private UUID createdBy; // FK to Staff.id (for custom packages)

    @NotNull(message = "Active status is required")
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // Many-to-One relationship with ServiceCategories
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_category_id", insertable = false, updatable = false)
    private ServiceCategories serviceCategory;

    // Constructors
    public ServicePackages() {}

    public ServicePackages(String packageName, UUID serviceCategoryId, BigDecimal estimatedCost) {
        this.packageName = packageName;
        this.serviceCategoryId = serviceCategoryId;
        this.estimatedCost = estimatedCost;
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public UUID getServiceCategoryId() {
        return serviceCategoryId;
    }

    public void setServiceCategoryId(UUID serviceCategoryId) {
        this.serviceCategoryId = serviceCategoryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getServicesIncluded() {
        return servicesIncluded;
    }

    public void setServicesIncluded(String servicesIncluded) {
        this.servicesIncluded = servicesIncluded;
    }

    public BigDecimal getEstimatedCost() {
        return estimatedCost;
    }

    public void setEstimatedCost(BigDecimal estimatedCost) {
        this.estimatedCost = estimatedCost;
    }

    public Integer getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(Integer estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public Boolean getIsCustom() {
        return isCustom;
    }

    public void setIsCustom(Boolean isCustom) {
        this.isCustom = isCustom;
    }

    public UUID getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UUID createdBy) {
        this.createdBy = createdBy;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
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

    public ServiceCategories getServiceCategory() {
        return serviceCategory;
    }

    public void setServiceCategory(ServiceCategories serviceCategory) {
        this.serviceCategory = serviceCategory;
    }

    @Override
    public String toString() {
        return "ServicePackages{" +
                "id=" + id +
                ", packageName='" + packageName + '\'' +
                ", serviceCategoryId=" + serviceCategoryId +
                ", description='" + description + '\'' +
                ", servicesIncluded='" + servicesIncluded + '\'' +
                ", estimatedCost=" + estimatedCost +
                ", estimatedTime=" + estimatedTime +
                ", isCustom=" + isCustom +
                ", createdBy=" + createdBy +
                ", isActive=" + isActive +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
