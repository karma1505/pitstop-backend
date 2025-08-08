package com.garage.backend.payment.controller;

import com.garage.backend.payment.dto.CreateGaragePaymentMethodRequest;
import com.garage.backend.payment.dto.GaragePaymentMethodResponse;
import com.garage.backend.payment.service.GaragePaymentMethodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin/payment-methods")
@CrossOrigin(origins = "*")
public class GaragePaymentMethodsController {

    @Autowired
    private GaragePaymentMethodsService garagePaymentMethodsService;

    /**
     * Create a new garage payment method
     */
    @PostMapping
    public ResponseEntity<GaragePaymentMethodResponse> createPaymentMethod(@RequestBody CreateGaragePaymentMethodRequest request) {
        try {
            GaragePaymentMethodResponse response = garagePaymentMethodsService.createGaragePaymentMethod(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Get payment method by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<GaragePaymentMethodResponse> getPaymentMethodById(@PathVariable UUID id) {
        try {
            GaragePaymentMethodResponse response = garagePaymentMethodsService.getPaymentMethodById(id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Get all payment methods for a garage
     */
    @GetMapping("/garage/{garageId}")
    public ResponseEntity<List<GaragePaymentMethodResponse>> getPaymentMethodsByGarageId(@PathVariable UUID garageId) {
        try {
            List<GaragePaymentMethodResponse> response = garagePaymentMethodsService.getPaymentMethodsByGarageId(garageId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Get active payment methods for a garage
     */
    @GetMapping("/garage/{garageId}/active")
    public ResponseEntity<List<GaragePaymentMethodResponse>> getActivePaymentMethodsByGarageId(@PathVariable UUID garageId) {
        try {
            List<GaragePaymentMethodResponse> response = garagePaymentMethodsService.getActivePaymentMethodsByGarageId(garageId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Get payment methods for current user's garage
     */
    @GetMapping("/my-payment-methods")
    public ResponseEntity<List<GaragePaymentMethodResponse>> getCurrentUserPaymentMethods() {
        try {
            List<GaragePaymentMethodResponse> response = garagePaymentMethodsService.getCurrentUserPaymentMethods();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Deactivate payment method
     */
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<GaragePaymentMethodResponse> deactivatePaymentMethod(@PathVariable UUID id) {
        try {
            GaragePaymentMethodResponse response = garagePaymentMethodsService.deactivatePaymentMethod(id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Activate payment method
     */
    @PatchMapping("/{id}/activate")
    public ResponseEntity<GaragePaymentMethodResponse> activatePaymentMethod(@PathVariable UUID id) {
        try {
            GaragePaymentMethodResponse response = garagePaymentMethodsService.activatePaymentMethod(id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
