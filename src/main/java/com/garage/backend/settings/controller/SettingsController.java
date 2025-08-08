package com.garage.backend.settings.controller;

import com.garage.backend.settings.dto.CreateSettingRequest;
import com.garage.backend.settings.dto.SettingResponse;
import com.garage.backend.settings.service.SettingsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin/settings")
@CrossOrigin(origins = "*")
public class SettingsController {

    @Autowired
    private SettingsService settingsService;

    /**
     * Create a new setting
     */
    @PostMapping
    public ResponseEntity<SettingResponse> createSetting(@Valid @RequestBody CreateSettingRequest request) {
        try {
            SettingResponse response = settingsService.createSetting(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get setting by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<SettingResponse> getSettingById(@PathVariable UUID id) {
        try {
            SettingResponse response = settingsService.getSettingById(id);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get setting by key
     */
    @GetMapping("/key/{settingKey}")
    public ResponseEntity<SettingResponse> getSettingByKey(@PathVariable String settingKey) {
        try {
            SettingResponse response = settingsService.getSettingByKey(settingKey);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get all settings with pagination
     */
    @GetMapping
    public ResponseEntity<Page<SettingResponse>> getAllSettings(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<SettingResponse> response = settingsService.getAllSettings(pageable);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get settings by category
     */
    @GetMapping("/category/{category}")
    public ResponseEntity<List<SettingResponse>> getSettingsByCategory(@PathVariable String category) {
        try {
            List<SettingResponse> response = settingsService.getSettingsByCategory(category);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Update setting
     */
    @PutMapping("/{id}")
    public ResponseEntity<SettingResponse> updateSetting(
            @PathVariable UUID id,
            @Valid @RequestBody CreateSettingRequest request) {
        try {
            SettingResponse response = settingsService.updateSetting(id, request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Update setting value by key
     */
    @PatchMapping("/key/{settingKey}")
    public ResponseEntity<SettingResponse> updateSettingValue(
            @PathVariable String settingKey,
            @RequestBody String newValue) {
        try {
            SettingResponse response = settingsService.updateSettingValue(settingKey, newValue);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Delete setting
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSetting(@PathVariable UUID id) {
        try {
            settingsService.deleteSetting(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Health check endpoint
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Settings API is running - " + System.currentTimeMillis());
    }
}
