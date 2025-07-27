# Money Data API Testing Guide

## Overview
This document contains comprehensive testing scenarios and cURL commands for the Money Data API endpoints.

## Base Configuration
- **Base URL**: `http://localhost:8080/api/v1/admin/financial-transactions`
- **Authorization**: Bearer token required for all endpoints
- **Content-Type**: `application/json`

## Authorization Token
```bash
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJrYXJtYW55YXNpbmdoOEBnbWFpbC5jb20iLCJpYXQiOjE3NTM2NDQ2MTUsImV4cCI6MTc1MzczMTAxNX0.A-j0bgVpY7AbR8TpUxIWemaM9ghXkzpJeKXl01ap3mc
```

---

## 1. Health Check Endpoint

### Test API Availability
```bash
curl -X GET "http://localhost:8080/api/v1/admin/financial-transactions/health" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJrYXJtYW55YXNpbmdoOEBnbWFpbC5jb20iLCJpYXQiOjE3NTM2NDQ2MTUsImV4cCI6MTc1MzczMTAxNX0.A-j0bgVpY7AbR8TpUxIWemaM9ghXkzpJeKXl01ap3mc"
```

**Expected Response:**
```json
"Financial Transaction API is running - [timestamp]"
```

---

## 2. Money Data Dashboard Endpoint

### Get Dashboard Summary
```bash
curl -X GET "http://localhost:8080/api/v1/admin/financial-transactions/money-data" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJrYXJtYW55YXNpbmdoOEBnbWFpbC5jb20iLCJpYXQiOjE3NTM2NDQ2MTUsImV4cCI6MTc1MzczMTAxNX0.A-j0bgVpY7AbR8TpUxIWemaM9ghXkzpJeKXl01ap3mc"
```

**Expected Response (Empty Database):**
```json
{
  "amountToBeCollected": 0.00,
  "receivedToday": 0.00,
  "spentToday": 0.00,
  "netProfit": 0.00,
  "recentTransactions": []
}
```

**Expected Response (With Data):**
```json
{
  "amountToBeCollected": 5000.00,
  "receivedToday": 2500.00,
  "spentToday": 1000.00,
  "netProfit": 1500.00,
  "recentTransactions": [
    {
      "id": "uuid-here",
      "amount": 2500.00,
      "transactionType": "INCOME",
      "transactionDate": "2025-01-27T10:30:00",
      "description": "Service payment",
      "status": "COMPLETED",
      "customer": {
        "id": "customer-uuid",
        "name": "John Doe",
        "phone": "9876543210"
      },
      "vehicle": {
        "id": "vehicle-uuid",
        "registrationNumber": "MH12AB1234",
        "make": "Honda",
        "model": "City"
      },
      "paymentMethod": "CASH",
      "referenceNumber": "REF001",
      "createdAt": "2025-01-27T10:30:00",
      "createdBy": {
        "id": "staff-uuid",
        "name": "Mechanic Name",
        "role": "MECHANIC"
      }
    }
  ]
}
```

---

## 3. Create Expense Transaction

### Basic Expense Transaction
```bash
curl -X POST "http://localhost:8080/api/v1/admin/financial-transactions/expense" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJrYXJtYW55YXNpbmdoOEBnbWFpbC5jb20iLCJpYXQiOjE3NTM2NDQ2MTUsImV4cCI6MTc1MzczMTAxNX0.A-j0bgVpY7AbR8TpUxIWemaM9ghXkzpJeKXl01ap3mc" \
  -d '{
    "amount": 1000.00,
    "description": "Purchase of engine oil and filters",
    "expenseCategory": "SUPPLIES",
    "vendorName": "Auto Parts Store",
    "vendorContact": "9876543210",
    "referenceNumber": "EXP001",
    "notes": "Monthly supplies purchase"
  }'
```

### Different Expense Categories
```bash
# Utilities Expense
curl -X POST "http://localhost:8080/api/v1/admin/financial-transactions/expense" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJrYXJtYW55YXNpbmdoOEBnbWFpbC5jb20iLCJpYXQiOjE3NTM2NDQ2MTUsImV4cCI6MTc1MzczMTAxNX0.A-j0bgVpY7AbR8TpUxIWemaM9ghXkzpJeKXl01ap3mc" \
  -d '{
    "amount": 500.00,
    "description": "Electricity bill payment",
    "expenseCategory": "UTILITIES",
    "vendorName": "Electricity Board",
    "referenceNumber": "EXP002"
  }'

# Equipment Expense
curl -X POST "http://localhost:8080/api/v1/admin/financial-transactions/expense" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJrYXJtYW55YXNpbmdoOEBnbWFpbC5jb20iLCJpYXQiOjE3NTM2NDQ2MTUsImV4cCI6MTc1MzczMTAxNX0.A-j0bgVpY7AbR8TpUxIWemaM9ghXkzpJeKXl01ap3mc" \
  -d '{
    "amount": 5000.00,
    "description": "Purchase of diagnostic tool",
    "expenseCategory": "EQUIPMENT",
    "vendorName": "Tool Supplier",
    "referenceNumber": "EXP003"
  }'
```

**Valid Expense Categories:**
- `UTILITIES`
- `SALARY`
- `EQUIPMENT`
- `SUPPLIES`
- `RENT`
- `INSURANCE`
- `MAINTENANCE`
- `MARKETING`
- `OFFICE_SUPPLIES`
- `OTHER`

---

## 4. Create Income Transaction

### Basic Income Transaction
```bash
curl -X POST "http://localhost:8080/api/v1/admin/financial-transactions/income" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJrYXJtYW55YXNpbmdoOEBnbWFpbC5jb20iLCJpYXQiOjE3NTM2NDQ2MTUsImV4cCI6MTc1MzczMTAxNX0.A-j0bgVpY7AbR8TpUxIWemaM9ghXkzpJeKXl01ap3mc" \
  -d '{
    "amount": 2500.00,
    "description": "Engine oil change and service",
    "customerId": "550e8400-e29b-41d4-a716-446655440000",
    "vehicleId": "660e8400-e29b-41d4-a716-446655440001",
    "jobCardId": "770e8400-e29b-41d4-a716-446655440002",
    "paymentMethod": "CASH",
    "referenceNumber": "INC001"
  }'
```

### Different Payment Methods
```bash
# UPI Payment
curl -X POST "http://localhost:8080/api/v1/admin/financial-transactions/income" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJrYXJtYW55YXNpbmdoOEBnbWFpbC5jb20iLCJpYXQiOjE3NTM2NDQ2MTUsImV4cCI6MTc1MzczMTAxNX0.A-j0bgVpY7AbR8TpUxIWemaM9ghXkzpJeKXl01ap3mc" \
  -d '{
    "amount": 1500.00,
    "description": "Brake pad replacement",
    "customerId": "550e8400-e29b-41d4-a716-446655440000",
    "paymentMethod": "UPI",
    "referenceNumber": "INC002"
  }'

# Card Payment
curl -X POST "http://localhost:8080/api/v1/admin/financial-transactions/income" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJrYXJtYW55YXNpbmdoOEBnbWFpbC5jb20iLCJpYXQiOjE3NTM2NDQ2MTUsImV4cCI6MTc1MzczMTAxNX0.A-j0bgVpY7AbR8TpUxIWemaM9ghXkzpJeKXl01ap3mc" \
  -d '{
    "amount": 3000.00,
    "description": "Full car service",
    "customerId": "550e8400-e29b-41d4-a716-446655440000",
    "paymentMethod": "CARD",
    "referenceNumber": "INC003"
  }'
```

**Valid Payment Methods:**
- `CASH`
- `CARD`
- `UPI`
- `BANK_TRANSFER`
- `CHEQUE`

---

## 5. Get Transactions (Filtered & Paginated)

### Get All Transactions
```bash
curl -X GET "http://localhost:8080/api/v1/admin/financial-transactions" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJrYXJtYW55YXNpbmdoOEBnbWFpbC5jb20iLCJpYXQiOjE3NTM2NDQ2MTUsImV4cCI6MTc1MzczMTAxNX0.A-j0bgVpY7AbR8TpUxIWemaM9ghXkzpJeKXl01ap3mc"
```

### Get Income Transactions Only
```bash
curl -X GET "http://localhost:8080/api/v1/admin/financial-transactions?transaction_type=INCOME" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJrYXJtYW55YXNpbmdoOEBnbWFpbC5jb20iLCJpYXQiOjE3NTM2NDQ2MTUsImV4cCI6MTc1MzczMTAxNX0.A-j0bgVpY7AbR8TpUxIWemaM9ghXkzpJeKXl01ap3mc"
```

### Get Expense Transactions Only
```bash
curl -X GET "http://localhost:8080/api/v1/admin/financial-transactions?transaction_type=EXPENSE" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJrYXJtYW55YXNpbmdoOEBnbWFpbC5jb20iLCJpYXQiOjE3NTM2NDQ2MTUsImV4cCI6MTc1MzczMTAxNX0.A-j0bgVpY7AbR8TpUxIWemaM9ghXkzpJeKXl01ap3mc"
```

### Get Transactions with Date Range
```bash
curl -X GET "http://localhost:8080/api/v1/admin/financial-transactions?from_date=2025-01-01&to_date=2025-01-31" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJrYXJtYW55YXNpbmdoOEBnbWFpbC5jb20iLCJpYXQiOjE3NTM2NDQ2MTUsImV4cCI6MTc1MzczMTAxNX0.A-j0bgVpY7AbR8TpUxIWemaM9ghXkzpJeKXl01ap3mc"
```

### Get Transactions with Pagination
```bash
curl -X GET "http://localhost:8080/api/v1/admin/financial-transactions?page=0&size=10" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJrYXJtYW55YXNpbmdoOEBnbWFpbC5jb20iLCJpYXQiOjE3NTM2NDQ2MTUsImV4cCI6MTc1MzczMTAxNX0.A-j0bgVpY7AbR8TpUxIWemaM9ghXkzpJeKXl01ap3mc"
```

### Combined Filters
```bash
curl -X GET "http://localhost:8080/api/v1/admin/financial-transactions?transaction_type=INCOME&from_date=2025-01-01&to_date=2025-01-31&page=0&size=5" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJrYXJtYW55YXNpbmdoOEBnbWFpbC5jb20iLCJpYXQiOjE3NTM2NDQ2MTUsImV4cCI6MTc1MzczMTAxNX0.A-j0bgVpY7AbR8TpUxIWemaM9ghXkzpJeKXl01ap3mc"
```

---

## 6. Get Today's Transactions

### Get Today's Income Transactions
```bash
curl -X GET "http://localhost:8080/api/v1/admin/financial-transactions/today?transaction_type=INCOME" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJrYXJtYW55YXNpbmdoOEBnbWFpbC5jb20iLCJpYXQiOjE3NTM2NDQ2MTUsImV4cCI6MTc1MzczMTAxNX0.A-j0bgVpY7AbR8TpUxIWemaM9ghXkzpJeKXl01ap3mc"
```

### Get Today's Expense Transactions
```bash
curl -X GET "http://localhost:8080/api/v1/admin/financial-transactions/today?transaction_type=EXPENSE" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJrYXJtYW55YXNpbmdoOEBnbWFpbC5jb20iLCJpYXQiOjE3NTM2NDQ2MTUsImV4cCI6MTc1MzczMTAxNX0.A-j0bgVpY7AbR8TpUxIWemaM9ghXkzpJeKXl01ap3mc"
```

---

## 7. Testing Scenarios

### Scenario 1: Empty Database Test
1. **Test Health Check** - Should return API status
2. **Test Money Data** - Should return all zeros and empty transactions array
3. **Test Get Transactions** - Should return empty page

### Scenario 2: Create and Verify Expense
1. **Create Expense** - Add a test expense transaction
2. **Verify Money Data** - Check if spentToday and netProfit are updated
3. **Get Today's Expenses** - Verify expense appears in today's list
4. **Get All Transactions** - Verify expense appears in filtered list

### Scenario 3: Create and Verify Income
1. **Create Income** - Add a test income transaction
2. **Verify Money Data** - Check if receivedToday and netProfit are updated
3. **Get Today's Income** - Verify income appears in today's list
4. **Get All Transactions** - Verify income appears in filtered list

### Scenario 4: Mixed Transactions
1. **Create Multiple Expenses** - Add different types of expenses
2. **Create Multiple Income** - Add different payment methods
3. **Verify Dashboard** - Check all calculations are correct
4. **Test Filtering** - Verify income/expense filters work
5. **Test Pagination** - Verify pagination works with large datasets

### Scenario 5: Date Range Testing
1. **Create Transactions on Different Dates** - Use different dates
2. **Test Date Range Filter** - Verify only transactions in range are returned
3. **Test Today's Filter** - Verify only today's transactions are returned

### Scenario 6: Error Handling
1. **Invalid Amount** - Try negative or zero amounts
2. **Invalid Category** - Try invalid expense categories
3. **Invalid Payment Method** - Try invalid payment methods
4. **Missing Required Fields** - Test validation errors
5. **Invalid UUIDs** - Test with non-existent customer/vehicle IDs

---

## 8. Expected Error Responses

### Validation Error (400 Bad Request)
```json
{
  "timestamp": "2025-01-27T10:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "errors": [
    {
      "field": "amount",
      "message": "Amount must be positive"
    }
  ]
}
```

### Authentication Error (401 Unauthorized)
```json
{
  "timestamp": "2025-01-27T10:30:00",
  "status": 401,
  "error": "Unauthorized",
  "message": "Invalid or missing authentication token"
}
```

### Server Error (500 Internal Server Error)
```json
{
  "timestamp": "2025-01-27T10:30:00",
  "status": 500,
  "error": "Internal Server Error",
  "message": "An unexpected error occurred"
}
```

---

## 9. Performance Testing

### Load Testing
```bash
# Test with multiple concurrent requests
for i in {1..10}; do
  curl -X GET "http://localhost:8080/api/v1/admin/financial-transactions/money-data" \
    -H "Content-Type: application/json" \
    -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJrYXJtYW55YXNpbmdoOEBnbWFpbC5jb20iLCJpYXQiOjE3NTM2NDQ2MTUsImV4cCI6MTc1MzczMTAxNX0.A-j0bgVpY7AbR8TpUxIWemaM9ghXkzpJeKXl01ap3mc" &
done
wait
```

### Large Dataset Testing
1. Create 100+ transactions
2. Test pagination with large datasets
3. Test filtering performance
4. Monitor response times

---

## 10. Database Verification

### Check Database Tables
```sql
-- Check transactions table
SELECT COUNT(*) FROM "Transactions";

-- Check today's transactions
SELECT * FROM "Transactions" 
WHERE CAST(transaction_date AS date) = CURRENT_DATE;

-- Check transaction types
SELECT transaction_type, COUNT(*) 
FROM "Transactions" 
GROUP BY transaction_type;
```

---

## Notes
- All timestamps are in ISO 8601 format
- Amounts are in decimal format (e.g., 1000.00)
- UUIDs are used for all entity references
- The API uses UTC timezone for all date/time operations
- Pagination is 0-based (page 0 is the first page)
- Default page size is 20 if not specified 