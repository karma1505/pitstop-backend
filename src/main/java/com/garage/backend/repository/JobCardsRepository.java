package com.garage.backend.repository;

import com.garage.backend.entity.JobCards;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface JobCardsRepository extends JpaRepository<JobCards, UUID> {

    /**
     * Find job card by job number
     * @param jobNumber job number
     * @return Optional<JobCards>
     */
    Optional<JobCards> findByJobNumber(String jobNumber);

    /**
     * Check if job card exists by job number
     * @param jobNumber job number
     * @return boolean
     */
    boolean existsByJobNumber(String jobNumber);

    /**
     * Find job cards by customer ID
     * @param customerId customer ID
     * @return List<JobCards>
     */
    List<JobCards> findByCustomerId(UUID customerId);

    /**
     * Find job cards by vehicle ID
     * @param vehicleId vehicle ID
     * @return List<JobCards>
     */
    List<JobCards> findByVehicleId(UUID vehicleId);

    /**
     * Find job cards by status
     * @param status job card status
     * @return List<JobCards>
     */
    List<JobCards> findByStatus(String status);

    /**
     * Find job cards by assigned staff
     * @param assignedTo staff ID
     * @return List<JobCards>
     */
    List<JobCards> findByAssignedToId(UUID assignedTo);

    /**
     * Find job cards by customer ID and status
     * @param customerId customer ID
     * @param status job card status
     * @return List<JobCards>
     */
    List<JobCards> findByCustomerIdAndStatus(UUID customerId, String status);

    /**
     * Find job cards by vehicle ID and status
     * @param vehicleId vehicle ID
     * @param status job card status
     * @return List<JobCards>
     */
    List<JobCards> findByVehicleIdAndStatus(UUID vehicleId, String status);
} 