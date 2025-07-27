package com.garage.backend.repository;

import com.garage.backend.entity.Transactions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionsRepository extends JpaRepository<Transactions, UUID> {

    /**
     * Get today's income (completed transactions)
     * @return BigDecimal total income for today
     */
    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transactions t " +
           "WHERE t.transactionType = 'INCOME' AND t.status = 'COMPLETED' " +
           "AND DATE(t.transactionDate) = CURRENT_DATE")
    BigDecimal getTodayIncome();

    /**
     * Get today's expenses (completed transactions)
     * @return BigDecimal total expenses for today
     */
    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transactions t " +
           "WHERE t.transactionType = 'EXPENSE' AND t.status = 'COMPLETED' " +
           "AND DATE(t.transactionDate) = CURRENT_DATE")
    BigDecimal getTodayExpenses();

    /**
     * Get pending income (pending transactions)
     * @return BigDecimal total pending income
     */
    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transactions t " +
           "WHERE t.transactionType = 'INCOME' AND t.status = 'PENDING'")
    BigDecimal getPendingIncome();

    /**
     * Get recent transactions for a specific date range
     * @param startDate start date
     * @param endDate end date
     * @return List<Transactions> recent transactions
     */
    @Query("SELECT t FROM Transactions t " +
           "WHERE t.transactionDate >= :startDate AND t.transactionDate <= :endDate " +
           "ORDER BY t.transactionDate DESC")
    List<Transactions> getRecentTransactions(@Param("startDate") LocalDateTime startDate, 
                                           @Param("endDate") LocalDateTime endDate);

    /**
     * Get customer payment history
     * @param customerId customer ID
     * @return List<Transactions> customer payment history
     */
    @Query("SELECT t FROM Transactions t " +
           "WHERE t.transactionType = 'INCOME' AND t.customer.id = :customerId " +
           "ORDER BY t.transactionDate DESC")
    List<Transactions> getCustomerPaymentHistory(@Param("customerId") UUID customerId);

    /**
     * Get transactions by type and date range
     * @param transactionType transaction type (INCOME/EXPENSE)
     * @param startDate start date
     * @param endDate end date
     * @param pageable pagination
     * @return Page<Transactions> paginated transactions
     */
    @Query("SELECT t FROM Transactions t " +
           "WHERE t.transactionType = :transactionType " +
           "AND t.transactionDate >= :startDate AND t.transactionDate <= :endDate " +
           "ORDER BY t.transactionDate DESC")
    Page<Transactions> findByTransactionTypeAndDateRange(@Param("transactionType") String transactionType,
                                                        @Param("startDate") LocalDateTime startDate,
                                                        @Param("endDate") LocalDateTime endDate,
                                                        Pageable pageable);

    /**
     * Get transactions by type for today
     * @param transactionType transaction type (INCOME/EXPENSE)
     * @return List<Transactions> today's transactions
     */
    @Query("SELECT t FROM Transactions t " +
           "WHERE t.transactionType = :transactionType " +
           "AND DATE(t.transactionDate) = CURRENT_DATE " +
           "ORDER BY t.transactionDate DESC")
    List<Transactions> findByTransactionTypeAndToday(@Param("transactionType") String transactionType);

    /**
     * Get transactions by customer ID
     * @param customerId customer ID
     * @return List<Transactions> customer transactions
     */
    List<Transactions> findByCustomerId(UUID customerId);

    /**
     * Get transactions by job card ID
     * @param jobCardId job card ID
     * @return List<Transactions> job card transactions
     */
    List<Transactions> findByJobCardId(UUID jobCardId);

    /**
     * Get transactions by expense category
     * @param expenseCategory expense category
     * @return List<Transactions> expense transactions by category
     */
    List<Transactions> findByExpenseCategory(String expenseCategory);

    /**
     * Get transactions by payment method
     * @param paymentMethod payment method
     * @return List<Transactions> income transactions by payment method
     */
    List<Transactions> findByPaymentMethod(String paymentMethod);

    /**
     * Get transactions by status
     * @param status transaction status
     * @return List<Transactions> transactions by status
     */
    List<Transactions> findByStatus(String status);

    /**
     * Get transactions by created by staff
     * @param createdBy staff ID
     * @return List<Transactions> transactions created by staff
     */
    List<Transactions> findByCreatedById(UUID createdBy);
} 