# Financial Transactions API Test Sheet

## Base Configuration
- **Base URL**: `http://localhost:8080/api/v1/admin/financial-transactions`
- **Valid Token**: `Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJrYWJpcnNpbmdoQGdtYWlsLmNvbSIsImlhdCI6MTc1NDYzNjgwMiwiZXhwIjoxNzU0NzIzMjAyfQ.5DngjCRZKdzdILyxNCLdnLblwK2ttR4ghVbpUurDH5M`
- **Content-Type**: `application/json`

## API Endpoints Overview

### 1. GET `/money-data` - Dashboard Money Data
### 2. POST `/income` - Create Income Transaction
### 3. POST `/expense` - Create Expense Transaction
### 4. PATCH `/expense/{id}` - Update Expense Transaction
### 5. GET `/` - Get Transactions with Filters
### 6. GET `/today` - Get Today's Transactions
### 7. GET `/health` - Health Check
### 8. GET `/test` - Test Endpoint

---

## 1. GET `/money-data` - Dashboard Money Data

### Test Case 1.1: Get Money Data Success
**Description**: Retrieve dashboard money data successfully
**Expected Status**: 200 OK
**Request**:
```bash
curl -X GET "http://localhost:8080/api/v1/admin/financial-transactions/money-data" \
-H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJrYWJpcnNpbmdoQGdtYWlsLmNvbSIsImlhdCI6MTc1NDYzNjgwMiwiZXhwIjoxNzU0NzIzMjAyfQ.5DngjCRZKdzdILyxNCLdnLblwK2ttR4ghVbpUurDH5M"
```
**Expected Response**:
```json
{
  "amountToBeCollected": 0.00,
  "receivedToday": 0.00,
  "spentToday": 0.00,
  "netProfit": 0.00,
  "recentTransactions": []
}
```

### Test Case 1.2: Get Money Data Unauthorized
**Description**: Access without authorization
**Expected Status**: 401 Unauthorized
**Request**:
```bash
curl -X GET "http://localhost:8080/api/v1/admin/financial-transactions/money-data"
```

---

## 2. POST `/income` - Create Income Transaction

### Test Case 2.1: Create Income Transaction Success
**Description**: Create income transaction with all required fields
**Expected Status**: 201 Created
**Request**:
```bash
curl -X POST "http://localhost:8080/api/v1/admin/financial-transactions/income" \
-H "Content-Type: application/json" \
-H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJrYWJpcnNpbmdoQGdtYWlsLmNvbSIsImlhdCI6MTc1NDYzNjgwMiwiZXhwIjoxNzU0NzIzMjAyfQ.5DngjCRZKdzdILyxNCLdnLblwK2ttR4ghVbpUurDH5M" \
-d '{
  "amount": 1500.00,
  "description": "Car repair service payment",
  "customerId": "550e8400-e29b-41d4-a716-446655440000",
  "vehicleId": "550e8400-e29b-41d4-a716-446655440001",
  "jobCardId": "550e8400-e29b-41d4-a716-446655440002",
  "paymentMethod": "CARD",
  "referenceNumber": "INC001",
  "notes": "Engine oil change and filter replacement"
}'
```

### Test Case 2.2: Create Income Transaction Missing Required Fields
**Description**: Create income transaction without required fields
**Expected Status**: 400 Bad Request
**Request**:
```bash
curl -X POST "http://localhost:8080/api/v1/admin/financial-transactions/income" \
-H "Content-Type: application/json" \
-H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJrYWJpcnNpbmdoQGdtYWlsLmNvbSIsImlhdCI6MTc1NDYzNjgwMiwiZXhwIjoxNzU0NzIzMjAyfQ.5DngjCRZKdzdILyxNCLdnLblwK2ttR4ghVbpUurDH5M" \
-d '{
  "amount": 1500.00,
  "description": "Car repair service payment"
}'
```

### Test Case 2.3: Create Income Transaction Invalid Amount
**Description**: Create income transaction with invalid amount
**Expected Status**: 400 Bad Request
**Request**:
```bash
curl -X POST "http://localhost:8080/api/v1/admin/financial-transactions/income" \
-H "Content-Type: application/json" \
-H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJrYWJpcnNpbmdoQGdtYWlsLmNvbSIsImlhdCI6MTc1NDYzNjgwMiwiZXhwIjoxNzU0NzIzMjAyfQ.5DngjCRZKdzdILyxNCLdnLblwK2ttR4ghVbpUurDH5M" \
-d '{
  "amount": -100.00,
  "description": "Car repair service payment",
  "customerId": "550e8400-e29b-41d4-a716-446655440000",
  "paymentMethod": "CARD"
}'
```

### Test Case 2.4: Create Income Transaction Invalid Payment Method
**Description**: Create income transaction with invalid payment method
**Expected Status**: 400 Bad Request
**Request**:
```bash
curl -X POST "http://localhost:8080/api/v1/admin/financial-transactions/income" \
-H "Content-Type: application/json" \
-H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJrYWJpcnNpbmdoQGdtYWlsLmNvbSIsImlhdCI6MTc1NDYzNjgwMiwiZXhwIjoxNzU0NzIzMjAyfQ.5DngjCRZKdzdILyxNCLdnLblwK2ttR4ghVbpUurDH5M" \
-d '{
  "amount": 1500.00,
  "description": "Car repair service payment",
  "customerId": "550e8400-e29b-41d4-a716-446655440000",
  "paymentMethod": "INVALID_METHOD"
}'
```

### Test Case 2.5: Create Income Transaction Customer Not Found
**Description**: Create income transaction with non-existent customer
**Expected Status**: 400 Bad Request
**Request**:
```bash
curl -X POST "http://localhost:8080/api/v1/admin/financial-transactions/income" \
-H "Content-Type: application/json" \
-H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJrYWJpcnNpbmdoQGdtYWlsLmNvbSIsImlhdCI6MTc1NDYzNjgwMiwiZXhwIjoxNzU0NzIzMjAyfQ.5DngjCRZKdzdILyxNCLdnLblwK2ttR4ghVbpUurDH5M" \
-d '{
  "amount": 1500.00,
  "description": "Car repair service payment",
  "customerId": "00000000-0000-0000-0000-000000000000",
  "paymentMethod": "CARD"
}'
```

### Test Case 2.6: Create Income Transaction Field Length Validation
**Description**: Test field length validations
**Expected Status**: 400 Bad Request
**Request**:
```bash
curl -X POST "http://localhost:8080/api/v1/admin/financial-transactions/income" \
-H "Content-Type: application/json" \
-H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJrYWJpcnNpbmdoQGdtYWlsLmNvbSIsImlhdCI6MTc1NDYzNjgwMiwiZXhwIjoxNzU0NzIzMjAyfQ.5DngjCRZKdzdILyxNCLdnLblwK2ttR4ghVbpUurDH5M" \
-d '{
  "amount": 1500.00,
  "description": "This is a very long description that exceeds the maximum allowed length of 255 characters. This should cause a validation error because it is too long and exceeds the limit that was set in the CreateIncomeTransactionRequest DTO. The system should reject this request and return a 400 Bad Request status code.",
  "customerId": "550e8400-e29b-41d4-a716-446655440000",
  "paymentMethod": "CARD",
  "referenceNumber": "This reference number is also too long and should exceed the 50 character limit",
  "notes": "These notes are also too long and should exceed the 500 character limit"
}'
```

---

## 3. POST `/expense` - Create Expense Transaction

### Test Case 3.1: Create Expense Transaction Success
**Description**: Create expense transaction with all required fields
**Expected Status**: 201 Created
**Request**:
```bash
curl -X POST "http://localhost:8080/api/v1/admin/financial-transactions/expense" \
-H "Content-Type: application/json" \
-H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJrYWJpcnNpbmdoQGdtYWlsLmNvbSIsImlhdCI6MTc1NDYzNjgwMiwiZXhwIjoxNzU0NzIzMjAyfQ.5DngjCRZKdzdILyxNCLdnLblwK2ttR4ghVbpUurDH5M" \
-d '{
  "amount": 500.00,
  "description": "Purchase of engine oil and filters",
  "expenseCategory": "SUPPLIES",
  "vendorName": "Auto Parts Store",
  "vendorContact": "9876543210",
  "referenceNumber": "EXP001",
  "notes": "Monthly supplies purchase"
}'
```

### Test Case 3.2: Create Expense Transaction Missing Required Fields
**Description**: Create expense transaction without required fields
**Expected Status**: 400 Bad Request
**Request**:
```bash
curl -X POST "http://localhost:8080/api/v1/admin/financial-transactions/expense" \
-H "Content-Type: application/json" \
-H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJrYWJpcnNpbmdoQGdtYWlsLmNvbSIsImlhdCI6MTc1NDYzNjgwMiwiZXhwIjoxNzU0NzIzMjAyfQ.5DngjCRZKdzdILyxNCLdnLblwK2ttR4ghVbpUurDH5M" \
-d '{
  "amount": 500.00,
  "description": "Purchase of engine oil and filters"
}'
```

### Test Case 3.3: Create Expense Transaction Invalid Expense Category
**Description**: Create expense transaction with invalid expense category
**Expected Status**: 400 Bad Request
**Request**:
```bash
curl -X POST "http://localhost:8080/api/v1/admin/financial-transactions/expense" \
-H "Content-Type: application/json" \
-H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJrYWJpcnNpbmdoQGdtYWlsLmNvbSIsImlhdCI6MTc1NDYzNjgwMiwiZXhwIjoxNzU0NzIzMjAyfQ.5DngjCRZKdzdILyxNCLdnLblwK2ttR4ghVbpUurDH5M" \
-d '{
  "amount": 500.00,
  "description": "Purchase of engine oil and filters",
  "expenseCategory": "INVALID_CATEGORY"
}'
```

### Test Case 3.4: Create Expense Transaction All Expense Categories
**Description**: Test all valid expense categories
**Expected Status**: 201 Created
**Request**:
```bash
# Test each expense category
curl -X POST "http://localhost:8080/api/v1/admin/financial-transactions/expense" \
-H "Content-Type: application/json" \
-H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJrYWJpcnNpbmdoQGdtYWlsLmNvbSIsImlhdCI6MTc1NDYzNjgwMiwiZXhwIjoxNzU0NzIzMjAyfQ.5DngjCRZKdzdILyxNCLdnLblwK2ttR4ghVbpUurDH5M" \
-d '{
  "amount": 100.00,
  "description": "Electricity bill",
  "expenseCategory": "UTILITIES",
  "vendorName": "Power Company",
  "vendorContact": "1234567890",
  "referenceNumber": "EXP002"
}'
```

---

## 4. PATCH `/expense/{id}` - Update Expense Transaction

### Test Case 4.1: Update Expense Transaction Success
**Description**: Update existing expense transaction
**Expected Status**: 200 OK
**Request**:
```bash
curl -X PATCH "http://localhost:8080/api/v1/admin/financial-transactions/expense/550e8400-e29b-41d4-a716-446655440003" \
-H "Content-Type: application/json" \
-H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJrYWJpcnNpbmdoQGdtYWlsLmNvbSIsImlhdCI6MTc1NDYzNjgwMiwiZXhwIjoxNzU0NzIzMjAyfQ.5DngjCRZKdzdILyxNCLdnLblwK2ttR4ghVbpUurDH5M" \
-d '{
  "amount": 750.00,
  "description": "Updated: Purchase of engine oil and filters",
  "expenseCategory": "SUPPLIES",
  "vendorName": "Updated Auto Parts Store",
  "vendorContact": "9876543210",
  "referenceNumber": "EXP001-UPDATED",
  "notes": "Updated monthly supplies purchase"
}'
```

### Test Case 4.2: Update Non-Existent Transaction
**Description**: Update transaction that doesn't exist
**Expected Status**: 400 Bad Request
**Request**:
```bash
curl -X PATCH "http://localhost:8080/api/v1/admin/financial-transactions/expense/00000000-0000-0000-0000-000000000000" \
-H "Content-Type: application/json" \
-H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJrYWJpcnNpbmdoQGdtYWlsLmNvbSIsImlhdCI6MTc1NDYzNjgwMiwiZXhwIjoxNzU0NzIzMjAyfQ.5DngjCRZKdzdILyxNCLdnLblwK2ttR4ghVbpUurDH5M" \
-d '{
  "amount": 750.00,
  "description": "Updated description",
  "expenseCategory": "SUPPLIES"
}'
```

### Test Case 4.3: Update Income Transaction as Expense
**Description**: Try to update income transaction using expense endpoint
**Expected Status**: 400 Bad Request
**Request**:
```bash
curl -X PATCH "http://localhost:8080/api/v1/admin/financial-transactions/expense/550e8400-e29b-41d4-a716-446655440004" \
-H "Content-Type: application/json" \
-H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJrYWJpcnNpbmdoQGdtYWlsLmNvbSIsImlhdCI6MTc1NDYzNjgwMiwiZXhwIjoxNzU0NzIzMjAyfQ.5DngjCRZKdzdILyxNCLdnLblwK2ttR4ghVbpUurDH5M" \
-d '{
  "amount": 750.00,
  "description": "Updated description",
  "expenseCategory": "SUPPLIES"
}'
```

---

## 5. GET `/` - Get Transactions with Filters

### Test Case 5.1: Get All Transactions
**Description**: Get all transactions with default pagination
**Expected Status**: 200 OK
**Request**:
```bash
curl -X GET "http://localhost:8080/api/v1/admin/financial-transactions" \
-H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJrYWJpcnNpbmdoQGdtYWlsLmNvbSIsImlhdCI6MTc1NDYzNjgwMiwiZXhwIjoxNzU0NzIzMjAyfQ.5DngjCRZKdzdILyxNCLdnLblwK2ttR4ghVbpUurDH5M"
```

### Test Case 5.2: Filter by Transaction Type
**Description**: Get transactions filtered by type
**Expected Status**: 200 OK
**Request**:
```bash
curl -X GET "http://localhost:8080/api/v1/admin/financial-transactions?transactionType=INCOME" \
-H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJrYWJpcnNpbmdoQGdtYWlsLmNvbSIsImlhdCI6MTc1NDYzNjgwMiwiZXhwIjoxNzU0NzIzMjAyfQ.5DngjCRZKdzdILyxNCLdnLblwK2ttR4ghVbpUurDH5M"
```

### Test Case 5.3: Filter by Date Range
**Description**: Get transactions within date range
**Expected Status**: 200 OK
**Request**:
```bash
curl -X GET "http://localhost:8080/api/v1/admin/financial-transactions?fromDate=2025-01-01&toDate=2025-01-31" \
-H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJrYWJpcnNpbmdoQGdtYWlsLmNvbSIsImlhdCI6MTc1NDYzNjgwMiwiZXhwIjoxNzU0NzIzMjAyfQ.5DngjCRZKdzdILyxNCLdnLblwK2ttR4ghVbpUurDH5M"
```

### Test Case 5.4: Filter by Payment Method
**Description**: Get transactions by payment method
**Expected Status**: 200 OK
**Request**:
```bash
curl -X GET "http://localhost:8080/api/v1/admin/financial-transactions?paymentMethod=CARD" \
-H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJrYWJpcnNpbmdoQGdtYWlsLmNvbSIsImlhdCI6MTc1NDYzNjgwMiwiZXhwIjoxNzU0NzIzMjAyfQ.5DngjCRZKdzdILyxNCLdnLblwK2ttR4ghVbpUurDH5M"
```

### Test Case 5.5: Filter by Transaction Status
**Description**: Get transactions by status
**Expected Status**: 200 OK
**Request**:
```bash
curl -X GET "http://localhost:8080/api/v1/admin/financial-transactions?transactionStatus=COMPLETED" \
-H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJrYWJpcnNpbmdoQGdtYWlsLmNvbSIsImlhdCI6MTc1NDYzNjgwMiwiZXhwIjoxNzU0NzIzMjAyfQ.5DngjCRZKdzdILyxNCLdnLblwK2ttR4ghVbpUurDH5M"
```

### Test Case 5.6: Filter by Expense Category
**Description**: Get expense transactions by category
**Expected Status**: 200 OK
**Request**:
```bash
curl -X GET "http://localhost:8080/api/v1/admin/financial-transactions?transactionType=EXPENSE&expenseCategory=SUPPLIES" \
-H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJrYWJpcnNpbmdoQGdtYWlsLmNvbSIsImlhdCI6MTc1NDYzNjgwMiwiZXhwIjoxNzU0NzIzMjAyfQ.5DngjCRZKdzdILyxNCLdnLblwK2ttR4ghVbpUurDH5M"
```

### Test Case 5.7: Combined Filters
**Description**: Get transactions with multiple filters
**Expected Status**: 200 OK
**Request**:
```bash
curl -X GET "http://localhost:8080/api/v1/admin/financial-transactions?transactionType=INCOME&paymentMethod=CARD&fromDate=2025-01-01&toDate=2025-01-31&page=0&size=10" \
-H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJrYWJpcnNpbmdoQGdtYWlsLmNvbSIsImlhdCI6MTc1NDYzNjgwMiwiZXhwIjoxNzU0NzIzMjAyfQ.5DngjCRZKdzdILyxNCLdnLblwK2ttR4ghVbpUurDH5M"
```

### Test Case 5.8: Invalid Filter Values
**Description**: Test with invalid filter values
**Expected Status**: 500 Internal Server Error
**Request**:
```bash
curl -X GET "http://localhost:8080/api/v1/admin/financial-transactions?expenseCategory=INVALID_CATEGORY" \
-H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJrYWJpcnNpbmdoQGdtYWlsLmNvbSIsImlhdCI6MTc1NDYzNjgwMiwiZXhwIjoxNzU0NzIzMjAyfQ.5DngjCRZKdzdILyxNCLdnLblwK2ttR4ghVbpUurDH5M"
```

### Test Case 5.9: Pagination
**Description**: Test pagination parameters
**Expected Status**: 200 OK
**Request**:
```bash
curl -X GET "http://localhost:8080/api/v1/admin/financial-transactions?page=1&size=5" \
-H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJrYWJpcnNpbmdoQGdtYWlsLmNvbSIsImlhdCI6MTc1NDYzNjgwMiwiZXhwIjoxNzU0NzIzMjAyfQ.5DngjCRZKdzdILyxNCLdnLblwK2ttR4ghVbpUurDH5M"
```

---

## 6. GET `/today` - Get Today's Transactions

### Test Case 6.1: Get Today's Income Transactions
**Description**: Get today's income transactions
**Expected Status**: 200 OK
**Request**:
```bash
curl -X GET "http://localhost:8080/api/v1/admin/financial-transactions/today?transactionType=INCOME" \
-H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJrYWJpcnNpbmdoQGdtYWlsLmNvbSIsImlhdCI6MTc1NDYzNjgwMiwiZXhwIjoxNzU0NzIzMjAyfQ.5DngjCRZKdzdILyxNCLdnLblwK2ttR4ghVbpUurDH5M"
```

### Test Case 6.2: Get Today's Expense Transactions
**Description**: Get today's expense transactions
**Expected Status**: 200 OK
**Request**:
```bash
curl -X GET "http://localhost:8080/api/v1/admin/financial-transactions/today?transactionType=EXPENSE" \
-H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJrYWJpcnNpbmdoQGdtYWlsLmNvbSIsImlhdCI6MTc1NDYzNjgwMiwiZXhwIjoxNzU0NzIzMjAyfQ.5DngjCRZKdzdILyxNCLdnLblwK2ttR4ghVbpUurDH5M"
```

### Test Case 6.3: Get Today's Transactions Missing Type
**Description**: Get today's transactions without specifying type
**Expected Status**: 200 OK
**Request**:
```bash
curl -X GET "http://localhost:8080/api/v1/admin/financial-transactions/today" \
-H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJrYWJpcnNpbmdoQGdtYWlsLmNvbSIsImlhdCI6MTc1NDYzNjgwMiwiZXhwIjoxNzU0NzIzMjAyfQ.5DngjCRZKdzdILyxNCLdnLblwK2ttR4ghVbpUurDH5M"
```

---

## 7. GET `/health` - Health Check

### Test Case 7.1: Health Check Success
**Description**: Check API health status
**Expected Status**: 200 OK
**Request**:
```bash
curl -X GET "http://localhost:8080/api/v1/admin/financial-transactions/health"
```
**Expected Response**:
```json
"Financial Transaction API is running - 1754636802000"
```

---

## 8. GET `/test` - Test Endpoint

### Test Case 8.1: Test Endpoint Success
**Description**: Test endpoint functionality
**Expected Status**: 200 OK
**Request**:
```bash
curl -X GET "http://localhost:8080/api/v1/admin/financial-transactions/test"
```
**Expected Response**:
```json
"Test endpoint working!"
```

---

## Valid Enum Values

### TransactionType
- `INCOME`
- `EXPENSE`

### TransactionStatus
- `PENDING`
- `COMPLETED`
- `CANCELLED`
- `REFUNDED`

### PaymentMethod
- `CASH`
- `CARD`
- `UPI`
- `BANK_TRANSFER`
- `CHEQUE`

### ExpenseCategory
- `UTILITIES`
- `SALARY`
- `EQUIPMENT`
- `SUPPLIES`
- `RENT`
- `INSURANCE`
- `MAINTENANCE`
- `INVENTORY`

### StaffRole
- `OWNER`
- `MANAGER`
- `MECHANIC`
- `RECEPTIONIST`

### JobCardStatus
- `PENDING`
- `IN_PROGRESS`
- `COMPLETED`
- `CANCELLED`
- `ASSIGNED`

---

## Field Validation Rules

### CreateIncomeTransactionRequest
- `amount`: Required, positive decimal (> 0.01)
- `description`: Required, max 255 characters
- `customerId`: Required, valid UUID
- `vehicleId`: Optional, valid UUID
- `jobCardId`: Optional, valid UUID
- `paymentMethod`: Required, valid enum value
- `referenceNumber`: Optional, max 50 characters
- `notes`: Optional, max 500 characters

### CreateExpenseTransactionRequest
- `amount`: Required, positive decimal (> 0.01)
- `description`: Required, max 255 characters
- `expenseCategory`: Required, valid enum value
- `vendorName`: Optional, max 100 characters
- `vendorContact`: Optional, max 50 characters
- `referenceNumber`: Optional, max 50 characters
- `notes`: Optional, max 500 characters

---

## Status Code Summary

- **200 OK**: Successful GET/PATCH operations
- **201 Created**: Successful POST operations
- **400 Bad Request**: Validation errors, invalid data, business logic errors
- **401 Unauthorized**: Missing or invalid authorization token
- **500 Internal Server Error**: Server errors, database errors, unexpected exceptions
