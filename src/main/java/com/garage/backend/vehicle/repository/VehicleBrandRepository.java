package com.garage.backend.vehicle.repository;

import com.garage.backend.vehicle.entity.VehicleBrand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VehicleBrandRepository extends JpaRepository<VehicleBrand, UUID> {

    // Find by brand name
    Optional<VehicleBrand> findByBrandName(String brandName);

    // Find active brands
    List<VehicleBrand> findByIsActiveTrue();

    // Find by manufacturer
    List<VehicleBrand> findByManufacturer(String manufacturer);

    // Find by country of origin
    List<VehicleBrand> findByCountryOfOrigin(String countryOfOrigin);

    // Find brands by brand name containing text
    List<VehicleBrand> findByBrandNameContainingIgnoreCase(String brandName);

    // Find brands by manufacturer containing text
    List<VehicleBrand> findByManufacturerContainingIgnoreCase(String manufacturer);

    // Check if brand name exists
    boolean existsByBrandName(String brandName);

    // Custom query to find brands by multiple criteria
    @Query("SELECT vb FROM VehicleBrand vb WHERE " +
           "(:brandName IS NULL OR vb.brandName LIKE %:brandName%) AND " +
           "(:manufacturer IS NULL OR vb.manufacturer = :manufacturer) AND " +
           "(:countryOfOrigin IS NULL OR vb.countryOfOrigin = :countryOfOrigin) AND " +
           "(:isActive IS NULL OR vb.isActive = :isActive)")
    List<VehicleBrand> findBrandsByCriteria(@Param("brandName") String brandName,
                                          @Param("manufacturer") String manufacturer,
                                          @Param("countryOfOrigin") String countryOfOrigin,
                                          @Param("isActive") Boolean isActive);
}
