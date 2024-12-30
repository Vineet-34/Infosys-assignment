package com.reward.transaction.service;

import com.reward.transaction.model.RewardResponse;
import com.reward.transaction.model.Transaction;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TransactionService {

    public RewardResponse calculatePoints(List<Transaction> transactions, String customerId, String startDate, String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);

        Map<String, Integer> monthlyPoints = new HashMap<>();
        int totalPoints = 0;

        for (Transaction transaction : transactions) {
            if (transaction.getCustomerId().equals(customerId) && isInRange(transaction.getTransactionDate(), start, end)) {
                int points = calculatePoints(transaction.getAmount());
                String month = transaction.getTransactionDate().getYear() + "-" + String.format("%02d", transaction.getTransactionDate().getMonthValue());
                monthlyPoints.put(month, monthlyPoints.getOrDefault(month, 0) + points);
                totalPoints += points;
            }
        }

        return new RewardResponse(customerId, totalPoints, monthlyPoints);
    }

    private boolean isInRange(LocalDate transactionDate, LocalDate startDate, LocalDate endDate) {
        return (transactionDate.isEqual(startDate) || transactionDate.isAfter(startDate)) &&
                (transactionDate.isEqual(endDate) || transactionDate.isBefore(endDate));
    }

    private int calculatePoints(double amount) {
        int points = 0;
        if (amount > 100) {
            points += (int) ((amount - 100) * 2);
            points += 50;
        } else if (amount > 50) {
            points += (int) ((amount - 50) * 1);
        }
        return points;
    }
}
