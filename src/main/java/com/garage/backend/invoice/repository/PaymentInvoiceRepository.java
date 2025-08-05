package com.garage.backend.invoice.repository;

import com.garage.backend.invoice.entity.PaymentInvoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface PaymentInvoiceRepository extends JpaRepository<PaymentInvoice, UUID> {

    // Find by invoice ID
    List<PaymentInvoice> findByInvoiceId(UUID invoiceId);

    // Find by payment method ID
    List<PaymentInvoice> findByPaymentMethodId(UUID paymentMethodId);

    // Find by payment date range
    List<PaymentInvoice> findByPaymentDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    // Find by amount range
    List<PaymentInvoice> findByAmountBetween(BigDecimal minAmount, BigDecimal maxAmount);

    // Find by reference number
    List<PaymentInvoice> findByReferenceNumber(String referenceNumber);

    // Find payments by invoice ID and payment method
    List<PaymentInvoice> findByInvoiceIdAndPaymentMethodId(UUID invoiceId, UUID paymentMethodId);

    // Find payments by payment method and date range
    List<PaymentInvoice> findByPaymentMethodIdAndPaymentDateBetween(UUID paymentMethodId, LocalDateTime startDate, LocalDateTime endDate);

    // Custom query to find payments by multiple criteria
    @Query("SELECT pi FROM PaymentInvoice pi WHERE " +
           "(:invoiceId IS NULL OR pi.invoiceId = :invoiceId) AND " +
           "(:paymentMethodId IS NULL OR pi.paymentMethodId = :paymentMethodId) AND " +
           "(:startDate IS NULL OR pi.paymentDate >= :startDate) AND " +
           "(:endDate IS NULL OR pi.paymentDate <= :endDate)")
    List<PaymentInvoice> findPaymentsByCriteria(@Param("invoiceId") UUID invoiceId,
                                             @Param("paymentMethodId") UUID paymentMethodId,
                                             @Param("startDate") LocalDateTime startDate,
                                             @Param("endDate") LocalDateTime endDate);

    // Find recent payments
    List<PaymentInvoice> findTop10ByOrderByPaymentDateDesc();

    // Find payments by reference number containing text
    List<PaymentInvoice> findByReferenceNumberContainingIgnoreCase(String referenceNumber);

    // Get total amount paid for an invoice
    @Query("SELECT SUM(pi.amount) FROM PaymentInvoice pi WHERE pi.invoiceId = :invoiceId")
    BigDecimal getTotalAmountPaidForInvoice(@Param("invoiceId") UUID invoiceId);

    // Find payments by amount
    List<PaymentInvoice> findByAmount(BigDecimal amount);
}
