package com.reward.transaction.service;

import com.reward.transaction.exception.InvalidTransactionException;
import com.reward.transaction.exception.TransactionNotFoundException;
import com.reward.transaction.model.MonthlyPoints;
import com.reward.transaction.model.RewardResponse;
import com.reward.transaction.model.Transaction;
import com.reward.transaction.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a service to calculate reward points.
 * This service calculates reward points based on the customer's transactions.
 */
@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    /**
     * Calculates reward points for a given customer and transaction period.
     *
     * @param customerId the ID of the customer for whom the points are being calculated
     * @param startDate  the start date of the period for which the points are calculated (inclusive)
     * @param endDate    the end date of the period for which the points are calculated (inclusive)
     * @return a response containing the total points and monthly breakdown for the customer
     * @throws TransactionNotFoundException if no transactions are found for the customer in the given period
     * @throws InvalidTransactionException  if any of the transactions are invalid
     */
    public RewardResponse calculatePoints(Long customerId, LocalDate startDate, LocalDate endDate) {
        if (customerId == null) {
            throw new IllegalArgumentException("Customer ID cannot be null.");
        }
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Start date and end date cannot be null.");
        }
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date cannot be after end date.");
        }
        List<Transaction> transactions = transactionRepository.findByCustomerIdAndTransactionDateBetween(customerId, startDate, endDate);

        if (transactions == null || transactions.isEmpty()) {
            throw new TransactionNotFoundException("No transactions found for customer ID: " + customerId);
        }

        List<MonthlyPoints> monthlyPointsList = new ArrayList<>();
        int totalPoints = 0;

        for (Transaction transaction : transactions) {
            if (transaction.getId().equals(customerId) && isInRange(transaction.getTransactionDate(), startDate, endDate)) {
                int points = calculatePoints(transaction.getAmount());
                String monthKey = transaction.getTransactionDate().getYear() + "-" + String.format("%02d", transaction.getTransactionDate().getMonthValue());

                MonthlyPoints monthlyPoints = monthlyPointsList.stream()
                        .filter(mp -> mp.getYear() == transaction.getTransactionDate().getYear() && mp.getMonth() == transaction.getTransactionDate().getMonthValue())
                        .findFirst()
                        .orElse(null);

                if (monthlyPoints == null) {
                    monthlyPoints = new MonthlyPoints(transaction.getTransactionDate().getYear(), transaction.getTransactionDate().getMonthValue(), points);
                    monthlyPointsList.add(monthlyPoints);
                } else {
                    monthlyPoints.setPoints(monthlyPoints.getPoints() + points);
                }

                totalPoints += points;
            } else {
                throw new InvalidTransactionException("Transaction " + transaction.getId() + " for customer ID " + customerId + " is invalid.");
            }
        }

        return new RewardResponse(customerId, totalPoints, monthlyPointsList); // Return the list of MonthlyPoints in the response
    }

    /**
     * Checks the date provided is in range.
     *
     * @param transactionDate the data of the transaction
     * @param startDate       the start date of the period for which the points are calculated (inclusive)
     * @param endDate         the end date of the period for which the points are calculated (inclusive)
     * @return a response containing boolean
     **/
    private boolean isInRange(LocalDate transactionDate, LocalDate startDate, LocalDate endDate) {
        return (transactionDate.isEqual(startDate) || transactionDate.isAfter(startDate)) &&
                (transactionDate.isEqual(endDate) || transactionDate.isBefore(endDate));
    }

    /**
     * Checks the date provided is in range.
     *
     * @param amount It takes the amount and calculate the reward based on the amount.
     * @return a response containing integer
     **/
    int calculatePoints(double amount) {
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