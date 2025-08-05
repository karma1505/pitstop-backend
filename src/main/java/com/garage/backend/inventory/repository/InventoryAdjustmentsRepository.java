package com.garage.backend.inventory.repository;

import com.garage.backend.inventory.entity.InventoryAdjustments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface InventoryAdjustmentsRepository extends JpaRepository<InventoryAdjustments, UUID> {

    // Find by inventory ID
    List<InventoryAdjustments> findByInventoryId(UUID inventoryId);

    // Find by adjustment type
    List<InventoryAdjustments> findByAdjustmentType(String adjustmentType);

    // Find by adjusted by (staff ID)
    List<InventoryAdjustments> findByAdjustedBy(UUID adjustedBy);

    // Find by adjusted date range
    List<InventoryAdjustments> findByAdjustedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    // Find by reference number
    List<InventoryAdjustments> findByReferenceNumber(String referenceNumber);

    // Find adjustments by inventory ID and adjustment type
    List<InventoryAdjustments> findByInventoryIdAndAdjustmentType(UUID inventoryId, String adjustmentType);

    // Find adjustments by inventory ID and date range
    List<InventoryAdjustments> findByInventoryIdAndAdjustedAtBetween(UUID inventoryId, LocalDateTime startDate, LocalDateTime endDate);

    // Custom query to find adjustments by multiple criteria
    @Query("SELECT ia FROM InventoryAdjustments ia WHERE " +
           "(:inventoryId IS NULL OR ia.inventoryId = :inventoryId) AND " +
           "(:adjustmentType IS NULL OR ia.adjustmentType = :adjustmentType) AND " +
           "(:adjustedBy IS NULL OR ia.adjustedBy = :adjustedBy) AND " +
           "(:startDate IS NULL OR ia.adjustedAt >= :startDate) AND " +
           "(:endDate IS NULL OR ia.adjustedAt <= :endDate)")
    List<InventoryAdjustments> findAdjustmentsByCriteria(@Param("inventoryId") UUID inventoryId,
                                                       @Param("adjustmentType") String adjustmentType,
                                                       @Param("adjustedBy") UUID adjustedBy,
                                                       @Param("startDate") LocalDateTime startDate,
                                                       @Param("endDate") LocalDateTime endDate);

    // Find recent adjustments
    List<InventoryAdjustments> findTop10ByOrderByAdjustedAtDesc();

    // Find adjustments by reason containing text
    List<InventoryAdjustments> findByReasonContainingIgnoreCase(String reason);
}
