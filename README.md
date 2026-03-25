# Library-Microservices
Library management system built with Spring Boot to demonstrate a micoservice architecture and synchonous communication between services.
Future Updates will expand its features. This future updates will be explained later.
# Technologies Used
- Java 17
- Spring Boot 3.4.4
- Spring Cloud (Eureka, Config, Gateway)
- JWT (JSON Web Tokens)
- JPA
- SQL SERVER
- OpenFeign to communicate services
# Architecture Overview
The project consists of independent services, this are the services used:
- config-service: Provides configuration for all services.
- eureka-service: Service discovery server. All services are registered with Eureka, except
the eureka server itself and the config server.
- gateway-service: API Gateway for routing requests.
- user-service: Manages user registrations and authentication.
- book-service: Manages book information.
- loan-service: Manages borrowing books.
Communication between services uses OpenFeign.
# Current Features
- User registration and login with password hashing using BCrypt.
- JWT (JSON Web Tokens) based authentication with access and refresh token.
- Add, and list books by id, by book name, by author name and by availabilty.
- Borrow books.
- Track borrowed books by a user.
# Future Features
- Integrate JWT authentication accross all microservices.
- Asynchronous communication between services using Kafka.
- Add testing using JUnit and Mockito.




