package com.garage.backend.purchase.repository;

import com.garage.backend.purchase.entity.PurchaseReturn;
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
public interface PurchaseReturnRepository extends JpaRepository<PurchaseReturn, UUID> {

    // Find by return number
    Optional<PurchaseReturn> findByReturnNumber(String returnNumber);

    // Find by purchase ID
    List<PurchaseReturn> findByPurchaseId(UUID purchaseId);

    // Find by supplier ID
    List<PurchaseReturn> findBySupplierId(UUID supplierId);

    // Find by status
    List<PurchaseReturn> findByStatus(String status);

    // Find by return date range
    List<PurchaseReturn> findByReturnDateBetween(LocalDate startDate, LocalDate endDate);

    // Find by total amount range
    List<PurchaseReturn> findByTotalAmountBetween(BigDecimal minAmount, BigDecimal maxAmount);

    // Check if return number exists
    boolean existsByReturnNumber(String returnNumber);

    // Find returns by purchase ID and status
    List<PurchaseReturn> findByPurchaseIdAndStatus(UUID purchaseId, String status);

    // Find returns by supplier ID and status
    List<PurchaseReturn> findBySupplierIdAndStatus(UUID supplierId, String status);

    // Find returns by supplier ID and date range
    List<PurchaseReturn> findBySupplierIdAndReturnDateBetween(UUID supplierId, LocalDate startDate, LocalDate endDate);

    // Custom query to find returns by multiple criteria
    @Query("SELECT pr FROM PurchaseReturn pr WHERE " +
           "(:purchaseId IS NULL OR pr.purchaseId = :purchaseId) AND " +
           "(:supplierId IS NULL OR pr.supplierId = :supplierId) AND " +
           "(:status IS NULL OR pr.status = :status) AND " +
           "(:startDate IS NULL OR pr.returnDate >= :startDate) AND " +
           "(:endDate IS NULL OR pr.returnDate <= :endDate)")
    List<PurchaseReturn> findReturnsByCriteria(@Param("purchaseId") UUID purchaseId,
                                            @Param("supplierId") UUID supplierId,
                                            @Param("status") String status,
                                            @Param("startDate") LocalDate startDate,
                                            @Param("endDate") LocalDate endDate);

    // Find recent returns
    List<PurchaseReturn> findTop10ByOrderByReturnDateDesc();

    // Find pending returns
    List<PurchaseReturn> findByStatusOrderByReturnDateAsc(String status);

    // Find returns by total amount
    List<PurchaseReturn> findByTotalAmount(BigDecimal totalAmount);

    // Find returns by reason containing text
    List<PurchaseReturn> findByReasonContainingIgnoreCase(String reason);

    // Get total return amount for a supplier
    @Query("SELECT SUM(pr.totalAmount) FROM PurchaseReturn pr WHERE pr.supplierId = :supplierId")
    BigDecimal getTotalReturnAmountForSupplier(@Param("supplierId") UUID supplierId);

    // Get total return amount for a purchase
    @Query("SELECT SUM(pr.totalAmount) FROM PurchaseReturn pr WHERE pr.purchaseId = :purchaseId")
    BigDecimal getTotalReturnAmountForPurchase(@Param("purchaseId") UUID purchaseId);
}
