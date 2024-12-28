package com.reward.transaction.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class Customer {
    private String customerId;
    private Map<String, Integer> monthlyPoints;
    private int totalPoints;
}
