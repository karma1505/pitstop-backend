package com.garage.backend.onboarding.dto;

import com.garage.backend.address.dto.AddressResponse;
import com.garage.backend.payment.dto.GaragePaymentMethodResponse;
import com.garage.backend.settings.dto.GarageResponse;
import com.garage.backend.staff.dto.StaffResponse;

import java.util.List;

public class OnboardingResponse {

    private boolean success;
    private String message;
    private GarageResponse garage;
    private AddressResponse address;
    private List<GaragePaymentMethodResponse> paymentMethods;
    private List<StaffResponse> staffMembers;

    // Constructors
    public OnboardingResponse() {}

    public OnboardingResponse(boolean success, String message, GarageResponse garage, 
                            AddressResponse address, List<GaragePaymentMethodResponse> paymentMethods,
                            List<StaffResponse> staffMembers) {
        this.success = success;
        this.message = message;
        this.garage = garage;
        this.address = address;
        this.paymentMethods = paymentMethods;
        this.staffMembers = staffMembers;
    }

    // Builder pattern
    public static OnboardingResponseBuilder builder() {
        return new OnboardingResponseBuilder();
    }

    public static class OnboardingResponseBuilder {
        private boolean success;
        private String message;
        private GarageResponse garage;
        private AddressResponse address;
        private List<GaragePaymentMethodResponse> paymentMethods;
        private List<StaffResponse> staffMembers;

        public OnboardingResponseBuilder success(boolean success) {
            this.success = success;
            return this;
        }

        public OnboardingResponseBuilder message(String message) {
            this.message = message;
            return this;
        }

        public OnboardingResponseBuilder garage(GarageResponse garage) {
            this.garage = garage;
            return this;
        }

        public OnboardingResponseBuilder address(AddressResponse address) {
            this.address = address;
            return this;
        }

        public OnboardingResponseBuilder paymentMethods(List<GaragePaymentMethodResponse> paymentMethods) {
            this.paymentMethods = paymentMethods;
            return this;
        }

        public OnboardingResponseBuilder staffMembers(List<StaffResponse> staffMembers) {
            this.staffMembers = staffMembers;
            return this;
        }

        public OnboardingResponse build() {
            return new OnboardingResponse(success, message, garage, address, paymentMethods, staffMembers);
        }
    }

    // Getters and Setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public GarageResponse getGarage() {
        return garage;
    }

    public void setGarage(GarageResponse garage) {
        this.garage = garage;
    }

    public AddressResponse getAddress() {
        return address;
    }

    public void setAddress(AddressResponse address) {
        this.address = address;
    }

    public List<GaragePaymentMethodResponse> getPaymentMethods() {
        return paymentMethods;
    }

    public void setPaymentMethods(List<GaragePaymentMethodResponse> paymentMethods) {
        this.paymentMethods = paymentMethods;
    }

    public List<StaffResponse> getStaffMembers() {
        return staffMembers;
    }

    public void setStaffMembers(List<StaffResponse> staffMembers) {
        this.staffMembers = staffMembers;
    }

    @Override
    public String toString() {
        return "OnboardingResponse{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", garage=" + garage +
                ", address=" + address +
                ", paymentMethods=" + paymentMethods +
                ", staffMembers=" + staffMembers +
                '}';
    }
}
