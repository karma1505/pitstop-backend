package com.garage.backend.onboarding.service;

import com.garage.backend.address.dto.AddressResponse;
import com.garage.backend.address.dto.CreateAddressRequest;
import com.garage.backend.address.service.AddressService;
import com.garage.backend.onboarding.dto.NextStepResponse;
import com.garage.backend.onboarding.dto.OnboardingRequest;
import com.garage.backend.onboarding.dto.OnboardingResponse;
import com.garage.backend.onboarding.dto.OnboardingStatus;
import com.garage.backend.payment.dto.CreateGaragePaymentMethodRequest;
import com.garage.backend.payment.dto.GaragePaymentMethodResponse;
import com.garage.backend.payment.service.GaragePaymentMethodsService;
import com.garage.backend.settings.dto.CreateGarageRequest;
import com.garage.backend.settings.dto.GarageResponse;
import com.garage.backend.settings.service.GarageService;
import com.garage.backend.shared.service.GarageContextService;
import com.garage.backend.staff.dto.CreateStaffRequest;
import com.garage.backend.staff.dto.StaffResponse;
import com.garage.backend.staff.service.StaffService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class OnboardingService {

    private static final Logger logger = LoggerFactory.getLogger(OnboardingService.class);

    @Autowired
    private GarageService garageService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private GaragePaymentMethodsService paymentMethodsService;

    @Autowired
    private StaffService staffService;

    @Autowired
    private GarageContextService garageContextService;

    /**
     * Complete onboarding flow in a single transaction
     */
    @Transactional
    public OnboardingResponse completeOnboarding(OnboardingRequest request) {
        logger.info("Starting onboarding completion for user");
        try {
            // Step 1: Create Garage
            logger.debug("Step 1: Creating garage");
            GarageResponse garage = garageService.createGarage(request.getGarageRequest());
            UUID garageId = garage.getId();
            logger.debug("Garage created with ID: {}", garageId);

            // Step 2: Create Address
            logger.debug("Step 2: Creating address");
            AddressResponse address = null;
            if (request.getAddressRequest() != null) {
                address = addressService.createAddress(request.getAddressRequest());
                logger.debug("Address created successfully");
            } else {
                logger.debug("No address request provided");
            }

            // Step 3: Configure Payment Methods
            logger.debug("Step 3: Configuring payment methods");
            List<GaragePaymentMethodResponse> paymentMethods = null;
            if (request.getPaymentMethodRequests() != null && !request.getPaymentMethodRequests().isEmpty()) {
                paymentMethods = request.getPaymentMethodRequests().stream()
                        .map(paymentMethodsService::createGaragePaymentMethod)
                        .toList();
                logger.debug("Created {} payment methods", paymentMethods.size());
            } else {
                logger.debug("No payment methods to configure");
            }

            // Step 4: Add Staff Members
            logger.debug("Step 4: Adding staff members");
            List<StaffResponse> staffMembers = null;
            if (request.getStaffRequests() != null && !request.getStaffRequests().isEmpty()) {
                staffMembers = request.getStaffRequests().stream()
                        .map(staffService::createStaff)
                        .toList();
                logger.debug("Created {} staff members", staffMembers.size());
            } else {
                logger.debug("No staff members to add");
            }

            logger.info("Onboarding completed successfully");
            return OnboardingResponse.builder()
                    .success(true)
                    .message("Onboarding completed successfully")
                    .garage(garage)
                    .address(address)
                    .paymentMethods(paymentMethods)
                    .staffMembers(staffMembers)
                    .build();

        } catch (Exception e) {
            logger.error("Error during onboarding completion", e);
            throw new RuntimeException("Onboarding failed: " + e.getMessage(), e);
        }
    }

    /**
     * Get onboarding status for current user
     */
    public OnboardingStatus getOnboardingStatus() {
        try {
            UUID userId = garageContextService.getCurrentUserId();
            
            // Check if user has garage
            boolean hasGarage = false;
            boolean hasAddress = false;
            boolean hasPaymentMethods = false;
            boolean hasStaff = false;

            try {
                garageContextService.getCurrentUserGarage();
                hasGarage = true;
                
                // Check for address
                List<AddressResponse> addresses = addressService.getCurrentUserAddresses();
                hasAddress = !addresses.isEmpty();
                
                // Check for payment methods
                List<GaragePaymentMethodResponse> paymentMethods = paymentMethodsService.getCurrentUserPaymentMethods();
                hasPaymentMethods = !paymentMethods.isEmpty();
                
                // Check for staff
                List<StaffResponse> staffMembers = staffService.getCurrentUserStaff();
                hasStaff = !staffMembers.isEmpty();
                
            } catch (RuntimeException e) {
                // User doesn't have garage yet
            }

            int completionPercentage = calculateCompletionPercentage(hasGarage, hasAddress, hasPaymentMethods, hasStaff);

            return OnboardingStatus.builder()
                    .userId(userId)
                    .hasGarage(hasGarage)
                    .hasAddress(hasAddress)
                    .hasPaymentMethods(hasPaymentMethods)
                    .hasStaff(hasStaff)
                    .completionPercentage(completionPercentage)
                    .build();

        } catch (Exception e) {
            throw new RuntimeException("Failed to get onboarding status: " + e.getMessage(), e);
        }
    }

    /**
     * Get next step for onboarding
     */
    public NextStepResponse getNextStep() {
        OnboardingStatus status = getOnboardingStatus();
        
        if (!status.isHasGarage()) {
            return NextStepResponse.builder()
                    .step("CREATE_GARAGE")
                    .message("Create your garage profile")
                    .priority(1)
                    .build();
        }
        
        if (!status.isHasAddress()) {
            return NextStepResponse.builder()
                    .step("ADD_ADDRESS")
                    .message("Add your garage address")
                    .priority(2)
                    .build();
        }
        
        if (!status.isHasPaymentMethods()) {
            return NextStepResponse.builder()
                    .step("CONFIGURE_PAYMENT_METHODS")
                    .message("Configure payment methods")
                    .priority(3)
                    .build();
        }
        
        if (!status.isHasStaff()) {
            return NextStepResponse.builder()
                    .step("ADD_STAFF")
                    .message("Add staff members")
                    .priority(4)
                    .build();
        }
        
        return NextStepResponse.builder()
                .step("COMPLETED")
                .message("Onboarding completed!")
                .priority(5)
                .build();
    }

    private int calculateCompletionPercentage(boolean hasGarage, boolean hasAddress, 
                                           boolean hasPaymentMethods, boolean hasStaff) {
        int completed = 0;
        if (hasGarage) completed++;
        if (hasAddress) completed++;
        if (hasPaymentMethods) completed++;
        if (hasStaff) completed++;
        
        return (completed * 100) / 4; // 4 total steps
    }
}
