package com.garage.backend.service.repository;

import com.garage.backend.service.entity.ServiceCategories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ServiceCategoriesRepository extends JpaRepository<ServiceCategories, UUID> {

    // Find by category name
    Optional<ServiceCategories> findByCategoryName(String categoryName);

    // Find by category code
    Optional<ServiceCategories> findByCategoryCode(String categoryCode);

    // Find active categories
    List<ServiceCategories> findByIsActiveTrue();

    // Find by category name containing text
    List<ServiceCategories> findByCategoryNameContainingIgnoreCase(String categoryName);

    // Find by estimated time range
    List<ServiceCategories> findByEstimatedTimeBetween(Integer minTime, Integer maxTime);

    // Check if category name exists
    boolean existsByCategoryName(String categoryName);

    // Check if category code exists
    boolean existsByCategoryCode(String categoryCode);

    // Custom query to find categories by multiple criteria
    @Query("SELECT sc FROM ServiceCategories sc WHERE " +
           "(:categoryName IS NULL OR sc.categoryName LIKE %:categoryName%) AND " +
           "(:categoryCode IS NULL OR sc.categoryCode = :categoryCode) AND " +
           "(:isActive IS NULL OR sc.isActive = :isActive)")
    List<ServiceCategories> findCategoriesByCriteria(@Param("categoryName") String categoryName,
                                                  @Param("categoryCode") String categoryCode,
                                                  @Param("isActive") Boolean isActive);

    // Find categories by estimated time
    List<ServiceCategories> findByEstimatedTime(Integer estimatedTime);

    // Find categories with estimated time greater than specified
    List<ServiceCategories> findByEstimatedTimeGreaterThan(Integer estimatedTime);

    // Find categories with estimated time less than specified
    List<ServiceCategories> findByEstimatedTimeLessThan(Integer estimatedTime);
}
