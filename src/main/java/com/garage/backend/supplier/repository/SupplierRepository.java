package com.garage.backend.supplier.repository;

import com.garage.backend.supplier.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, UUID> {

    // Find by supplier code
    Optional<Supplier> findBySupplierCode(String supplierCode);

    // Find by supplier name containing text
    List<Supplier> findBySupplierNameContainingIgnoreCase(String supplierName);

    // Find by phone
    Optional<Supplier> findByPhone(String phone);

    // Find by email
    Optional<Supplier> findByEmail(String email);

    // Find by GST number
    Optional<Supplier> findByGstNumber(String gstNumber);

    // Find active suppliers
    List<Supplier> findByIsActiveTrue();

    // Find by contact person containing text
    List<Supplier> findByContactPersonContainingIgnoreCase(String contactPerson);

    // Find by credit limit range
    List<Supplier> findByCreditLimitBetween(BigDecimal minLimit, BigDecimal maxLimit);

    // Check if supplier code exists
    boolean existsBySupplierCode(String supplierCode);

    // Check if phone exists
    boolean existsByPhone(String phone);

    // Check if email exists
    boolean existsByEmail(String email);

    // Check if GST number exists
    boolean existsByGstNumber(String gstNumber);

    // Custom query to find suppliers by multiple criteria
    @Query("SELECT s FROM Supplier s WHERE " +
           "(:supplierName IS NULL OR s.supplierName LIKE %:supplierName%) AND " +
           "(:contactPerson IS NULL OR s.contactPerson LIKE %:contactPerson%) AND " +
           "(:phone IS NULL OR s.phone = :phone) AND " +
           "(:email IS NULL OR s.email = :email) AND " +
           "(:gstNumber IS NULL OR s.gstNumber = :gstNumber) AND " +
           "(:isActive IS NULL OR s.isActive = :isActive)")
    List<Supplier> findSuppliersByCriteria(@Param("supplierName") String supplierName,
                                        @Param("contactPerson") String contactPerson,
                                        @Param("phone") String phone,
                                        @Param("email") String email,
                                        @Param("gstNumber") String gstNumber,
                                        @Param("isActive") Boolean isActive);

    // Find suppliers with credit limit greater than specified
    List<Supplier> findByCreditLimitGreaterThan(BigDecimal creditLimit);

    // Find suppliers with credit limit less than specified
    List<Supplier> findByCreditLimitLessThan(BigDecimal creditLimit);

    // Find suppliers by payment terms
    List<Supplier> findByPaymentTerms(String paymentTerms);

    // Find suppliers by payment terms containing text
    List<Supplier> findByPaymentTermsContainingIgnoreCase(String paymentTerms);
}
