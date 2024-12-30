package com.reward.transaction.model;

import java.util.Map;

public class RewardResponse {

    private String customerId;
    private int totalPoints;
    private Map<String, Integer> monthlyPoints;

    public RewardResponse(String customerId, int totalPoints, Map<String, Integer> monthlyPoints) {
        this.customerId = customerId;
        this.totalPoints = totalPoints;
        this.monthlyPoints = monthlyPoints;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    public Map<String, Integer> getMonthlyPoints() {
        return monthlyPoints;
    }

    public void setMonthlyPoints(Map<String, Integer> monthlyPoints) {
        this.monthlyPoints = monthlyPoints;
    }
}
