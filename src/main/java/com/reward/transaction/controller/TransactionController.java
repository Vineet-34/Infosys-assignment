package com.reward.transaction.controller;

import com.reward.transaction.exception.InvalidTransactionException;
import com.reward.transaction.exception.RewardPointsNotFoundException;
import com.reward.transaction.exception.TransactionNotFoundException;
import com.reward.transaction.model.RewardResponse;
import com.reward.transaction.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST endpoint to calculate reward points for a customer within a specified time frame.
 *
 * This method handles the reward points calculation for a given customer based on their
 * transactions during the provided start and end dates. It will return the calculated
 * total reward points and monthly breakdown in the form of a {@link RewardResponse}.
 * In case of any errors, it handles exceptions and returns an appropriate response with
 * an error message.
 *
 * @PathValiable customerId The unique identifier for the customer whose rewards are being calculated.
 * @RequestParam startDate  The start date of the time frame (inclusive) in the format "yyyy-MM-dd".
 * @RequestParam endDate    The end date of the time frame (inclusive) in the format "yyyy-MM-dd".
 * @return           A {@link RewardResponse} containing the total reward points and the
 *                   monthly breakdown of the points for the customer.
 *                   The response is returned with an HTTP status code 200 (OK) on success.
 *                   If an exception occurs, it will return a bad request response with 0 points.
 * @throws TransactionNotFoundException If no transactions are found for the given customer
 *                                      within the specified date range.
 * @throws InvalidTransactionException  If a transaction is invalid and cannot be processed.
 * @throws RewardPointsNotFoundException If the reward points for the customer could not be found
 *                                       or calculated.
 */

@RestController
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/calculate")
    public RewardResponse calculateRewards(
            @PathVariable String customerId,
            @RequestParam String startDate,
            @RequestParam String endDate) {
        try {
            RewardResponse rewardResponse = transactionService.calculatePoints(customerId, startDate, endDate);
            return new ResponseEntity<>(rewardResponse, HttpStatus.OK).getBody();
        } catch (TransactionNotFoundException | InvalidTransactionException | RewardPointsNotFoundException ex) {
            return new ResponseEntity<>(new RewardResponse(customerId, 0, null), HttpStatus.BAD_REQUEST).getBody();
        }

    }
}
