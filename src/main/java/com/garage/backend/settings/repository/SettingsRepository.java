package com.garage.backend.settings.repository;

import com.garage.backend.settings.entity.Settings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SettingsRepository extends JpaRepository<Settings, UUID> {

    // Find by setting key
    Optional<Settings> findBySettingKey(String settingKey);

    // Find by category
    List<Settings> findByCategory(String category);

    // Find by category containing text
    List<Settings> findByCategoryContainingIgnoreCase(String category);

    // Find editable settings
    List<Settings> findByIsEditableTrue();

    // Find non-editable settings
    List<Settings> findByIsEditableFalse();

    // Find by setting key containing text
    List<Settings> findBySettingKeyContainingIgnoreCase(String settingKey);

    // Find by setting value containing text
    List<Settings> findBySettingValueContainingIgnoreCase(String settingValue);

    // Find by category and editable flag
    List<Settings> findByCategoryAndIsEditable(String category, Boolean isEditable);

    // Check if setting key exists
    boolean existsBySettingKey(String settingKey);

    // Custom query to find settings by multiple criteria
    @Query("SELECT s FROM Settings s WHERE " +
           "(:settingKey IS NULL OR s.settingKey LIKE %:settingKey%) AND " +
           "(:category IS NULL OR s.category = :category) AND " +
           "(:isEditable IS NULL OR s.isEditable = :isEditable)")
    List<Settings> findSettingsByCriteria(@Param("settingKey") String settingKey,
                                       @Param("category") String category,
                                       @Param("isEditable") Boolean isEditable);

    // Find settings by description containing text
    List<Settings> findByDescriptionContainingIgnoreCase(String description);

    // Find recent settings
    List<Settings> findTop10ByOrderByCreatedAtDesc();

    // Find settings by setting value
    List<Settings> findBySettingValue(String settingValue);
}
