package com.garage.backend.staff.entity;

import com.garage.backend.settings.entity.Garage;
import com.garage.backend.shared.enums.Enums;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "Staff")
@EntityListeners(AuditingEntityListener.class)
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "UUID")
    private UUID id;

    @NotNull(message = "Garage ID is required")
    @Column(name = "garage_id", nullable = false, columnDefinition = "UUID")
    private UUID garageId;

    // Many-to-One relationship with Garage (read-only)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "garage_id", insertable = false, updatable = false)
    private Garage garage;

    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Size(max = 50, message = "Last name must not exceed 50 characters")
    @Column(name = "last_name")
    private String lastName;

    @NotBlank(message = "Mobile number is required")
    @Pattern(regexp = "^[0-9]{10,11}$", message = "Mobile number must be 10-11 digits")
    @Column(name = "mobile_number", nullable = false)
    private String mobileNumber;

    @Size(max = 12, message = "Aadhar number must not exceed 12 characters")
    @Column(name = "aadhar_number")
    private String aadharNumber;

    @NotNull(message = "Role is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Enums.StaffRole role;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "jobs_completed")
    private Integer jobsCompleted = 0;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // Constructors
    public Staff() {}

    public Staff(UUID garageId, String firstName, String lastName, String mobileNumber, String aadharNumber, Enums.StaffRole role) {
        this.garageId = garageId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobileNumber = mobileNumber;
        this.aadharNumber = aadharNumber;
        this.role = role;
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getGarageId() {
        return garageId;
    }

    public void setGarageId(UUID garageId) {
        this.garageId = garageId;
    }

    public Garage getGarage() {
        return garage;
    }

    public void setGarage(Garage garage) {
        this.garage = garage;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getAadharNumber() {
        return aadharNumber;
    }

    public void setAadharNumber(String aadharNumber) {
        this.aadharNumber = aadharNumber;
    }

    public Enums.StaffRole getRole() {
        return role;
    }

    public void setRole(Enums.StaffRole role) {
        this.role = role;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Integer getJobsCompleted() {
        return jobsCompleted;
    }

    public void setJobsCompleted(Integer jobsCompleted) {
        this.jobsCompleted = jobsCompleted;
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
        return "Staff{" +
                "id=" + id +
                ", garageId=" + garageId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", aadharNumber='" + aadharNumber + '\'' +
                ", role='" + role + '\'' +
                ", isActive=" + isActive +
                ", jobsCompleted=" + jobsCompleted +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
} 