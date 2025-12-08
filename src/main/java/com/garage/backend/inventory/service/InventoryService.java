package com.garage.backend.inventory.service;

import com.garage.backend.inventory.dto.*;
import com.garage.backend.inventory.entity.Inventory;
import com.garage.backend.inventory.entity.InventoryAdjustments;
import com.garage.backend.inventory.entity.ProductInventory;
import com.garage.backend.inventory.repository.InventoryAdjustmentsRepository;
import com.garage.backend.inventory.repository.InventoryRepository;
import com.garage.backend.inventory.repository.ProductInventoryRepository;
import com.garage.backend.authentication.entity.User;
import com.garage.backend.authentication.repository.UserRepository;
import com.garage.backend.settings.entity.Garage;
import com.garage.backend.settings.repository.GarageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service class for inventory management operations
 * 
 * Handles all business logic related to inventory operations including:
 * - Creating and updating inventory items
 * - Managing stock levels
 * - Recording stock adjustments
 * - Retrieving inventory information
 * - Low stock alerts
 */
@Service
@Transactional
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ProductInventoryRepository productInventoryRepository;

    @Autowired
    private InventoryAdjustmentsRepository inventoryAdjustmentsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GarageRepository garageRepository;

    /**
     * Create a new inventory item with initial stock
     * 
     * Scenario: Create New Inventory Item
     *   Given a garage owner wants to add a new part to inventory
     *   When they provide item details and initial quantity
     *   Then the system should create an inventory record
     *   And initialize stock levels in ProductInventory
     *   And record initial stock as an adjustment if quantity > 0
     *   And return the item information
     *   But if item code already exists
     *   Then the system should throw an exception
     */
    public InventoryItemResponse createInventoryItem(CreateInventoryItemRequest request) {
        // Check if item code already exists
        if (inventoryRepository.existsByItemCode(request.getItemCode())) {
            throw new RuntimeException("Item with code " + request.getItemCode() + " already exists");
        }

        // Validate max stock level if provided
        if (request.getMaxStockLevel() != null && request.getMaxStockLevel() < request.getMinStockLevel()) {
            throw new RuntimeException("Maximum stock level cannot be less than minimum stock level");
        }

        // Get current user and branch information
        String currentUserEmail = getCurrentUserEmail();
        User currentUser = userRepository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new RuntimeException("Current user not found"));
        
        // Get user's garage/branch
        Optional<Garage> garageOpt = garageRepository.findByCreatedBy(currentUser.getId()).stream().findFirst();
        UUID branchId = garageOpt.map(Garage::getId)
                .orElseThrow(() -> new RuntimeException("No garage found for current user"));

        // Create inventory item
        Inventory inventory = new Inventory();
        inventory.setItemCode(request.getItemCode());
        inventory.setItemName(request.getItemName());
        inventory.setDescription(request.getDescription());
        inventory.setCategory(request.getCategory());
        inventory.setUnit(request.getUnit());
        inventory.setCostPrice(request.getCostPrice());
        inventory.setSellingPrice(request.getSellingPrice());
        inventory.setMinStockLevel(request.getMinStockLevel());
        inventory.setMaxStockLevel(request.getMaxStockLevel());
        inventory.setBranchId(branchId);
        inventory.setIsActive(true);

        Inventory savedInventory = inventoryRepository.save(inventory);

        // Initialize product inventory with initial quantity
        ProductInventory productInventory = new ProductInventory();
        productInventory.setInventoryId(savedInventory.getId());
        productInventory.setQuantity(request.getInitialQuantity());
        productInventory.setReservedQuantity(0);

        ProductInventory savedProductInventory = productInventoryRepository.save(productInventory);

        // Record initial stock as adjustment if quantity > 0
        if (request.getInitialQuantity() > 0) {
            InventoryAdjustments adjustment = new InventoryAdjustments();
            adjustment.setInventoryId(savedInventory.getId());
            adjustment.setAdjustmentType("ADD");
            adjustment.setQuantity(request.getInitialQuantity());
            adjustment.setReason("Initial stock");
            adjustment.setAdjustedBy(currentUser.getId());
            
            inventoryAdjustmentsRepository.save(adjustment);
        }

        return convertToResponse(savedInventory, savedProductInventory);
    }

    /**
     * Update an existing inventory item
     * 
     * Scenario: Update Inventory Item
     *   Given a garage owner wants to update item details
     *   When they provide updated information
     *   Then the system should update the inventory record
     *   And return the updated item information
     *   But if item does not exist
     *   Then the system should throw an exception
     */
    public InventoryItemResponse updateInventoryItem(UUID itemId, UpdateInventoryItemRequest request) {
        // Find existing inventory item
        Inventory existingInventory = inventoryRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Inventory item not found with ID: " + itemId));

        // Validate max stock level if provided
        if (request.getMaxStockLevel() != null && request.getMaxStockLevel() < request.getMinStockLevel()) {
            throw new RuntimeException("Maximum stock level cannot be less than minimum stock level");
        }

        // Update inventory fields
        existingInventory.setItemName(request.getItemName());
        existingInventory.setDescription(request.getDescription());
        existingInventory.setCategory(request.getCategory());
        existingInventory.setUnit(request.getUnit());
        existingInventory.setCostPrice(request.getCostPrice());
        existingInventory.setSellingPrice(request.getSellingPrice());
        existingInventory.setMinStockLevel(request.getMinStockLevel());
        existingInventory.setMaxStockLevel(request.getMaxStockLevel());
        existingInventory.setIsActive(request.getIsActive());

        Inventory updatedInventory = inventoryRepository.save(existingInventory);

        // Get product inventory
        ProductInventory productInventory = productInventoryRepository.findByInventoryId(itemId)
                .orElseThrow(() -> new RuntimeException("Product inventory not found for item: " + itemId));

        return convertToResponse(updatedInventory, productInventory);
    }

    /**
     * Get inventory item by ID
     * 
     * Scenario: Retrieve Inventory Item by ID
     *   Given a garage owner wants to view item details
     *   When they provide a valid item ID
     *   Then the system should return the item information with stock levels
     *   But if item does not exist
     *   Then the system should throw an exception
     */
    @Transactional(readOnly = true)
    public InventoryItemResponse getInventoryItemById(UUID itemId) {
        Inventory inventory = inventoryRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Inventory item not found with ID: " + itemId));
        
        ProductInventory productInventory = productInventoryRepository.findByInventoryId(itemId)
                .orElseThrow(() -> new RuntimeException("Product inventory not found for item: " + itemId));

        return convertToResponse(inventory, productInventory);
    }

    /**
     * Get all inventory items with pagination and optional filtering
     * 
     * Scenario: List All Inventory Items with Filters
     *   Given a garage owner wants to view inventory with filters
     *   When they request inventory list with optional filters and pagination
     *   Then the system should return filtered and paginated inventory list
     *   And include stock levels for each item
     */
    @Transactional(readOnly = true)
    public Page<InventoryListResponse> getAllInventoryItems(Pageable pageable, String category, 
                                                            String itemName, String itemCode, 
                                                            Boolean lowStockOnly, Boolean isActive) {
        try {
            // Apply filters if any are provided
            if (hasAnyFilter(category, itemName, itemCode, lowStockOnly, isActive)) {
                List<Inventory> allFilteredItems = getFilteredInventoryItems(category, itemName, itemCode, isActive);
                
                // Convert to responses with stock info
                List<InventoryListResponse> allResponses = allFilteredItems.stream()
                        .map(this::convertToListResponse)
                        .collect(Collectors.toList());
                
                // Apply low stock filter if requested
                if (lowStockOnly != null && lowStockOnly) {
                    allResponses = allResponses.stream()
                            .filter(InventoryListResponse::getIsLowStock)
                            .collect(Collectors.toList());
                }
                
                // Apply manual pagination
                int start = (int) pageable.getOffset();
                int end = Math.min((start + pageable.getPageSize()), allResponses.size());
                List<InventoryListResponse> pageContent = allResponses.subList(start, end);
                
                return new PageImpl<>(pageContent, pageable, allResponses.size());
            } else {
                // No filters, use repository pagination directly
                Page<Inventory> inventoryPage = inventoryRepository.findAll(pageable);
                
                List<InventoryListResponse> responses = inventoryPage.getContent().stream()
                        .map(this::convertToListResponse)
                        .collect(Collectors.toList());
                
                return new PageImpl<>(responses, pageable, inventoryPage.getTotalElements());
            }
        } catch (Exception e) {
            System.err.println("Error in getAllInventoryItems: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Adjust stock levels (Add, Remove, or Set)
     * 
     * Scenario: Adjust Stock Levels
     *   Given a garage owner wants to adjust stock
     *   When they specify adjustment type and quantity
     *   Then the system should update stock levels
     *   And record the adjustment in history
     *   And return updated stock information
     *   But if adjustment would result in negative stock
     *   Then the system should throw an exception
     */
    public InventoryItemResponse adjustStock(UUID itemId, AdjustStockRequest request) {
        // Find inventory item
        Inventory inventory = inventoryRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Inventory item not found with ID: " + itemId));

        // Find product inventory
        ProductInventory productInventory = productInventoryRepository.findByInventoryId(itemId)
                .orElseThrow(() -> new RuntimeException("Product inventory not found for item: " + itemId));

        // Get current user
        String currentUserEmail = getCurrentUserEmail();
        User currentUser = userRepository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new RuntimeException("Current user not found"));

        // Store quantity before adjustment
        Integer quantityBefore = productInventory.getQuantity();
        Integer quantityAfter;

        // Apply adjustment based on type
        switch (request.getAdjustmentType()) {
            case ADD:
                quantityAfter = quantityBefore + request.getQuantity();
                break;
            case REMOVE:
                quantityAfter = quantityBefore - request.getQuantity();
                if (quantityAfter < 0) {
                    throw new RuntimeException("Cannot remove " + request.getQuantity() + 
                            " units. Only " + quantityBefore + " units available.");
                }
                break;
            case SET:
                quantityAfter = request.getQuantity();
                break;
            default:
                throw new RuntimeException("Invalid adjustment type: " + request.getAdjustmentType());
        }

        // Update product inventory
        productInventory.setQuantity(quantityAfter);
        ProductInventory updatedProductInventory = productInventoryRepository.save(productInventory);

        // Record adjustment in history
        InventoryAdjustments adjustment = new InventoryAdjustments();
        adjustment.setInventoryId(itemId);
        adjustment.setAdjustmentType(request.getAdjustmentType().name());
        adjustment.setQuantity(request.getQuantity());
        adjustment.setReason(request.getReason());
        adjustment.setReferenceNumber(request.getNotes()); // Using notes as reference
        adjustment.setAdjustedBy(currentUser.getId());
        
        inventoryAdjustmentsRepository.save(adjustment);

        return convertToResponse(inventory, updatedProductInventory);
    }

    /**
     * Get low stock items (quantity < minStockLevel)
     * 
     * Scenario: Get Low Stock Items
     *   Given a garage owner wants to see items that need reordering
     *   When they request low stock items
     *   Then the system should return items where current quantity is below minimum level
     */
    @Transactional(readOnly = true)
    public Page<InventoryListResponse> getLowStockItems(Pageable pageable) {
        List<Inventory> allItems = inventoryRepository.findByIsActive(true);
        
        List<InventoryListResponse> lowStockItems = allItems.stream()
                .map(this::convertToListResponse)
                .filter(InventoryListResponse::getIsLowStock)
                .collect(Collectors.toList());
        
        // Apply pagination
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), lowStockItems.size());
        List<InventoryListResponse> pageContent = lowStockItems.subList(start, end);
        
        return new PageImpl<>(pageContent, pageable, lowStockItems.size());
    }

    /**
     * Get stock adjustment history for an item
     * 
     * Scenario: View Stock Adjustment History
     *   Given a garage owner wants to see stock adjustment history
     *   When they request history for a specific item
     *   Then the system should return all adjustments for that item
     */
    @Transactional(readOnly = true)
    public Page<StockAdjustmentResponse> getStockHistory(UUID itemId, Pageable pageable) {
        // Verify item exists
        Inventory inventory = inventoryRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Inventory item not found with ID: " + itemId));

        // Get all adjustments for this item
        List<InventoryAdjustments> adjustments = inventoryAdjustmentsRepository.findByInventoryId(itemId);
        
        List<StockAdjustmentResponse> responses = adjustments.stream()
                .map(adj -> convertToAdjustmentResponse(adj, inventory))
                .collect(Collectors.toList());
        
        // Apply pagination
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), responses.size());
        List<StockAdjustmentResponse> pageContent = responses.subList(start, end);
        
        return new PageImpl<>(pageContent, pageable, responses.size());
    }

    /**
     * Deactivate an inventory item (soft delete)
     * 
     * Scenario: Deactivate Inventory Item
     *   Given a garage owner wants to remove an item from active inventory
     *   When they deactivate the item
     *   Then the system should mark it as inactive
     *   And it should not appear in default listings
     */
    public InventoryItemResponse deactivateItem(UUID itemId) {
        Inventory inventory = inventoryRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Inventory item not found with ID: " + itemId));

        inventory.setIsActive(false);
        Inventory updatedInventory = inventoryRepository.save(inventory);

        ProductInventory productInventory = productInventoryRepository.findByInventoryId(itemId)
                .orElseThrow(() -> new RuntimeException("Product inventory not found for item: " + itemId));

        return convertToResponse(updatedInventory, productInventory);
    }

    /**
     * Reactivate an inventory item
     * 
     * Scenario: Reactivate Inventory Item
     *   Given a garage owner wants to restore an inactive item
     *   When they reactivate the item
     *   Then the system should mark it as active
     *   And it should appear in default listings again
     */
    public InventoryItemResponse reactivateItem(UUID itemId) {
        Inventory inventory = inventoryRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Inventory item not found with ID: " + itemId));

        inventory.setIsActive(true);
        Inventory updatedInventory = inventoryRepository.save(inventory);

        ProductInventory productInventory = productInventoryRepository.findByInventoryId(itemId)
                .orElseThrow(() -> new RuntimeException("Product inventory not found for item: " + itemId));

        return convertToResponse(updatedInventory, productInventory);
    }

    // ==================== Private Helper Methods ====================

    /**
     * Get filtered inventory items based on provided criteria
     */
    @Transactional(readOnly = true)
    private List<Inventory> getFilteredInventoryItems(String category, String itemName, 
                                                     String itemCode, Boolean isActive) {
        List<Inventory> result = inventoryRepository.findAll();
        
        // Apply category filter
        if (category != null && !category.trim().isEmpty()) {
            result = result.stream()
                    .filter(item -> item.getCategory() != null && 
                            item.getCategory().toLowerCase().contains(category.toLowerCase()))
                    .collect(Collectors.toList());
        }
        
        // Apply item name filter
        if (itemName != null && !itemName.trim().isEmpty()) {
            result = result.stream()
                    .filter(item -> item.getItemName().toLowerCase().contains(itemName.toLowerCase()))
                    .collect(Collectors.toList());
        }
        
        // Apply item code filter
        if (itemCode != null && !itemCode.trim().isEmpty()) {
            result = result.stream()
                    .filter(item -> item.getItemCode().toLowerCase().contains(itemCode.toLowerCase()))
                    .collect(Collectors.toList());
        }
        
        // Apply active status filter
        if (isActive != null) {
            result = result.stream()
                    .filter(item -> item.getIsActive().equals(isActive))
                    .collect(Collectors.toList());
        }
        
        return result;
    }

    /**
     * Check if any filter parameters are provided
     */
    private boolean hasAnyFilter(String category, String itemName, String itemCode, 
                                 Boolean lowStockOnly, Boolean isActive) {
        return (category != null && !category.trim().isEmpty()) ||
               (itemName != null && !itemName.trim().isEmpty()) ||
               (itemCode != null && !itemCode.trim().isEmpty()) ||
               lowStockOnly != null || isActive != null;
    }

    /**
     * Get current user email from security context
     */
    private String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getName() != null) {
            return authentication.getName();
        }
        throw new RuntimeException("No authenticated user found");
    }

    /**
     * Convert Inventory and ProductInventory entities to InventoryItemResponse DTO
     */
    private InventoryItemResponse convertToResponse(Inventory inventory, ProductInventory productInventory) {
        Integer availableQty = productInventory.getAvailableQuantity();
        Boolean isLowStock = availableQty < inventory.getMinStockLevel();

        return new InventoryItemResponse(
                inventory.getId(),
                inventory.getItemCode(),
                inventory.getItemName(),
                inventory.getDescription(),
                inventory.getCategory(),
                inventory.getUnit(),
                inventory.getCostPrice(),
                inventory.getSellingPrice(),
                inventory.getSupplierId(),
                inventory.getBranchId(),
                inventory.getMinStockLevel(),
                inventory.getMaxStockLevel(),
                inventory.getIsActive(),
                productInventory.getQuantity(),
                productInventory.getReservedQuantity(),
                availableQty,
                isLowStock,
                inventory.getCreatedAt(),
                inventory.getUpdatedAt()
        );
    }

    /**
     * Convert Inventory entity to InventoryListResponse DTO
     */
    private InventoryListResponse convertToListResponse(Inventory inventory) {
        ProductInventory productInventory = productInventoryRepository.findByInventoryId(inventory.getId())
                .orElse(new ProductInventory(inventory.getId(), 0, 0));

        Integer availableQty = productInventory.getAvailableQuantity();
        Boolean isLowStock = availableQty < inventory.getMinStockLevel();

        return new InventoryListResponse(
                inventory.getId(),
                inventory.getItemCode(),
                inventory.getItemName(),
                inventory.getCategory(),
                inventory.getUnit(),
                inventory.getSellingPrice(),
                productInventory.getQuantity(),
                availableQty,
                isLowStock,
                inventory.getIsActive()
        );
    }

    /**
     * Convert InventoryAdjustments entity to StockAdjustmentResponse DTO
     */
    private StockAdjustmentResponse convertToAdjustmentResponse(InventoryAdjustments adjustment, Inventory inventory) {
        // Get user who performed adjustment
        String performedBy = "Unknown";
        Optional<User> userOpt = userRepository.findById(adjustment.getAdjustedBy());
        if (userOpt.isPresent()) {
            performedBy = userOpt.get().getEmail();
        }

        return new StockAdjustmentResponse(
                adjustment.getId(),
                adjustment.getInventoryId(),
                inventory.getItemCode(),
                inventory.getItemName(),
                adjustment.getAdjustmentType(),
                null, // quantityBefore - not stored in current schema
                null, // quantityAfter - not stored in current schema
                adjustment.getQuantity(),
                adjustment.getReason(),
                adjustment.getReferenceNumber(),
                performedBy,
                adjustment.getAdjustedAt()
        );
    }
}
