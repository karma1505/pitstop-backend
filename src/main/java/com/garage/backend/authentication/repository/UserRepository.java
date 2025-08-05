package com.garage.backend.authentication.repository;

import com.garage.backend.authentication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    /**
     * Find user by email
     * @param email user email
     * @return Optional<User>
     */
    Optional<User> findByEmail(String email);

    /**
     * Check if user exists by email
     * @param email user email
     * @return boolean
     */
    boolean existsByEmail(String email);

    /**
     * Find active user by email
     * @param email user email
     * @return Optional<User>
     */
    @Query("SELECT u FROM User u WHERE u.email = :email AND u.isActive = true")
    Optional<User> findActiveUserByEmail(@Param("email") String email);

    /**
     * Find user by mobile number
     * @param mobileNumber user mobile number
     * @return Optional<User>
     */
    Optional<User> findByMobileNumber(String mobileNumber);

    /**
     * Find user by email and garage name
     * @param email user email
     * @param garageName garage name
     * @return Optional<User>
     */
    Optional<User> findByEmailAndGarageName(String email, String garageName);
} 