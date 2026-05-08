# Library-Microservices
Library management system built with Spring Boot to demonstrate a microservice architecture and synchronous communication between services.
All API requests are limited to 10 requests per minute per user to ensure system stability.

## Technologies Used
- Java 17
- Spring Boot 3.4.4
- JUnit and Mockito for unit testing
- Bucket4J to limit requests
- Spring Cloud (Eureka, Config, Gateway)
- JWT (JSON Web Tokens)
- JPA
- SQL SERVER
- OpenFeign for synchronous service communication
- Apache Kafka for asynchronous event-driven communication

## Architecture Overview
The project consists of independent services, these are the services used:
- **config-service**: Provides configuration for all services.
- **eureka-service**: Service discovery server. All services are registered with Eureka, except
the eureka server itself and the config server.
- **gateway-service**: API Gateway for routing requests.
- **user-service**: Manages user registrations and authentication.
- **book-service**: Manages book information.
- **loan-service**: Manages borrowing books.
- **reservation-service**: Manages reservations.

## Use case using Kafka
### 1 - Loan creation
 - Loan service attempts to create a loan.
 - It checks whether the book is in stock.
 - If book does not have stock available, loan service publishes event `book_out_of_stock`.
 - If it does, loan service publishes event `loan_created`.
### 2 - Handling event `loan_created`
 - Book service consumes the event and decreases book stock by one.
### 3 - Handling event `book_out_of_stock`
 - Reservation service consumes the event and stores the reservation in database.
### 4 - Book returned
 - When a book is returned, loan service publishes event `book_returned`.
 - Book service consumes the event and increases book stock by one.
 - Reservation service also consumes the event and checks if there is a pending reservation.
 - If so, Reservation service publishes event `reservation_ready`.
### 5 - Handling event `reservation_ready`
 - Loan service consumes the event and creates a new loan.

## Current Features
- User registration and login with password hashing using BCrypt.
- JWT (JSON Web Tokens) based authentication with access and refresh token across all microservices.
- Add and list books by ID, book name, or author name.
- Borrow and return books.
- Track borrowed books by a user.
- Creating events using Apache Kafka (example: `book_out_of_stock`).
- Add testing using JUnit and Mockito.






