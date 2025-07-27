package com.garage.backend.repository;

import com.garage.backend.entity.Staff;
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
     * Find staff by email
     * @param email staff email
     * @return Optional<Staff>
     */
    Optional<Staff> findByEmail(String email);

    /**
     * Check if staff exists by email
     * @param email staff email
     * @return boolean
     */
    boolean existsByEmail(String email);

    /**
     * Find active staff by email
     * @param email staff email
     * @return Optional<Staff>
     */
    @Query("SELECT s FROM Staff s WHERE s.email = :email AND s.isActive = true")
    Optional<Staff> findActiveStaffByEmail(@Param("email") String email);

    /**
     * Find staff by phone number
     * @param phone staff phone number
     * @return Optional<Staff>
     */
    Optional<Staff> findByPhone(String phone);

    /**
     * Find all active staff
     * @return List<Staff>
     */
    @Query("SELECT s FROM Staff s WHERE s.isActive = true")
    List<Staff> findAllActive();

    /**
     * Find staff by role
     * @param role staff role
     * @return List<Staff>
     */
    List<Staff> findByRole(String role);

    /**
     * Find active staff by role
     * @param role staff role
     * @return List<Staff>
     */
    @Query("SELECT s FROM Staff s WHERE s.role = :role AND s.isActive = true")
    List<Staff> findActiveByRole(@Param("role") String role);
} 