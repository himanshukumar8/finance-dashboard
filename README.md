# Finance Dashboard Backend API

## Overview

This project is a backend REST API for managing financial records, users, and categories in a finance dashboard system.

It supports:

- User management
- Category management
- Financial record CRUD operations
- Filtering by type
- Filtering by date range
- Financial summaries
- Pagination support
- Global exception handling

The system is designed using a layered architecture to demonstrate clean backend engineering practices.

---

## Tech Stack

- Java 17
- Spring Boot
- Spring Data JPA
- Hibernate
- PostgreSQL (or H2)
- Maven
- Swagger (Springdoc OpenAPI)
- Lombok

---

## Project Structure

```
src/main/java/com/finance/dashboard

├── user
│   ├── controller
│   ├── service
│   ├── repository
│   ├── entity
│   └── dto
│
├── category
│   ├── controller
│   ├── service
│   ├── repository
│   ├── entity
│   └── dto
│
├── record
│   ├── controller
│   ├── service
│   ├── repository
│   ├── entity
│   └── dto
│
├── common
│   └── enums
│
└── exception
    └── GlobalExceptionHandler
```

Architecture Flow:

```
Controller → Service → Repository → Database
```

---

## Setup Instructions

### 1. Clone Repository

```
git clone https://github.com/himanshukumar8/finance-dashboard.git
cd finance-dashboard-backend
```

---

### 2. Configure Database

Update `application.properties`:

#### PostgreSQL Configuration

```
spring.datasource.url=jdbc:postgresql://localhost:5432/finance_db
spring.datasource.username=your_username
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

#### H2 Configuration (Alternative)

```
spring.datasource.url=jdbc:h2:mem:testdb
spring.jpa.hibernate.ddl-auto=update
spring.h2.console.enabled=true
```

---

### 3. Build Project

```
mvn clean install
```

---

### 4. Run Application

```
mvn spring-boot:run
```

Application runs at:

```
http://localhost:8080
```

Swagger UI:

```
http://localhost:8080/swagger-ui/index.html
```

---

# API Endpoints

## User APIs

### Create User

```
POST /api/users
```

Request Body:

```
{
  "name": "John Doe",
  "email": "john@example.com"
}
```

---

## Category APIs

### Create Category

```
POST /api/categories
```

Request Body:

```
{
  "name": "Food"
}
```

### Get All Categories

```
GET /api/categories
```

---

## Financial Record APIs

### Create Record

```
POST /api/records
```

Request Body:

```
{
  "userId": "uuid",
  "categoryId": "uuid",
  "amount": 250.01,
  "type": "EXPENSE",
  "description": "Lunch",
  "recordDate": "2026-04-05"
}
```

---

### Get All Records (Paginated)

```
GET /api/records?page=0&size=5
```

Supports:

- Pagination  
- Large dataset handling  

---

### Get Records By User

```
GET /api/records/user/{userId}
```

---

### Get Records By Type

```
GET /api/records/user/{userId}/type/{type}
```

Example:

```
type = INCOME or EXPENSE
```

---

### Get Records Between Dates

```
GET /api/records/date-range?start=2026-04-01&end=2026-04-30
```

---

### Get Financial Summary

```
GET /api/records/summary/{userId}
```

Example Response:

```
{
  "totalIncome": 500,
  "totalExpense": 250.01,
  "balance": 249.99,
  "totalRecords": 2
}
```

---

### Update Record

```
PUT /api/records/{recordId}
```

---

### Delete Record

```
DELETE /api/records/{recordId}
```

---

# Pagination Support

Pagination is implemented using **Spring Pageable**.

Example:

```
GET /api/records?page=0&size=2
```

Example Response:

```
{
  "content": [],
  "totalElements": 10,
  "totalPages": 5,
  "size": 2,
  "number": 0
}
```

This ensures scalable data retrieval.

---

# Exception Handling

Global exception handling is implemented using:

```
GlobalExceptionHandler
```

Handles:

- Resource Not Found
- Invalid Requests
- Runtime Exceptions

Example Response:

```
{
  "timestamp": "2026-04-05T12:00:00",
  "status": 404,
  "error": "Resource Not Found",
  "message": "Record not found"
}
```

---

# Assumptions

- Each record belongs to a valid user
- Each record belongs to a valid category
- UUID is used as primary keys
- Financial record types are limited to:

```
INCOME
EXPENSE
```

- Pagination is applied where large datasets are expected

---

# Design Decisions

Several design decisions were made to improve maintainability and scalability.

## Layered Architecture

Separates:

- Controller
- Service
- Repository

Improves:

- Code readability
- Testability
- Maintainability

---

## DTO Usage

DTOs are used to:

- Prevent entity exposure
- Control response format
- Improve API stability

---

## UUID Primary Keys

Chosen instead of auto-increment IDs to:

- Improve uniqueness
- Avoid collision
- Support distributed systems

---

## Pagination Support

Added to:

- Prevent large payloads
- Improve performance
- Enable scalable APIs

---

# Tradeoffs

Some tradeoffs were made:

- No authentication layer implemented (out of scope)
- Basic validation used instead of advanced validation frameworks
- Focus prioritized on core backend functionality

---

# Future Improvements

Possible future enhancements:

- JWT Authentication
- Role-based Authorization
- Redis Caching
- Unit Testing
- Integration Testing
- API Rate Limiting

---

# Testing

All APIs were tested using:

```
Swagger UI
```

Available at:

```
http://localhost:8080/swagger-ui/index.html
```

---

# Author

**Himanshu Kumar**
