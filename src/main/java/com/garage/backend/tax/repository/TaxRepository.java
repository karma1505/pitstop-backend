package com.garage.backend.tax.repository;

import com.garage.backend.tax.entity.Tax;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TaxRepository extends JpaRepository<Tax, UUID> {

    // Find by tax code
    Optional<Tax> findByTaxCode(String taxCode);

    // Find by tax name containing text
    List<Tax> findByTaxNameContainingIgnoreCase(String taxName);

    // Find active taxes
    List<Tax> findByIsActiveTrue();

    // Find by tax rate
    List<Tax> findByTaxRate(BigDecimal taxRate);

    // Find by tax rate range
    List<Tax> findByTaxRateBetween(BigDecimal minRate, BigDecimal maxRate);

    // Check if tax code exists
    boolean existsByTaxCode(String taxCode);

    // Custom query to find taxes by multiple criteria
    @Query("SELECT t FROM Tax t WHERE " +
           "(:taxName IS NULL OR t.taxName LIKE %:taxName%) AND " +
           "(:taxCode IS NULL OR t.taxCode = :taxCode) AND " +
           "(:isActive IS NULL OR t.isActive = :isActive)")
    List<Tax> findTaxesByCriteria(@Param("taxName") String taxName,
                                @Param("taxCode") String taxCode,
                                @Param("isActive") Boolean isActive);

    // Find taxes with rate greater than specified
    List<Tax> findByTaxRateGreaterThan(BigDecimal taxRate);

    // Find taxes with rate less than specified
    List<Tax> findByTaxRateLessThan(BigDecimal taxRate);

    // Find taxes by description containing text
    List<Tax> findByDescriptionContainingIgnoreCase(String description);
}
