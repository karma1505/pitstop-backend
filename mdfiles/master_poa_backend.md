# Master Plan of Action - Backend Implementation

## Overview
This document outlines the systematic implementation approach for the PitStop backend API, organized by complexity and dependencies to ensure efficient development and quick wins.

## Implementation Strategy
The implementation follows a phased approach, starting with the simplest features that have minimal dependencies and progressing to more complex, interconnected systems.

## Phase 1: Foundation APIs (Week 1)

### 1. Money Data API
**Priority**: Highest - No Dependencies
**Complexity**: Low
**Estimated Time**: 2-3 days

**Data Points**:
- Amount To Be Collected: ₹45,250
- Received Today: ₹12,800
- Spent Today: ₹8,450
- Net Profit: ₹4,350

**Why First**:
- No dependencies on other systems
- Single table approach for all financial transactions
- Provides immediate value to garage owners
- Foundation for revenue analytics

**Required Database Tables**:
- Staff (id, name, email, phone, role, is_active, created_at, updated_at)
- Customers (id, name, phone, email, address, created_at, updated_at)
- Vehicles (id, customer_id, registration_number, make, model, year, vin, engine_number, fuel_type, transmission_type, created_at, updated_at)
- JobCards (id, customer_id, vehicle_id, job_number, description, estimated_cost, actual_cost, status, assigned_to, created_at, updated_at)
- Transactions (id, amount, transaction_type, transaction_date, description, status, customer_id, vehicle_id, job_card_id, payment_method, expense_category, vendor_name, vendor_contact, reference_number, notes, created_at, updated_at, created_by, updated_by)

**API Endpoints**:
- GET /api/v1/financial-transactions/money-data
- GET /api/v1/financial-transactions/money-data?type=income
- GET /api/v1/financial-transactions/money-data?type=expense
- GET /api/v1/financial-transactions/money-data?transaction_type=INCOME&date=today
- GET /api/v1/financial-transactions/money-data?transaction_type=EXPENSE&date=today
- GET /api/v1/financial-transactions/money-data?page=0&size=20&transaction_type=INCOME&from_date=2024-01-01&to_date=2024-01-31
- POST /api/v1/financial-transactions/income
- POST /api/v1/financial-transactions/expense

**Implementation Steps**:
1. Create core tables (staff, customers, vehicles, job_cards)
2. Create FinancialTransaction entity with single table design
3. Implement repository interfaces with aggregation queries
4. Create service layer with income/expense business logic
5. Implement controller with separate income/expense endpoints
6. Add validation, error handling, and audit trail

**Complete Database Schema for Money Data API**:

```sql
-- Core tables required for financial transactions
CREATE TABLE Staff (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone VARCHAR(15),
    role VARCHAR(30) NOT NULL CHECK (role IN ('OWNER', 'MANAGER', 'MECHANIC', 'RECEPTIONIST')),
    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE Customers (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(100) NOT NULL,
    phone VARCHAR(15) NOT NULL UNIQUE,
    email VARCHAR(100),
    address TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE Vehicles (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    customer_id UUID NOT NULL,
    registration_number VARCHAR(20) NOT NULL,
    make VARCHAR(50),
    model VARCHAR(50),
    year INTEGER,
    vin VARCHAR(17),
    engine_number VARCHAR(20),
    fuel_type VARCHAR(20),
    transmission_type VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_vehicles_customer FOREIGN KEY (customer_id) REFERENCES Customers(id)
);

CREATE TABLE JobCards (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    customer_id UUID NOT NULL,
    vehicle_id UUID NOT NULL,
    job_number VARCHAR(20) NOT NULL UNIQUE,
    description TEXT NOT NULL,
    estimated_cost DECIMAL(10,2),
    actual_cost DECIMAL(10,2),
    status VARCHAR(20) DEFAULT 'PENDING' CHECK (status IN ('PENDING', 'IN_PROGRESS', 'COMPLETED', 'CANCELLED')),
    assigned_to UUID,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_job_cards_customer FOREIGN KEY (customer_id) REFERENCES Customers(id),
    CONSTRAINT fk_job_cards_vehicle FOREIGN KEY (vehicle_id) REFERENCES Vehicles(id),
    CONSTRAINT fk_job_cards_assigned_to FOREIGN KEY (assigned_to) REFERENCES Staff(id)
);

-- Single table for all financial transactions
CREATE TABLE Transactions (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    amount DECIMAL(10,2) NOT NULL,
    transaction_type VARCHAR(20) NOT NULL CHECK (transaction_type IN ('INCOME', 'EXPENSE')),
    transaction_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    description VARCHAR(255) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'COMPLETED' CHECK (status IN ('PENDING', 'COMPLETED', 'CANCELLED', 'REFUNDED')),
    
    -- Income-specific fields (NULL for expenses)
    customer_id UUID,
    vehicle_id UUID,
    job_card_id UUID,
    payment_method VARCHAR(30) CHECK (payment_method IN ('CASH', 'CARD', 'UPI', 'BANK_TRANSFER', 'CHEQUE')),
    
    -- Expense-specific fields (NULL for income)
    expense_category VARCHAR(50) CHECK (expense_category IN (
        'UTILITIES', 'SALARY', 'EQUIPMENT', 'SUPPLIES', 'RENT', 
        'INSURANCE', 'MAINTENANCE', 'MARKETING', 'OFFICE_SUPPLIES', 'OTHER'
    )),
    vendor_name VARCHAR(100),
    vendor_contact VARCHAR(50),
    
    -- Common fields
    reference_number VARCHAR(50),
    notes TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by UUID NOT NULL,
    updated_by UUID,
    
    -- Foreign Key Constraints
    CONSTRAINT fk_transactions_customer FOREIGN KEY (customer_id) REFERENCES Customers(id),
    CONSTRAINT fk_transactions_vehicle FOREIGN KEY (vehicle_id) REFERENCES Vehicles(id),
    CONSTRAINT fk_transactions_job_card FOREIGN KEY (job_card_id) REFERENCES JobCards(id),
    CONSTRAINT fk_transactions_created_by FOREIGN KEY (created_by) REFERENCES Staff(id),
    CONSTRAINT fk_transactions_updated_by FOREIGN KEY (updated_by) REFERENCES Staff(id),
    
    -- Business Rules
    CONSTRAINT chk_income_fields CHECK (
        transaction_type = 'INCOME' AND customer_id IS NOT NULL AND payment_method IS NOT NULL
        OR transaction_type = 'EXPENSE'
    ),
    CONSTRAINT chk_expense_fields CHECK (
        transaction_type = 'EXPENSE' AND expense_category IS NOT NULL
        OR transaction_type = 'INCOME'
    ),
    CONSTRAINT chk_amount_positive CHECK (amount > 0)
);


```

---

### 2. Customer Data API
**Priority**: High - Minimal Dependencies
**Complexity**: Low
**Estimated Time**: 2-3 days

**Data Points**:
- New Customers: 5
- Returning Customers: 18
- Customer Satisfaction: 4.8/5
- Reviews Today: 3

**Why Second**:
- Minimal dependencies on other systems
- Foundation for vehicle management
- Simple customer relationship management
- Essential for business operations

**Required Database Tables**:
- Customers (reuse from Money Data API)
- CustomerReviews (id, customer_id, rating, comment, review_date, created_at, updated_at)

**API Endpoints**:
- GET /api/v1/customer-data
- GET /api/v1/customers
- POST /api/v1/customers
- GET /api/v1/customers/{id}
- PATCH /api/v1/customers/{id}
- DELETE /api/v1/customers/{id}

**Implementation Steps**:
1. Create Customer and CustomerReview entities
2. Implement repository interfaces
3. Create service layer with customer management
4. Implement controller with CRUD operations
5. Add customer analytics and metrics

---

## Phase 2: Core Business APIs (Week 2)

### 3. Inventory Data API
**Priority**: Medium - Independent Domain
**Complexity**: Medium
**Estimated Time**: 3-4 days

**Data Points**:
- Stock Received Today: 25 items
- Stock Purchased Today: ₹3,200
- Low Stock: 3 items
- Orders Placed Today: 2

**Why Third**:
- Independent domain with self-contained functionality
- Simple inventory tracking and management
- No complex relationships with other systems
- Essential for garage operations

**Required Database Tables**:
- inventory_items (id, name, category, quantity, min_stock, price)
- inventory_transactions (id, item_id, type, quantity, amount, date)
- inventory_orders (id, supplier, total_amount, status, date)

**API Endpoints**:
- GET /api/v1/inventory-data
- GET /api/v1/inventory
- POST /api/v1/inventory
- PATCH /api/v1/inventory/{id}
- DELETE /api/v1/inventory/{id}
- GET /api/v1/inventory/low-stock

**Implementation Steps**:
1. Create InventoryItem and InventoryTransaction entities
2. Implement repository interfaces with custom queries
3. Create service layer with inventory management
4. Implement controller with full CRUD operations
5. Add inventory analytics and alerts

---

### 4. Jobcard Data API
**Priority**: Medium - Requires Basic Vehicle Data
**Complexity**: Medium
**Estimated Time**: 3-4 days

**Data Points**:
- MH-12-AB-1234: In Progress
- DL-01-CD-5678: Completed
- KA-02-EF-9012: Pending

**Why Fourth**:
- Requires basic vehicle management
- Simple status tracking system
- Foundation for appointment management
- Essential for service tracking

**Required Database Tables**:
- Vehicles (reuse from Money Data API)
- JobCards (reuse from Money Data API)
- JobCardStatusHistory (id, job_card_id, status, changed_by, changed_at, notes)

**API Endpoints**:
- GET /api/v1/jobcard-data
- GET /api/v1/job-cards
- POST /api/v1/job-cards
- PATCH /api/v1/job-cards/{id}/status
- GET /api/v1/job-cards/{id}

**Implementation Steps**:
1. Create Vehicle and JobCard entities
2. Implement repository interfaces
3. Create service layer with job tracking
4. Implement controller with status management
5. Add job card analytics and reporting

---

## Phase 3: Advanced Analytics APIs (Week 3)

### 5. Revenue Analytics API
**Priority**: Medium - Builds on Money Data
**Complexity**: Medium
**Estimated Time**: 3-4 days

**Data Points**:
- This Week: ₹89,450
- Last Week: ₹76,200
- Monthly Target: ₹3,50,000
- Achieved: 85%

**Why Fifth**:
- Builds on existing money data infrastructure
- Requires complex date-based aggregations
- Provides advanced financial insights
- Extends payment tracking capabilities

**Required Database Tables**:
- Transactions (reuse from Money Data API)
- RevenueTargets (id, target_amount, period, start_date, end_date, created_at, updated_at)

**API Endpoints**:
- GET /api/v1/revenue-analytics
- GET /api/v1/revenue/weekly
- GET /api/v1/revenue/monthly
- POST /api/v1/revenue-targets
- GET /api/v1/revenue-targets

**Implementation Steps**:
1. Extend existing payment service
2. Create RevenueTarget entity
3. Implement complex aggregation queries
4. Create analytics service layer
5. Implement controller with reporting endpoints

---

### 6. Garage Data API
**Priority**: High - Comprehensive Dashboard
**Complexity**: High
**Estimated Time**: 5-6 days

**Data Points**:
- Total Vehicles: 12
- Total Workers Present: 8
- Parts Received Today: 15
- Total Appointments Today: 6

**Why Last**:
- Multiple dependencies on all previous APIs
- Complex relationships between entities
- Comprehensive dashboard functionality
- Integrates all garage management aspects

**Required Database Tables**:
- Vehicles (reuse from Money Data API)
- Staff (reuse from Money Data API)
- Appointments (id, customer_id, vehicle_id, scheduled_date, status, description, created_at, updated_at)
- PartsReceived (id, part_name, quantity, received_date, supplier, cost, created_at, updated_at)

**API Endpoints**:
- GET /api/v1/garage-data
- GET /api/v1/garage-data?detailed=true
- GET /api/v1/workers
- POST /api/v1/workers
- GET /api/v1/appointments
- POST /api/v1/appointments

**Implementation Steps**:
1. Create comprehensive entity relationships
2. Implement complex aggregation queries
3. Create service layer with dashboard logic
4. Implement controller with detailed/summary views
5. Add comprehensive analytics and reporting

---

## Technical Implementation Guidelines

### Database Design Principles
- Use UUID primary keys for all entities
- Implement proper foreign key relationships
- Add audit fields (created_at, updated_at, created_by, updated_by)
- Use appropriate data types and constraints
- Implement proper indexing strategies
- Single financial_transactions table for all income/expense tracking
- Business rule constraints for data integrity

### API Design Standards
- Follow RESTful conventions
- Implement consistent error handling
- Use proper HTTP status codes
- Add comprehensive validation
- Include pagination for list endpoints

### Service Layer Architecture
- Implement interface-based design
- Use dependency injection
- Follow single responsibility principle
- Implement proper transaction management
- Add comprehensive logging
- Separate income and expense business logic in single service
- Implement aggregation queries for dashboard metrics

### Security Considerations
- Implement JWT-based authentication
- Add role-based access control
- Validate all input data
- Implement rate limiting
- Add audit logging

### Performance Optimization
- Use appropriate database indexes
- Implement query optimization
- Add caching where appropriate
- Use pagination for large datasets
- Monitor query performance

### Testing Strategy
- Unit tests for service layer
- Integration tests for controllers
- Database integration tests
- API endpoint testing
- Performance testing

## Success Metrics

### Phase 1 Success Criteria
- Money Data API returns accurate financial metrics
- Customer Data API provides customer analytics
- All endpoints respond within 200ms
- 100% test coverage for core functionality

### Phase 2 Success Criteria
- Inventory management with real-time stock tracking
- Job card system with status management
- Integration between inventory and job cards
- Comprehensive error handling and validation

### Phase 3 Success Criteria
- Advanced analytics with accurate reporting
- Comprehensive garage dashboard
- Full integration between all systems
- Production-ready performance and security

## Risk Mitigation

### Technical Risks
- Database performance with large datasets
- Complex query optimization
- Integration between multiple systems
- Data consistency across entities

### Mitigation Strategies
- Implement proper indexing from start
- Use database query optimization tools
- Implement comprehensive testing
- Use database transactions for consistency

## Timeline Summary

**Week 1**: Foundation APIs (Money Data, Customer Data)
**Week 2**: Core Business APIs (Inventory, Jobcard)
**Week 3**: Advanced Analytics APIs (Revenue, Garage Data)

**Total Estimated Time**: 3 weeks
**Total API Endpoints**: 30+ endpoints
**Total Database Tables**: 12 tables (with single financial_transactions table)

This phased approach ensures quick wins, builds solid foundations, and delivers comprehensive garage management functionality in a systematic manner. 