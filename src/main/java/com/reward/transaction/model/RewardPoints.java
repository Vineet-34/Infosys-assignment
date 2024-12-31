package com.reward.transaction.model;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.MapKeyColumn;

import javax.persistence.*;
import java.util.Map;

@Entity
public class RewardPoints {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private int totalPoints;

    @ElementCollection
    @MapKeyColumn(name = "month")
    @Column(name = "points")
    private Map<String, Integer> monthlyPoints;


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

    public Map<String, Integer> getMonthlyPoints() {
        return monthlyPoints;
    }

    public void setMonthlyPoints(Map<String, Integer> monthlyPoints) {
        this.monthlyPoints = monthlyPoints;
    }

    public RewardPoints(Customer customer, Long id, int totalPoints, Map<String, Integer> monthlyPoints) {
        this.customer = customer;
        this.id = id;
        this.totalPoints = totalPoints;
        this.monthlyPoints = monthlyPoints;
    }

}

