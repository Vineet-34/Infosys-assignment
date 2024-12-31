package com.reward.transaction.exception;

public class RewardPointsNotFoundException extends RuntimeException {
    public RewardPointsNotFoundException(String message) {
        super(message);
    }
}