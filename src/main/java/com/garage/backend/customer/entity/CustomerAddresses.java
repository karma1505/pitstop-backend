package com.garage.backend.customer.entity;

import com.garage.backend.address.entity.Addresses;
import com.garage.backend.customer.entity.Customers;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "Customer_Addresses")
@EntityListeners(AuditingEntityListener.class)
public class CustomerAddresses {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "UUID")
    private UUID id;

    @NotNull(message = "Customer ID is required")
    @Column(name = "customer_id", nullable = false, columnDefinition = "UUID")
    private UUID customerId;

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

    // Many-to-One relationship with Customers
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", insertable = false, updatable = false)
    private Customers customer;

    // Many-to-One relationship with Addresses
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", insertable = false, updatable = false)
    private Addresses address;

    // Constructors
    public CustomerAddresses() {}

    public CustomerAddresses(UUID customerId, UUID addressId, String addressType, Boolean isPrimary) {
        this.customerId = customerId;
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

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
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

    public Customers getCustomer() {
        return customer;
    }

    public void setCustomer(Customers customer) {
        this.customer = customer;
    }

    public Addresses getAddress() {
        return address;
    }

    public void setAddress(Addresses address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "CustomerAddresses{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", addressId=" + addressId +
                ", addressType='" + addressType + '\'' +
                ", isPrimary=" + isPrimary +
                ", createdAt=" + createdAt +
                '}';
    }
}
