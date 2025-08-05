package com.garage.backend.shared.repository;

import com.garage.backend.shared.entity.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, UUID> {

    // Find by method name
    Optional<PaymentMethod> findByMethodName(String methodName);

    // Find by method name containing text
    List<PaymentMethod> findByMethodNameContainingIgnoreCase(String methodName);

    // Find by method type
    List<PaymentMethod> findByMethodType(String methodType);

    // Find active payment methods
    List<PaymentMethod> findByIsActiveTrue();

    // Find by processing fee percentage
    List<PaymentMethod> findByProcessingFeePercentage(Double processingFeePercentage);

    // Find by processing fee fixed
    List<PaymentMethod> findByProcessingFeeFixed(Double processingFeeFixed);

    // Find by method type and active status
    List<PaymentMethod> findByMethodTypeAndIsActive(String methodType, Boolean isActive);

    // Find payment methods with processing fees
    List<PaymentMethod> findByProcessingFeePercentageGreaterThanOrProcessingFeeFixedGreaterThan(Double percentage, Double fixed);

    // Check if method name exists
    boolean existsByMethodName(String methodName);

    // Custom query to find payment methods by multiple criteria
    @Query("SELECT pm FROM PaymentMethod pm WHERE " +
           "(:methodName IS NULL OR pm.methodName LIKE %:methodName%) AND " +
           "(:methodType IS NULL OR pm.methodType = :methodType) AND " +
           "(:isActive IS NULL OR pm.isActive = :isActive)")
    List<PaymentMethod> findPaymentMethodsByCriteria(@Param("methodName") String methodName,
                                                   @Param("methodType") String methodType,
                                                   @Param("isActive") Boolean isActive);

    // Find payment methods by description containing text
    List<PaymentMethod> findByDescriptionContainingIgnoreCase(String description);

    // Find recent payment methods
    List<PaymentMethod> findTop10ByOrderByCreatedAtDesc();

    // Find payment methods by processing fee percentage range
    List<PaymentMethod> findByProcessingFeePercentageBetween(Double minPercentage, Double maxPercentage);

    // Find payment methods by processing fee fixed range
    List<PaymentMethod> findByProcessingFeeFixedBetween(Double minFixed, Double maxFixed);
}
