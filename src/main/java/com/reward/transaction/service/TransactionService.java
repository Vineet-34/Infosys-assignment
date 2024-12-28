package com.reward.transaction.service;

import com.reward.transaction.exception.InvalidTransactionException;
import com.reward.transaction.exception.TransactionNotFoundException;
import com.reward.transaction.model.Customer;
import com.reward.transaction.model.Transaction;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TransactionService {

    public Customer calculatePoints(List<Transaction> transactions) {
        if (transactions == null || transactions.isEmpty()) {
            throw new TransactionNotFoundException("No transactions found for the customer.");
        }

        Map<String, Integer> monthlyPoints = new HashMap<>();
        int totalPoints = 0;

        for (Transaction transaction : transactions) {
            validateTransaction(transaction);

            String monthYear = transaction.getTransactionDate().format(DateTimeFormatter.ofPattern("yyyy-MM"));

            int points = calculateTransactionPoints(transaction.getAmount());

            monthlyPoints.put(monthYear, monthlyPoints.getOrDefault(monthYear, 0) + points);
            totalPoints += points;
        }

        String customerId = transactions.getFirst().getCustomerId();

        return new Customer(customerId, monthlyPoints, totalPoints);
    }

    private void validateTransaction(Transaction transaction) {
        if (transaction.getAmount() <= 0) {
            throw new InvalidTransactionException("Transaction amount must be a positive value.");
        }

        if (transaction.getCustomerId() == null || transaction.getCustomerId().isEmpty()) {
            throw new InvalidTransactionException("Customer ID is required.");
        }

        if (transaction.getTransactionDate() == null) {
            throw new InvalidTransactionException("Transaction date is required.");
        }
    }

    private int calculateTransactionPoints(double amount) {
        int points = 0;

        if (amount > 100) {
            points += (int) (2 * (amount - 100));
            points += 50;
        } else if (amount > 50) {
            points += (int) (amount - 50);
        }

        return points;
    }
}
