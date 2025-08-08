package com.garage.backend.onboarding.dto;

import com.garage.backend.address.dto.CreateAddressRequest;
import com.garage.backend.payment.dto.CreateGaragePaymentMethodRequest;
import com.garage.backend.settings.dto.CreateGarageRequest;
import com.garage.backend.staff.dto.CreateStaffRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class OnboardingRequest {

    @NotNull(message = "Garage details are required")
    @Valid
    private CreateGarageRequest garageRequest;

    @Valid
    private CreateAddressRequest addressRequest;

    private List<@Valid CreateGaragePaymentMethodRequest> paymentMethodRequests;

    private List<@Valid CreateStaffRequest> staffRequests;

    // Constructors
    public OnboardingRequest() {}

    public OnboardingRequest(CreateGarageRequest garageRequest, CreateAddressRequest addressRequest,
                           List<CreateGaragePaymentMethodRequest> paymentMethodRequests,
                           List<CreateStaffRequest> staffRequests) {
        this.garageRequest = garageRequest;
        this.addressRequest = addressRequest;
        this.paymentMethodRequests = paymentMethodRequests;
        this.staffRequests = staffRequests;
    }

    // Getters and Setters
    public CreateGarageRequest getGarageRequest() {
        return garageRequest;
    }

    public void setGarageRequest(CreateGarageRequest garageRequest) {
        this.garageRequest = garageRequest;
    }

    public CreateAddressRequest getAddressRequest() {
        return addressRequest;
    }

    public void setAddressRequest(CreateAddressRequest addressRequest) {
        this.addressRequest = addressRequest;
    }

    public List<CreateGaragePaymentMethodRequest> getPaymentMethodRequests() {
        return paymentMethodRequests;
    }

    public void setPaymentMethodRequests(List<CreateGaragePaymentMethodRequest> paymentMethodRequests) {
        this.paymentMethodRequests = paymentMethodRequests;
    }

    public List<CreateStaffRequest> getStaffRequests() {
        return staffRequests;
    }

    public void setStaffRequests(List<CreateStaffRequest> staffRequests) {
        this.staffRequests = staffRequests;
    }

    @Override
    public String toString() {
        return "OnboardingRequest{" +
                "garageRequest=" + garageRequest +
                ", addressRequest=" + addressRequest +
                ", paymentMethodRequests=" + paymentMethodRequests +
                ", staffRequests=" + staffRequests +
                '}';
    }
}
