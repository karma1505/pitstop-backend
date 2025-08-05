package com.garage.backend.purchase.repository;

import com.garage.backend.purchase.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, UUID> {

    // Find by purchase number
    Optional<Purchase> findByPurchaseNumber(String purchaseNumber);

    // Find by supplier ID
    List<Purchase> findBySupplierId(UUID supplierId);

    // Find by branch ID
    List<Purchase> findByBranchId(UUID branchId);

    // Find by status
    List<Purchase> findByStatus(String status);

    // Find by purchase date range
    List<Purchase> findByPurchaseDateBetween(LocalDate startDate, LocalDate endDate);

    // Find by expected delivery date range
    List<Purchase> findByExpectedDeliveryBetween(LocalDate startDate, LocalDate endDate);

    // Find by total amount range
    List<Purchase> findByTotalAmountBetween(BigDecimal minAmount, BigDecimal maxAmount);

    // Check if purchase number exists
    boolean existsByPurchaseNumber(String purchaseNumber);

    // Find purchases by supplier and status
    List<Purchase> findBySupplierIdAndStatus(UUID supplierId, String status);

    // Find purchases by branch and status
    List<Purchase> findByBranchIdAndStatus(UUID branchId, String status);

    // Find purchases by supplier and date range
    List<Purchase> findBySupplierIdAndPurchaseDateBetween(UUID supplierId, LocalDate startDate, LocalDate endDate);

    // Find purchases by branch and date range
    List<Purchase> findByBranchIdAndPurchaseDateBetween(UUID branchId, LocalDate startDate, LocalDate endDate);

    // Custom query to find purchases by multiple criteria
    @Query("SELECT p FROM Purchase p WHERE " +
           "(:supplierId IS NULL OR p.supplierId = :supplierId) AND " +
           "(:branchId IS NULL OR p.branchId = :branchId) AND " +
           "(:status IS NULL OR p.status = :status) AND " +
           "(:startDate IS NULL OR p.purchaseDate >= :startDate) AND " +
           "(:endDate IS NULL OR p.purchaseDate <= :endDate)")
    List<Purchase> findPurchasesByCriteria(@Param("supplierId") UUID supplierId,
                                        @Param("branchId") UUID branchId,
                                        @Param("status") String status,
                                        @Param("startDate") LocalDate startDate,
                                        @Param("endDate") LocalDate endDate);

    // Find recent purchases
    List<Purchase> findTop10ByOrderByPurchaseDateDesc();

    // Find pending purchases
    List<Purchase> findByStatusOrderByPurchaseDateAsc(String status);

    // Find purchases with overdue delivery
    List<Purchase> findByExpectedDeliveryBeforeAndStatus(LocalDate date, String status);

    // Find purchases by total amount
    List<Purchase> findByTotalAmount(BigDecimal totalAmount);

    // Find purchases by subtotal range
    List<Purchase> findBySubtotalBetween(BigDecimal minSubtotal, BigDecimal maxSubtotal);
}
