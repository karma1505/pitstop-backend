# Garage Data API & Vehicle Table - Plan of Action

## Overview
This document outlines the implementation plan for creating a comprehensive Garage Data API that provides both summary statistics and detailed garage information, along with a Vehicle table to support the garage management system.

## Current Frontend Requirements
Based on the HomeScreen analysis, the Garage Data card currently displays:
- Total Vehicles: 12
- Total Workers Present: 8  
- Parts Received Today: 15
- Total Appointments Today: 6

## API Design Strategy

### 1. Single Endpoint with Query Parameter Approach
**Endpoint**: `GET /api/v1/garage-data`
- **Without query param**: Returns summary statistics (4 key metrics)
- **With query param**: Returns comprehensive garage data

**Query Parameter**: `?detailed=true`

### 2. Response Structure

#### Summary Response (default)
```json
{
  "success": true,
  "data": {
    "totalVehicles": 12,
    "totalWorkersPresent": 8,
    "partsReceivedToday": 15,
    "totalAppointmentsToday": 6,
    "lastUpdated": "2024-01-15T10:30:00Z"
  }
}
```

#### Detailed Response (with ?detailed=true)
```json
{
  "success": true,
  "data": {
    "summary": {
      "totalVehicles": 12,
      "totalWorkersPresent": 8,
      "partsReceivedToday": 15,
      "totalAppointmentsToday": 6
    },
    "vehicles": [
      {
        "id": "uuid",
        "registrationNumber": "MH-12-AB-1234",
        "make": "Honda",
        "model": "City",
        "year": 2020,
        "customerName": "John Doe",
        "lastServiceDate": "2024-01-10",
        "nextServiceDue": "2024-02-10",
        "status": "ACTIVE"
      }
    ],
    "workers": [
      {
        "id": "uuid",
        "name": "Rajesh Kumar",
        "role": "MECHANIC",
        "isPresent": true,
        "currentJobCard": "JC-001",
        "startTime": "09:00"
      }
    ],
    "appointments": [
      {
        "id": "uuid",
        "customerName": "Priya Singh",
        "vehicleRegistration": "DL-01-CD-5678",
        "serviceType": "OIL_CHANGE",
        "scheduledTime": "14:00",
        "status": "CONFIRMED"
      }
    ],
    "partsReceived": [
      {
        "id": "uuid",
        "partName": "Oil Filter",
        "quantity": 5,
        "receivedAt": "2024-01-15T09:00:00Z",
        "supplier": "Auto Parts Co."
      }
    ]
  }
}
```

## Database Schema Design

### 1. Vehicle Table
```sql
CREATE TABLE vehicles (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    garage_id UUID NOT NULL REFERENCES users(id),
    customer_id UUID NOT NULL REFERENCES customers(id),
    registration_number VARCHAR(20) NOT NULL,
    make VARCHAR(50) NOT NULL,
    model VARCHAR(50) NOT NULL,
    year INTEGER NOT NULL,
    vin VARCHAR(17),
    chassis_number VARCHAR(50),
    color VARCHAR(30),
    fuel_type VARCHAR(20),
    transmission_type VARCHAR(20),
    engine_capacity VARCHAR(20),
    insurance_expiry_date DATE,
    fitness_certificate_expiry DATE,
    puc_expiry_date DATE,
    status VARCHAR(20) DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by UUID REFERENCES users(id),
    updated_by UUID REFERENCES users(id)
);

-- Indexes for performance

CREATE INDEX idx_vehicles_registration_number ON vehicles(registration_number);
```

### 2. Supporting Tables (for comprehensive data)

#### Customer Table
```sql
CREATE TABLE customers (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    garage_id UUID NOT NULL REFERENCES users(id),
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100),
    phone_number VARCHAR(15) NOT NULL,
    address_line1 VARCHAR(255),
    address_line2 VARCHAR(255),
    city VARCHAR(50),
    state VARCHAR(50),
    pincode VARCHAR(10)
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

#### Worker Table
```sql
CREATE TABLE workers (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    garage_id UUID NOT NULL REFERENCES users(id),
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    phone_number VARCHAR(15),
    role VARCHAR(30) NOT NULL,
    is_present BOOLEAN DEFAULT false,
    current_job_card_id UUID,
    start_time TIME,
    end_time TIME,
    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

#### Appointment Table
```sql
CREATE TABLE appointments (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    garage_id UUID NOT NULL REFERENCES users(id),
    customer_id UUID NOT NULL REFERENCES customers(id),
    vehicle_id UUID NOT NULL REFERENCES vehicles(id),
    service_type VARCHAR(50) NOT NULL,
    scheduled_date DATE NOT NULL,
    scheduled_time TIME NOT NULL,
    status VARCHAR(20) DEFAULT 'PENDING',
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

#### Parts Received Table
```sql
CREATE TABLE parts_received (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    garage_id UUID NOT NULL REFERENCES users(id),
    part_name VARCHAR(100) NOT NULL,
    quantity INTEGER NOT NULL,
    supplier VARCHAR(100),
    received_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by UUID REFERENCES users(id)
);
```

## Implementation Plan

### Phase 1: Database Setup
1. **Create Vehicle Entity**
   - Follow existing User entity pattern
   - Include all required fields with proper annotations
   - Add validation constraints
   - Implement auditing (created_at, updated_at)

2. **Create Supporting Entities**
   - Customer entity
   - Worker entity  
   - Appointment entity
   - PartsReceived entity

3. **Create Repository Interfaces**
   - VehicleRepository with custom query methods
   - CustomerRepository
   - WorkerRepository
   - AppointmentRepository
   - PartsReceivedRepository

### Phase 2: DTOs and Request/Response Classes
1. **Create DTOs**
   - VehicleDTO
   - CustomerDTO
   - WorkerDTO
   - AppointmentDTO
   - PartsReceivedDTO

2. **Create Response Classes**
   - GarageDataSummaryResponse
   - GarageDataDetailedResponse
   - VehicleListResponse

### Phase 3: Service Layer
1. **Create GarageDataService**
   - `getGarageDataSummary()` - Returns 4 key metrics
   - `getGarageDataDetailed()` - Returns comprehensive data
   - `getVehicleCount()` - Count vehicles for garage
   - `getWorkerCount()` - Count present workers
   - `getAppointmentCount()` - Count today's appointments
   - `getPartsReceivedCount()` - Count today's received parts

2. **Create VehicleService**
   - `createVehicle()` - Add new vehicle
   - `getVehiclesByGarage()` - Get all vehicles for garage
   - `getVehicleById()` - Get specific vehicle
   - `updateVehicle()` - Update vehicle details
   - `deleteVehicle()` - Soft delete vehicle

### Phase 4: Controller Layer
1. **Create GarageDataController**
   - `GET /api/v1/garage-data` - Main endpoint
   - Query parameter handling for detailed view
   - Proper error handling and validation

2. **Create VehicleController**
   - `GET /api/v1/vehicles` - List vehicles
   - `POST /api/v1/vehicles` - Create vehicle
   - `GET /api/v1/vehicles/{id}` - Get vehicle
   - `PATCH /api/v1/vehicles/{id}` - Update vehicle
   - `DELETE /api/v1/vehicles/{id}` - Delete vehicle

### Phase 5: Query Optimization
1. **Custom Query Methods**
   - Use `@Query` annotations for complex queries
   - Implement pagination for large datasets
   - Add proper indexing strategies

2. **Performance Considerations**
   - Use `@EntityGraph` for eager loading when needed
   - Implement `@BatchSize` for collections
   - Use `@Fetch` for specific fetch strategies

## API Endpoints

### Garage Data API
```
GET /api/v1/garage-data
GET /api/v1/garage-data?detailed=true
```

### Vehicle Management API
```
GET    /api/v1/vehicles
POST   /api/v1/vehicles
GET    /api/v1/vehicles/{id}
PATCH  /api/v1/vehicles/{id}
DELETE /api/v1/vehicles/{id}
```

## Coding Principles & Best Practices

### 1. DRY (Don't Repeat Yourself)
- Create base entity classes for common fields
- Use inheritance for similar entities
- Implement shared utility methods

### 2. Abstraction
- Use service interfaces for business logic
- Implement repository pattern consistently
- Create abstract base classes for common operations

### 3. Definitive Variable Naming
- Use descriptive names: `totalVehiclesInGarage` instead of `count`
- Follow Java naming conventions
- Use consistent naming across entities

### 4. Query Optimization
- Use appropriate indexes
- Implement pagination for large datasets
- Use `@Query` for complex operations
- Avoid N+1 query problems

### 5. No Caching Strategy
- Implement real-time data fetching
- Use database queries for fresh data
- Avoid `@Cacheable` annotations
- Implement proper transaction management

### 6. Error Handling
- Use consistent exception handling
- Implement proper validation
- Return meaningful error messages
- Log errors appropriately

### 7. Security
- Implement proper authorization
- Validate user permissions
- Sanitize input data
- Use parameterized queries

## File Structure

```
src/main/java/com/garage/backend/
├── entity/
│   ├── Vehicle.java
│   ├── Customer.java
│   ├── Worker.java
│   ├── Appointment.java
│   └── PartsReceived.java
├── dto/
│   ├── VehicleDTO.java
│   ├── CustomerDTO.java
│   ├── WorkerDTO.java
│   ├── AppointmentDTO.java
│   ├── PartsReceivedDTO.java
│   ├── GarageDataSummaryResponse.java
│   └── GarageDataDetailedResponse.java
├── repository/
│   ├── VehicleRepository.java
│   ├── CustomerRepository.java
│   ├── WorkerRepository.java
│   ├── AppointmentRepository.java
│   └── PartsReceivedRepository.java
├── service/
│   ├── GarageDataService.java
│   ├── VehicleService.java
│   ├── CustomerService.java
│   ├── WorkerService.java
│   ├── AppointmentService.java
│   └── PartsReceivedService.java
└── controller/
    ├── GarageDataController.java
    └── VehicleController.java
```

## Questions & Clarifications

1. **Vehicle Registration Number Format**: Should we support different formats for different states in India (e.g., MH-12-AB-1234, DL-01-CD-5678)?

2. **Worker Roles**: What specific roles should we support (Mechanic, Electrician, Helper, etc.)?

3. **Service Types**: What are the standard service types we should support (Oil Change, Brake Service, etc.)?

4. **Parts Management**: Should we create a separate parts catalog table, or keep it simple with just the parts received table?

5. **Customer-Vehicle Relationship**: Can one customer have multiple vehicles? Should we track vehicle ownership history?

6. **Data Retention**: How long should we keep historical data for vehicles, appointments, and parts?

7. **External Vehicle API**: Should we integrate with external APIs for vehicle make/model data, or start with a basic catalog?

8. **Real-time Updates**: Should the garage data be real-time, or is it acceptable to have slight delays?

9. **Multi-garage Support**: Should the system support multiple garages per user, or one garage per user?

10. **Audit Trail**: Do we need to track who created/modified each record for audit purposes? 