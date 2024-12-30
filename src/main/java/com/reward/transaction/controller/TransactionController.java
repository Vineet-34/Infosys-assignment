package com.reward.transaction.controller;

import com.reward.transaction.model.RewardRequest;
import com.reward.transaction.model.RewardResponse;
import com.reward.transaction.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

    @Autowired
    private TransactionService rewardPointsService;

    @PostMapping("/calculate")
    public RewardResponse calculateRewards(
            @RequestParam String startDate,
            @RequestParam String endDate,
            @RequestBody RewardRequest rewardRequest) {

        return rewardPointsService.calculatePoints(
                rewardRequest.getTransactions(),
                rewardRequest.getCustomerId(),
                startDate,
                endDate
        );
    }
}
