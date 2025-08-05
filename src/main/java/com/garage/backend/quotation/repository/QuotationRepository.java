package com.garage.backend.quotation.repository;

import com.garage.backend.quotation.entity.Quotation;
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
public interface QuotationRepository extends JpaRepository<Quotation, UUID> {

    // Find by quotation number
    Optional<Quotation> findByQuotationNumber(String quotationNumber);

    // Find by customer ID
    List<Quotation> findByCustomerId(UUID customerId);

    // Find by vehicle ID
    List<Quotation> findByVehicleId(UUID vehicleId);

    // Find by branch ID
    List<Quotation> findByBranchId(UUID branchId);

    // Find by status
    List<Quotation> findByStatus(String status);

    // Find by quotation date range
    List<Quotation> findByQuotationDateBetween(LocalDate startDate, LocalDate endDate);

    // Find by valid until date range
    List<Quotation> findByValidUntilBetween(LocalDate startDate, LocalDate endDate);

    // Find expired quotations
    List<Quotation> findByValidUntilBeforeAndStatus(LocalDate date, String status);

    // Find by total amount range
    List<Quotation> findByTotalAmountBetween(BigDecimal minAmount, BigDecimal maxAmount);

    // Check if quotation number exists
    boolean existsByQuotationNumber(String quotationNumber);

    // Find quotations by customer and status
    List<Quotation> findByCustomerIdAndStatus(UUID customerId, String status);

    // Find quotations by branch and status
    List<Quotation> findByBranchIdAndStatus(UUID branchId, String status);

    // Find quotations by customer and date range
    List<Quotation> findByCustomerIdAndQuotationDateBetween(UUID customerId, LocalDate startDate, LocalDate endDate);

    // Find quotations by vehicle and status
    List<Quotation> findByVehicleIdAndStatus(UUID vehicleId, String status);

    // Custom query to find quotations by multiple criteria
    @Query("SELECT q FROM Quotation q WHERE " +
           "(:customerId IS NULL OR q.customerId = :customerId) AND " +
           "(:vehicleId IS NULL OR q.vehicleId = :vehicleId) AND " +
           "(:branchId IS NULL OR q.branchId = :branchId) AND " +
           "(:status IS NULL OR q.status = :status) AND " +
           "(:startDate IS NULL OR q.quotationDate >= :startDate) AND " +
           "(:endDate IS NULL OR q.quotationDate <= :endDate)")
    List<Quotation> findQuotationsByCriteria(@Param("customerId") UUID customerId,
                                          @Param("vehicleId") UUID vehicleId,
                                          @Param("branchId") UUID branchId,
                                          @Param("status") String status,
                                          @Param("startDate") LocalDate startDate,
                                          @Param("endDate") LocalDate endDate);

    // Find recent quotations
    List<Quotation> findTop10ByOrderByQuotationDateDesc();

    // Find draft quotations
    List<Quotation> findByStatusOrderByQuotationDateAsc(String status);

    // Find quotations by total amount
    List<Quotation> findByTotalAmount(BigDecimal totalAmount);

    // Find quotations by subtotal range
    List<Quotation> findBySubtotalBetween(BigDecimal minSubtotal, BigDecimal maxSubtotal);

    // Find active quotations (not expired)
    @Query("SELECT q FROM Quotation q WHERE q.validUntil >= :currentDate OR q.validUntil IS NULL")
    List<Quotation> findActiveQuotations(@Param("currentDate") LocalDate currentDate);
}
