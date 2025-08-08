package com.garage.backend.staff.repository;

import com.garage.backend.staff.entity.Staff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface StaffRepository extends JpaRepository<Staff, UUID> {

    /**
     * Find staff by mobile number
     * @param mobileNumber staff mobile number
     * @return Optional<Staff>
     */
    Optional<Staff> findByMobileNumber(String mobileNumber);

    /**
     * Check if staff exists by mobile number
     * @param mobileNumber staff mobile number
     * @return boolean
     */
    boolean existsByMobileNumber(String mobileNumber);

    /**
     * Find active staff by mobile number
     * @param mobileNumber staff mobile number
     * @return Optional<Staff>
     */
    @Query("SELECT s FROM Staff s WHERE s.mobileNumber = :mobileNumber AND s.isActive = true")
    Optional<Staff> findActiveStaffByMobileNumber(@Param("mobileNumber") String mobileNumber);

    /**
     * Find all active staff
     * @return List<Staff>
     */
    @Query("SELECT s FROM Staff s WHERE s.isActive = true")
    List<Staff> findAllActive();

    /**
     * Find active staff
     * @return List<Staff>
     */
    List<Staff> findByIsActiveTrue();

    /**
     * Find staff by garage ID with pagination
     * @param garageId garage ID
     * @param pageable pagination
     * @return Page<Staff>
     */
    Page<Staff> findByGarageId(UUID garageId, Pageable pageable);

    /**
     * Find staff by garage ID and role with pagination
     * @param garageId garage ID
     * @param role staff role
     * @param pageable pagination
     * @return Page<Staff>
     */
    Page<Staff> findByGarageIdAndRole(UUID garageId, com.garage.backend.shared.enums.Enums.StaffRole role, Pageable pageable);

    /**
     * Find staff by garage ID and multiple roles with pagination
     * @param garageId garage ID
     * @param roles list of staff roles
     * @param pageable pagination
     * @return Page<Staff>
     */
    Page<Staff> findByGarageIdAndRoleIn(UUID garageId, List<com.garage.backend.shared.enums.Enums.StaffRole> roles, Pageable pageable);

    /**
     * Find active staff by role
     * @param role staff role
     * @return List<Staff>
     */
    @Query("SELECT s FROM Staff s WHERE s.role = :role AND s.isActive = true")
    List<Staff> findActiveByRole(@Param("role") com.garage.backend.shared.enums.Enums.StaffRole role);

    /**
     * Find staff by garage ID
     * @param garageId garage ID
     * @return List<Staff>
     */
    List<Staff> findByGarageId(UUID garageId);
} 