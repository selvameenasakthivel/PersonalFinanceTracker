# FinanceTracker

## Introduction
This project is a Spring Boot based RESTful API service for a basic Personal Finance Tracker. It allows users to record expenses, incomes, and budgets, and generate financial reports. The project supports an in-memory data source or a legacy database.

## Features
- **Expenses:**
  - Record a new expense
  - Update an existing expense
  - Delete an expense
- **Incomes:**
  - Record a new income
- **Reports:**
  - Generate a financial report
- **Budgets:**
  - Set up a new budget
  - Retrieve all budgets
- **Books:**
  - Retrieve all books with filters for searching
  
## Requirements
- Java 8 or higher
- Maven 3.6.0 or higher
- Spring Boot 2.5.4 or higher
- H2 Database (for development and testing)
- Swagger (for API documentation)

## Dependencies
- Spring Boot Starter Web
- Spring Boot Starter Data JPA
- Spring Boot Starter Validation
- H2 Database (for in-memory DB)
- Lombok

## API Endpoints
### Expenses
- **POST** `/api/expenses` - Record a new expense
- **PUT** `/api/expenses/{expenseId}` - Update an existing expense
- **DELETE** `/api/expenses/{expenseId}` - Delete an expense

### Incomes
- **POST** `/api/incomes` - Record a new income

### Reports
- **GET** `/api/reports` - Generate a financial report

### Budgets
- **POST** `/api/budgets` - Set up a new budget
- **GET** `/api/budgets` - Retrieve all budgets

### Books
- **GET** `/api/books` - Retrieve all books with filters for searching