package com.garage.backend.inventory.repository;

import com.garage.backend.inventory.entity.InventoryTransfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface InventoryTransferRepository extends JpaRepository<InventoryTransfer, UUID> {

    // Find by transfer number
    Optional<InventoryTransfer> findByTransferNumber(String transferNumber);

    // Find by inventory ID
    List<InventoryTransfer> findByInventoryId(UUID inventoryId);

    // Find by from branch ID
    List<InventoryTransfer> findByFromBranchId(UUID fromBranchId);

    // Find by to branch ID
    List<InventoryTransfer> findByToBranchId(UUID toBranchId);

    // Find by status
    List<InventoryTransfer> findByStatus(String status);

    // Find by transferred by (staff ID)
    List<InventoryTransfer> findByTransferredBy(UUID transferredBy);

    // Find by transfer date range
    List<InventoryTransfer> findByTransferDateBetween(LocalDate startDate, LocalDate endDate);

    // Find transfers by inventory ID anv
    // 5d status
    List<InventoryTransfer> findByInventoryIdAndStatus(UUID inventoryId, String status);

    // Find transfers by from branch and status
    List<InventoryTransfer> findByFromBranchIdAndStatus(UUID fromBranchId, String status);

    // Find transfers by to branch and status
    List<InventoryTransfer> findByToBranchIdAndStatus(UUID toBranchId, String status);

    // Find pending transfers
    List<InventoryTransfer> findByStatusOrderByTransferDateAsc(String status);

    // Check if transfer number exists
    boolean existsByTransferNumber(String transferNumber);

    // Custom query to find transfers by multiple criteria
    @Query("SELECT it FROM InventoryTransfer it WHERE " +
           "(:inventoryId IS NULL OR it.inventoryId = :inventoryId) AND " +
           "(:fromBranchId IS NULL OR it.fromBranchId = :fromBranchId) AND " +
           "(:toBranchId IS NULL OR it.toBranchId = :toBranchId) AND " +
           "(:status IS NULL OR it.status = :status) AND " +
           "(:transferredBy IS NULL OR it.transferredBy = :transferredBy) AND " +
           "(:startDate IS NULL OR it.transferDate >= :startDate) AND " +
           "(:endDate IS NULL OR it.transferDate <= :endDate)")
    List<InventoryTransfer> findTransfersByCriteria(@Param("inventoryId") UUID inventoryId,
                                                 @Param("fromBranchId") UUID fromBranchId,
                                                 @Param("toBranchId") UUID toBranchId,
                                                 @Param("status") String status,
                                                 @Param("transferredBy") UUID transferredBy,
                                                 @Param("startDate") LocalDate startDate,
                                                 @Param("endDate") LocalDate endDate);

    // Find recent transfers
    List<InventoryTransfer> findTop10ByOrderByTransferDateDesc();

    // Find transfers between specific branches
    List<InventoryTransfer> findByFromBranchIdAndToBranchId(UUID fromBranchId, UUID toBranchId);
}
