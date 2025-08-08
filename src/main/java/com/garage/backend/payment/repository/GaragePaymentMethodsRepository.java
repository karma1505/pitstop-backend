package com.garage.backend.payment.repository;

import com.garage.backend.payment.entity.GaragePaymentMethods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface GaragePaymentMethodsRepository extends JpaRepository<GaragePaymentMethods, UUID> {

    // Find all payment methods for a specific garage
    List<GaragePaymentMethods> findByGarageId(UUID garageId);

    // Find active payment methods for a specific garage
    List<GaragePaymentMethods> findByGarageIdAndIsActiveTrue(UUID garageId);

    // Check if a specific payment method exists for a garage
    boolean existsByGarageIdAndPaymentMethod(UUID garageId, com.garage.backend.shared.enums.Enums.PaymentMethod paymentMethod);

    // Find by garage ID and payment method
    GaragePaymentMethods findByGarageIdAndPaymentMethod(UUID garageId, com.garage.backend.shared.enums.Enums.PaymentMethod paymentMethod);
}
