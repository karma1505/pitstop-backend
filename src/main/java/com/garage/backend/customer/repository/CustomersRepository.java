package com.garage.backend.customer.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.garage.backend.customer.entity.Customers;

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

    /**
     * Find customers with vehicles by registration number
     * @param registrationNumber vehicle registration number
     * @return List<Customers>
     */
    @Query("SELECT DISTINCT c FROM Customers c JOIN Vehicles v ON c.id = v.customer.id WHERE UPPER(v.registrationNumber) LIKE UPPER(CONCAT('%', :registrationNumber, '%'))")
    List<Customers> findByVehicleRegistrationNumber(@Param("registrationNumber") String registrationNumber);

    /**
     * Find customers with vehicles by make (brand)
     * @param make vehicle make/brand
     * @return List<Customers>
     */
    @Query("SELECT DISTINCT c FROM Customers c JOIN Vehicles v ON c.id = v.customer.id WHERE UPPER(v.make) LIKE UPPER(CONCAT('%', :make, '%'))")
    List<Customers> findByVehicleMake(@Param("make") String make);

    /**
     * Find customers created on specific date
     * @param date creation date
     * @return List<Customers>
     */
    @Query("SELECT c FROM Customers c WHERE DATE(c.createdAt) = :date")
    List<Customers> findByCreatedDate(@Param("date") LocalDate date);

    /**
     * Find customers created within date range
     * @param fromDate start date
     * @param toDate end date
     * @return List<Customers>
     */
    @Query("SELECT c FROM Customers c WHERE DATE(c.createdAt) BETWEEN :fromDate AND :toDate")
    List<Customers> findByCreatedDateBetween(@Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate);

    /**
     * Find customers with combined filters
     * @param customerName customer name (partial match)
     * @param vehicleRegistrationNumber vehicle registration number (partial match)
     * @param vehicleMake vehicle make/brand (partial match)
     * @param fromDate start date
     * @param toDate end date
     * @return List<Customers>
     */
    /**
     * Find customers by name only (for filtering)
     * @param name customer name
     * @return List<Customers>
     */
    @Query("SELECT c FROM Customers c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Customers> findByNameFilter(@Param("name") String name);

    /**
     * Find customers by date range
     * @param fromDate start date
     * @param toDate end date
     * @return List<Customers>
     */
    @Query("SELECT c FROM Customers c WHERE c.createdAt >= :fromDate AND c.createdAt <= :toDate")
    List<Customers> findByDateRangeFilter(@Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate);

    /**
     * Find customers with vehicles by registration number (simple approach)
     * @param registrationNumber vehicle registration number
     * @return List<Customers>
     */
    @Query("SELECT DISTINCT c FROM Customers c JOIN Vehicles v ON c.id = v.customer.id WHERE LOWER(v.registrationNumber) LIKE LOWER(CONCAT('%', :registrationNumber, '%'))")
    List<Customers> findByVehicleRegistrationFilter(@Param("registrationNumber") String registrationNumber);

    /**
     * Find customers with vehicles by make (simple approach)
     * @param make vehicle make/brand
     * @return List<Customers>
     */
    @Query("SELECT DISTINCT c FROM Customers c JOIN Vehicles v ON c.id = v.customer.id WHERE LOWER(v.make) LIKE LOWER(CONCAT('%', :make, '%'))")
    List<Customers> findByVehicleMakeFilter(@Param("make") String make);
} 