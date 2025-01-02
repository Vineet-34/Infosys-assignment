package com.reward.transaction.service;

import com.reward.transaction.exception.InvalidTransactionException;
import com.reward.transaction.exception.TransactionNotFoundException;
import com.reward.transaction.model.Customer;
import com.reward.transaction.model.RewardResponse;
import com.reward.transaction.model.Transaction;
import com.reward.transaction.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class TransactionServiceTest {

    @InjectMocks
    private TransactionService transactionService;

    @Mock
    private TransactionRepository transactionRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should calculate points successfully for valid transactions")
    void testCalculatePoints_Success() {
        Long customerId = 1l;
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 1, 31);

        Transaction transaction1 = new Transaction();
        transaction1.setId(1L);
        transaction1.setAmount(150.0);
        transaction1.setTransactionDate(LocalDate.of(2024, 1, 15));
        transaction1.setCustomer(new Customer(customerId));

        Transaction transaction2 = new Transaction();
        transaction2.setId(2L);
        transaction2.setAmount(75.0);
        transaction2.setTransactionDate(LocalDate.of(2024, 1, 20));
        transaction2.setCustomer(new Customer(customerId));

        when(transactionRepository.findByCustomerIdAndTransactionDateBetween(customerId, startDate, endDate))
                .thenReturn(Arrays.asList(transaction1, transaction2));

        RewardResponse response = transactionService.calculatePoints(customerId, startDate, endDate);

        assertEquals(1L, response.getCustomerId());
        assertEquals(125, response.getTotalPoints());
        assertEquals(2, response.getMonthlyPoints().size());
    }

    @Test
    @DisplayName("Should throw TransactionNotFoundException when no transactions exist")
    void testCalculatePoints_NoTransactions() {
        Long customerId = 123L;
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 1, 31);

        when(transactionRepository.findByCustomerIdAndTransactionDateBetween(customerId, startDate, endDate))
                .thenReturn(Collections.emptyList());

        Exception exception = assertThrows(TransactionNotFoundException.class, () -> {
            transactionService.calculatePoints(customerId, startDate, endDate);
        });

        assertEquals("No transactions found for customer ID: " + customerId, exception.getMessage());
    }

    @Test
    @DisplayName("Should throw InvalidTransactionException for transactions outside date range")
    void testCalculatePoints_InvalidTransaction() {
        Long customerId = 123L;
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 1, 31);

        Transaction transaction = new Transaction();
        transaction.setId(1L);
        transaction.setAmount(50.0);
        transaction.setTransactionDate(LocalDate.of(2024, 2, 1)); // Outside the date range
        transaction.setCustomer(new Customer(customerId));

        when(transactionRepository.findByCustomerIdAndTransactionDateBetween(customerId, startDate, endDate))
                .thenReturn(Arrays.asList(transaction));

        Exception exception = assertThrows(InvalidTransactionException.class, () -> {
            transactionService.calculatePoints(customerId, startDate, endDate);
        });

        assertEquals("Transaction " + transaction.getId() + " for customer ID " + customerId + " is invalid.", exception.getMessage());
    }
}

