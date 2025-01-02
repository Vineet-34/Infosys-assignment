# Infosys-assignment

Infy Assignment

# Rewards Program for transaction

This is a Spring Boot application that calculates reward points for customers based on their transactions. Customers
earn points based on the amount spent in each transaction.

## Table of Contents

- Overview
- Features
- Technologies
- Setup
- Usage
- API Endpoints
- Testing
- License

## Overview

A retailer offers a rewards program to its customers, awarding points based on each recorded purchase. A customer
receives:

- 2 points for every dollar spent over $100 in each transaction.
- 1 point for every dollar spent between $50 and $100 in each transaction.

For example, a $120 purchase earns `2x$20 + 1x$50 = 90 points`.

## Features

- Calculate reward points for each transaction.
- Calculate monthly reward points for each customer.
- Calculate total reward points for each customer over a three-month period.
- RESTful API to retrieve reward points.

## Technologies

- Java 23
- Spring Boot
- JUnit 5
- Mysql
- Mockito

## Server Port

- 8080
-
## Sample Response

## API Endpoints

- http://localhost:8080/calculate/addTransaction

```
{
"transactionDate":2024-01-01
"amount": 100.0,
"customer_id": 1
}
```

## Sample output

```
{
"status": 200,
"message": "Transaction saved successfully"
}
```

- http://localhost:8080/calculate/{customerId}?startDate=2024-01-01&endDate=2024-03-31

### Sample Output

```json 
{
  "customerId": "12",
  "totalPoints": 180,
  "monthlyPoints": [
    {
      "year": 2024,
      "month": "NOVEMBER",
      "points": 90
    },
    {
      "year": 2024,
      "month": "JANUARY",
      "points": 80
    },
    {
      "year": 2024,
      "month": "FEBRUARY",
      "points": 10
    }
  ]
}
```
