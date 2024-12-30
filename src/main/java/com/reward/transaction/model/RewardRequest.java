package com.reward.transaction.model;

import java.util.List;

public class RewardRequest {

    private final String customerId;
    private final String startDate;
    private final String endDate;
    private final List<Transaction> transactions;

    public RewardRequest(String customerId, String startDate, String endDate, List<Transaction> transactions) {
        this.customerId = customerId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.transactions = transactions;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }


    public List<Transaction> getTransactions() {
        return transactions;
    }

}