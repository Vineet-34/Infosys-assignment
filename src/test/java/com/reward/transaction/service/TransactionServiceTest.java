package com.reward.transaction.service;

import com.reward.transaction.exception.InvalidTransactionException;
import com.reward.transaction.exception.TransactionNotFoundException;
import com.reward.transaction.model.MonthlyPoints;
import com.reward.transaction.model.RewardPoints;
import com.reward.transaction.model.RewardResponse;
import com.reward.transaction.model.Transaction;
import com.reward.transaction.repository.RewardPointsRepository;
import com.reward.transaction.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private RewardPointsRepository rewardPointsRepository;

    @InjectMocks
    private TransactionService transactionService;

    private Transaction transaction1;
    private Transaction transaction2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        transaction1 = new Transaction("customer123", 120, LocalDate.of(2024, 1, 15));
        transaction2 = new Transaction("customer123", 80, LocalDate.of(2024, 2, 10));
        // transaction2 = new Transaction(2l,120L, 123.3,LocalDate.of(2024, 1, 10));
    }

    @Test
    @DisplayName("Test calculate points for valid transactions")
    void testCalculatePoints_validTransactions() {
        Transaction transaction1 = new Transaction();
        transaction1.setCustomerId("customer123");
        transaction1.setAmount(90);
        transaction1.setTransactionDate(LocalDate.of(2024, 1, 15));

        Transaction transaction2 = new Transaction();
        transaction2.setCustomerId("customer123");
        transaction2.setAmount(80);
        transaction2.setTransactionDate(LocalDate.of(2024, 2, 20));

        List<Transaction> transactions = Arrays.asList(transaction1, transaction2);
        when(transactionRepository.findByCustomerIdAndTransactionDateBetween("customer123", LocalDate.of(2024, 1, 1), LocalDate.of(2024, 3, 31)))
                .thenReturn(transactions);
        when(rewardPointsRepository.findByCustomerId("customer123")).thenReturn(null);

        RewardResponse rewardResponse = transactionService.calculatePoints("customer123", "2024-01-01", "2024-03-31");

        assertNotNull(rewardResponse);
        assertEquals("customer123", rewardResponse.getCustomerId());
        assertEquals(170, rewardResponse.getTotalPoints());

        List<MonthlyPoints> monthlyPoints = rewardResponse.getMonthlyPoints();

        assertEquals(2, monthlyPoints.size());

        MonthlyPoints januaryPoints = monthlyPoints.stream()
                .filter(mp -> mp.getYear() == 2024 && mp.getMonth() == 1)
                .findFirst()
                .orElse(null);
        assertNotNull(januaryPoints);
        assertEquals(90, januaryPoints.getPoints());

        MonthlyPoints februaryPoints = monthlyPoints.stream()
                .filter(mp -> mp.getYear() == 2024 && mp.getMonth() == 2)
                .findFirst()
                .orElse(null);
        assertNotNull(februaryPoints);
        assertEquals(80, februaryPoints.getPoints());
    }

    @Test
    @DisplayName("Test calculate points when no transactions are found")
    void testCalculatePoints_noTransactionsFound() {
        when(transactionRepository.findByCustomerIdAndTransactionDateBetween("customer123", LocalDate.of(2024, 1, 1), LocalDate.of(2024, 3, 31)))
                .thenReturn(null);

        TransactionNotFoundException exception = assertThrows(TransactionNotFoundException.class, () -> {
            transactionService.calculatePoints("customer123", "2024-01-01", "2024-03-31");
        });

        assertEquals("No transactions found for customer ID: customer123", exception.getMessage());
    }

    @Test
    @DisplayName("Test calculate points for invalid transaction")
    void testCalculatePoints_invalidTransaction() {
        Transaction invalidTransaction = new Transaction();
        invalidTransaction.setCustomerId("someOtherCustomer");
        invalidTransaction.setAmount(30);
        invalidTransaction.setTransactionDate(LocalDate.of(2024, 1, 15));

        List<Transaction> transactions = Arrays.asList(invalidTransaction);
        when(transactionRepository.findByCustomerIdAndTransactionDateBetween("customer123", LocalDate.of(2024, 1, 1), LocalDate.of(2024, 3, 31)))
                .thenReturn(transactions);

        InvalidTransactionException exception = assertThrows(InvalidTransactionException.class, () -> {
            transactionService.calculatePoints("customer123", "2024-01-01", "2024-03-31");
        });

        assertEquals("Transaction " + invalidTransaction.getCustomerId() + " for customer ID customer123 is invalid.", exception.getMessage());
    }


    @Test
    @DisplayName("Save Reward Points and verify repository interaction")
    void testSaveRewardPoints() {
        Transaction transaction1 = new Transaction("customer123", 100, LocalDate.of(2024, 1, 15)); // 100 points
        Transaction transaction2 = new Transaction("customer123", 120, LocalDate.of(2024, 2, 20)); // 120 points

        List<Transaction> transactions = Arrays.asList(transaction1, transaction2);

        when(transactionRepository.findByCustomerIdAndTransactionDateBetween("customer123", LocalDate.of(2024, 1, 1), LocalDate.of(2024, 3, 31)))
                .thenReturn(transactions);
        when(rewardPointsRepository.findByCustomerId("customer123")).thenReturn(null);

        RewardPoints rewardPoints = new RewardPoints();
        rewardPoints.setId(123L);
        rewardPoints.setTotalPoints(220);
        when(rewardPointsRepository.save(Mockito.any(RewardPoints.class))).thenReturn(rewardPoints);

        RewardResponse rewardResponse = transactionService.calculatePoints("customer123", "2024-01-01", "2024-03-31");

        verify(rewardPointsRepository, times(1)).save(Mockito.any(RewardPoints.class));

        assertNotNull(rewardResponse);
        assertEquals("customer123", rewardResponse.getCustomerId());
        assertEquals(220, rewardResponse.getTotalPoints());
    }

    @Test
    @DisplayName("Edge case Test if the customer spend $50 dollar")
    void testCalculatePoints_forFiftyDollarTransaction() {
        double transactionAmount = 50.0;
        int expectedPoints = 50;
        int actualPoints = transactionService.calculatePoints(transactionAmount);
        assertEquals(expectedPoints, actualPoints, "Points calculation for $50 transaction is incorrect");
    }
}

