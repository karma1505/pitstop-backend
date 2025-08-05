package com.garage.backend.inventory.repository;

import com.garage.backend.inventory.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, UUID> {

    // Find by item code
    Optional<Inventory> findByItemCode(String itemCode);

    // Find by item name containing text
    List<Inventory> findByItemNameContainingIgnoreCase(String itemName);

    // Find by category
    List<Inventory> findByCategory(String category);

    // Find by supplier ID
    List<Inventory> findBySupplierId(UUID supplierId);

    // Find by branch ID
    List<Inventory> findByBranchId(UUID branchId);

    // Find active items
    List<Inventory> findByIsActiveTrue();

    // Find by cost price range
    List<Inventory> findByCostPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);

    // Find by selling price range
    List<Inventory> findBySellingPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);

    // Check if item code exists
    boolean existsByItemCode(String itemCode);

    // Find items with low stock (below minimum level)
    @Query("SELECT i FROM Inventory i WHERE i.minStockLevel > 0")
    List<Inventory> findItemsWithLowStock();

    // Custom query to find inventory by multiple criteria
    @Query("SELECT i FROM Inventory i WHERE " +
           "(:itemName IS NULL OR i.itemName LIKE %:itemName%) AND " +
           "(:category IS NULL OR i.category = :category) AND " +
           "(:supplierId IS NULL OR i.supplierId = :supplierId) AND " +
           "(:branchId IS NULL OR i.branchId = :branchId) AND " +
           "(:isActive IS NULL OR i.isActive = :isActive)")
    List<Inventory> findInventoryByCriteria(@Param("itemName") String itemName,
                                         @Param("category") String category,
                                         @Param("supplierId") UUID supplierId,
                                         @Param("branchId") UUID branchId,
                                         @Param("isActive") Boolean isActive);

    // Find items by branch and category
    List<Inventory> findByBranchIdAndCategory(UUID branchId, String category);

    // Find items by supplier and branch
    List<Inventory> findBySupplierIdAndBranchId(UUID supplierId, UUID branchId);
}
