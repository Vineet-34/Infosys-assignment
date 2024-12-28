package com.reward.transaction;

import com.reward.transaction.model.Transaction;
import com.reward.transaction.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TransactionServiceTest {

    @InjectMocks
    private TransactionService rewardService;

    public TransactionServiceTest() {

        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCalculatePoints() {
        List<Transaction> transactions = Arrays.asList(
                new Transaction("VINEET01", LocalDate.of(2024, 12, 15), 120),
                new Transaction("VINEET01", LocalDate.of(2024, 12, 25), 80)
        );

        var result = rewardService.calculatePoints(transactions);

        assertNotNull(result);
        assertEquals(120, result.getMonthlyPoints().get("2024-12"));
        assertEquals(120, result.getTotalPoints());
    }
}
