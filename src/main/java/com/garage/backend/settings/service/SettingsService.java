package com.garage.backend.settings.service;

import com.garage.backend.settings.dto.CreateSettingRequest;
import com.garage.backend.settings.dto.SettingResponse;
import com.garage.backend.settings.entity.Settings;
import com.garage.backend.settings.repository.SettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SettingsService {

    @Autowired
    private SettingsRepository settingsRepository;

    /**
     * Create a new setting
     */
    public SettingResponse createSetting(CreateSettingRequest request) {
        // Check if setting with same key already exists
        if (settingsRepository.existsBySettingKey(request.getSettingKey())) {
            throw new RuntimeException("Setting with this key already exists");
        }

        Settings setting = new Settings();
        setting.setSettingKey(request.getSettingKey());
        setting.setSettingValue(request.getSettingValue());
        setting.setDescription(request.getDescription());
        setting.setCategory(request.getCategory());
        setting.setIsEditable(request.getIsEditable());

        Settings savedSetting = settingsRepository.save(setting);
        return convertToResponse(savedSetting);
    }

    /**
     * Get setting by ID
     */
    public SettingResponse getSettingById(UUID id) {
        Optional<Settings> settingOptional = settingsRepository.findById(id);
        if (settingOptional.isPresent()) {
            return convertToResponse(settingOptional.get());
        } else {
            throw new RuntimeException("Setting not found with ID: " + id);
        }
    }

    /**
     * Get setting by key
     */
    public SettingResponse getSettingByKey(String settingKey) {
        Optional<Settings> settingOptional = settingsRepository.findBySettingKey(settingKey);
        if (settingOptional.isPresent()) {
            return convertToResponse(settingOptional.get());
        } else {
            throw new RuntimeException("Setting not found with key: " + settingKey);
        }
    }

    /**
     * Get all settings with pagination
     */
    public Page<SettingResponse> getAllSettings(Pageable pageable) {
        Page<Settings> settings = settingsRepository.findAll(pageable);
        return settings.map(this::convertToResponse);
    }

    /**
     * Get settings by category
     */
    public List<SettingResponse> getSettingsByCategory(String category) {
        List<Settings> settings = settingsRepository.findByCategory(category);
        return settings.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Update setting
     */
    public SettingResponse updateSetting(UUID id, CreateSettingRequest request) {
        Optional<Settings> settingOptional = settingsRepository.findById(id);
        if (settingOptional.isEmpty()) {
            throw new RuntimeException("Setting not found with ID: " + id);
        }

        Settings setting = settingOptional.get();

        // Check if setting key is being changed and if it already exists
        if (!setting.getSettingKey().equals(request.getSettingKey()) &&
            settingsRepository.existsBySettingKey(request.getSettingKey())) {
            throw new RuntimeException("Setting with this key already exists");
        }

        // Check if setting is editable
        if (!setting.getIsEditable()) {
            throw new RuntimeException("This setting is not editable");
        }

        setting.setSettingKey(request.getSettingKey());
        setting.setSettingValue(request.getSettingValue());
        setting.setDescription(request.getDescription());
        setting.setCategory(request.getCategory());
        setting.setIsEditable(request.getIsEditable());

        Settings savedSetting = settingsRepository.save(setting);
        return convertToResponse(savedSetting);
    }

    /**
     * Update setting value by key
     */
    public SettingResponse updateSettingValue(String settingKey, String newValue) {
        Optional<Settings> settingOptional = settingsRepository.findBySettingKey(settingKey);
        if (settingOptional.isEmpty()) {
            throw new RuntimeException("Setting not found with key: " + settingKey);
        }

        Settings setting = settingOptional.get();

        // Check if setting is editable
        if (!setting.getIsEditable()) {
            throw new RuntimeException("This setting is not editable");
        }

        setting.setSettingValue(newValue);
        Settings savedSetting = settingsRepository.save(setting);
        return convertToResponse(savedSetting);
    }

    /**
     * Delete setting
     */
    public void deleteSetting(UUID id) {
        Optional<Settings> settingOptional = settingsRepository.findById(id);
        if (settingOptional.isEmpty()) {
            throw new RuntimeException("Setting not found with ID: " + id);
        }

        Settings setting = settingOptional.get();

        // Check if setting is editable
        if (!setting.getIsEditable()) {
            throw new RuntimeException("This setting cannot be deleted");
        }

        settingsRepository.delete(setting);
    }

    /**
     * Convert Settings entity to SettingResponse
     */
    private SettingResponse convertToResponse(Settings setting) {
        return new SettingResponse(
                setting.getId(),
                setting.getSettingKey(),
                setting.getSettingValue(),
                setting.getDescription(),
                setting.getCategory(),
                setting.getIsEditable(),
                setting.getCreatedAt(),
                setting.getUpdatedAt()
        );
    }
}
