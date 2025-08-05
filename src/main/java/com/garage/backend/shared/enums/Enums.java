package com.garage.backend.shared.enums;

public class Enums {

    /**
     * Transaction types
     */
    public enum TransactionType {
        INCOME,
        EXPENSE
    }

    /**
     * Transaction status
     */
    public enum TransactionStatus {
        PENDING,
        COMPLETED,
        CANCELLED,
        REFUNDED
    }

    /**
     * Payment methods
     */
    public enum PaymentMethod {
        CASH,
        CARD,
        UPI,
        BANK_TRANSFER,
        CHEQUE
    }

    /**
     * Expense categories
     */
    public enum ExpenseCategory {
        UTILITIES,
        SALARY,
        EQUIPMENT,
        SUPPLIES,
        RENT,
        INSURANCE,
        MAINTENANCE,
        INVENTORY
    }

    /**
     * Staff roles
     */
    public enum StaffRole {
        OWNER,
        MANAGER,
        MECHANIC,
        RECEPTIONIST
    }

    /**
     * Job card status
     */
    public enum JobCardStatus {
        PENDING,
        IN_PROGRESS,
        COMPLETED,
        CANCELLED,
        ASSIGNED
    }
}
