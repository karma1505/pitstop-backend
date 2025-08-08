package com.garage.backend.onboarding.dto;

public class NextStepResponse {

    private String step;
    private String message;
    private int priority;

    // Constructors
    public NextStepResponse() {}

    public NextStepResponse(String step, String message, int priority) {
        this.step = step;
        this.message = message;
        this.priority = priority;
    }

    // Builder pattern
    public static NextStepResponseBuilder builder() {
        return new NextStepResponseBuilder();
    }

    public static class NextStepResponseBuilder {
        private String step;
        private String message;
        private int priority;

        public NextStepResponseBuilder step(String step) {
            this.step = step;
            return this;
        }

        public NextStepResponseBuilder message(String message) {
            this.message = message;
            return this;
        }

        public NextStepResponseBuilder priority(int priority) {
            this.priority = priority;
            return this;
        }

        public NextStepResponse build() {
            return new NextStepResponse(step, message, priority);
        }
    }

    // Getters and Setters
    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "NextStepResponse{" +
                "step='" + step + '\'' +
                ", message='" + message + '\'' +
                ", priority=" + priority +
                '}';
    }
}
