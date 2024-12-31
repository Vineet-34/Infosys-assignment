package com.reward.transaction.repository;

import com.reward.transaction.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository interface for managing {@link Transaction} entities.
 * <p>
 * This interface extends {@link JpaRepository} to provide methods for performing CRUD (Create, Read,
 * Update, Delete) operations on {@link Transaction} entities, which represent individual customer
 * transactions. The repository supports operations to query transactions associated with a specific
 * customer based on a date range.
 */
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    /**
     * Finds a list of {@link Transaction} entities for a given customer ID and a specified date range.
     * <p>
     * This method retrieves all transactions made by a customer within the provided date range (inclusive).
     * The results are returned as a list of {@link Transaction} entities, ordered by transaction date.
     * If no transactions are found for the customer within the specified date range, an empty list is returned.
     *
     * @param customerId The unique identifier of the customer whose transactions are to be retrieved.
     * @param startDate  The start date of the time range (inclusive).
     * @param endDate    The end date of the time range (inclusive).
     * @return A list of {@link Transaction} entities associated with the provided customer ID
     * and within the specified date range. Returns an empty list if no transactions are found.
     */
    List<Transaction> findByCustomerIdAndTransactionDateBetween(String customerId, LocalDate startDate, LocalDate endDate);
}
