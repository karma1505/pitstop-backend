package com.garage.backend.onboarding.dto;

import java.util.UUID;

public class OnboardingStatus {

    private UUID userId;
    private boolean hasGarage;
    private boolean hasAddress;
    private boolean hasPaymentMethods;
    private boolean hasStaff;
    private int completionPercentage;

    // Constructors
    public OnboardingStatus() {}

    public OnboardingStatus(UUID userId, boolean hasGarage, boolean hasAddress, 
                          boolean hasPaymentMethods, boolean hasStaff, int completionPercentage) {
        this.userId = userId;
        this.hasGarage = hasGarage;
        this.hasAddress = hasAddress;
        this.hasPaymentMethods = hasPaymentMethods;
        this.hasStaff = hasStaff;
        this.completionPercentage = completionPercentage;
    }

    // Builder pattern
    public static OnboardingStatusBuilder builder() {
        return new OnboardingStatusBuilder();
    }

    public static class OnboardingStatusBuilder {
        private UUID userId;
        private boolean hasGarage;
        private boolean hasAddress;
        private boolean hasPaymentMethods;
        private boolean hasStaff;
        private int completionPercentage;

        public OnboardingStatusBuilder userId(UUID userId) {
            this.userId = userId;
            return this;
        }

        public OnboardingStatusBuilder hasGarage(boolean hasGarage) {
            this.hasGarage = hasGarage;
            return this;
        }

        public OnboardingStatusBuilder hasAddress(boolean hasAddress) {
            this.hasAddress = hasAddress;
            return this;
        }

        public OnboardingStatusBuilder hasPaymentMethods(boolean hasPaymentMethods) {
            this.hasPaymentMethods = hasPaymentMethods;
            return this;
        }

        public OnboardingStatusBuilder hasStaff(boolean hasStaff) {
            this.hasStaff = hasStaff;
            return this;
        }

        public OnboardingStatusBuilder completionPercentage(int completionPercentage) {
            this.completionPercentage = completionPercentage;
            return this;
        }

        public OnboardingStatus build() {
            return new OnboardingStatus(userId, hasGarage, hasAddress, hasPaymentMethods, hasStaff, completionPercentage);
        }
    }

    // Getters and Setters
    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public boolean isHasGarage() {
        return hasGarage;
    }

    public void setHasGarage(boolean hasGarage) {
        this.hasGarage = hasGarage;
    }

    public boolean isHasAddress() {
        return hasAddress;
    }

    public void setHasAddress(boolean hasAddress) {
        this.hasAddress = hasAddress;
    }

    public boolean isHasPaymentMethods() {
        return hasPaymentMethods;
    }

    public void setHasPaymentMethods(boolean hasPaymentMethods) {
        this.hasPaymentMethods = hasPaymentMethods;
    }

    public boolean isHasStaff() {
        return hasStaff;
    }

    public void setHasStaff(boolean hasStaff) {
        this.hasStaff = hasStaff;
    }

    public int getCompletionPercentage() {
        return completionPercentage;
    }

    public void setCompletionPercentage(int completionPercentage) {
        this.completionPercentage = completionPercentage;
    }

    @Override
    public String toString() {
        return "OnboardingStatus{" +
                "userId=" + userId +
                ", hasGarage=" + hasGarage +
                ", hasAddress=" + hasAddress +
                ", hasPaymentMethods=" + hasPaymentMethods +
                ", hasStaff=" + hasStaff +
                ", completionPercentage=" + completionPercentage +
                '}';
    }
}
