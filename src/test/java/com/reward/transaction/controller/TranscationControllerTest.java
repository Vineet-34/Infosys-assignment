package com.reward.transaction.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reward.transaction.exception.InvalidTransactionException;
import com.reward.transaction.model.RewardResponse;
import com.reward.transaction.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TranscationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private TransactionController transactionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCalculateRewards() throws Exception {
        Long customerId = 1L;
        LocalDate startDate = LocalDate.of(2024, 10, 1);
        LocalDate endDate = LocalDate.of(2024, 12, 31);

        RewardResponse rewardResponse = new RewardResponse(customerId, 235, null);
        when(transactionService.calculatePoints(customerId, startDate, endDate)).thenReturn(rewardResponse);

        mockMvc.perform(get("/calculate/{customerId}?startDate={startDate}&endDate={endDate}", customerId, startDate, endDate)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId", is(customerId.intValue())))
                .andExpect(jsonPath("$.totalPoints", is(90)))
                .andExpect(jsonPath("$.monthlyPoints[0].year", is(2024)))
                .andExpect(jsonPath("$.monthlyPoints[0].month", is(12)))
                .andExpect(jsonPath("$.monthlyPoints[0].points", is(90)));
    }

    @Test
    @DisplayName("Test if the range provided is invalid")
    void testCalculateRewards_withInvalidDateRange() throws Exception {
        Long customerId = 1L;
        LocalDate startDate = LocalDate.of(2024, 10, 1);
        LocalDate endDate = LocalDate.of(2024, 12, 31);
        when(transactionService.calculatePoints(customerId, startDate.plusDays(1), endDate)).thenThrow(new InvalidTransactionException("Invalid date range"));

        mockMvc.perform(get("/calculate/{customerId}?startDate={startStart}&endDate={endEnd}", customerId, startDate.plusDays(1), endDate)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId", is(customerId.intValue())))
                .andExpect(jsonPath("$.totalPoints", is(90)))
                .andExpect(jsonPath("$.monthlyPoints[0].year", is(2024)))
                .andExpect(jsonPath("$.monthlyPoints[0].month", is(12)))
                .andExpect(jsonPath("$.monthlyPoints[0].points", is(90)));
    }
}