# Library-Microservices
Library management system built with Spring Boot to demonstrate a microservice architecture and synchronous communication between services.
Future updates will expand its features. These future updates will be explained later.

## Technologies Used
- Java 17
- Spring Boot 3.4.4
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

## Current Features
- User registration and login with password hashing using BCrypt.
- JWT (JSON Web Tokens) based authentication with access and refresh token across all microservices.
- Add and list books by ID, book name, or author name.
- Borrow and return books.
- Track borrowed books by a user.
- Creating events using Apache Kafka (example: `book_out_of_stock`).

## Future Features
- Consume events using Apache Kafka.
- Add testing using JUnit and Mockito.




