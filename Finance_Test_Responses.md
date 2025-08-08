1.1 {
    "amountToBeCollected": 0,
    "receivedToday": 0,
    "spentToday": 0,
    "netProfit": 0,
    "recentTransactions": []
}
Has_Bug = False

Status Code : 200


1.2 {
    "amountToBeCollected": 0,
    "receivedToday": 0,
    "spentToday": 0,
    "netProfit": 0,
    "recentTransactions": []
}

Status Code: 200
Has_Bug = True
Curl- curl --location 'http://localhost:8080/api/v1/admin/financial-transactions/money-data'



2.1 Status Code: 400 
Empty Response
Has_Bug = True

curl --location 'http://localhost:8080/api/v1/admin/financial-transactions/income' \
   --header 'Content-Type: application/json' \
   --header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJrYWJpcnNpbmdoQGdtYWlsLmNvbSIsImlhdCI6MTc1NDYzNjgwMiwiZXhwIjoxNzU0NzIzMjAyfQ.5DngjCRZKdzdILyxNCLdnLblwK2ttR4ghVbpUurDH5M' \
   --data '{
   "amount": 1500.00,
   "description": "Car repair service payment",
   "customerId": "550e8400-e29b-41d4-a716-446655440000",
   "vehicleId": "550e8400-e29b-41d4-a716-446655440001",
   "jobCardId": "550e8400-e29b-41d4-a716-446655440002",
   "paymentMethod": "CARD",
   "referenceNumber": "INC001",
   "notes": "Engine oil change and filter replacement"
   }'

2.2 {
"success": false,
"message": "Validation failed",
"errors": {
"customerId": "Customer ID is required",
"paymentMethod": "Payment method is required"
}
}

Status Code: 400

Curl- curl --location 'http://localhost:8080/api/v1/admin/financial-transactions/income' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJrYWJpcnNpbmdoQGdtYWlsLmNvbSIsImlhdCI6MTc1NDYzNjgwMiwiZXhwIjoxNzU0NzIzMjAyfQ.5DngjCRZKdzdILyxNCLdnLblwK2ttR4ghVbpUurDH5M' \
--data '{
"amount": 1500.00,
"description": "Car repair service payment"
}'

Has_bug= False