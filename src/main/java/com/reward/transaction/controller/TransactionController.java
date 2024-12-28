package com.reward.transaction.controller;

import com.reward.transaction.model.Customer;
import com.reward.transaction.model.Transaction;
import com.reward.transaction.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TransactionController {

    @Autowired
    private TransactionService rewardPointsService;

    @PostMapping("/calculate")
    public Customer calculateRewardPoints(@RequestBody List<Transaction> transactions) {
        return rewardPointsService.calculatePoints(transactions);
    }
}
