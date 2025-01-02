package com.reward.transaction.model;

import java.util.List;

public class RewardResponse {

    private Long customerId;
    private int totalPoints;
    private List<MonthlyPoints> monthlyPoints;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    public List<MonthlyPoints> getMonthlyPoints() {
        return monthlyPoints;
    }

    public void setMonthlyPoints(List<MonthlyPoints> monthlyPoints) {
        this.monthlyPoints = monthlyPoints;
    }

    public RewardResponse(Long customerId, int totalPoints, List<MonthlyPoints> monthlyPoints) {
        this.customerId = customerId;
        this.totalPoints = totalPoints;
        this.monthlyPoints = monthlyPoints;
    }

    public RewardResponse() {
    }
}
