package com.garage.backend.address.service;

import com.garage.backend.address.dto.AddressResponse;
import com.garage.backend.address.dto.CreateAddressRequest;
import com.garage.backend.address.entity.Addresses;
import com.garage.backend.address.repository.AddressesRepository;
import com.garage.backend.shared.service.GarageContextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AddressService {

    @Autowired
    private AddressesRepository addressRepository;

    @Autowired
    private GarageContextService garageContextService;

    /**
     * Create a new address
     */
    public AddressResponse createAddress(CreateAddressRequest request) {
        Addresses address = new Addresses();
        address.setGarageId(garageContextService.getCurrentUserGarageId());
        address.setAddressLine1(request.getAddressLine1());
        address.setAddressLine2(request.getAddressLine2());
        address.setCity(request.getCity());
        address.setState(request.getState());
        address.setPincode(request.getPincode());
        address.setCountry(request.getCountry());

        Addresses savedAddress = addressRepository.save(address);
        return convertToResponse(savedAddress);
    }

    /**
     * Get address by ID
     */
    public AddressResponse getAddressById(UUID id) {
        Optional<Addresses> addressOptional = addressRepository.findById(id);
        if (addressOptional.isPresent()) {
            return convertToResponse(addressOptional.get());
        } else {
            throw new RuntimeException("Address not found with ID: " + id);
        }
    }

    /**
     * Get all addresses for the current user's garage
     */
    public List<AddressResponse> getCurrentUserAddresses() {
        UUID garageId = garageContextService.getCurrentUserGarageId();
        List<Addresses> addresses = addressRepository.findByGarageId(garageId);
        return addresses.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Update address
     */
    public AddressResponse updateAddress(UUID id, CreateAddressRequest request) {
        Optional<Addresses> addressOptional = addressRepository.findById(id);
        if (addressOptional.isEmpty()) {
            throw new RuntimeException("Address not found with ID: " + id);
        }

        Addresses address = addressOptional.get();
        // Verify this address belongs to the current user's garage
        if (!address.getGarageId().equals(garageContextService.getCurrentUserGarageId())) {
            throw new RuntimeException("Address does not belong to your garage");
        }
        address.setAddressLine1(request.getAddressLine1());
        address.setAddressLine2(request.getAddressLine2());
        address.setCity(request.getCity());
        address.setState(request.getState());
        address.setPincode(request.getPincode());
        address.setCountry(request.getCountry());

        Addresses savedAddress = addressRepository.save(address);
        return convertToResponse(savedAddress);
    }

    /**
     * Delete address
     */
    public void deleteAddress(UUID id) {
        Optional<Addresses> addressOptional = addressRepository.findById(id);
        if (addressOptional.isEmpty()) {
            throw new RuntimeException("Address not found with ID: " + id);
        }
        
        Addresses address = addressOptional.get();
        // Verify this address belongs to the current user's garage
        if (!address.getGarageId().equals(garageContextService.getCurrentUserGarageId())) {
            throw new RuntimeException("Address does not belong to your garage");
        }
        
        addressRepository.deleteById(id);
    }

    /**
     * Convert entity to response DTO
     */
    private AddressResponse convertToResponse(Addresses address) {
        return new AddressResponse(
                address.getId(),
                address.getAddressLine1(),
                address.getAddressLine2(),
                address.getCity(),
                address.getState(),
                address.getPincode(),
                address.getCountry(),
                address.getCreatedAt(),
                address.getUpdatedAt()
        );
    }
}
