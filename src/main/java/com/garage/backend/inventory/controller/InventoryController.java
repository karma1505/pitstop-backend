package com.garage.backend.inventory.controller;

import com.garage.backend.inventory.dto.*;
import com.garage.backend.inventory.service.InventoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * REST Controller for inventory management operations
 * 
 * Provides endpoints for:
 * - Creating new inventory items
 * - Updating existing items
 * - Retrieving inventory information
 * - Managing stock levels
 * - Viewing stock history
 * - Low stock alerts
 * 
 * All endpoints follow RESTful conventions and return appropriate HTTP status codes
 */
@RestController
@RequestMapping("/admin/inventory")
@CrossOrigin(origins = "*")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    /**
     * Create a new inventory item
     * 
     * Scenario: Create New Inventory Item via API
     *   Given a garage owner wants to add a new part via API
     *   When they send a POST request with valid item details
     *   Then the system should create an inventory record
     *   And initialize stock levels
     *   And return 201 Created with item information
     *   But if validation fails
     *   Then the system should return 400 Bad Request
     *   And if item code already exists
     *   Then the system should return 400 Bad Request
     * 
     * @param request CreateInventoryItemRequest with item details
     * @return ResponseEntity<InventoryItemResponse> with created item
     */
    @PostMapping
    public ResponseEntity<InventoryItemResponse> createInventoryItem(@Valid @RequestBody CreateInventoryItemRequest request) {
        try {
            InventoryItemResponse response = inventoryService.createInventoryItem(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            // Return bad request for business logic errors (duplicate code, etc.)
            System.err.println("Error creating inventory item: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            // Return internal server error for unexpected errors
            System.err.println("Unexpected error creating inventory item: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Update an existing inventory item
     * 
     * Scenario: Update Inventory Item via API
     *   Given a garage owner wants to update item details via API
     *   When they send a PATCH request with valid updated information
     *   Then the system should update the inventory record
     *   And return 200 OK with updated item information
     *   But if item does not exist
     *   Then the system should return 404 Not Found
     *   And if validation fails
     *   Then the system should return 400 Bad Request
     * 
     * @param id Item ID
     * @param request UpdateInventoryItemRequest with updated details
     * @return ResponseEntity<InventoryItemResponse> with updated item
     */
    @PatchMapping("/{id}")
    public ResponseEntity<InventoryItemResponse> updateInventoryItem(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateInventoryItemRequest request) {
        try {
            InventoryItemResponse response = inventoryService.updateInventoryItem(id, request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            // Check if it's a not found error or validation error
            if (e.getMessage().contains("not found")) {
                return ResponseEntity.notFound().build();
            }
            System.err.println("Error updating inventory item: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            System.err.println("Unexpected error updating inventory item: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get inventory item by ID
     * 
     * Scenario: Retrieve Inventory Item by ID via API
     *   Given a garage owner wants to view item details via API
     *   When they send a GET request with a valid item ID
     *   Then the system should return 200 OK with item information and stock levels
     *   But if item does not exist
     *   Then the system should return 404 Not Found
     * 
     * @param id Item ID
     * @return ResponseEntity<InventoryItemResponse> with item details
     */
    @GetMapping("/{id}")
    public ResponseEntity<InventoryItemResponse> getInventoryItemById(@PathVariable UUID id) {
        try {
            InventoryItemResponse response = inventoryService.getInventoryItemById(id);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            System.err.println("Unexpected error retrieving inventory item: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get all inventory items with pagination, sorting, and filtering
     * 
     * Scenario: List All Inventory Items with Filters via API
     *   Given a garage owner wants to view inventory with optional filters via API
     *   When they send a GET request with optional pagination and filter parameters
     *   Then the system should return 200 OK with filtered and paginated inventory list
     *   And include total count and page information
     *   And sort by item name ascending by default
     * 
     * @param page Page number (default: 0)
     * @param size Page size (default: 20)
     * @param sortBy Sort field (default: itemName)
     * @param sortDir Sort direction (default: asc)
     * @param category Category filter (partial match)
     * @param itemName Item name filter (partial match)
     * @param itemCode Item code filter (partial match)
     * @param lowStockOnly Show only low stock items (default: false)
     * @param isActive Show only active/inactive items (default: true)
     * @return ResponseEntity<Page<InventoryListResponse>> with filtered and paginated items
     */
    @GetMapping
    public ResponseEntity<Page<InventoryListResponse>> getAllInventoryItems(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "itemName") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String itemName,
            @RequestParam(required = false) String itemCode,
            @RequestParam(required = false) Boolean lowStockOnly,
            @RequestParam(defaultValue = "true") Boolean isActive) {
        try {
            // Create sort object
            Sort sort = sortDir.equalsIgnoreCase("desc") 
                ? Sort.by(sortBy).descending() 
                : Sort.by(sortBy).ascending();
            
            Pageable pageable = PageRequest.of(page, size, sort);
            Page<InventoryListResponse> response = inventoryService.getAllInventoryItems(
                    pageable, category, itemName, itemCode, lowStockOnly, isActive);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.err.println("Error retrieving inventory items: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Adjust stock levels for an inventory item
     * 
     * Scenario: Adjust Stock via API
     *   Given a garage owner wants to adjust stock levels via API
     *   When they send a POST request with adjustment details
     *   Then the system should update stock levels
     *   And record the adjustment in history
     *   And return 200 OK with updated item information
     *   But if adjustment would result in negative stock
     *   Then the system should return 400 Bad Request
     *   And if item does not exist
     *   Then the system should return 404 Not Found
     * 
     * @param id Item ID
     * @param request AdjustStockRequest with adjustment details
     * @return ResponseEntity<InventoryItemResponse> with updated item
     */
    @PostMapping("/{id}/adjust-stock")
    public ResponseEntity<InventoryItemResponse> adjustStock(
            @PathVariable UUID id,
            @Valid @RequestBody AdjustStockRequest request) {
        try {
            InventoryItemResponse response = inventoryService.adjustStock(id, request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found")) {
                return ResponseEntity.notFound().build();
            }
            System.err.println("Error adjusting stock: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            System.err.println("Unexpected error adjusting stock: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get low stock items
     * 
     * Scenario: Get Low Stock Items via API
     *   Given a garage owner wants to see items that need reordering via API
     *   When they send a GET request to the low-stock endpoint
     *   Then the system should return 200 OK with items below minimum stock level
     *   And include pagination information
     * 
     * @param page Page number (default: 0)
     * @param size Page size (default: 20)
     * @param sortBy Sort field (default: itemName)
     * @param sortDir Sort direction (default: asc)
     * @return ResponseEntity<Page<InventoryListResponse>> with low stock items
     */
    @GetMapping("/low-stock")
    public ResponseEntity<Page<InventoryListResponse>> getLowStockItems(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "itemName") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        try {
            Sort sort = sortDir.equalsIgnoreCase("desc") 
                ? Sort.by(sortBy).descending() 
                : Sort.by(sortBy).ascending();
            
            Pageable pageable = PageRequest.of(page, size, sort);
            Page<InventoryListResponse> response = inventoryService.getLowStockItems(pageable);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.err.println("Error retrieving low stock items: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get stock adjustment history for an item
     * 
     * Scenario: Get Stock History via API
     *   Given a garage owner wants to view stock adjustment history via API
     *   When they send a GET request to the history endpoint
     *   Then the system should return 200 OK with adjustment history
     *   And include pagination information
     *   But if item does not exist
     *   Then the system should return 404 Not Found
     * 
     * @param id Item ID
     * @param page Page number (default: 0)
     * @param size Page size (default: 20)
     * @return ResponseEntity<Page<StockAdjustmentResponse>> with adjustment history
     */
    @GetMapping("/{id}/history")
    public ResponseEntity<Page<StockAdjustmentResponse>> getStockHistory(
            @PathVariable UUID id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("adjustedAt").descending());
            Page<StockAdjustmentResponse> response = inventoryService.getStockHistory(id, pageable);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found")) {
                return ResponseEntity.notFound().build();
            }
            System.err.println("Error retrieving stock history: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            System.err.println("Unexpected error retrieving stock history: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Deactivate an inventory item
     * 
     * Scenario: Deactivate Item via API
     *   Given a garage owner wants to remove an item from active inventory via API
     *   When they send a PATCH request to the deactivate endpoint
     *   Then the system should mark the item as inactive
     *   And return 200 OK with updated item information
     *   But if item does not exist
     *   Then the system should return 404 Not Found
     * 
     * @param id Item ID
     * @return ResponseEntity<InventoryItemResponse> with updated item
     */
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<InventoryItemResponse> deactivateItem(@PathVariable UUID id) {
        try {
            InventoryItemResponse response = inventoryService.deactivateItem(id);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            System.err.println("Unexpected error deactivating item: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Reactivate an inventory item
     * 
     * Scenario: Reactivate Item via API
     *   Given a garage owner wants to restore an inactive item via API
     *   When they send a PATCH request to the reactivate endpoint
     *   Then the system should mark the item as active
     *   And return 200 OK with updated item information
     *   But if item does not exist
     *   Then the system should return 404 Not Found
     * 
     * @param id Item ID
     * @return ResponseEntity<InventoryItemResponse> with updated item
     */
    @PatchMapping("/{id}/reactivate")
    public ResponseEntity<InventoryItemResponse> reactivateItem(@PathVariable UUID id) {
        try {
            InventoryItemResponse response = inventoryService.reactivateItem(id);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            System.err.println("Unexpected error reactivating item: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Health check endpoint
     * 
     * Scenario: Health Check
     *   Given the system needs to verify inventory service health
     *   When a health check request is made
     *   Then the system should return 200 OK with service status
     * 
     * @return ResponseEntity<String> with health status
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Inventory API is running - " + System.currentTimeMillis());
    }
}
