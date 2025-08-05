package com.garage.backend.supplier.repository;

import com.garage.backend.supplier.entity.SupplierAddresses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SupplierAddressesRepository extends JpaRepository<SupplierAddresses, UUID> {

    // Find addresses by supplier ID
    List<SupplierAddresses> findBySupplierId(UUID supplierId);

    // Find addresses by address ID
    List<SupplierAddresses> findByAddressId(UUID addressId);

    // Find primary address for a supplier
    List<SupplierAddresses> findBySupplierIdAndIsPrimaryTrue(UUID supplierId);

    // Find addresses by address type
    List<SupplierAddresses> findByAddressType(String addressType);

    // Find addresses by supplier ID and address type
    List<SupplierAddresses> findBySupplierIdAndAddressType(UUID supplierId, String addressType);

    // Check if supplier has primary address
    boolean existsBySupplierIdAndIsPrimaryTrue(UUID supplierId);

    // Custom query to find supplier addresses by multiple criteria
    @Query("SELECT sa FROM SupplierAddresses sa WHERE " +
           "(:supplierId IS NULL OR sa.supplierId = :supplierId) AND " +
           "(:addressType IS NULL OR sa.addressType = :addressType) AND " +
           "(:isPrimary IS NULL OR sa.isPrimary = :isPrimary)")
    List<SupplierAddresses> findSupplierAddressesByCriteria(@Param("supplierId") UUID supplierId,
                                                         @Param("addressType") String addressType,
                                                         @Param("isPrimary") Boolean isPrimary);

    // Find addresses by supplier ID and primary flag
    List<SupplierAddresses> findBySupplierIdAndIsPrimary(UUID supplierId, Boolean isPrimary);
}
