package com.garage.backend.customer.repository;

import com.garage.backend.customer.entity.CustomerAddresses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CustomerAddressesRepository extends JpaRepository<CustomerAddresses, UUID> {

    // Find addresses by customer ID
    List<CustomerAddresses> findByCustomerId(UUID customerId);

    // Find addresses by address ID
    List<CustomerAddresses> findByAddressId(UUID addressId);

    // Find primary address for a customer
    List<CustomerAddresses> findByCustomerIdAndIsPrimaryTrue(UUID customerId);

    // Find addresses by address type
    List<CustomerAddresses> findByAddressType(String addressType);

    // Find addresses by customer ID and address type
    List<CustomerAddresses> findByCustomerIdAndAddressType(UUID customerId, String addressType);

    // Check if customer has primary address
    boolean existsByCustomerIdAndIsPrimaryTrue(UUID customerId);

    // Custom query to find customer addresses by multiple criteria
    @Query("SELECT ca FROM CustomerAddresses ca WHERE " +
           "(:customerId IS NULL OR ca.customerId = :customerId) AND " +
           "(:addressType IS NULL OR ca.addressType = :addressType) AND " +
           "(:isPrimary IS NULL OR ca.isPrimary = :isPrimary)")
    List<CustomerAddresses> findCustomerAddressesByCriteria(@Param("customerId") UUID customerId,
                                                          @Param("addressType") String addressType,
                                                          @Param("isPrimary") Boolean isPrimary);
}
