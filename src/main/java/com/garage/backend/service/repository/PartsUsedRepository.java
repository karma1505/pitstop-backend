package com.garage.backend.service.repository;

import com.garage.backend.service.entity.PartsUsed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface PartsUsedRepository extends JpaRepository<PartsUsed, UUID> {

    // Find by job card ID
    List<PartsUsed> findByJobcardId(UUID jobcardId);

    // Find by inventory ID
    List<PartsUsed> findByInventoryId(UUID inventoryId);

    // Find by quantity used range
    List<PartsUsed> findByQuantityUsedBetween(Integer minQuantity, Integer maxQuantity);

    // Find by unit price range
    List<PartsUsed> findByUnitPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);

    // Find by total price range
    List<PartsUsed> findByTotalPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);

    // Find by job card ID and inventory ID
    List<PartsUsed> findByJobcardIdAndInventoryId(UUID jobcardId, UUID inventoryId);

    // Find by created date range
    List<PartsUsed> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    // Custom query to find parts used by multiple criteria
    @Query("SELECT pu FROM PartsUsed pu WHERE " +
           "(:jobcardId IS NULL OR pu.jobcardId = :jobcardId) AND " +
           "(:inventoryId IS NULL OR pu.inventoryId = :inventoryId) AND " +
           "(:minQuantity IS NULL OR pu.quantityUsed >= :minQuantity) AND " +
           "(:maxQuantity IS NULL OR pu.quantityUsed <= :maxQuantity)")
    List<PartsUsed> findPartsUsedByCriteria(@Param("jobcardId") UUID jobcardId,
                                         @Param("inventoryId") UUID inventoryId,
                                         @Param("minQuantity") Integer minQuantity,
                                         @Param("maxQuantity") Integer maxQuantity);

    // Find recent parts usage
    List<PartsUsed> findTop10ByOrderByCreatedAtDesc();

    // Find parts used by quantity
    List<PartsUsed> findByQuantityUsed(Integer quantityUsed);

    // Find parts used by unit price
    List<PartsUsed> findByUnitPrice(BigDecimal unitPrice);

    // Find parts used by total price
    List<PartsUsed> findByTotalPrice(BigDecimal totalPrice);

    // Get total cost of parts used for a job card
    @Query("SELECT SUM(pu.totalPrice) FROM PartsUsed pu WHERE pu.jobcardId = :jobcardId")
    BigDecimal getTotalCostForJobCard(@Param("jobcardId") UUID jobcardId);

    // Get total quantity of parts used for a job card
    @Query("SELECT SUM(pu.quantityUsed) FROM PartsUsed pu WHERE pu.jobcardId = :jobcardId")
    Integer getTotalQuantityForJobCard(@Param("jobcardId") UUID jobcardId);
}
