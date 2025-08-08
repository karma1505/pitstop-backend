package com.garage.backend.payment.dto;

import com.garage.backend.shared.enums.Enums;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public class CreateGaragePaymentMethodRequest {
    // garageId will be extracted from user's garage automatically

    @NotNull(message = "Payment method is required")
    private Enums.PaymentMethod paymentMethod;

    // Constructors
    public CreateGaragePaymentMethodRequest() {}

    public CreateGaragePaymentMethodRequest(Enums.PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    // Getters and Setters

    public Enums.PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(Enums.PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @Override
    public String toString() {
        return "CreateGaragePaymentMethodRequest{" +
                "paymentMethod=" + paymentMethod +
                '}';
    }
}
