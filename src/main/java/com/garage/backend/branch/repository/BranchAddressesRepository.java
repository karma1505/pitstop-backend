package com.garage.backend.branch.repository;

import com.garage.backend.branch.entity.BranchAddresses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BranchAddressesRepository extends JpaRepository<BranchAddresses, UUID> {

    // Find addresses by branch ID
    List<BranchAddresses> findByBranchId(UUID branchId);

    // Find addresses by address ID
    List<BranchAddresses> findByAddressId(UUID addressId);

    // Find primary address for a branch
    List<BranchAddresses> findByBranchIdAndIsPrimaryTrue(UUID branchId);

    // Find addresses by address type
    List<BranchAddresses> findByAddressType(String addressType);

    // Find addresses by branch ID and address type
    List<BranchAddresses> findByBranchIdAndAddressType(UUID branchId, String addressType);

    // Check if branch has primary address
    boolean existsByBranchIdAndIsPrimaryTrue(UUID branchId);

    // Custom query to find branch addresses by multiple criteria
    @Query("SELECT ba FROM BranchAddresses ba WHERE " +
           "(:branchId IS NULL OR ba.branchId = :branchId) AND " +
           "(:addressType IS NULL OR ba.addressType = :addressType) AND " +
           "(:isPrimary IS NULL OR ba.isPrimary = :isPrimary)")
    List<BranchAddresses> findBranchAddressesByCriteria(@Param("branchId") UUID branchId,
                                                       @Param("addressType") String addressType,
                                                       @Param("isPrimary") Boolean isPrimary);
}
