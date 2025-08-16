package com.garage.backend.settings.repository;

import com.garage.backend.settings.entity.Garage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GarageRepository extends JpaRepository<Garage, UUID> {

    // Find by garage name containing text
    List<Garage> findByGarageNameContainingIgnoreCase(String garageName);

    // Find by business registration number
    Optional<Garage> findByBusinessRegistrationNumber(String businessRegistrationNumber);

    // Find by GST number
    Optional<Garage> findByGstNumber(String gstNumber);

    // Find by website URL
    List<Garage> findByWebsiteUrl(String websiteUrl);

    // Find active garages
    List<Garage> findByIsActiveTrue();

    // Check if business registration number exists
    boolean existsByBusinessRegistrationNumber(String businessRegistrationNumber);

    // Check if GST number exists
    boolean existsByGstNumber(String gstNumber);

    // Custom query to find garages by multiple criteria
    @Query("SELECT g FROM Garage g WHERE " +
           "(:garageName IS NULL OR g.garageName LIKE %:garageName%) AND " +
           "(:websiteUrl IS NULL OR g.websiteUrl = :websiteUrl) AND " +
           "(:isActive IS NULL OR g.isActive = :isActive)")
    List<Garage> findGaragesByCriteria(@Param("garageName") String garageName,
                                     @Param("websiteUrl") String websiteUrl,
                                     @Param("isActive") Boolean isActive);

    // Find garages by website URL containing text
    List<Garage> findByWebsiteUrlContainingIgnoreCase(String websiteUrl);

    // Find recent garages
    List<Garage> findTop10ByOrderByCreatedAtDesc();

    // Find garage by user email (joins with User table)
    @Query("SELECT g FROM Garage g JOIN User u ON g.createdBy = u.id WHERE u.email = :userEmail")
    Optional<Garage> findByUserEmail(@Param("userEmail") String userEmail);

    // Find garages by created by user ID
    List<Garage> findByCreatedBy(UUID createdBy);
}
