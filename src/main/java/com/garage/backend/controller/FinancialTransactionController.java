package com.garage.backend.controller;

import com.garage.backend.dto.CreateIncomeTransactionRequest;
import com.garage.backend.dto.CreateExpenseTransactionRequest;
import com.garage.backend.dto.FinancialTransactionResponse;
import com.garage.backend.dto.MoneyDataResponse;
import com.garage.backend.service.FinancialTransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/admin/financial-transactions")
@CrossOrigin(origins = "*")
public class FinancialTransactionController {

    @Autowired
    private FinancialTransactionService financialTransactionService;

    /**
     * Get money data for dashboard
     * @return MoneyDataResponse
     */
    @GetMapping("/money-data")
    public ResponseEntity<MoneyDataResponse> getMoneyData() {
        try {
            MoneyDataResponse response = financialTransactionService.getMoneyData();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Create income transaction
     * @param request income transaction request
     * @return FinancialTransactionResponse
     */
    @PostMapping("/income")
    public ResponseEntity<FinancialTransactionResponse> createIncomeTransaction(
            @Valid @RequestBody CreateIncomeTransactionRequest request) {
        try {
            // TODO: Get staff ID from authentication context
            // For now, using a placeholder UUID - this should come from JWT token
            java.util.UUID staffId = java.util.UUID.randomUUID(); // Placeholder
            
            FinancialTransactionResponse response = financialTransactionService.createIncomeTransaction(request, staffId);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Create expense transaction
     * @param request expense transaction request
     * @return FinancialTransactionResponse
     */
    @PostMapping("/expense")
    public ResponseEntity<FinancialTransactionResponse> createExpenseTransaction(
            @Valid @RequestBody CreateExpenseTransactionRequest request) {
        try {
            // TODO: Get staff ID from authentication context
            // For now, using a placeholder UUID - this should come from JWT token
            java.util.UUID staffId = java.util.UUID.randomUUID(); // Placeholder
            
            FinancialTransactionResponse response = financialTransactionService.createExpenseTransaction(request, staffId);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get transactions with filtering and pagination
     * @param transactionType transaction type filter (INCOME/EXPENSE)
     * @param fromDate start date filter
     * @param toDate end date filter
     * @param page page number (default: 0)
     * @param size page size (default: 20)
     * @return Page<FinancialTransactionResponse>
     */
    @GetMapping
    public ResponseEntity<Page<FinancialTransactionResponse>> getTransactions(
            @RequestParam(required = false) String transactionType,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        try {
            Page<FinancialTransactionResponse> response = financialTransactionService.getTransactions(
                    transactionType, fromDate, toDate, page, size);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get today's transactions by type
     * @param transactionType transaction type (INCOME/EXPENSE)
     * @return List<FinancialTransactionResponse>
     */
    @GetMapping("/today")
    public ResponseEntity<List<FinancialTransactionResponse>> getTodayTransactions(
            @RequestParam String transactionType) {
        try {
            List<FinancialTransactionResponse> response = financialTransactionService.getTodayTransactions(transactionType);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Health check endpoint
     * @return String
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Financial Transaction API is running - " + System.currentTimeMillis());
    }
    
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Test endpoint working!");
    }
} 