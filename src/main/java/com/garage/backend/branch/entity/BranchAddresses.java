package com.garage.backend.branch.entity;

import com.garage.backend.address.entity.Addresses;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "Branch_Addresses")
@EntityListeners(AuditingEntityListener.class)
public class BranchAddresses {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "UUID")
    private UUID id;

    @NotNull(message = "Branch ID is required")
    @Column(name = "branch_id", nullable = false, columnDefinition = "UUID")
    private UUID branchId;

    @NotNull(message = "Address ID is required")
    @Column(name = "address_id", nullable = false, columnDefinition = "UUID")
    private UUID addressId;

    @NotBlank(message = "Address type is required")
    @Size(max = 50, message = "Address type must not exceed 50 characters")
    @Column(name = "address_type", nullable = false)
    private String addressType;

    @NotNull(message = "Primary flag is required")
    @Column(name = "is_primary", nullable = false)
    private Boolean isPrimary = false;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // Many-to-One relationship with Branch
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id", insertable = false, updatable = false)
    private Branch branch;

    // Many-to-One relationship with Addresses
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", insertable = false, updatable = false)
    private Addresses address;

    // Constructors
    public BranchAddresses() {}

    public BranchAddresses(UUID branchId, UUID addressId, String addressType, Boolean isPrimary) {
        this.branchId = branchId;
        this.addressId = addressId;
        this.addressType = addressType;
        this.isPrimary = isPrimary;
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getBranchId() {
        return branchId;
    }

    public void setBranchId(UUID branchId) {
        this.branchId = branchId;
    }

    public UUID getAddressId() {
        return addressId;
    }

    public void setAddressId(UUID addressId) {
        this.addressId = addressId;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    public Boolean getIsPrimary() {
        return isPrimary;
    }

    public void setIsPrimary(Boolean isPrimary) {
        this.isPrimary = isPrimary;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public Addresses getAddress() {
        return address;
    }

    public void setAddress(Addresses address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "BranchAddresses{" +
                "id=" + id +
                ", branchId=" + branchId +
                ", addressId=" + addressId +
                ", addressType='" + addressType + '\'' +
                ", isPrimary=" + isPrimary +
                ", createdAt=" + createdAt +
                '}';
    }
}
