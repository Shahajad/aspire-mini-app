# Aspire Loan API

## Overview
Aspire Loan API is a backend service that allows users to register, apply for loans, and make weekly repayments. The application ensures that only admins can register new users and approve loans. Users can view and repay their loans once approved.

## Features
- **User Registration**: Only admins can register new users.
- **Loan Application**: Users can apply for loans by specifying the amount and term.
- **Loan Approval**: Admins can approve loan applications.
- **Repayment Schedule**: Loans are repaid weekly.
- **User Notifications**: Users are notified when their loan is approved.
- **Loan Management**: Users can view and manage their loans, including making repayments.

## Endpoints

### Authentication
- **POST /auth/login**: Authenticate and retrieve a JWT token and use this JWT token in API Header.
- curl example : ```curl -X 'POST' \
  'http://localhost:8080/authenticate' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "userId": "admin",
  "userPassword": "admin"
  }'```

### User Management
- **POST /admin/users**: Register a new user (only admin can create).

### Loan Management
- **POST /loans**: Apply for a loan.
- **GET /loans**: View all loans for the authenticated user.
- **POST /loans/{loanId}/repayments**: Make a repayment for a loan.
- **PUT /admin/loans/{loanId}/approve**: Approve a loan (only admin can approve).

## Design Patterns

### Chain of Command Pattern
The Chain of Command pattern was used because there are many flows in loan management. This pattern allows for the creation of a chain of commands for a flow or sub-flow. For example:
- When a loan is applied, a loan application with a repayment plan needs to be created.
- Once the loan is approved, the user should be notified.

Using this pattern helps in managing complex workflows and ensures that each step in the process is handled appropriately.

## Setup Instructions

### Prerequisites
- Docker (for Docker installation)
- Java 17 or higher (for manual installation)
- Maven (for manual installation)

### Installation

#### Option 1: Using Docker

1. **Install Docker**: Ensure Docker is installed on your system. If not, download and install it from [Docker's official website](https://www.docker.com/get-started).

2. **Clone the repository**
   git clone https://github.com/yourusername/aspire-loan-api.git
   cd aspire-loan-api
3. **Build the Docker image**
   docker build -t aspire-loan-api . 
4. **Run the Docker container**
   docker run -p 8080:8080 aspire-loan-api

#### Option 2: Manual Installation
1. **Install Java 17 and Maven**
   Ensure Java 17 and Maven are installed on your system. If not, download and install them from their respective official websites.

2. **Clone the repository**
   git clone https://github.com/yourusername/aspire-loan-api.git
   cd aspire-loan-api
3. **Build the project**
   ./mvnw clean install
4. Run the application
   java -jar target/aspire-loan-api-0.0.1-SNAPSHOT.jar

#### Access Swagger UI
Open your browser and navigate to http://localhost:8080/swagger-ui/index.html to access the Swagger UI for API documentation and testing.
### Adding JWT Token in Swagger UI
To test the authenticated endpoints using Swagger UI:
1. Open your browser and navigate to `http://localhost:8080/swagger-ui/index.html`.
2. In the top right corner, you will see an "Authorize" button.
3. Click on the "Authorize" button and enter your JWT token in the following format:

### Default Users for Testing
For testing purposes, this service will by default create three users:

1. **Admin User**:
- **ID**: admin
- **Username**: admin
- **Password**: admin
- **Role**: ADMIN

2. **Regular User 1**:
- **ID**: user1
- **Username**: user1
- **Password**: user1
- **Role**: USER

3. **Regular User 2**:
- **ID**: user2
- **Username**: user2
- **Password**: user2
- **Role**: USER