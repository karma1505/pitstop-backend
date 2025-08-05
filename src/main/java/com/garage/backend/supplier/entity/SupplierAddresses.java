package com.garage.backend.supplier.entity;

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
@Table(name = "Supplier_Addresses")
@EntityListeners(AuditingEntityListener.class)
public class SupplierAddresses {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "UUID")
    private UUID id;

    @NotNull(message = "Supplier ID is required")
    @Column(name = "supplier_id", nullable = false, columnDefinition = "UUID")
    private UUID supplierId;

    @NotNull(message = "Address ID is required")
    @Column(name = "address_id", nullable = false, columnDefinition = "UUID")
    private UUID addressId;

    @NotBlank(message = "Address type is required")
    @Size(max = 50, message = "Address type must not exceed 50 characters")
    @Column(name = "address_type", nullable = false)
    private String addressType; // Billing/Shipping/etc

    @NotNull(message = "Primary flag is required")
    @Column(name = "is_primary", nullable = false)
    private Boolean isPrimary = false;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // Many-to-One relationship with Supplier
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id", insertable = false, updatable = false)
    private Supplier supplier;

    // Many-to-One relationship with Addresses
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", insertable = false, updatable = false)
    private Addresses address;

    // Constructors
    public SupplierAddresses() {}

    public SupplierAddresses(UUID supplierId, UUID addressId, String addressType, Boolean isPrimary) {
        this.supplierId = supplierId;
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

    public UUID getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(UUID supplierId) {
        this.supplierId = supplierId;
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

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Addresses getAddress() {
        return address;
    }

    public void setAddress(Addresses address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "SupplierAddresses{" +
                "id=" + id +
                ", supplierId=" + supplierId +
                ", addressId=" + addressId +
                ", addressType='" + addressType + '\'' +
                ", isPrimary=" + isPrimary +
                ", createdAt=" + createdAt +
                '}';
    }
}
