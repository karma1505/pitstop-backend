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

    // Find by phone
    Optional<Garage> findByPhone(String phone);

    // Find by email
    Optional<Garage> findByEmail(String email);

    // Find by website
    List<Garage> findByWebsite(String website);

    // Find active garages
    List<Garage> findByIsActiveTrue();

    // Find by timezone
    List<Garage> findByTimezone(String timezone);

    // Find by currency
    List<Garage> findByCurrency(String currency);

    // Check if business registration number exists
    boolean existsByBusinessRegistrationNumber(String businessRegistrationNumber);

    // Check if GST number exists
    boolean existsByGstNumber(String gstNumber);

    // Check if phone exists
    boolean existsByPhone(String phone);

    // Check if email exists
    boolean existsByEmail(String email);

    // Custom query to find garages by multiple criteria
    @Query("SELECT g FROM Garage g WHERE " +
           "(:garageName IS NULL OR g.garageName LIKE %:garageName%) AND " +
           "(:phone IS NULL OR g.phone = :phone) AND " +
           "(:email IS NULL OR g.email = :email) AND " +
           "(:timezone IS NULL OR g.timezone = :timezone) AND " +
           "(:currency IS NULL OR g.currency = :currency) AND " +
           "(:isActive IS NULL OR g.isActive = :isActive)")
    List<Garage> findGaragesByCriteria(@Param("garageName") String garageName,
                                     @Param("phone") String phone,
                                     @Param("email") String email,
                                     @Param("timezone") String timezone,
                                     @Param("currency") String currency,
                                     @Param("isActive") Boolean isActive);

    // Find garages by website containing text
    List<Garage> findByWebsiteContainingIgnoreCase(String website);

    // Find recent garages
    List<Garage> findTop10ByOrderByCreatedAtDesc();
}
