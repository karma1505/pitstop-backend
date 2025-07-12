# Garage Management Backend

A scalable Spring Boot backend for a garage management system with JWT authentication, PostgreSQL database, and AWS-ready configuration.

## Technology Stack

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Security** with JWT
- **PostgreSQL** database
- **Flyway** for database migrations
- **Maven** build system
- **AWS SDK** for future cloud integration

## Project Structure

```
src/
├── main/
│   ├── java/com/garage/backend/
│   │   ├── config/           # Configuration classes
│   │   ├── controller/       # REST controllers
│   │   ├── dto/             # Data Transfer Objects
│   │   ├── entity/          # JPA entities
│   │   ├── exception/       # Exception handlers
│   │   ├── repository/      # Data access layer
│   │   ├── security/        # JWT and security components
│   │   ├── service/         # Business logic
│   │   └── GarageBackendApplication.java
│   └── resources/
│       ├── db/migration/    # Database migration scripts
│       └── application.yml  # Application configuration
```

## Prerequisites

- Java 17 or higher
- PostgreSQL 12 or higher
- Maven 3.6 or higher

## Setup Instructions

### 1. Database Setup

Create a PostgreSQL database:

```sql
CREATE DATABASE garage_db_dev;
```

### 2. Configuration

Update the database configuration in `src/main/resources/application-dev.yml`:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/garage_db_dev
    username: your_username
    password: your_password
```

### 3. JWT Secret

Update the JWT secret in `src/main/resources/application.yml`:

```yaml
spring:
  security:
    jwt:
      secret: your-256-bit-secret-key-here-change-in-production
```

### 4. Build and Run

```bash
# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## API Endpoints

### Base URL: `http://localhost:8080/api/v1`

### Authentication Endpoints

#### 1. Register User
- **URL:** `POST /admin/register`
- **Description:** Register a new garage owner
- **Request Body:**
```json
{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "password": "SecurePass123!",
  "confirmPassword": "SecurePass123!",
  "state": "California",
  "city": "Los Angeles",
  "pincode": "90210",
  "mobileNumber": "1234567890",
  "garageName": "John's Auto Repair"
}
```

#### 2. Login User
- **URL:** `POST /admin/login`
- **Description:** Authenticate user and get JWT token
- **Request Body:**
```json
{
  "email": "john.doe@example.com",
  "password": "SecurePass123!"
}
```

### Response Format

#### Success Response
```json
{
  "success": true,
  "message": "Authentication successful",
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "refreshToken": "eyJhbGciOiJIUzI1NiJ9...",
  "tokenType": "Bearer",
  "expiresIn": 86400000,
  "userInfo": {
    "id": 1,
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com",
    "garageName": "John's Auto Repair",
    "state": "California",
    "city": "Los Angeles",
    "createdAt": "2024-01-01T10:00:00"
  }
}
```

#### Error Response
```json
{
  "success": false,
  "message": "Error message here"
}
```

## Password Requirements

- Minimum 8 characters
- At least one uppercase letter
- At least one lowercase letter
- At least one digit
- At least one special character (@#$%^&+=!)

## Security Features

- **JWT Authentication:** Stateless authentication with access and refresh tokens
- **Password Encryption:** BCrypt password hashing
- **CORS Configuration:** Cross-origin resource sharing enabled
- **Input Validation:** Comprehensive request validation
- **Error Handling:** Global exception handling with consistent error responses

## Database Schema

### Users Table
- `id` (BIGSERIAL PRIMARY KEY)
- `first_name` (VARCHAR(50))
- `last_name` (VARCHAR(50))
- `email` (VARCHAR(100) UNIQUE)
- `password` (VARCHAR(255))
- `state` (VARCHAR(50))
- `city` (VARCHAR(50))
- `pincode` (VARCHAR(6))
- `mobile_number` (VARCHAR(11))
- `garage_name` (VARCHAR(100))
- `is_active` (BOOLEAN)
- `created_at` (TIMESTAMP)
- `updated_at` (TIMESTAMP)

## Environment Configuration

### Development
- Profile: `dev`
- Database: Local PostgreSQL
- Logging: DEBUG level
- SQL logging: Enabled

### Production
- Profile: `prod`
- Database: Environment variables
- Logging: INFO level
- SQL logging: Disabled

## Monitoring

The application includes Spring Boot Actuator for monitoring:
- Health checks: `/actuator/health`
- Application info: `/actuator/info`
- Metrics: `/actuator/metrics`

## Future Enhancements

- Refresh token endpoint
- Password reset functionality
- Email verification
- Role-based access control
- AWS S3 integration for file uploads
- Redis caching
- API rate limiting
- Swagger/OpenAPI documentation

## Testing

```bash
# Run tests
mvn test

# Run with coverage
mvn jacoco:report
```

## Deployment

### Local Development
```bash
mvn spring-boot:run -Dspring.profiles.active=dev
```

### Production
```bash
mvn clean package
java -jar target/garage-backend-1.0.0.jar --spring.profiles.active=prod
```

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests
5. Submit a pull request

## License

This project is licensed under the MIT License. 