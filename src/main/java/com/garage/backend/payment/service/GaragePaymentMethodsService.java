package com.garage.backend.payment.service;

import com.garage.backend.payment.dto.CreateGaragePaymentMethodRequest;
import com.garage.backend.payment.dto.GaragePaymentMethodResponse;
import com.garage.backend.payment.entity.GaragePaymentMethods;
import com.garage.backend.payment.repository.GaragePaymentMethodsRepository;
import com.garage.backend.shared.service.GarageContextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class GaragePaymentMethodsService {

    @Autowired
    private GaragePaymentMethodsRepository garagePaymentMethodsRepository;

    @Autowired
    private GarageContextService garageContextService;

    /**
     * Create a new garage payment method
     */
    public GaragePaymentMethodResponse createGaragePaymentMethod(CreateGaragePaymentMethodRequest request) {
        // Get current user's garage ID
        UUID garageId = garageContextService.getCurrentUserGarageId();
        
        // Check if this payment method already exists for the garage
        if (garagePaymentMethodsRepository.existsByGarageIdAndPaymentMethod(garageId, request.getPaymentMethod())) {
            throw new RuntimeException("This payment method is already configured for this garage");
        }

        GaragePaymentMethods garagePaymentMethod = new GaragePaymentMethods();
        garagePaymentMethod.setGarageId(garageId);
        garagePaymentMethod.setPaymentMethod(request.getPaymentMethod());
        garagePaymentMethod.setIsActive(true);

        GaragePaymentMethods savedPaymentMethod = garagePaymentMethodsRepository.save(garagePaymentMethod);
        return convertToResponse(savedPaymentMethod);
    }

    /**
     * Get payment method by ID
     */
    public GaragePaymentMethodResponse getPaymentMethodById(UUID id) {
        Optional<GaragePaymentMethods> paymentMethodOptional = garagePaymentMethodsRepository.findById(id);
        if (paymentMethodOptional.isEmpty()) {
            throw new RuntimeException("Payment method not found with ID: " + id);
        }
        return convertToResponse(paymentMethodOptional.get());
    }

    /**
     * Get all payment methods for a garage
     */
    public List<GaragePaymentMethodResponse> getPaymentMethodsByGarageId(UUID garageId) {
        List<GaragePaymentMethods> paymentMethods = garagePaymentMethodsRepository.findByGarageId(garageId);
        return paymentMethods.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Get active payment methods for a garage
     */
    public List<GaragePaymentMethodResponse> getActivePaymentMethodsByGarageId(UUID garageId) {
        List<GaragePaymentMethods> paymentMethods = garagePaymentMethodsRepository.findByGarageIdAndIsActiveTrue(garageId);
        return paymentMethods.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Deactivate payment method
     */
    public GaragePaymentMethodResponse deactivatePaymentMethod(UUID id) {
        Optional<GaragePaymentMethods> paymentMethodOptional = garagePaymentMethodsRepository.findById(id);
        if (paymentMethodOptional.isEmpty()) {
            throw new RuntimeException("Payment method not found with ID: " + id);
        }

        GaragePaymentMethods paymentMethod = paymentMethodOptional.get();
        paymentMethod.setIsActive(false);
        GaragePaymentMethods savedPaymentMethod = garagePaymentMethodsRepository.save(paymentMethod);
        return convertToResponse(savedPaymentMethod);
    }

    /**
     * Activate payment method
     */
    public GaragePaymentMethodResponse activatePaymentMethod(UUID id) {
        Optional<GaragePaymentMethods> paymentMethodOptional = garagePaymentMethodsRepository.findById(id);
        if (paymentMethodOptional.isEmpty()) {
            throw new RuntimeException("Payment method not found with ID: " + id);
        }

        GaragePaymentMethods paymentMethod = paymentMethodOptional.get();
        paymentMethod.setIsActive(true);
        GaragePaymentMethods savedPaymentMethod = garagePaymentMethodsRepository.save(paymentMethod);
        return convertToResponse(savedPaymentMethod);
    }

    /**
     * Get payment methods for current user's garage
     */
    public List<GaragePaymentMethodResponse> getCurrentUserPaymentMethods() {
        UUID garageId = garageContextService.getCurrentUserGarageId();
        List<GaragePaymentMethods> paymentMethods = garagePaymentMethodsRepository.findByGarageId(garageId);
        return paymentMethods.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Convert GaragePaymentMethods entity to GaragePaymentMethodResponse
     */
    private GaragePaymentMethodResponse convertToResponse(GaragePaymentMethods paymentMethod) {
        return new GaragePaymentMethodResponse(
                paymentMethod.getId(),
                paymentMethod.getPaymentMethod(),
                paymentMethod.getIsActive(),
                paymentMethod.getCreatedAt(),
                paymentMethod.getUpdatedAt()
        );
    }
}
