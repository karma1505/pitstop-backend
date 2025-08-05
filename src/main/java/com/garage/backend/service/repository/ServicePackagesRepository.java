package com.garage.backend.service.repository;

import com.garage.backend.service.entity.ServicePackages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Repository
public interface ServicePackagesRepository extends JpaRepository<ServicePackages, UUID> {

    // Find by service category ID
    List<ServicePackages> findByServiceCategoryId(UUID serviceCategoryId);

    // Find by package name containing text
    List<ServicePackages> findByPackageNameContainingIgnoreCase(String packageName);

    // Find custom packages
    List<ServicePackages> findByIsCustomTrue();

    // Find standard packages
    List<ServicePackages> findByIsCustomFalse();

    // Find by created by (staff ID)
    List<ServicePackages> findByCreatedBy(UUID createdBy);

    // Find active packages
    List<ServicePackages> findByIsActiveTrue();

    // Find by estimated cost range
    List<ServicePackages> findByEstimatedCostBetween(BigDecimal minCost, BigDecimal maxCost);

    // Find by estimated time range
    List<ServicePackages> findByEstimatedTimeBetween(Integer minTime, Integer maxTime);

    // Find packages by service category and custom flag
    List<ServicePackages> findByServiceCategoryIdAndIsCustom(UUID serviceCategoryId, Boolean isCustom);

    // Find packages by service category and active status
    List<ServicePackages> findByServiceCategoryIdAndIsActive(UUID serviceCategoryId, Boolean isActive);

    // Custom query to find packages by multiple criteria
    @Query("SELECT sp FROM ServicePackages sp WHERE " +
           "(:serviceCategoryId IS NULL OR sp.serviceCategoryId = :serviceCategoryId) AND " +
           "(:packageName IS NULL OR sp.packageName LIKE %:packageName%) AND " +
           "(:isCustom IS NULL OR sp.isCustom = :isCustom) AND " +
           "(:createdBy IS NULL OR sp.createdBy = :createdBy) AND " +
           "(:isActive IS NULL OR sp.isActive = :isActive)")
    List<ServicePackages> findPackagesByCriteria(@Param("serviceCategoryId") UUID serviceCategoryId,
                                              @Param("packageName") String packageName,
                                              @Param("isCustom") Boolean isCustom,
                                              @Param("createdBy") UUID createdBy,
                                              @Param("isActive") Boolean isActive);

    // Find packages by estimated cost
    List<ServicePackages> findByEstimatedCost(BigDecimal estimatedCost);

    // Find packages by estimated time
    List<ServicePackages> findByEstimatedTime(Integer estimatedTime);

    // Find packages with estimated cost greater than specified
    List<ServicePackages> findByEstimatedCostGreaterThan(BigDecimal estimatedCost);

    // Find packages with estimated cost less than specified
    List<ServicePackages> findByEstimatedCostLessThan(BigDecimal estimatedCost);
}
