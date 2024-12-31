package com.reward.transaction.repository;

import com.reward.transaction.model.RewardPoints;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing {@link RewardPoints} entities.
 * <p>
 * This interface provides methods for performing CRUD (Create, Read, Update, Delete) operations
 * on the {@link RewardPoints} entity, which stores the reward points data for customers.
 * It extends {@link JpaRepository}, which provides built-in methods for interacting with the
 * underlying relational database.
 * <p>
 * The {@link RewardPointsRepository} includes custom query methods for fetching reward points
 * associated with a specific customer based on their customer ID.
 */
@Repository
public interface RewardPointsRepository extends JpaRepository<RewardPoints, Long> {
    /**
     * Finds the {@link RewardPoints} entity for a given customer ID.
     * <p>
     * This method retrieves the reward points associated with a customer, based on the customer's
     * unique identifier. It returns a {@link RewardPoints} object containing the total reward points
     * and the breakdown of the points for the customer. If no reward points record is found, it returns null.
     *
     * @param customerId The unique identifier of the customer whose reward points are to be retrieved.
     * @return The {@link RewardPoints} entity associated with the provided customer ID, or null if not found.
     */
    RewardPoints findByCustomerId(String customerId);
}
