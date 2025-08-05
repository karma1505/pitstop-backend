package com.garage.backend.invoice.repository;

import com.garage.backend.invoice.entity.Invoice;
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
public interface InvoiceRepository extends JpaRepository<Invoice, UUID> {

    // Find by invoice number
    Optional<Invoice> findByInvoiceNumber(String invoiceNumber);

    // Find by customer ID
    List<Invoice> findByCustomerId(UUID customerId);

    // Find by job card ID
    List<Invoice> findByJobcardId(UUID jobcardId);

    // Find by branch ID
    List<Invoice> findByBranchId(UUID branchId);

    // Find by status
    List<Invoice> findByStatus(String status);

    // Find by invoice date range
    List<Invoice> findByInvoiceDateBetween(LocalDate startDate, LocalDate endDate);

    // Find by due date range
    List<Invoice> findByDueDateBetween(LocalDate startDate, LocalDate endDate);

    // Find overdue invoices
    List<Invoice> findByDueDateBeforeAndStatus(LocalDate date, String status);

    // Find by total amount range
    List<Invoice> findByTotalAmountBetween(BigDecimal minAmount, BigDecimal maxAmount);

    // Check if invoice number exists
    boolean existsByInvoiceNumber(String invoiceNumber);

    // Find invoices by customer and status
    List<Invoice> findByCustomerIdAndStatus(UUID customerId, String status);

    // Find invoices by branch and status
    List<Invoice> findByBranchIdAndStatus(UUID branchId, String status);

    // Find invoices by customer and date range
    List<Invoice> findByCustomerIdAndInvoiceDateBetween(UUID customerId, LocalDate startDate, LocalDate endDate);

    // Custom query to find invoices by multiple criteria
    @Query("SELECT i FROM Invoice i WHERE " +
           "(:customerId IS NULL OR i.customerId = :customerId) AND " +
           "(:jobcardId IS NULL OR i.jobcardId = :jobcardId) AND " +
           "(:branchId IS NULL OR i.branchId = :branchId) AND " +
           "(:status IS NULL OR i.status = :status) AND " +
           "(:startDate IS NULL OR i.invoiceDate >= :startDate) AND " +
           "(:endDate IS NULL OR i.invoiceDate <= :endDate)")
    List<Invoice> findInvoicesByCriteria(@Param("customerId") UUID customerId,
                                       @Param("jobcardId") UUID jobcardId,
                                       @Param("branchId") UUID branchId,
                                       @Param("status") String status,
                                       @Param("startDate") LocalDate startDate,
                                       @Param("endDate") LocalDate endDate);

    // Find recent invoices
    List<Invoice> findTop10ByOrderByInvoiceDateDesc();

    // Find pending invoices
    List<Invoice> findByStatusOrderByInvoiceDateAsc(String status);

    // Find invoices with outstanding amounts
    @Query("SELECT i FROM Invoice i WHERE i.status IN ('PENDING', 'OVERDUE')")
    List<Invoice> findOutstandingInvoices();
}
