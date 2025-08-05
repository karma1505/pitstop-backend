package com.garage.backend.financial.dto;

import java.math.BigDecimal;
import java.util.List;

public class MoneyDataResponse {

    private BigDecimal amountToBeCollected;
    private BigDecimal receivedToday;
    private BigDecimal spentToday;
    private BigDecimal netProfit;
    private List<FinancialTransactionResponse> recentTransactions;

    // Constructors
    public MoneyDataResponse() {}

    public MoneyDataResponse(BigDecimal amountToBeCollected, BigDecimal receivedToday, 
                           BigDecimal spentToday, BigDecimal netProfit, 
                           List<FinancialTransactionResponse> recentTransactions) {
        this.amountToBeCollected = amountToBeCollected;
        this.receivedToday = receivedToday;
        this.spentToday = spentToday;
        this.netProfit = netProfit;
        this.recentTransactions = recentTransactions;
    }

    // Getters and Setters
    public BigDecimal getAmountToBeCollected() {
        return amountToBeCollected;
    }

    public void setAmountToBeCollected(BigDecimal amountToBeCollected) {
        this.amountToBeCollected = amountToBeCollected;
    }

    public BigDecimal getReceivedToday() {
        return receivedToday;
    }

    public void setReceivedToday(BigDecimal receivedToday) {
        this.receivedToday = receivedToday;
    }

    public BigDecimal getSpentToday() {
        return spentToday;
    }

    public void setSpentToday(BigDecimal spentToday) {
        this.spentToday = spentToday;
    }

    public BigDecimal getNetProfit() {
        return netProfit;
    }

    public void setNetProfit(BigDecimal netProfit) {
        this.netProfit = netProfit;
    }

    public List<FinancialTransactionResponse> getRecentTransactions() {
        return recentTransactions;
    }

    public void setRecentTransactions(List<FinancialTransactionResponse> recentTransactions) {
        this.recentTransactions = recentTransactions;
    }

    @Override
    public String toString() {
        return "MoneyDataResponse{" +
                "amountToBeCollected=" + amountToBeCollected +
                ", receivedToday=" + receivedToday +
                ", spentToday=" + spentToday +
                ", netProfit=" + netProfit +
                ", recentTransactions=" + recentTransactions +
                '}';
    }
} 