package com.reward.transaction.model;

import java.time.LocalDate;

public class Transaction {
    private String customerId;
    private double amount;
    private LocalDate transactionDate;

    public Transaction(String customerId, double amount, LocalDate transactionDate) {
        this.customerId = customerId;
        this.transactionDate = transactionDate;
        this.amount = amount;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
