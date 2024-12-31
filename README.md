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

## API Endpoints

- http://localhost:8080/calculate/vineet01?startDate=2024-01-01&endDate=2024-03-31

## Sample Response

### Sample input

```
Transaction Date	Amount     	Reward Points
2024-01-15      	$120	        90 points
2024-02-10      	$80	            80 points
2024-03-05      	$60	            10 points
```

### Sample Output

```json
{
  "customerId": "VINEET01",
  "totalPoints": 235,
  "monthlyPoints": {
    "2024-11": 110,
    "2024-10": 5,
    "2024-12": 120
  }
}
```
