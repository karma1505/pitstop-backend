package com.garage.backend.repository;

import com.garage.backend.entity.Customers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomersRepository extends JpaRepository<Customers, UUID> {

    /**
     * Find customer by phone number
     * @param phone customer phone number
     * @return Optional<Customers>
     */
    Optional<Customers> findByPhone(String phone);

    /**
     * Check if customer exists by phone number
     * @param phone customer phone number
     * @return boolean
     */
    boolean existsByPhone(String phone);

    /**
     * Find customer by email
     * @param email customer email
     * @return Optional<Customers>
     */
    Optional<Customers> findByEmail(String email);

    /**
     * Find customers by name (partial match)
     * @param name customer name
     * @return List<Customers>
     */
    @Query("SELECT c FROM Customers c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Customers> findByNameContainingIgnoreCase(@Param("name") String name);

    /**
     * Find customers by phone number (partial match)
     * @param phone customer phone number
     * @return List<Customers>
     */
    @Query("SELECT c FROM Customers c WHERE c.phone LIKE CONCAT('%', :phone, '%')")
    List<Customers> findByPhoneContaining(@Param("phone") String phone);
} 