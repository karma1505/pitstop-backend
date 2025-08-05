package com.garage.backend.financial.service;

import com.garage.backend.financial.dto.CreateIncomeTransactionRequest;
import com.garage.backend.financial.dto.CreateExpenseTransactionRequest;
import com.garage.backend.financial.dto.FinancialTransactionResponse;
import com.garage.backend.financial.dto.MoneyDataResponse;
import com.garage.backend.financial.entity.Transactions;
import com.garage.backend.customer.entity.Customers;
import com.garage.backend.vehicle.entity.Vehicles;
import com.garage.backend.jobcard.entity.JobCards;
import com.garage.backend.staff.entity.Staff;
import com.garage.backend.shared.enums.Enums;
import com.garage.backend.financial.repository.TransactionsRepository;
import com.garage.backend.customer.repository.CustomersRepository;
import com.garage.backend.vehicle.repository.VehiclesRepository;
import com.garage.backend.jobcard.repository.JobCardsRepository;
import com.garage.backend.staff.repository.StaffRepository;
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
     * @return FinancialTransactionResponse
     */
    public FinancialTransactionResponse createIncomeTransaction(CreateIncomeTransactionRequest request) {
        // Validate customer exists
        Customers customer = customersRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + request.getCustomerId()));

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
                request.getReferenceNumber()
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
     * @return FinancialTransactionResponse
     */
    public FinancialTransactionResponse createExpenseTransaction(CreateExpenseTransactionRequest request) {
        // Create transaction
        Transactions transaction = new Transactions(
                request.getAmount(),
                request.getDescription(),
                request.getExpenseCategory(),
                request.getVendorName(),
                request.getVendorContact(),
                request.getReferenceNumber(),
                request.getNotes()
        );

        Transactions savedTransaction = transactionsRepository.save(transaction);
        return mapToResponse(savedTransaction);
    }

    /**
     * Update expense transaction
     * @param id transaction ID
     * @param request expense transaction update request
     * @return FinancialTransactionResponse
     */
    public FinancialTransactionResponse updateExpenseTransaction(UUID id, CreateExpenseTransactionRequest request) {
        // Find existing transaction
        Transactions transaction = transactionsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found with ID: " + id));

        // Validate it's an expense transaction
        if (Enums.TransactionType.EXPENSE != transaction.getTransactionType()) {
            throw new RuntimeException("Transaction is not an expense transaction");
        }

        // Update fields
        transaction.setAmount(request.getAmount());
        transaction.setDescription(request.getDescription());
        transaction.setExpenseCategory(request.getExpenseCategory());
        transaction.setVendorName(request.getVendorName());
        transaction.setVendorContact(request.getVendorContact());
        transaction.setReferenceNumber(request.getReferenceNumber());
        transaction.setNotes(request.getNotes());

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
     * @param paymentMethod payment method filter
     * @param transactionStatus transaction status filter
     * @param expenseCategory expense category filter
     * @param page page number
     * @param size page size
     * @return Page<FinancialTransactionResponse>
     */
    public Page<FinancialTransactionResponse> getTransactions(String transactionType, LocalDate fromDate, 
                                                             LocalDate toDate, String paymentMethod, String transactionStatus, 
                                                             String expenseCategory, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        
        LocalDateTime startDateTime = null;
        LocalDateTime endDateTime = null;
        
        if (fromDate != null) {
            startDateTime = fromDate.atStartOfDay();
        }
        if (toDate != null) {
            endDateTime = toDate.atTime(LocalTime.MAX);
        }

        // Convert expense category string to enum if provided
        Enums.ExpenseCategory expenseCategoryEnum = null;
        if (expenseCategory != null && !expenseCategory.isEmpty()) {
            try {
                expenseCategoryEnum = Enums.ExpenseCategory.valueOf(expenseCategory.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Invalid expense category: " + expenseCategory);
            }
        }

        // Convert payment method string to enum if provided
        Enums.PaymentMethod paymentMethodEnum = null;
        if (paymentMethod != null && !paymentMethod.isEmpty()) {
            try {
                paymentMethodEnum = Enums.PaymentMethod.valueOf(paymentMethod.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Invalid payment method: " + paymentMethod);
            }
        }

        // Use the new repository method with all filters
        Page<Transactions> transactionsPage = transactionsRepository.findWithFilters(
                transactionType, fromDate, toDate, paymentMethodEnum, transactionStatus, expenseCategoryEnum, pageable);

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
        response.setTransactionDateOnly(transaction.getTransactionDateOnly());
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