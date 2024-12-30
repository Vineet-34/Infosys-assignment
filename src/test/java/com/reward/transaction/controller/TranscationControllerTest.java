package com.reward.transaction.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reward.transaction.model.RewardRequest;
import com.reward.transaction.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static java.util.Collections.emptyMap;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TranscationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {

    }

    @Test
    void testCalculateRewards() throws Exception {
        List<Transaction> transactions = Arrays.asList(
                new Transaction("VINEET01", LocalDate.of(2024, 12, 15), 120),
                new Transaction("VINEET01", LocalDate.of(2024, 12, 25), 80),
                new Transaction("VINEET01", LocalDate.of(2024, 11, 15), 130),
                new Transaction("VINEET01", LocalDate.of(2024, 10, 5), 55)
        );

        // Create the request body object
        RewardRequest rewardRequest = new RewardRequest(
                "VINEET01",
                "2024-10-01",
                "2024-12-31",
                transactions

        );

        // Convert the request object to JSON
        String requestBody = objectMapper.writeValueAsString(rewardRequest);

        // Send the POST request with query parameters startDate and endDate
        mockMvc.perform(post("/calculate?startDate=2024-10-01&endDate=2024-12-31")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId", is("VINEET01")))
                .andExpect(jsonPath("$.totalPoints", is(235)))
                .andExpect(jsonPath("$.monthlyPoints['2024-12']", is(120)))
                .andExpect(jsonPath("$.monthlyPoints['2024-11']", is(110)))
                .andExpect(jsonPath("$.monthlyPoints['2024-10']", is(5)));
    }

    @Test
    void testCalculateRewards_withEmptyTransactions() throws Exception {
        List<Transaction> transactions = Arrays.asList();

        RewardRequest rewardRequest = new RewardRequest(
                "VINEET01",
                "2024-10-01",
                "2024-12-31",
                transactions
        );

        String requestBody = objectMapper.writeValueAsString(rewardRequest);

        mockMvc.perform(post("/calculate?startDate=2024-10-01&endDate=2024-12-31")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId", is("VINEET01")))
                .andExpect(jsonPath("$.totalPoints", is(0)))
                .andExpect(jsonPath("$.monthlyPoints", is(emptyMap())));
    }

    @Test
    void testCalculateRewards_withInvalidDateRange() throws Exception {
        List<Transaction> transactions = Arrays.asList(
                new Transaction("VINEET01", LocalDate.of(2024, 12, 15), 120),
                new Transaction("VINEET01", LocalDate.of(2024, 12, 25), 80)
        );

        RewardRequest rewardRequest = new RewardRequest(
                "VINEET01",
                "2024-10-01",
                "2024-12-31",
                transactions
        );

        String requestBody = objectMapper.writeValueAsString(rewardRequest);

        mockMvc.perform(post("/calculate?startDate=2025-01-01&endDate=2025-01-31")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId", is("VINEET01")))
                .andExpect(jsonPath("$.totalPoints", is(0)))
                .andExpect(jsonPath("$.monthlyPoints", is(emptyMap())));
    }
}