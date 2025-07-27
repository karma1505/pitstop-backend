package com.garage.backend.repository;

import com.garage.backend.entity.Vehicles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VehiclesRepository extends JpaRepository<Vehicles, UUID> {

    /**
     * Find vehicle by registration number
     * @param registrationNumber vehicle registration number
     * @return Optional<Vehicles>
     */
    Optional<Vehicles> findByRegistrationNumber(String registrationNumber);

    /**
     * Check if vehicle exists by registration number
     * @param registrationNumber vehicle registration number
     * @return boolean
     */
    boolean existsByRegistrationNumber(String registrationNumber);

    /**
     * Find vehicles by customer ID
     * @param customerId customer ID
     * @return List<Vehicles>
     */
    List<Vehicles> findByCustomerId(UUID customerId);

    /**
     * Find vehicles by make
     * @param make vehicle make
     * @return List<Vehicles>
     */
    List<Vehicles> findByMake(String make);

    /**
     * Find vehicles by make and model
     * @param make vehicle make
     * @param model vehicle model
     * @return List<Vehicles>
     */
    List<Vehicles> findByMakeAndModel(String make, String model);

    /**
     * Find vehicles by registration number (partial match)
     * @param registrationNumber vehicle registration number
     * @return List<Vehicles>
     */
    @Query("SELECT v FROM Vehicles v WHERE UPPER(v.registrationNumber) LIKE UPPER(CONCAT('%', :registrationNumber, '%'))")
    List<Vehicles> findByRegistrationNumberContainingIgnoreCase(@Param("registrationNumber") String registrationNumber);

    /**
     * Find vehicles by VIN
     * @param vin vehicle VIN
     * @return Optional<Vehicles>
     */
    Optional<Vehicles> findByVin(String vin);
} 