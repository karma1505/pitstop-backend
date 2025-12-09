package com.garage.backend.service.service;

import com.garage.backend.service.dto.*;
import com.garage.backend.service.entity.ServiceCategories;
import com.garage.backend.service.repository.ServiceCategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service class for managing service categories
 * Handles business logic for CRUD operations on service categories
 */
@Service
@Transactional
public class ServiceCategoryService {

    @Autowired
    private ServiceCategoriesRepository serviceCategoriesRepository;

    /**
     * Create a new service category
     * 
     * @param request CreateServiceCategoryRequest with category details
     * @return ServiceCategoryResponse with created category
     * @throws RuntimeException if category code already exists
     */
    public ServiceCategoryResponse createServiceCategory(CreateServiceCategoryRequest request) {
        // Validate unique category code
        String upperCaseCode = request.getCategoryCode().toUpperCase();
        if (serviceCategoriesRepository.existsByCategoryCode(upperCaseCode)) {
            throw new RuntimeException("Service category with code " + upperCaseCode + " already exists");
        }

        // Create new category
        ServiceCategories category = new ServiceCategories();
        category.setCategoryName(request.getCategoryName());
        category.setCategoryCode(upperCaseCode);
        category.setDescription(request.getDescription());
        category.setEstimatedTime(request.getEstimatedTime());
        category.setIsActive(true);

        ServiceCategories savedCategory = serviceCategoriesRepository.save(category);
        return convertToResponse(savedCategory);
    }

    /**
     * Update an existing service category
     * 
     * @param id Category ID
     * @param request UpdateServiceCategoryRequest with updated details
     * @return ServiceCategoryResponse with updated category
     * @throws RuntimeException if category not found
     */
    public ServiceCategoryResponse updateServiceCategory(UUID id, UpdateServiceCategoryRequest request) {
        ServiceCategories category = serviceCategoriesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service category not found with ID: " + id));

        // Update fields (category code is immutable)
        category.setCategoryName(request.getCategoryName());
        category.setDescription(request.getDescription());
        category.setEstimatedTime(request.getEstimatedTime());
        category.setIsActive(request.getIsActive());

        ServiceCategories updatedCategory = serviceCategoriesRepository.save(category);
        return convertToResponse(updatedCategory);
    }

    /**
     * Get service category by ID
     * 
     * @param id Category ID
     * @return ServiceCategoryResponse
     * @throws RuntimeException if category not found
     */
    @Transactional(readOnly = true)
    public ServiceCategoryResponse getServiceCategoryById(UUID id) {
        ServiceCategories category = serviceCategoriesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service category not found with ID: " + id));
        return convertToResponse(category);
    }

    /**
     * Get all service categories with pagination and filtering
     * 
     * @param categoryName Filter by category name (partial match)
     * @param categoryCode Filter by category code (exact match)
     * @param isActive Filter by active status
     * @param pageable Pagination parameters
     * @return Page of ServiceCategoryListResponse
     */
    @Transactional(readOnly = true)
    public Page<ServiceCategoryListResponse> getAllServiceCategories(
            String categoryName,
            String categoryCode,
            Boolean isActive,
            Pageable pageable) {

        List<ServiceCategories> categories;

        // Apply filters if any are provided
        if (categoryName != null || categoryCode != null || isActive != null) {
            categories = serviceCategoriesRepository.findCategoriesByCriteria(
                    categoryName,
                    categoryCode != null ? categoryCode.toUpperCase() : null,
                    isActive
            );
        } else {
            categories = serviceCategoriesRepository.findAll();
        }

        // Apply sorting
        categories = categories.stream()
                .sorted((c1, c2) -> {
                    if (pageable.getSort().isSorted()) {
                        String sortProperty = pageable.getSort().iterator().next().getProperty();
                        boolean ascending = pageable.getSort().iterator().next().isAscending();
                        
                        int comparison = 0;
                        switch (sortProperty) {
                            case "categoryName":
                                comparison = c1.getCategoryName().compareToIgnoreCase(c2.getCategoryName());
                                break;
                            case "categoryCode":
                                comparison = c1.getCategoryCode().compareToIgnoreCase(c2.getCategoryCode());
                                break;
                            case "createdAt":
                                comparison = c1.getCreatedAt().compareTo(c2.getCreatedAt());
                                break;
                            case "updatedAt":
                                comparison = c1.getUpdatedAt().compareTo(c2.getUpdatedAt());
                                break;
                            default:
                                comparison = c1.getCategoryName().compareToIgnoreCase(c2.getCategoryName());
                        }
                        return ascending ? comparison : -comparison;
                    }
                    return c1.getCategoryName().compareToIgnoreCase(c2.getCategoryName());
                })
                .collect(Collectors.toList());

        // Apply pagination
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), categories.size());
        List<ServiceCategories> pageContent = categories.subList(start, end);

        // Convert to response DTOs
        List<ServiceCategoryListResponse> responseList = pageContent.stream()
                .map(this::convertToListResponse)
                .collect(Collectors.toList());

        return new PageImpl<>(responseList, pageable, categories.size());
    }

    /**
     * Deactivate a service category (soft delete)
     * 
     * @param id Category ID
     * @return ServiceCategoryResponse with updated category
     * @throws RuntimeException if category not found
     */
    public ServiceCategoryResponse deactivateServiceCategory(UUID id) {
        ServiceCategories category = serviceCategoriesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service category not found with ID: " + id));

        category.setIsActive(false);
        ServiceCategories updatedCategory = serviceCategoriesRepository.save(category);
        return convertToResponse(updatedCategory);
    }

    /**
     * Reactivate a service category
     * 
     * @param id Category ID
     * @return ServiceCategoryResponse with updated category
     * @throws RuntimeException if category not found
     */
    public ServiceCategoryResponse reactivateServiceCategory(UUID id) {
        ServiceCategories category = serviceCategoriesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service category not found with ID: " + id));

        category.setIsActive(true);
        ServiceCategories updatedCategory = serviceCategoriesRepository.save(category);
        return convertToResponse(updatedCategory);
    }

    /**
     * Convert ServiceCategories entity to ServiceCategoryResponse DTO
     */
    private ServiceCategoryResponse convertToResponse(ServiceCategories category) {
        return new ServiceCategoryResponse(
                category.getId(),
                category.getCategoryName(),
                category.getCategoryCode(),
                category.getDescription(),
                category.getEstimatedTime(),
                category.getIsActive(),
                category.getCreatedAt(),
                category.getUpdatedAt()
        );
    }

    /**
     * Convert ServiceCategories entity to ServiceCategoryListResponse DTO
     */
    private ServiceCategoryListResponse convertToListResponse(ServiceCategories category) {
        return new ServiceCategoryListResponse(
                category.getId(),
                category.getCategoryName(),
                category.getCategoryCode(),
                category.getEstimatedTime(),
                category.getIsActive()
        );
    }
}
