package com.garage.backend.service;

import com.garage.backend.dto.CreateIncomeTransactionRequest;
import com.garage.backend.dto.CreateExpenseTransactionRequest;
import com.garage.backend.dto.FinancialTransactionResponse;
import com.garage.backend.dto.MoneyDataResponse;
import com.garage.backend.entity.*;
import com.garage.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class FinancialTransactionService {

    @Autowired
    private TransactionsRepository transactionsRepository;

    @Autowired
    private CustomersRepository customersRepository;

    @Autowired
    private VehiclesRepository vehiclesRepository;

    @Autowired
    private JobCardsRepository jobCardsRepository;

    @Autowired
    private StaffRepository staffRepository;

    /**
     * Create income transaction
     * @param request income transaction request
     * @param createdByStaffId staff ID who created the transaction
     * @return FinancialTransactionResponse
     */
    public FinancialTransactionResponse createIncomeTransaction(CreateIncomeTransactionRequest request, UUID createdByStaffId) {
        // Validate customer exists
        Customers customer = customersRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + request.getCustomerId()));

        // Validate staff exists
        Staff createdBy = staffRepository.findById(createdByStaffId)
                .orElseThrow(() -> new RuntimeException("Staff not found with ID: " + createdByStaffId));

        // Validate vehicle if provided
        Vehicles vehicle = null;
        if (request.getVehicleId() != null) {
            vehicle = vehiclesRepository.findById(request.getVehicleId())
                    .orElseThrow(() -> new RuntimeException("Vehicle not found with ID: " + request.getVehicleId()));
        }

        // Validate job card if provided
        JobCards jobCard = null;
        if (request.getJobCardId() != null) {
            jobCard = jobCardsRepository.findById(request.getJobCardId())
                    .orElseThrow(() -> new RuntimeException("Job card not found with ID: " + request.getJobCardId()));
        }

        // Create transaction
        Transactions transaction = new Transactions(
                request.getAmount(),
                request.getDescription(),
                customer,
                vehicle,
                jobCard,
                request.getPaymentMethod(),
                request.getReferenceNumber(),
                createdBy
        );

        if (request.getNotes() != null) {
            transaction.setNotes(request.getNotes());
        }

        Transactions savedTransaction = transactionsRepository.save(transaction);
        return mapToResponse(savedTransaction);
    }

    /**
     * Create expense transaction
     * @param request expense transaction request
     * @param createdByStaffId staff ID who created the transaction
     * @return FinancialTransactionResponse
     */
    public FinancialTransactionResponse createExpenseTransaction(CreateExpenseTransactionRequest request, UUID createdByStaffId) {
        // Validate staff exists
        Staff createdBy = staffRepository.findById(createdByStaffId)
                .orElseThrow(() -> new RuntimeException("Staff not found with ID: " + createdByStaffId));

        // Create transaction
        Transactions transaction = new Transactions(
                request.getAmount(),
                request.getDescription(),
                request.getExpenseCategory(),
                request.getVendorName(),
                request.getVendorContact(),
                request.getReferenceNumber(),
                request.getNotes(),
                createdBy
        );

        Transactions savedTransaction = transactionsRepository.save(transaction);
        return mapToResponse(savedTransaction);
    }

    /**
     * Get money data for dashboard
     * @return MoneyDataResponse
     */
    public MoneyDataResponse getMoneyData() {
        BigDecimal receivedToday = transactionsRepository.getTodayIncome();
        BigDecimal spentToday = transactionsRepository.getTodayExpenses();
        BigDecimal amountToBeCollected = transactionsRepository.getPendingIncome();
        
        // Handle null values from database
        if (receivedToday == null) receivedToday = BigDecimal.ZERO;
        if (spentToday == null) spentToday = BigDecimal.ZERO;
        if (amountToBeCollected == null) amountToBeCollected = BigDecimal.ZERO;
        
        BigDecimal netProfit = receivedToday.subtract(spentToday);

        // Get recent transactions (last 7 days)
        LocalDateTime weekAgo = LocalDateTime.now().minusDays(7);
        LocalDateTime now = LocalDateTime.now();
        List<Transactions> recentTransactions = transactionsRepository.getRecentTransactions(weekAgo, now);

        List<FinancialTransactionResponse> recentTransactionResponses = recentTransactions.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

        return new MoneyDataResponse(
                amountToBeCollected,
                receivedToday,
                spentToday,
                netProfit,
                recentTransactionResponses
        );
    }

    /**
     * Get transactions with filtering and pagination
     * @param transactionType transaction type filter
     * @param fromDate start date filter
     * @param toDate end date filter
     * @param page page number
     * @param size page size
     * @return Page<FinancialTransactionResponse>
     */
    public Page<FinancialTransactionResponse> getTransactions(String transactionType, LocalDate fromDate, 
                                                             LocalDate toDate, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        
        LocalDateTime startDateTime = null;
        LocalDateTime endDateTime = null;
        
        if (fromDate != null) {
            startDateTime = fromDate.atStartOfDay();
        }
        if (toDate != null) {
            endDateTime = toDate.atTime(LocalTime.MAX);
        }

        Page<Transactions> transactionsPage;
        
        if (transactionType != null && startDateTime != null && endDateTime != null) {
            transactionsPage = transactionsRepository.findByTransactionTypeAndDateRange(
                    transactionType, startDateTime, endDateTime, pageable);
        } else if (transactionType != null) {
            // If only transaction type is provided, get all transactions of that type
            List<Transactions> transactions = transactionsRepository.findByTransactionTypeAndToday(transactionType);
            // Convert to page manually (simplified approach)
            int start = (int) pageable.getOffset();
            int end = Math.min((start + pageable.getPageSize()), transactions.size());
            List<Transactions> pageContent = transactions.subList(start, end);
            transactionsPage = new PageImpl<>(pageContent, pageable, transactions.size());
        } else {
            // Get all transactions
            transactionsPage = transactionsRepository.findAll(pageable);
        }

        return transactionsPage.map(this::mapToResponse);
    }

    /**
     * Get transactions by type for today
     * @param transactionType transaction type (INCOME/EXPENSE)
     * @return List<FinancialTransactionResponse>
     */
    public List<FinancialTransactionResponse> getTodayTransactions(String transactionType) {
        List<Transactions> transactions = transactionsRepository.findByTransactionTypeAndToday(transactionType);
        return transactions.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Map transaction entity to response DTO
     * @param transaction transaction entity
     * @return FinancialTransactionResponse
     */
    private FinancialTransactionResponse mapToResponse(Transactions transaction) {
        FinancialTransactionResponse response = new FinancialTransactionResponse();
        response.setId(transaction.getId());
        response.setAmount(transaction.getAmount());
        response.setTransactionType(transaction.getTransactionType());
        response.setTransactionDate(transaction.getTransactionDate());
        response.setDescription(transaction.getDescription());
        response.setStatus(transaction.getStatus());
        response.setReferenceNumber(transaction.getReferenceNumber());
        response.setNotes(transaction.getNotes());
        response.setCreatedAt(transaction.getCreatedAt());

        // Set customer summary if available
        if (transaction.getCustomer() != null) {
            FinancialTransactionResponse.CustomerSummary customerSummary = 
                    new FinancialTransactionResponse.CustomerSummary(
                            transaction.getCustomer().getId(),
                            transaction.getCustomer().getName(),
                            transaction.getCustomer().getPhone()
                    );
            response.setCustomer(customerSummary);
        }

        // Set vehicle summary if available
        if (transaction.getVehicle() != null) {
            FinancialTransactionResponse.VehicleSummary vehicleSummary = 
                    new FinancialTransactionResponse.VehicleSummary(
                            transaction.getVehicle().getId(),
                            transaction.getVehicle().getRegistrationNumber(),
                            transaction.getVehicle().getMake(),
                            transaction.getVehicle().getModel()
                    );
            response.setVehicle(vehicleSummary);
        }

        // Set job card summary if available
        if (transaction.getJobCard() != null) {
            FinancialTransactionResponse.JobCardSummary jobCardSummary = 
                    new FinancialTransactionResponse.JobCardSummary(
                            transaction.getJobCard().getId(),
                            transaction.getJobCard().getJobNumber(),
                            transaction.getJobCard().getStatus()
                    );
            response.setJobCard(jobCardSummary);
        }

        // Set staff summary if available
        if (transaction.getCreatedBy() != null) {
            FinancialTransactionResponse.StaffSummary staffSummary = 
                    new FinancialTransactionResponse.StaffSummary(
                            transaction.getCreatedBy().getId(),
                            transaction.getCreatedBy().getName(),
                            transaction.getCreatedBy().getRole()
                    );
            response.setCreatedBy(staffSummary);
        }

        // Set income-specific fields
        response.setPaymentMethod(transaction.getPaymentMethod());

        // Set expense-specific fields
        response.setExpenseCategory(transaction.getExpenseCategory());
        response.setVendorName(transaction.getVendorName());
        response.setVendorContact(transaction.getVendorContact());

        return response;
    }
} 