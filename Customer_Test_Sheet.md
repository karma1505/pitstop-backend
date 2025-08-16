# Customer Management API Test Sheet

## Base Configuration

- **Base URL**: `http://localhost:8080/api/v1`
- **Content-Type**: `application/json`
- **Authentication**: JWT Bearer Token (required for all endpoints except health)

## Prerequisites

### 1. Get Authentication Token

```bash
# Register a new user first (if not already registered)
curl -X POST http://localhost:8080/api/v1/admin/register \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Test",
    "lastName": "User",
    "email": "test@example.com",
    "password": "TestPass123!",
    "confirmPassword": "TestPass123!",
    "state": "Karnataka",
    "city": "Bangalore",
    "pincode": "560001",
    "mobileNumber": "9876543210",
    "garageName": "Test Garage"
  }'

# Login to get JWT token
curl -X POST http://localhost:8080/api/v1/admin/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test@example.com",
    "password": "TestPass123!"
  }'
```

**Note**: Using the actual JWT token in all requests below.

---

## 1. HEALTH CHECK TESTS

### 1.1 Health Check (No Auth Required)

```bash
curl -X GET http://localhost:8080/api/v1/admin/customers/health
```

---

## 2. SECURITY TESTS

### 2.1 Access Without Token (Should Fail - 401)

```bash
curl -X GET http://localhost:8080/api/v1/admin/customers
```

### 2.2 Access With Invalid Token (Should Fail - 401)

```bash
curl -X GET http://localhost:8080/api/v1/admin/customers \
  -H "Authorization: Bearer invalid_token_here"
```

### 2.3 Access With Valid Token (Should Success - 200)

```bash
curl -X GET http://localhost:8080/api/v1/admin/customers \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huLmRvZUBnYXJhZ2UuY29tIiwiaWF0IjoxNzU1Mzc2OTg0LCJleHAiOjE3NTU0NjMzODR9.10fUrRlkY0VaeDR-W4SF5VWeMK4VZboKjvplRNJfQ0o"
```

---

## 3. CUSTOMER CREATION TESTS

### 3.1 Create Customer - Minimal Required Fields Only

```bash
curl -X POST http://localhost:8080/api/v1/admin/customers \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huLmRvZUBnYXJhZ2UuY29tIiwiaWF0IjoxNzU1Mzc2OTg0LCJleHAiOjE3NTU0NjMzODR9.10fUrRlkY0VaeDR-W4SF5VWeMK4VZboKjvplRNJfQ0o" \
  -d '{
    "name": "John Doe",
    "phone": "9876543210"
  }'
```

### 3.2 Create Customer - With All Optional Fields

```bash
curl -X POST http://localhost:8080/api/v1/admin/customers \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huLmRvZUBnYXJhZ2UuY29tIiwiaWF0IjoxNzU1Mzc2OTg0LCJleHAiOjE3NTU0NjMzODR9.10fUrRlkY0VaeDR-W4SF5VWeMK4VZboKjvplRNJfQ0o" \
  -d '{
    "name": "Jane Smith",
    "phone": "9876543211",
    "email": "jane@example.com",
    "addressLine1": "123 Main Street",
    "addressLine2": "Apartment 4B",
    "city": "Mumbai",
    "state": "Maharashtra"
  }'
```

### 3.3 Create Customer - With Vehicle Information

```bash
curl -X POST http://localhost:8080/api/v1/admin/customers \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huLmRvZUBnYXJhZ2UuY29tIiwiaWF0IjoxNzU1Mzc2OTg0LCJleHAiOjE3NTU0NjMzODR9.10fUrRlkY0VaeDR-W4SF5VWeMK4VZboKjvplRNJfQ0o" \
  -d '{
    "name": "Mike Johnson",
    "phone": "9876543212",
    "email": "mike@example.com",
    "addressLine1": "456 Oak Avenue",
    "city": "Delhi",
    "state": "Delhi",
    "vehicleInfo": {
      "registrationNumber": "DL01AB1234",
      "make": "Toyota",
      "model": "Camry",
      "year": 2020
    }
  }'
```

### 3.4 Create Customer - Complete Information

```bash
curl -X POST http://localhost:8080/api/v1/admin/customers \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huLmRvZUBnYXJhZ2UuY29tIiwiaWF0IjoxNzU1Mzc2OTg0LCJleHAiOjE3NTU0NjMzODR9.10fUrRlkY0VaeDR-W4SF5VWeMK4VZboKjvplRNJfQ0o" \
  -d '{
    "name": "Sarah Wilson",
    "phone": "9876543213",
    "email": "sarah@example.com",
    "addressLine1": "789 Pine Road",
    "addressLine2": "Building C, Floor 3",
    "city": "Chennai",
    "state": "Tamil Nadu",
    "vehicleInfo": {
      "registrationNumber": "TN01CD5678",
      "make": "Honda",
      "model": "Civic",
      "year": 2022
    }
  }'
```

---

## 4. VALIDATION TESTS

### 4.1 Missing Required Field - Name (Should Fail - 400)

```bash
curl -X POST http://localhost:8080/api/v1/admin/customers \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huLmRvZUBnYXJhZ2UuY29tIiwiaWF0IjoxNzU1Mzc2OTg0LCJleHAiOjE3NTU0NjMzODR9.10fUrRlkY0VaeDR-W4SF5VWeMK4VZboKjvplRNJfQ0o" \
  -d '{
    "phone": "9876543214"
  }'
```

### 4.2 Missing Required Field - Phone (Should Fail - 400)

```bash
curl -X POST http://localhost:8080/api/v1/admin/customers \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huLmRvZUBnYXJhZ2UuY29tIiwiaWF0IjoxNzU1Mzc2OTg0LCJleHAiOjE3NTU0NjMzODR9.10fUrRlkY0VaeDR-W4SF5VWeMK4VZboKjvplRNJfQ0o" \
  -d '{
    "name": "Test User"
  }'
```

### 4.3 Invalid Phone Format (Should Fail - 400)

```bash
curl -X POST http://localhost:8080/api/v1/admin/customers \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huLmRvZUBnYXJhZ2UuY29tIiwiaWF0IjoxNzU1Mzc2OTg0LCJleHAiOjE3NTU0NjMzODR9.10fUrRlkY0VaeDR-W4SF5VWeMK4VZboKjvplRNJfQ0o" \
  -d '{
    "name": "Test User",
    "phone": "123"
  }'
```

### 4.4 Invalid Email Format (Should Fail - 400)

```bash
curl -X POST http://localhost:8080/api/v1/admin/customers \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huLmRvZUBnYXJhZ2UuY29tIiwiaWF0IjoxNzU1Mzc2OTg0LCJleHAiOjE3NTU0NjMzODR9.10fUrRlkY0VaeDR-W4SF5VWeMK4VZboKjvplRNJfQ0o" \
  -d '{
    "name": "Test User",
    "phone": "9876543215",
    "email": "invalid-email"
  }'
```

### 4.5 Duplicate Phone Number (Should Fail - 400)

```bash
# First create a customer
curl -X POST http://localhost:8080/api/v1/admin/customers \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huLmRvZUBnYXJhZ2UuY29tIiwiaWF0IjoxNzU1Mzc2OTg0LCJleHAiOjE3NTU0NjMzODR9.10fUrRlkY0VaeDR-W4SF5VWeMK4VZboKjvplRNJfQ0o" \
  -d '{
    "name": "First User",
    "phone": "9876543216"
  }'

# Try to create another with same phone
curl -X POST http://localhost:8080/api/v1/admin/customers \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huLmRvZUBnYXJhZ2UuY29tIiwiaWF0IjoxNzU1Mzc2OTg0LCJleHAiOjE3NTU0NjMzODR9.10fUrRlkY0VaeDR-W4SF5VWeMK4VZboKjvplRNJfQ0o" \
  -d '{
    "name": "Second User",
    "phone": "9876543216"
  }'
```

### 4.6 Vehicle Year Out of Range (Should Fail - 400)

```bash
curl -X POST http://localhost:8080/api/v1/admin/customers \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huLmRvZUBnYXJhZ2UuY29tIiwiaWF0IjoxNzU1Mzc2OTg0LCJleHAiOjE3NTU0NjMzODR9.10fUrRlkY0VaeDR-W4SF5VWeMK4VZboKjvplRNJfQ0o" \
  -d '{
    "name": "Test User",
    "phone": "9876543217",
    "vehicleInfo": {
      "registrationNumber": "KA01EF9999",
      "make": "Toyota",
      "model": "Camry",
      "year": 1969
    }
  }'
```

### 4.7 Vehicle Year Future Invalid (Should Fail - 400)

```bash
curl -X POST http://localhost:8080/api/v1/admin/customers \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huLmRvZUBnYXJhZ2UuY29tIiwiaWF0IjoxNzU1Mzc2OTg0LCJleHAiOjE3NTU0NjMzODR9.10fUrRlkY0VaeDR-W4SF5VWeMK4VZboKjvplRNJfQ0o" \
  -d '{
    "name": "Test User",
    "phone": "9876543218",
    "vehicleInfo": {
      "registrationNumber": "KA01GH9999",
      "make": "Toyota",
      "model": "Camry",
      "year": 2051
    }
  }'
```

### 4.8 Missing Vehicle Required Fields (Should Fail - 400)

```bash
curl -X POST http://localhost:8080/api/v1/admin/customers \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huLmRvZUBnYXJhZ2UuY29tIiwiaWF0IjoxNzU1Mzc2OTg0LCJleHAiOjE3NTU0NjMzODR9.10fUrRlkY0VaeDR-W4SF5VWeMK4VZboKjvplRNJfQ0o" \
  -d '{
    "name": "Test User",
    "phone": "9876543219",
    "vehicleInfo": {
      "registrationNumber": "KA01IJ9999"
    }
  }'
```

### 4.9 Duplicate Vehicle Registration (Should Fail - 400)

```bash
# First create a customer with vehicle
curl -X POST http://localhost:8080/api/v1/admin/customers \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huLmRvZUBnYXJhZ2UuY29tIiwiaWF0IjoxNzU1Mzc2OTg0LCJleHAiOjE3NTU0NjMzODR9.10fUrRlkY0VaeDR-W4SF5VWeMK4VZboKjvplRNJfQ0o" \
  -d '{
    "name": "First Owner",
    "phone": "9876543220",
    "vehicleInfo": {
      "registrationNumber": "KA01DUPLICATE",
      "make": "Toyota",
      "model": "Camry",
      "year": 2020
    }
  }'

# Try to create another customer with same vehicle registration
curl -X POST http://localhost:8080/api/v1/admin/customers \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huLmRvZUBnYXJhZ2UuY29tIiwiaWF0IjoxNzU1Mzc2OTg0LCJleHAiOjE3NTU0NjMzODR9.10fUrRlkY0VaeDR-W4SF5VWeMK4VZboKjvplRNJfQ0o" \
  -d '{
    "name": "Second Owner",
    "phone": "9876543221",
    "vehicleInfo": {
      "registrationNumber": "KA01DUPLICATE",
      "make": "Honda",
      "model": "Civic",
      "year": 2021
    }
  }'
```

---

## 5. CUSTOMER RETRIEVAL TESTS

### 5.1 Get Customer by ID

```bash
# First create a customer and note the ID from response
curl -X POST http://localhost:8080/api/v1/admin/customers \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huLmRvZUBnYXJhZ2UuY29tIiwiaWF0IjoxNzU1Mzc2OTg0LCJleHAiOjE3NTU0NjMzODR9.10fUrRlkY0VaeDR-W4SF5VWeMK4VZboKjvplRNJfQ0o" \
  -d '{
    "name": "Get Test User",
    "phone": "9876543222"
  }'

# Then get by ID (replace CUSTOMER_ID with actual ID from above response)
curl -X GET http://localhost:8080/api/v1/admin/customers/CUSTOMER_ID \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huLmRvZUBnYXJhZ2UuY29tIiwiaWF0IjoxNzU1Mzc2OTg0LCJleHAiOjE3NTU0NjMzODR9.10fUrRlkY0VaeDR-W4SF5VWeMK4VZboKjvplRNJfQ0o"
```

### 5.2 Get Non-Existent Customer (Should Fail - 404)

```bash
curl -X GET http://localhost:8080/api/v1/admin/customers/00000000-0000-0000-0000-000000000000 \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huLmRvZUBnYXJhZ2UuY29tIiwiaWF0IjoxNzU1Mzc2OTg0LCJleHAiOjE3NTU0NjMzODR9.10fUrRlkY0VaeDR-W4SF5VWeMK4VZboKjvplRNJfQ0o"
```

### 5.3 Get All Customers - Default Pagination

```bash
curl -X GET http://localhost:8080/api/v1/admin/customers \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huLmRvZUBnYXJhZ2UuY29tIiwiaWF0IjoxNzU1Mzc2OTg0LCJleHAiOjE3NTU0NjMzODR9.10fUrRlkY0VaeDR-W4SF5VWeMK4VZboKjvplRNJfQ0o"
```

### 5.4 Get All Customers - Custom Pagination

```bash
curl -X GET "http://localhost:8080/api/v1/admin/customers?page=0&size=5" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huLmRvZUBnYXJhZ2UuY29tIiwiaWF0IjoxNzU1Mzc2OTg0LCJleHAiOjE3NTU0NjMzODR9.10fUrRlkY0VaeDR-W4SF5VWeMK4VZboKjvplRNJfQ0o"
```

### 5.5 Get All Customers - Custom Sorting

```bash
curl -X GET "http://localhost:8080/api/v1/admin/customers?sortBy=name&sortDir=asc" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huLmRvZUBnYXJhZ2UuY29tIiwiaWF0IjoxNzU1Mzc2OTg0LCJleHAiOjE3NTU0NjMzODR9.10fUrRlkY0VaeDR-W4SF5VWeMK4VZboKjvplRNJfQ0o"
```

---

## 6. FILTERING TESTS

### 6.1 Filter by Customer Name

```bash
curl -X GET "http://localhost:8080/api/v1/admin/customers?customerName=John" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huLmRvZUBnYXJhZ2UuY29tIiwiaWF0IjoxNzU1Mzc2OTg0LCJleHAiOjE3NTU0NjMzODR9.10fUrRlkY0VaeDR-W4SF5VWeMK4VZboKjvplRNJfQ0o"
```

### 6.2 Filter by Vehicle Registration Number

```bash
curl -X GET "http://localhost:8080/api/v1/admin/customers?vehicleRegistrationNumber=DL01" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huLmRvZUBnYXJhZ2UuY29tIiwiaWF0IjoxNzU1Mzc2OTg0LCJleHAiOjE3NTU0NjMzODR9.10fUrRlkY0VaeDR-W4SF5VWeMK4VZboKjvplRNJfQ0o"
```

### 6.3 Filter by Vehicle Brand

```bash
curl -X GET "http://localhost:8080/api/v1/admin/customers?vehicleBrand=Toyota" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huLmRvZUBnYXJhZ2UuY29tIiwiaWF0IjoxNzU1Mzc2OTg0LCJleHAiOjE3NTU0NjMzODR9.10fUrRlkY0VaeDR-W4SF5VWeMK4VZboKjvplRNJfQ0o"
```

### 6.4 Filter by Specific Date

```bash
curl -X GET "http://localhost:8080/api/v1/admin/customers?date=2024-01-15" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huLmRvZUBnYXJhZ2UuY29tIiwiaWF0IjoxNzU1Mzc2OTg0LCJleHAiOjE3NTU0NjMzODR9.10fUrRlkY0VaeDR-W4SF5VWeMK4VZboKjvplRNJfQ0o"
```

### 6.5 Filter by Date Range

```bash
curl -X GET "http://localhost:8080/api/v1/admin/customers?from=2024-01-01&to=2024-12-31" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huLmRvZUBnYXJhZ2UuY29tIiwiaWF0IjoxNzU1Mzc2OTg0LCJleHAiOjE3NTU0NjMzODR9.10fUrRlkY0VaeDR-W4SF5VWeMK4VZboKjvplRNJfQ0o"
```

### 6.6 Combined Filters

```bash
curl -X GET "http://localhost:8080/api/v1/admin/customers?customerName=John&vehicleBrand=Toyota" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huLmRvZUBnYXJhZ2UuY29tIiwiaWF0IjoxNzU1Mzc2OTg0LCJleHAiOjE3NTU0NjMzODR9.10fUrRlkY0VaeDR-W4SF5VWeMK4VZboKjvplRNJfQ0o"
```

### 6.7 All Filters Combined

```bash
curl -X GET "http://localhost:8080/api/v1/admin/customers?customerName=Sarah&vehicleRegistrationNumber=TN01&vehicleBrand=Honda&from=2024-01-01&to=2024-12-31&page=0&size=10&sortBy=createdAt&sortDir=desc" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huLmRvZUBnYXJhZ2UuY29tIiwiaWF0IjoxNzU1Mzc2OTg0LCJleHAiOjE3NTU0NjMzODR9.10fUrRlkY0VaeDR-W4SF5VWeMK4VZboKjvplRNJfQ0o"
```

---

## 7. CUSTOMER UPDATE TESTS

### 7.1 Update Customer - Basic Information

```bash
# First create a customer and note the ID
curl -X POST http://localhost:8080/api/v1/admin/customers \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huLmRvZUBnYXJhZ2UuY29tIiwiaWF0IjoxNzU1Mzc2OTg0LCJleHAiOjE3NTU0NjMzODR9.10fUrRlkY0VaeDR-W4SF5VWeMK4VZboKjvplRNJfQ0o" \
  -d '{
    "name": "Update Test User",
    "phone": "9876543223"
  }'

# Update the customer (replace CUSTOMER_ID with actual ID)
curl -X PATCH http://localhost:8080/api/v1/admin/customers/CUSTOMER_ID \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huLmRvZUBnYXJhZ2UuY29tIiwiaWF0IjoxNzU1Mzc2OTg0LCJleHAiOjE3NTU0NjMzODR9.10fUrRlkY0VaeDR-W4SF5VWeMK4VZboKjvplRNJfQ0o" \
  -d '{
    "name": "Updated Name",
    "phone": "9876543223",
    "email": "updated@example.com"
  }'
```

### 7.2 Update Customer - Add Address Information

```bash
# Update customer with address (replace CUSTOMER_ID with actual ID)
curl -X PATCH http://localhost:8080/api/v1/admin/customers/CUSTOMER_ID \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huLmRvZUBnYXJhZ2UuY29tIiwiaWF0IjoxNzU1Mzc2OTg0LCJleHAiOjE3NTU0NjMzODR9.10fUrRlkY0VaeDR-W4SF5VWeMK4VZboKjvplRNJfQ0o" \
  -d '{
    "name": "Updated Name",
    "phone": "9876543223",
    "email": "updated@example.com",
    "addressLine1": "New Address Line 1",
    "addressLine2": "New Address Line 2",
    "city": "New City",
    "state": "New State"
  }'
```

### 7.3 Update Customer - Add Vehicle Information

```bash
# Update customer with vehicle info (replace CUSTOMER_ID with actual ID)
curl -X PATCH http://localhost:8080/api/v1/admin/customers/CUSTOMER_ID \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huLmRvZUBnYXJhZ2UuY29tIiwiaWF0IjoxNzU1Mzc2OTg0LCJleHAiOjE3NTU0NjMzODR9.10fUrRlkY0VaeDR-W4SF5VWeMK4VZboKjvplRNJfQ0o" \
  -d '{
    "name": "Updated Name",
    "phone": "9876543223",
    "email": "updated@example.com",
    "vehicleInfo": {
      "registrationNumber": "KA01UPDATE123",
      "make": "Maruti",
      "model": "Swift",
      "year": 2023
    }
  }'
```

### 7.4 Update Non-Existent Customer (Should Fail - 404)

```bash
curl -X PATCH http://localhost:8080/api/v1/admin/customers/00000000-0000-0000-0000-000000000000 \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huLmRvZUBnYXJhZ2UuY29tIiwiaWF0IjoxNzU1Mzc2OTg0LCJleHAiOjE3NTU0NjMzODR9.10fUrRlkY0VaeDR-W4SF5VWeMK4VZboKjvplRNJfQ0o" \
  -d '{
    "name": "Non Existent",
    "phone": "9876543224"
  }'
```

### 7.5 Update Customer - Invalid Phone Format (Should Fail - 400)

```bash
# Update with invalid phone (replace CUSTOMER_ID with actual ID)
curl -X PATCH http://localhost:8080/api/v1/admin/customers/CUSTOMER_ID \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huLmRvZUBnYXJhZ2UuY29tIiwiaWF0IjoxNzU1Mzc2OTg0LCJleHAiOjE3NTU0NjMzODR9.10fUrRlkY0VaeDR-W4SF5VWeMK4VZboKjvplRNJfQ0o" \
  -d '{
    "name": "Updated Name",
    "phone": "123"
  }'
```

### 7.6 Update Customer - Duplicate Phone (Should Fail - 400)

```bash
# First create two customers
curl -X POST http://localhost:8080/api/v1/admin/customers \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huLmRvZUBnYXJhZ2UuY29tIiwiaWF0IjoxNzU1Mzc2OTg0LCJleHAiOjE3NTU0NjMzODR9.10fUrRlkY0VaeDR-W4SF5VWeMK4VZboKjvplRNJfQ0o" \
  -d '{
    "name": "Customer One",
    "phone": "9876543225"
  }'

curl -X POST http://localhost:8080/api/v1/admin/customers \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huLmRvZUBnYXJhZ2UuY29tIiwiaWF0IjoxNzU1Mzc2OTg0LCJleHAiOjE3NTU0NjMzODR9.10fUrRlkY0VaeDR-W4SF5VWeMK4VZboKjvplRNJfQ0o" \
  -d '{
    "name": "Customer Two",
    "phone": "9876543226"
  }'

# Try to update second customer with first customer's phone (replace CUSTOMER_TWO_ID)
curl -X PATCH http://localhost:8080/api/v1/admin/customers/CUSTOMER_TWO_ID \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huLmRvZUBnYXJhZ2UuY29tIiwiaWF0IjoxNzU1Mzc2OTg0LCJleHAiOjE3NTU0NjMzODR9.10fUrRlkY0VaeDR-W4SF5VWeMK4VZboKjvplRNJfQ0o" \
  -d '{
    "name": "Customer Two",
    "phone": "9876543225"
  }'
```

---

## 8. PERFORMANCE TESTS

### 8.1 Create Multiple Customers (Sequential)

```bash
# Create 5 customers sequentially to test performance
for i in {1..5}; do
  curl -X POST http://localhost:8080/api/v1/admin/customers \
    -H "Content-Type: application/json" \
    -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huLmRvZUBnYXJhZ2UuY29tIiwiaWF0IjoxNzU1Mzc2OTg0LCJleHAiOjE3NTU0NjMzODR9.10fUrRlkY0VaeDR-W4SF5VWeMK4VZboKjvplRNJfQ0o" \
    -d "{
      \"name\": \"Performance Test User $i\",
      \"phone\": \"987654400$i\",
      \"email\": \"perf$i@example.com\",
      \"vehicleInfo\": {
        \"registrationNumber\": \"PERF0$i\",
        \"make\": \"TestMake\",
        \"model\": \"TestModel\",
        \"year\": 2020
      }
    }"
done
```

### 8.2 Large Pagination Test

```bash
curl -X GET "http://localhost:8080/api/v1/admin/customers?page=0&size=100" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huLmRvZUBnYXJhZ2UuY29tIiwiaWF0IjoxNzU1Mzc2OTg0LCJleHAiOjE3NTU0NjMzODR9.10fUrRlkY0VaeDR-W4SF5VWeMK4VZboKjvplRNJfQ0o"
```

### 8.3 Complex Filter Performance

```bash
curl -X GET "http://localhost:8080/api/v1/admin/customers?customerName=Test&vehicleBrand=Test&from=2020-01-01&to=2025-12-31" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huLmRvZUBnYXJhZ2UuY29tIiwiaWF0IjoxNzU1Mzc2OTg0LCJleHAiOjE3NTU0NjMzODR9.10fUrRlkY0VaeDR-W4SF5VWeMK4VZboKjvplRNJfQ0o"
```

---

## 9. EDGE CASE TESTS

### 9.1 Very Long Name (Should Handle Gracefully)

```bash
curl -X POST http://localhost:8080/api/v1/admin/customers \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huLmRvZUBnYXJhZ2UuY29tIiwiaWF0IjoxNzU1Mzc2OTg0LCJleHAiOjE3NTU0NjMzODR9.10fUrRlkY0VaeDR-W4SF5VWeMK4VZboKjvplRNJfQ0o" \
  -d '{
    "name": "This is a very long name that might exceed normal expectations but should still be handled properly by the system",
    "phone": "9876543227"
  }'
```

### 9.2 Special Characters in Name

```bash
curl -X POST http://localhost:8080/api/v1/admin/customers \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huLmRvZUBnYXJhZ2UuY29tIiwiaWF0IjoxNzU1Mzc2OTg0LCJleHAiOjE3NTU0NjMzODR9.10fUrRlkY0VaeDR-W4SF5VWeMK4VZboKjvplRNJfQ0o" \
  -d '{
    "name": "José María O'\''Connor-Smith",
    "phone": "9876543228"
  }'
```

### 9.3 Unicode Characters

```bash
curl -X POST http://localhost:8080/api/v1/admin/customers \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huLmRvZUBnYXJhZ2UuY29tIiwiaWF0IjoxNzU1Mzc2OTg0LCJleHAiOjE3NTU0NjMzODR9.10fUrRlkY0VaeDR-W4SF5VWeMK4VZboKjvplRNJfQ0o" \
  -d '{
    "name": "राज कुमार",
    "phone": "9876543229"
  }'
```

### 9.4 Empty String Fields

```bash
curl -X POST http://localhost:8080/api/v1/admin/customers \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huLmRvZUBnYXJhZ2UuY29tIiwiaWF0IjoxNzU1Mzc2OTg0LCJleHAiOjE3NTU0NjMzODR9.10fUrRlkY0VaeDR-W4SF5VWeMK4VZboKjvplRNJfQ0o" \
  -d '{
    "name": "Empty Test",
    "phone": "9876543230",
    "email": "",
    "addressLine1": "",
    "city": "",
    "state": ""
  }'
```

### 9.5 Null Fields in JSON

```bash
curl -X POST http://localhost:8080/api/v1/admin/customers \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huLmRvZUBnYXJhZ2UuY29tIiwiaWF0IjoxNzU1Mzc2OTg0LCJleHAiOjE3NTU0NjMzODR9.10fUrRlkY0VaeDR-W4SF5VWeMK4VZboKjvplRNJfQ0o" \
  -d '{
    "name": "Null Test",
    "phone": "9876543231",
    "email": null,
    "addressLine1": null,
    "city": null,
    "state": null
  }'
```

---

## 10. MALFORMED REQUEST TESTS

### 10.1 Invalid JSON (Should Fail - 400)

```bash
curl -X POST http://localhost:8080/api/v1/admin/customers \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huLmRvZUBnYXJhZ2UuY29tIiwiaWF0IjoxNzU1Mzc2OTg0LCJleHAiOjE3NTU0NjMzODR9.10fUrRlkY0VaeDR-W4SF5VWeMK4VZboKjvplRNJfQ0o" \
  -d '{
    "name": "Invalid JSON"
    "phone": "9876543232"
  }'
```

### 10.2 Missing Content-Type Header (Should Fail - 415)

```bash
curl -X POST http://localhost:8080/api/v1/admin/customers \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huLmRvZUBnYXJhZ2UuY29tIiwiaWF0IjoxNzU1Mzc2OTg0LCJleHAiOjE3NTU0NjMzODR9.10fUrRlkY0VaeDR-W4SF5VWeMK4VZboKjvplRNJfQ0o" \
  -d '{
    "name": "No Content Type",
    "phone": "9876543233"
  }'
```

### 10.3 Wrong HTTP Method (Should Fail - 405)

```bash
curl -X PUT http://localhost:8080/api/v1/admin/customers \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huLmRvZUBnYXJhZ2UuY29tIiwiaWF0IjoxNzU1Mzc2OTg0LCJleHAiOjE3NTU0NjMzODR9.10fUrRlkY0VaeDR-W4SF5VWeMK4VZboKjvplRNJfQ0o" \
  -d '{
    "name": "Wrong Method",
    "phone": "9876543234"
  }'
```

---

## 11. BOUNDARY VALUE TESTS

### 11.1 Minimum Valid Year (1970)

```bash
curl -X POST http://localhost:8080/api/v1/admin/customers \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huLmRvZUBnYXJhZ2UuY29tIiwiaWF0IjoxNzU1Mzc2OTg0LCJleHAiOjE3NTU0NjMzODR9.10fUrRlkY0VaeDR-W4SF5VWeMK4VZboKjvplRNJfQ0o" \
  -d '{
    "name": "Min Year Test",
    "phone": "9876543235",
    "vehicleInfo": {
      "registrationNumber": "MIN1970",
      "make": "Classic",
      "model": "Car",
      "year": 1970
    }
  }'
```

### 11.2 Maximum Valid Year (2050)

```bash
curl -X POST http://localhost:8080/api/v1/admin/customers \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huLmRvZUBnYXJhZ2UuY29tIiwiaWF0IjoxNzU1Mzc2OTg0LCJleHAiOjE3NTU0NjMzODR9.10fUrRlkY0VaeDR-W4SF5VWeMK4VZboKjvplRNJfQ0o" \
  -d '{
    "name": "Max Year Test",
    "phone": "9876543236",
    "vehicleInfo": {
      "registrationNumber": "MAX2050",
      "make": "Future",
      "model": "Car",
      "year": 2050
    }
  }'
```

### 11.3 Minimum Phone Length (10 digits)

```bash
curl -X POST http://localhost:8080/api/v1/admin/customers \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huLmRvZUBnYXJhZ2UuY29tIiwiaWF0IjoxNzU1Mzc2OTg0LCJleHAiOjE3NTU0NjMzODR9.10fUrRlkY0VaeDR-W4SF5VWeMK4VZboKjvplRNJfQ0o" \
  -d '{
    "name": "Min Phone Test",
    "phone": "9876543237"
  }'
```

### 11.4 Maximum Phone Length (11 digits)

```bash
curl -X POST http://localhost:8080/api/v1/admin/customers \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huLmRvZUBnYXJhZ2UuY29tIiwiaWF0IjoxNzU1Mzc2OTg0LCJleHAiOjE3NTU0NjMzODR9.10fUrRlkY0VaeDR-W4SF5VWeMK4VZboKjvplRNJfQ0o" \
  -d '{
    "name": "Max Phone Test",
    "phone": "98765432380"
  }'
```

---

## Expected Response Codes Summary

- **200 OK**: Successful GET requests
- **201 Created**: Successful POST requests
- **400 Bad Request**: Validation errors, duplicate data, malformed JSON
- **401 Unauthorized**: Missing or invalid JWT token
- **404 Not Found**: Resource not found
- **405 Method Not Allowed**: Wrong HTTP method
- **415 Unsupported Media Type**: Missing Content-Type header
- **500 Internal Server Error**: Server-side errors

---

## Notes for Testing

1. **Replace Placeholders**:

   - `eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huLmRvZUBnYXJhZ2UuY29tIiwiaWF0IjoxNzU1Mzc2OTg0LCJleHAiOjE3NTU0NjMzODR9.10fUrRlkY0VaeDR-W4SF5VWeMK4VZboKjvplRNJfQ0o` with actual JWT token from login
   - `CUSTOMER_ID` with actual customer IDs from responses

2. **Sequential Dependencies**:

   - Some tests depend on previous tests (e.g., update tests need created customers)
   - Run tests in order or adjust IDs accordingly

3. **Database State**:

   - Tests may leave data in database
   - Consider cleanup between test runs if needed

4. **Performance Monitoring**:

   - Monitor response times for performance tests
   - Check server logs for any errors

5. **Error Reporting Format**:
   ```
   Test: [Test Name]
   Curl: [Exact curl command used]
   Expected: [Expected response code/behavior]
   Actual Response Code: [Actual HTTP status]
   Response Body: [Response content]
   Terminal Output: [Any terminal errors]
   ```
