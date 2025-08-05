package com.garage.backend.inventory.repository;

import com.garage.backend.inventory.entity.ProductInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductInventoryRepository extends JpaRepository<ProductInventory, UUID> {

    // Find by inventory ID
    Optional<ProductInventory> findByInventoryId(UUID inventoryId);

    // Find by quantity range
    List<ProductInventory> findByQuantityBetween(Integer minQuantity, Integer maxQuantity);

    // Find by reserved quantity range
    List<ProductInventory> findByReservedQuantityBetween(Integer minQuantity, Integer maxQuantity);

    // Find items with low stock (quantity <= 0)
    List<ProductInventory> findByQuantityLessThanEqual(Integer quantity);

    // Find items with available stock (quantity > reservedQuantity)
    @Query("SELECT pi FROM ProductInventory pi WHERE pi.quantity > pi.reservedQuantity")
    List<ProductInventory> findItemsWithAvailableStock();

    // Find items with no available stock
    @Query("SELECT pi FROM ProductInventory pi WHERE pi.quantity <= pi.reservedQuantity")
    List<ProductInventory> findItemsWithNoAvailableStock();

    // Find by last updated date range
    List<ProductInventory> findByLastUpdatedBetween(LocalDateTime startDate, LocalDateTime endDate);

    // Custom query to find product inventory by multiple criteria
    @Query("SELECT pi FROM ProductInventory pi WHERE " +
           "(:inventoryId IS NULL OR pi.inventoryId = :inventoryId) AND " +
           "(:minQuantity IS NULL OR pi.quantity >= :minQuantity) AND " +
           "(:maxQuantity IS NULL OR pi.quantity <= :maxQuantity)")
    List<ProductInventory> findProductInventoryByCriteria(@Param("inventoryId") UUID inventoryId,
                                                       @Param("minQuantity") Integer minQuantity,
                                                       @Param("maxQuantity") Integer maxQuantity);

    // Find items that need restocking (quantity = 0)
    List<ProductInventory> findByQuantity(Integer quantity);

    // Find items with high reserved quantity
    @Query("SELECT pi FROM ProductInventory pi WHERE pi.reservedQuantity > 0")
    List<ProductInventory> findItemsWithReservedStock();
}
