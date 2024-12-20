
package com.java.infya.service;

import com.java.infya.exception.RewardCalculationException;
import com.java.infya.model.Transaction;
import com.java.infya.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public Map<Long, Map<String, Integer>> calculateRewards(LocalDate startDate, LocalDate endDate) {
        try {
            List<Transaction> transactions = transactionRepository.findByCustomerIdAndDateBetween(null, startDate, endDate);

            return transactions.stream()
                    .collect(Collectors.groupingBy(Transaction::getCustomerId,
                            Collectors.groupingBy(t -> t.getDate().getMonth().toString(),
                                    Collectors.summingInt(this::calculatePoints))));
        } catch (Exception e) {
            throw new RewardCalculationException("Error calculating reward points", e);
        }
    }

    private int calculatePoints(Transaction transaction) {
        double amount = transaction.getAmount();
        int points = 0;

        if (amount > 100) {
            points += (amount - 100) * 2;
            amount = 100;
        }
        if (amount > 50) {
            points += (amount - 50);
        }

        return points;
    }
}
