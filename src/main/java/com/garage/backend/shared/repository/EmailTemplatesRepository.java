package com.garage.backend.shared.repository;

import com.garage.backend.shared.entity.EmailTemplates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmailTemplatesRepository extends JpaRepository<EmailTemplates, UUID> {

    // Find by template name
    Optional<EmailTemplates> findByTemplateName(String templateName);

    // Find by template name containing text
    List<EmailTemplates> findByTemplateNameContainingIgnoreCase(String templateName);

    // Find by template type
    List<EmailTemplates> findByTemplateType(String templateType);

    // Find active templates
    List<EmailTemplates> findByIsActiveTrue();

    // Find by subject containing text
    List<EmailTemplates> findBySubjectContainingIgnoreCase(String subject);

    // Find by template type and active status
    List<EmailTemplates> findByTemplateTypeAndIsActive(String templateType, Boolean isActive);

    // Check if template name exists
    boolean existsByTemplateName(String templateName);

    // Custom query to find email templates by multiple criteria
    @Query("SELECT et FROM EmailTemplates et WHERE " +
           "(:templateName IS NULL OR et.templateName LIKE %:templateName%) AND " +
           "(:templateType IS NULL OR et.templateType = :templateType) AND " +
           "(:isActive IS NULL OR et.isActive = :isActive)")
    List<EmailTemplates> findEmailTemplatesByCriteria(@Param("templateName") String templateName,
                                                   @Param("templateType") String templateType,
                                                   @Param("isActive") Boolean isActive);

    // Find email templates by description containing text
    List<EmailTemplates> findByDescriptionContainingIgnoreCase(String description);

    // Find recent email templates
    List<EmailTemplates> findTop10ByOrderByCreatedAtDesc();

    // Find email templates by body containing text
    List<EmailTemplates> findByBodyContainingIgnoreCase(String body);

    // Find email templates by subject
    List<EmailTemplates> findBySubject(String subject);
}
