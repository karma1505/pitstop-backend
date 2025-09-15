package com.garage.backend.settings.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.garage.backend.authentication.entity.User;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "Garage")
@EntityListeners(AuditingEntityListener.class)
public class Garage {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "UUID")
    private UUID id;

    @NotNull(message = "Created by user ID is required")
    @Column(name = "created_by", nullable = false, columnDefinition = "UUID")
    private UUID createdBy;

    // Many-to-One relationship with User (read-only)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", insertable = false, updatable = false)
    private User createdByUser;

    @NotBlank(message = "Garage name is required")
    @Size(max = 255, message = "Garage name must not exceed 255 characters")
    @Column(name = "garage_name", nullable = false)
    private String garageName;

    @NotBlank(message = "Business registration number is required")
    @Size(max = 100, message = "Business registration number must not exceed 100 characters")
    @Column(name = "business_registration_number", nullable = false, unique = true)
    private String businessRegistrationNumber;

    @Size(max = 50, message = "GST number must not exceed 50 characters")
    @Column(name = "gst_number", unique = true)
    private String gstNumber;

    @Size(max = 255, message = "Logo URL must not exceed 255 characters")
    @Column(name = "logo_url")
    private String logoUrl;

    @Size(max = 255, message = "Website URL must not exceed 255 characters")
    @Column(name = "website_url")
    private String websiteUrl;

    @NotBlank(message = "Business hours are required")
    @Column(name = "business_hours", columnDefinition = "TEXT", nullable = false)
    private String businessHours;

    @Column(name = "has_branch")
    private Boolean hasBranch = false;

    @NotNull(message = "Active status is required")
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // Constructors
    public Garage() {}

    public Garage(UUID createdBy, String garageName, String businessRegistrationNumber, String gstNumber, 
                 String businessHours) {
        this.createdBy = createdBy;
        this.garageName = garageName;
        this.businessRegistrationNumber = businessRegistrationNumber;
        this.gstNumber = gstNumber;
        this.businessHours = businessHours;
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UUID createdBy) {
        this.createdBy = createdBy;
    }

    public User getCreatedByUser() {
        return createdByUser;
    }

    public void setCreatedByUser(User createdByUser) {
        this.createdByUser = createdByUser;
    }

    public String getGarageName() {
        return garageName;
    }

    public void setGarageName(String garageName) {
        this.garageName = garageName;
    }

    public String getBusinessRegistrationNumber() {
        return businessRegistrationNumber;
    }

    public void setBusinessRegistrationNumber(String businessRegistrationNumber) {
        this.businessRegistrationNumber = businessRegistrationNumber;
    }

    public String getGstNumber() {
        return gstNumber;
    }

    public void setGstNumber(String gstNumber) {
        this.gstNumber = gstNumber;
    }



    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public String getBusinessHours() {
        return businessHours;
    }

    public void setBusinessHours(String businessHours) {
        this.businessHours = businessHours;
    }

    public Boolean getHasBranch() {
        return hasBranch;
    }

    public void setHasBranch(Boolean hasBranch) {
        this.hasBranch = hasBranch;
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

    @Override
    public String toString() {
        return "Garage{" +
                "id=" + id +
                ", createdBy=" + createdBy +
                ", garageName='" + garageName + '\'' +
                ", businessRegistrationNumber='" + businessRegistrationNumber + '\'' +
                ", gstNumber='" + gstNumber + '\'' +
                ", logoUrl='" + logoUrl + '\'' +
                ", websiteUrl='" + websiteUrl + '\'' +
                ", businessHours='" + businessHours + '\'' +
                ", hasBranch=" + hasBranch +
                ", isActive=" + isActive +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
