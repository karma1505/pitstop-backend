package com.garage.backend.address.controller;

import com.garage.backend.address.dto.AddressResponse;
import com.garage.backend.address.dto.CreateAddressRequest;
import com.garage.backend.address.service.AddressService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin/addresses")
@CrossOrigin(origins = "*")
public class AddressController {

    @Autowired
    private AddressService addressService;

    /**
     * Create a new address
     */
    @PostMapping
    public ResponseEntity<AddressResponse> createAddress(@Valid @RequestBody CreateAddressRequest request) {
        try {
            AddressResponse response = addressService.createAddress(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get address by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<AddressResponse> getAddressById(@PathVariable UUID id) {
        try {
            AddressResponse response = addressService.getAddressById(id);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get all addresses for the current user's garage
     */
    @GetMapping("/my-addresses")
    public ResponseEntity<List<AddressResponse>> getCurrentUserAddresses() {
        try {
            List<AddressResponse> responses = addressService.getCurrentUserAddresses();
            return ResponseEntity.ok(responses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Update address
     */
    @PatchMapping("/{id}")
    public ResponseEntity<AddressResponse> updateAddress(@PathVariable UUID id, 
                                                       @Valid @RequestBody CreateAddressRequest request) {
        try {
            AddressResponse response = addressService.updateAddress(id, request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Delete address
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable UUID id) {
        try {
            addressService.deleteAddress(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
