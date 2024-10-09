# Order Service - E-commerce Application
This is the Order Service for an e-commerce application. It manages order processing and integrates with other services such as the User Service, Payment Service, and Notification Service using WebClient for interservice communication. 
Additionally, it leverages Apache Kafka for asynchronous communication between services, containerization with Docker, Eureka for service discovery, and a load balancer for handling traffic distribution.
# Table of Contents
Technologies <br>
Features <br>
Installation <br>
Usage <br>
Endpoints <br>
Swagger/OpenAPI <br>
Docker <br>
Environment Variables <br>
Contributing <br>
License <br>
Contact <br>
# Technologies
Spring Boot 3.2.7 - Backend framework <br>
Spring WebFlux - Reactive programming support <br>
Apache Kafka - Asynchronous messaging <br>
Eureka Client - Service discovery <br>
Spring Cloud LoadBalancer - Load balancing across services <br>
Spring Actuator - Monitoring and health checks <br>
Lombok - Simplifies Java code (getters/setters) <br>
MySQL - Database for order management <br>
Docker - Containerization for deployment <br>
OpenAPI (Swagger) - API documentation <br>
Spring Security - Security framework for securing endpoints <br>
# Features
Inter-service communication using WebClient (User, Payment, and Notification services) <br>
Apache Kafka for asynchronous communication with Payment and Notification services <br>
Containerization using Docker <br>
Eureka Client for service discovery <br>
RESTful API for order management <br>
Spring WebFlux for reactive, non-blocking request handling <br>
Exception Handling with global error handling <br>
Logging with proper levels (INFO, ERROR, DEBUG) <br>
Spring Actuator for service health monitoring <br>
OpenAPI/Swagger for interactive API documentation <br>
Load Balancing across service instances using Spring Cloud LoadBalancer <br>
# Installation
# Prerequisites
Ensure you have the following installed: <br>

Java 17+ (JDK) <br>
Maven 3.6+ <br>
Docker <br>
Kafka <br>
Steps to Run Locally <br>
1.Clone the repository: <br>
  git clone https://github.com/habeneyasu/orderservice <br>
  cd order-service <br>
2.Set up environment variables: Create a .env file in the root directory and set the following variables:<br>
  SPRING_DATASOURCE_PASSWORD=your_db_password <br>
  JWT_SECRET=your_jwt_secret <br>
3.Build the project:<br>
  mvn clean install  <br>
4.Run the application:<br>
  mvn spring-boot:run <br>
# Using Docker <br>
To run the service using Docker, follow these steps:<br>
1.Build and start the services using Docker Compose: <br>
  docker-compose up --build <br>
2.The application will be available at http://localhost:8182.<br>
# Usage
The Order Service exposes several RESTful API endpoints for order management and communicates with other services for payment and notifications.

# Swagger/OpenAPI
This service is fully documented with OpenAPI 3 using Swagger.<br>

Once the service is running, access the API documentation at:<br>

URL: http://localhost:8182/swagger-ui/index.html<br>
The documentation includes:<br>

Request parameters <br>
Response models <br>
Ability to test API endpoints directly from the UI <br>

# Contributing
If you wish to contribute to this project:

1.Fork the repository. <br>
2.Create a new branch for your feature or bug fix.<br>
3.Commit your changes with clear messages. <br>
4.Open a pull request.<br>
# License
This project is licensed under the MIT License.<br>

# Contact
For any questions, feel free to reach out:

Email: habeneyasu@gmail.com <br>
LinkedIn: https://www.linkedin.com/in/habeneyasu
