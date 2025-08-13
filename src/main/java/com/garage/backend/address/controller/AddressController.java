package com.garage.backend.address.controller;

import com.garage.backend.address.dto.AddressResponse;
import com.garage.backend.address.dto.CreateAddressRequest;
import com.garage.backend.address.dto.UpdateAddressRequest;
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
        AddressResponse response = addressService.createAddress(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Get address by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<AddressResponse> getAddressById(@PathVariable UUID id) {
        AddressResponse response = addressService.getAddressById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Get all addresses for the current user's garage
     */
    @GetMapping("/my-addresses")
    public ResponseEntity<List<AddressResponse>> getCurrentUserAddresses() {
        List<AddressResponse> responses = addressService.getCurrentUserAddresses();
        return ResponseEntity.ok(responses);
    }

    /**
     * Update address (partial update)
     */
    @PatchMapping("/{id}")
    public ResponseEntity<AddressResponse> updateAddress(@PathVariable UUID id, 
                                                       @Valid @RequestBody UpdateAddressRequest request) {
        AddressResponse response = addressService.updateAddressPartial(id, request);
        return ResponseEntity.ok(response);
    }

    /**
     * Delete address
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable UUID id) {
        addressService.deleteAddress(id);
        return ResponseEntity.noContent().build();
    }
}
