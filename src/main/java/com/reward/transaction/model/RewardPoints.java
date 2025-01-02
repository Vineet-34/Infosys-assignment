package com.reward.transaction.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class RewardPoints {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private int totalPoints;

    private List<MonthlyPoints> monthlyPoints;

    public RewardPoints(Long id, Customer customer, int totalPoints, List<MonthlyPoints> monthlyPoints) {
        this.id = id;
        this.customer = customer;
        this.totalPoints = totalPoints;
        this.monthlyPoints = monthlyPoints;
    }

    public RewardPoints() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
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
}

