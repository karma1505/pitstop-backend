package com.garage.backend.onboarding.controller;

import com.garage.backend.onboarding.dto.NextStepResponse;
import com.garage.backend.onboarding.dto.OnboardingRequest;
import com.garage.backend.onboarding.dto.OnboardingResponse;
import com.garage.backend.onboarding.dto.OnboardingStatus;
import com.garage.backend.onboarding.service.OnboardingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/onboarding")
@CrossOrigin(origins = "*")
public class OnboardingController {

    @Autowired
    private OnboardingService onboardingService;

    /**
     * Complete onboarding flow in a single transaction
     */
    @PostMapping("/complete")
    @Transactional
    public ResponseEntity<OnboardingResponse> completeOnboarding(@Valid @RequestBody OnboardingRequest request) {
        try {
            OnboardingResponse response = onboardingService.completeOnboarding(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(OnboardingResponse.builder()
                            .success(false)
                            .message("Onboarding failed: " + e.getMessage())
                            .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(OnboardingResponse.builder()
                            .success(false)
                            .message("An error occurred during onboarding")
                            .build());
        }
    }

    /**
     * Get onboarding status for current user
     */
    @GetMapping("/status")
    public ResponseEntity<OnboardingStatus> getOnboardingStatus() {
        try {
            OnboardingStatus status = onboardingService.getOnboardingStatus();
            return ResponseEntity.ok(status);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get next step for onboarding
     */
    @GetMapping("/next-step")
    public ResponseEntity<NextStepResponse> getNextStep() {
        try {
            NextStepResponse nextStep = onboardingService.getNextStep();
            return ResponseEntity.ok(nextStep);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Health check for onboarding service
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Onboarding service is running");
    }
}
