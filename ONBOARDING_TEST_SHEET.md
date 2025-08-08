# 🧪 ONBOARDING FLOW TEST SHEET

## 📋 Test Configuration

### **Bearer Token Constant**
```
Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ0ZXN0QGV4YW1wbGUuY29tIiwiaWF0IjoxNjE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c
```

### **Base URL**
```
http://localhost:8080
```

---

## 🚀 STEP 1: User Registration

### **1.1 Register New User**
```http
POST {{baseUrl}}/admin/register
Content-Type: application/json

{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@garage.com",
  "password": "SecurePass123!",
  "confirmPassword": "SecurePass123!",
  "mobileNumber": "9876543210"
}
```

**Expected Response (200):**
```json
{
  "success": true,
  "message": "User registered successfully",
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "user": {
    "id": "uuid-here",
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@garage.com",
    "mobileNumber": "9876543210"
  }
}
```

### **1.2 Login with Registered User**
```http
POST {{baseUrl}}/admin/login
Content-Type: application/json

{
  "email": "john.doe@garage.com",
  "password": "SecurePass123!"
}
```

**Expected Response (200):**
```json
{
  "success": true,
  "message": "Login successful",
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "user": {
    "id": "uuid-here",
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@garage.com",
    "mobileNumber": "9876543210"
  }
}
```

**⚠️ IMPORTANT:** Copy the token from this response and use it as the Bearer token for all subsequent requests.

---

## 🏢 STEP 2: Garage Registration

### **2.1 Create Garage**
```http
POST {{baseUrl}}/admin/garage
Authorization: Bearer {{bearerToken}}
Content-Type: application/json

{
  "garageName": "John's Auto Service Center",
  "businessRegistrationNumber": "BR123456789",
  "gstNumber": "27AABCJ1234Z1Z5",
  "logoUrl": "https://example.com/garage-logo.png",
  "websiteUrl": "https://johnsautoservice.com",
  "businessHours": "Monday to Saturday: 9:00 AM - 7:00 PM, Sunday: 10:00 AM - 4:00 PM",
  "hasBranch": false
}
```

**Expected Response (201):**
```json
{
  "id": "uuid-here",
  "garageName": "John's Auto Service Center",
  "businessRegistrationNumber": "BR123456789",
  "gstNumber": "27AABCJ1234Z1Z5",
  "logoUrl": "https://example.com/garage-logo.png",
  "websiteUrl": "https://johnsautoservice.com",
  "businessHours": "Monday to Saturday: 9:00 AM - 7:00 PM, Sunday: 10:00 AM - 4:00 PM",
  "hasBranch": false,
  "isActive": true,
  "createdAt": "2024-01-15T10:30:00",
  "updatedAt": "2024-01-15T10:30:00"
}
```

### **2.2 Create Address for Garage**
```http
POST {{baseUrl}}/admin/addresses
Authorization: Bearer {{bearerToken}}
Content-Type: application/json

{
  "addressLine1": "123 Main Street",
  "addressLine2": "Building A, Ground Floor",
  "city": "Mumbai",
  "state": "Maharashtra",
  "pincode": "400001",
  "country": "India"
}
```

**Expected Response (201):**
```json
{
  "id": "uuid-here",
  "garageId": "{{garageId}}",
  "addressLine1": "123 Main Street",
  "addressLine2": "Building A, Ground Floor",
  "city": "Mumbai",
  "state": "Maharashtra",
  "pincode": "400001",
  "country": "India",
  "createdAt": "2024-01-15T10:31:00",
  "updatedAt": "2024-01-15T10:31:00"
}
```

### **2.2 Get Garage by ID**
```http
GET {{baseUrl}}/admin/garage/{{garageId}}
Authorization: Bearer {{bearerToken}}
```

### **2.3 Update Garage**
```http
PATCH {{baseUrl}}/admin/garage/{{garageId}}
Authorization: Bearer {{bearerToken}}
Content-Type: application/json

{
  "garageName": "John's Premium Auto Service Center",
  "businessRegistrationNumber": "BR123456789",
  "gstNumber": "27AABCJ1234Z1Z5",
  "logoUrl": "https://example.com/garage-logo-updated.png",
  "websiteUrl": "https://johnspremiumauto.com",
  "businessHours": "Monday to Saturday: 8:00 AM - 8:00 PM, Sunday: 9:00 AM - 5:00 PM",
  "hasBranch": true
}
```

### **2.4 Get Addresses for Garage**
```http
GET {{baseUrl}}/admin/addresses/my-addresses
Authorization: Bearer {{bearerToken}}
```

### **2.5 Update Address**
```http
PATCH {{baseUrl}}/admin/addresses/{{addressId}}
Authorization: Bearer {{bearerToken}}
Content-Type: application/json

{
  "addressLine1": "456 Premium Street",
  "addressLine2": "Building B, First Floor",
  "city": "Mumbai",
  "state": "Maharashtra",
  "pincode": "400002",
  "country": "India"
}
```

---

## 💳 STEP 3: Payment Configurations

**Note:** To test multiple payment methods configuration, run sections 3.1 through 3.4 in sequence to create all four payment methods (CASH, UPI, CARD, BANK_TRANSFER), then use section 3.5 to verify all methods are configured for your garage.

### **3.1 Create Payment Method - CASH**
```http
POST {{baseUrl}}/admin/payment-methods
Authorization: Bearer {{bearerToken}}
Content-Type: application/json

{
  "paymentMethod": "CASH"
}
```

**Expected Response (200):**
```json
{
  "id": "uuid-here",
  "garageId": "{{garageId}}",
  "paymentMethod": "CASH",
  "isActive": true,
  "createdAt": "2024-01-15T10:35:00",
  "updatedAt": "2024-01-15T10:35:00"
}
```

### **3.2 Create Payment Method - UPI**
```http
POST {{baseUrl}}/admin/payment-methods
Authorization: Bearer {{bearerToken}}
Content-Type: application/json

{
  "paymentMethod": "UPI"
}
```

### **3.3 Create Payment Method - CARD**
```http
POST {{baseUrl}}/admin/payment-methods
Authorization: Bearer {{bearerToken}}
Content-Type: application/json

{
  "paymentMethod": "CARD"
}
```

### **3.4 Create Payment Method - BANK_TRANSFER**
```http
POST {{baseUrl}}/admin/payment-methods
Authorization: Bearer {{bearerToken}}
Content-Type: application/json

{
  "paymentMethod": "BANK_TRANSFER"
}
```

### **3.5 Get All Payment Methods for Garage**
```http
GET {{baseUrl}}/admin/payment-methods/my-payment-methods
Authorization: Bearer {{bearerToken}}
```

**Expected Response (200):**
```json
[
  {
    "id": "uuid-1",
    "paymentMethod": "CASH",
    "isActive": true,
    "createdAt": "2024-01-15T10:35:00",
    "updatedAt": "2024-01-15T10:35:00"
  },
  {
    "id": "uuid-2",
    "paymentMethod": "UPI",
    "isActive": true,
    "createdAt": "2024-01-15T10:36:00",
    "updatedAt": "2024-01-15T10:36:00"
  },
  {
    "id": "uuid-3",
    "paymentMethod": "CARD",
    "isActive": true,
    "createdAt": "2024-01-15T10:37:00",
    "updatedAt": "2024-01-15T10:37:00"
  },
  {
    "id": "uuid-4",
    "paymentMethod": "BANK_TRANSFER",
    "isActive": true,
    "createdAt": "2024-01-15T10:38:00",
    "updatedAt": "2024-01-15T10:38:00"
  }
]
```

### **3.6 Deactivate Payment Method**
```http
PATCH {{baseUrl}}/admin/payment-methods/{{paymentMethodId}}/deactivate
Authorization: Bearer {{bearerToken}}
```

### **3.7 Activate Payment Method**
```http
PATCH {{baseUrl}}/admin/payment-methods/{{paymentMethodId}}/activate
Authorization: Bearer {{bearerToken}}
```

---

## 👥 STEP 4: Staff Registration

### **4.1 Create Staff - MECHANIC**
```http
POST {{baseUrl}}/admin/staff
Authorization: Bearer {{bearerToken}}
Content-Type: application/json

{
  "firstName": "Mike",
  "lastName": "Mechanic",
  "mobileNumber": "9876543211",
  "aadharNumber": "123456789012",
  "role": "MECHANIC"
}
```

**Expected Response (201):**
```json
{
  "id": "uuid-here",
  "garageId": "{{garageId}}",
  "firstName": "Mike",
  "lastName": "Mechanic",
  "mobileNumber": "9876543211",
  "aadharNumber": "123456789012",
  "role": "MECHANIC",
  "isActive": true,
  "jobsCompleted": 0,
  "createdAt": "2024-01-15T10:40:00",
  "updatedAt": "2024-01-15T10:40:00"
}
```

### **4.2 Create Staff - RECEPTIONIST**
```http
POST {{baseUrl}}/admin/staff
Authorization: Bearer {{bearerToken}}
Content-Type: application/json

{
  "firstName": "Sarah",
  "lastName": "Reception",
  "mobileNumber": "9876543212",
  "aadharNumber": "123456789013",
  "role": "RECEPTIONIST"
}
```

### **4.3 Create Staff - MANAGER**
```http
POST {{baseUrl}}/admin/staff
Authorization: Bearer {{bearerToken}}
Content-Type: application/json

{
  "firstName": "David",
  "lastName": "Manager",
  "mobileNumber": "9876543213",
  "aadharNumber": "123456789014",
  "role": "MANAGER"
}
```

### **4.4 Get All Staff**
```http
GET {{baseUrl}}/admin/staff
Authorization: Bearer {{bearerToken}}
```

**Expected Response (200):**
```json
{
  "content": [
    {
      "id": "uuid-1",
      "firstName": "Mike",
      "lastName": "Mechanic",
      "mobileNumber": "9876543211",
      "aadharNumber": "123456789012",
      "role": "MECHANIC",
      "isActive": true,
      "jobsCompleted": 0,
      "createdAt": "2024-01-15T10:40:00",
      "updatedAt": "2024-01-15T10:40:00"
    },
    {
      "id": "uuid-2",
      "firstName": "Sarah",
      "lastName": "Reception",
      "mobileNumber": "9876543212",
      "aadharNumber": "123456789013",
      "role": "RECEPTIONIST",
      "isActive": true,
      "createdAt": "2024-01-15T10:41:00",
      "updatedAt": "2024-01-15T10:41:00"
    },
    {
      "id": "uuid-3",
      "firstName": "David",
      "lastName": "Manager",
      "mobileNumber": "9876543213",
      "aadharNumber": "123456789014",
      "role": "MANAGER",
      "isActive": true,
      "jobsCompleted": 0,
      "createdAt": "2024-01-15T10:42:00",
      "updatedAt": "2024-01-15T10:42:00"
    }
  ],
  "totalElements": 3,
  "totalPages": 1,
  "size": 20,
  "number": 0
}
```

### **4.5 Get Staff by Role**
```http
GET {{baseUrl}}/admin/staff?role=MECHANIC
Authorization: Bearer {{bearerToken}}
```

**For multiple roles:**
```http
GET {{baseUrl}}/admin/staff?role=MECHANIC,MANAGER
Authorization: Bearer {{bearerToken}}
```

### **4.6 Update Staff**
```http
PATCH {{baseUrl}}/admin/staff/{{staffId}}
Authorization: Bearer {{bearerToken}}
Content-Type: application/json

{
  "firstName": "Michael",
  "lastName": "Senior Mechanic",
  "mobileNumber": "9876543211",
  "aadharNumber": "123456789012",
  "role": "MECHANIC"
}
```

### **4.7 Deactivate Staff**
```http
PATCH {{baseUrl}}/admin/staff/{{staffId}}?status=DEACTIVATE
Authorization: Bearer {{bearerToken}}
```

### **4.8 Activate Staff**
```http
PATCH {{baseUrl}}/admin/staff/{{staffId}}?status=ACTIVATE
Authorization: Bearer {{bearerToken}}
```

---

## 🔍 ADDITIONAL TEST SCENARIOS

### **Error Cases - User Registration**
```http
POST {{baseUrl}}/admin/register
Content-Type: application/json

{
  "firstName": "",
  "lastName": "Doe",
  "email": "invalid-email",
  "password": "weak",
  "confirmPassword": "different",
  "mobileNumber": "123"
}
```

### **Error Cases - Garage Registration**
```http
POST {{baseUrl}}/admin/garage
Authorization: Bearer {{bearerToken}}
Content-Type: application/json

{
  "garageName": "",
  "businessRegistrationNumber": "",
  "gstNumber": "invalid-gst",
  "addressLine1": "",
  "city": "",
  "country": "",
  "pincode": "12345",
  "state": "",
  "businessHours": ""
}
```

### **Error Cases - Payment Methods**
```http
POST {{baseUrl}}/admin/payment-methods
Authorization: Bearer {{bearerToken}}
Content-Type: application/json

{
  "garageId": "invalid-uuid",
  "paymentMethod": "INVALID_METHOD"
}
```

### **Error Cases - Staff Registration**
```http
POST {{baseUrl}}/admin/staff
Authorization: Bearer {{bearerToken}}
Content-Type: application/json

{
  "firstName": "",
  "lastName": "Test",
  "mobileNumber": "123",
  "aadharNumber": "123",
  "role": "INVALID_ROLE"
}
```

---

## 📝 TEST CHECKLIST

### **✅ Step 1: User Registration**
- [ ] Register new user with valid data
- [ ] Register user with invalid data (validation errors)
- [ ] Login with registered user
- [ ] Login with invalid credentials

### **✅ Step 2: Garage Registration**
- [ ] Create garage with all required fields
- [ ] Create garage with optional fields
- [ ] Get garage by ID
- [ ] Update garage details
- [ ] Create garage with duplicate business registration number (error)
- [ ] Create garage with duplicate GST number (error)
- [ ] Create address for garage
- [ ] Get addresses for garage
- [ ] Update address details
- [ ] Delete address

### **✅ Step 3: Payment Configurations**
- [ ] Create CASH payment method
- [ ] Create UPI payment method
- [ ] Create CARD payment method
- [ ] Create BANK_TRANSFER payment method
- [ ] Get all payment methods for garage
- [ ] Deactivate payment method
- [ ] Activate payment method
- [ ] Create duplicate payment method (error)

### **✅ Step 4: Staff Registration**
- [ ] Create MECHANIC staff
- [ ] Create RECEPTIONIST staff
- [ ] Create MANAGER staff
- [ ] Get all staff (paginated)
- [ ] Get staff by role
- [ ] Update staff details
- [ ] Deactivate staff
- [ ] Activate staff
- [ ] Create staff with duplicate mobile number (error)

### **✅ Integration Tests**
- [ ] Complete onboarding flow in sequence
- [ ] Verify data relationships between entities
- [ ] Test authorization with valid/invalid tokens
- [ ] Test pagination for list endpoints

---

## 🎯 SUCCESS CRITERIA

The onboarding flow is considered **COMPLETE** when:

1. ✅ User can register and login successfully
2. ✅ Garage can be created with all business details (with created_by field)
3. ✅ Multiple payment methods can be configured
4. ✅ Multiple staff members can be registered with different roles
5. ✅ All CRUD operations work for each entity
6. ✅ Proper validation errors are returned for invalid data
7. ✅ Authorization works correctly with Bearer tokens
8. ✅ Data relationships are maintained correctly
9. ✅ Transaction management ensures data consistency
10. ✅ Onboarding progress can be tracked and guided

---

## 📊 EXPECTED DATABASE STATE AFTER COMPLETE ONBOARDING

After running the complete test flow, you should have:

- **1 User** in the `Users` table
- **1 Garage** in the `Garage` table (linked to user via created_by)
- **1 Address** in the `Addresses` table (linked to garage)
- **4 Payment Methods** in the `Garage_Payment_Methods` table
- **3 Staff Members** in the `Staff` table

All entities should be properly linked and the garage should be ready for business operations.

---

## 🚀 NEW ONBOARDING SERVICE ENDPOINTS

### **Complete Onboarding in Single Transaction**
```http
POST {{baseUrl}}/admin/onboarding/complete
Authorization: Bearer {{bearerToken}}
Content-Type: application/json

{
  "garageRequest": {
    "garageName": "John's Auto Service Center",
    "businessRegistrationNumber": "BR123456789",
    "gstNumber": "27AABCJ1234Z1Z5",
    "logoUrl": "https://example.com/garage-logo.png",
    "websiteUrl": "https://johnsautoservice.com",
    "businessHours": "Monday to Saturday: 9:00 AM - 7:00 PM, Sunday: 10:00 AM - 4:00 PM",
    "hasBranch": false
  },
  "addressRequest": {
    "addressLine1": "123 Main Street",
    "addressLine2": "Building A, Ground Floor",
    "city": "Mumbai",
    "state": "Maharashtra",
    "pincode": "400001",
    "country": "India"
  },
  "paymentMethodRequests": [
    {"paymentMethod": "CASH"},
    {"paymentMethod": "UPI"},
    {"paymentMethod": "CARD"},
    {"paymentMethod": "BANK_TRANSFER"}
  ],
  "staffRequests": [
    {
      "firstName": "Mike",
      "lastName": "Mechanic",
      "mobileNumber": "9876543211",
      "aadharNumber": "123456789012",
      "role": "MECHANIC"
    },
    {
      "firstName": "Sarah",
      "lastName": "Reception",
      "mobileNumber": "9876543212",
      "aadharNumber": "123456789013",
      "role": "RECEPTIONIST"
    },
    {
      "firstName": "David",
      "lastName": "Manager",
      "mobileNumber": "9876543213",
      "aadharNumber": "123456789014",
      "role": "MANAGER"
    }
  ]
}
```

### **Get Onboarding Status**
```http
GET {{baseUrl}}/admin/onboarding/status
Authorization: Bearer {{bearerToken}}
```

**Expected Response:**
```json
{
  "userId": "uuid-here",
  "hasGarage": true,
  "hasAddress": true,
  "hasPaymentMethods": true,
  "hasStaff": true,
  "completionPercentage": 100
}
```

### **Get Next Step**
```http
GET {{baseUrl}}/admin/onboarding/next-step
Authorization: Bearer {{bearerToken}}
```

**Expected Response:**
```json
{
  "step": "COMPLETED",
  "message": "Onboarding completed!",
  "priority": 5
}
```
