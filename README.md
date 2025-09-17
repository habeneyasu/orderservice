# 🛒 Order Service - E-Commerce Microservices Platform

[![Java](https://img.shields.io/badge/Java-17+-orange.svg)](https://openjdk.java.net/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.7-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Spring WebFlux](https://img.shields.io/badge/Spring%20WebFlux-Reactive-blue.svg)](https://spring.io/projects/spring-webflux)
[![Apache Kafka](https://img.shields.io/badge/Apache%20Kafka-Messaging-black.svg)](https://kafka.apache.org/)
[![MySQL](https://img.shields.io/badge/MySQL-8.0+-blue.svg)](https://www.mysql.com/)
[![Docker](https://img.shields.io/badge/Docker-Enabled-2496ED.svg)](https://www.docker.com/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

A robust, **reactive Order Management Service** built as part of a comprehensive e-commerce microservices platform. This service handles order processing, inventory management, and orchestrates complex business workflows using reactive programming patterns and event-driven architecture.

## 📋 Table of Contents

- [Overview](#overview)
- [Architecture](#architecture)
- [Features](#features)
- [Technologies](#technologies)
- [Quick Start](#quick-start)
- [API Documentation](#api-documentation)
- [Configuration](#configuration)
- [Monitoring & Observability](#monitoring--observability)
- [Docker Deployment](#docker-deployment)
- [Development](#development)
- [Contributing](#contributing)
- [License](#license)

## 🎯 Overview

The Order Service is a critical component of our e-commerce microservices platform, providing:

- **Reactive Order Processing** using Spring WebFlux for high-throughput operations
- **Event-Driven Architecture** with Apache Kafka for asynchronous communication
- **Service Integration** with User, Payment, and Notification services
- **State Management** with comprehensive order lifecycle handling
- **Caching Layer** with Redis for performance optimization
- **GraphQL Support** for flexible data querying

## 🏗️ Architecture

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   API Gateway   │───▶│   Order Service │───▶│   MySQL         │
│                 │    │   (Reactive)    │    │   Database      │
└─────────────────┘    └─────────────────┘    └─────────────────┘
         │                       │                       │
         │                       ▼                       │
         │              ┌─────────────────┐              │
         │              │   Apache Kafka  │              │
         │              │   Event Bus     │              │
         │              └─────────────────┘              │
         │                       │                       │
         │                       ▼                       │
         │              ┌─────────────────┐              │
         │              │   User Service  │              │
         │              │   Payment Svc   │              │
         │              │   Notification  │              │
         └──────────────┴─────────────────┴──────────────┘
```

## ✨ Features

### 🛒 Order Management
- **Order Creation** with comprehensive validation
- **Order Status Tracking** with state machine pattern
- **Order History** with detailed audit trails
- **Order Search & Filtering** with advanced query capabilities
- **Bulk Order Operations** for administrative tasks

### ⚡ Reactive Programming
- **Non-blocking I/O** using Spring WebFlux
- **Backpressure Handling** for high-load scenarios
- **Reactive Streams** for data processing
- **Asynchronous Processing** with CompletableFuture

### 🔄 Event-Driven Architecture
- **Kafka Integration** for event publishing and consumption
- **Event Sourcing** for order state management
- **Saga Pattern** for distributed transactions
- **Eventual Consistency** across microservices

### 🔗 Service Integration
- **User Service Integration** for customer validation
- **Payment Service Integration** for payment processing
- **Notification Service Integration** for order updates
- **Load Balancing** with Spring Cloud LoadBalancer

### 📊 Advanced Features
- **GraphQL API** for flexible data querying
- **Redis Caching** for performance optimization
- **Strategy Pattern** for different order processing types
- **State Machine** for order lifecycle management
- **Comprehensive Logging** with structured JSON format

## 🛠️ Technologies

| Category | Technology | Version |
|----------|------------|---------|
| **Framework** | Spring Boot | 3.2.7 |
| **Reactive** | Spring WebFlux | 3.0+ |
| **Messaging** | Apache Kafka | 3.0+ |
| **Database** | MySQL | 8.0+ |
| **ORM** | Spring Data JPA | 3.0+ |
| **Caching** | Redis | 7.0+ |
| **API** | GraphQL | 3.0+ |
| **Documentation** | OpenAPI 3.0 (Swagger) | 3.0+ |
| **Service Discovery** | Eureka Client | 3.0+ |
| **Load Balancing** | Spring Cloud LoadBalancer | 3.0+ |
| **Monitoring** | Spring Actuator | 3.0+ |
| **Containerization** | Docker | Latest |
| **Build Tool** | Maven | 3.6+ |
| **Java** | OpenJDK | 17+ |

## 🚀 Quick Start

### Prerequisites

- **Java 17+** (OpenJDK recommended)
- **Maven 3.6+**
- **Docker & Docker Compose**
- **MySQL 8.0+** (or use Docker)
- **Apache Kafka** (or use Docker)
- **Redis** (or use Docker)

### 1. Clone the Repository

```bash
git clone https://github.com/habeneyasu/e-commerce.git
cd e-commerce/orderservice
```

### 2. Environment Setup

Create a `.env` file in the project root:

```bash
# Database Configuration
SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3308/order_service_db
SPRING_DATASOURCE_USERNAME=user
SPRING_DATASOURCE_PASSWORD=your_secure_password

# Kafka Configuration
KAFKA_BOOTSTRAP_SERVERS=localhost:9092
KAFKA_TOPIC_ORDER_EVENT=order-event

# Redis Configuration
REDIS_HOST=localhost
REDIS_PORT=6379

# Service Discovery
EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE=http://localhost:8761/eureka
```

### 3. Run with Docker Compose (Recommended)

```bash
# Start all services including dependencies
docker-compose up -d

# Check service status
docker-compose ps

# View logs
docker-compose logs -f order-service
```

### 4. Run Locally

```bash
# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

### 5. Verify Installation

- **Service Health**: http://localhost:8182/api/v1/actuator/health
- **API Documentation**: http://localhost:8182/api/v1/swagger-ui/index.html
- **GraphQL Playground**: http://localhost:8182/api/v1/graphql

## 📚 API Documentation

### Base URL
```
http://localhost:8182/api/v1
```

### REST API Endpoints

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| `POST` | `/orders` | Create new order | ✅ |
| `GET` | `/orders` | Get all orders (paginated) | ✅ |
| `GET` | `/orders/{id}` | Get order by ID | ✅ |
| `PUT` | `/orders/{id}` | Update order | ✅ |
| `DELETE` | `/orders/{id}` | Cancel order | ✅ |
| `GET` | `/orders/user/{userId}` | Get orders by user | ✅ |
| `PUT` | `/orders/{id}/status` | Update order status | ✅ |

### GraphQL Endpoints

| Endpoint | Description |
|----------|-------------|
| `POST /graphql` | GraphQL query endpoint |
| `GET /graphql` | GraphQL playground |

### Item Management Endpoints

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| `GET` | `/items` | Get all items | ✅ |
| `GET` | `/items/{id}` | Get item by ID | ✅ |
| `POST` | `/items` | Create new item | ✅ |
| `PUT` | `/items/{id}` | Update item | ✅ |
| `DELETE` | `/items/{id}` | Delete item | ✅ |

### Example API Usage

#### 1. Create Order
```bash
curl -X POST http://localhost:8182/api/v1/orders \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -d '{
    "userId": 1,
    "items": [
      {
        "itemId": 1,
        "quantity": 2,
        "price": 29.99
      }
    ],
    "totalAmount": 59.98,
    "shippingAddress": "123 Main St, City, State"
  }'
```

#### 2. Get Order by ID
```bash
curl -X GET http://localhost:8182/api/v1/orders/1 \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

#### 3. Update Order Status
```bash
curl -X PUT http://localhost:8182/api/v1/orders/1/status \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -d '{
    "status": "SHIPPED"
  }'
```

#### 4. GraphQL Query
```graphql
query {
  orders(userId: 1) {
    id
    orderCode
    status
    totalAmount
    items {
      id
      name
      price
      quantity
    }
    createdAt
  }
}
```

## ⚙️ Configuration

### Application Properties

The service uses Spring Boot's configuration system with profiles:

- **`application.properties`** - Base configuration
- **`application-container.properties`** - Container-specific settings
- **`application-dev.properties`** - Development environment
- **`application-prod.properties`** - Production environment

### Key Configuration Options

```properties
# Server Configuration
server.port=8182
server.servlet.context-path=/api/v1

# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3308/order_service_db
spring.datasource.username=user
spring.datasource.password=${SPRING_DATASOURCE_PASSWORD}
spring.jpa.hibernate.ddl-auto=validate

# Kafka Configuration
spring.kafka.bootstrap-servers=${KAFKA_BOOTSTRAP_SERVERS}
spring.kafka.producer.key-serializer=org.apache.kafka.common.serialization.StringSerializer
spring.kafka.producer.value-serializer=org.apache.kafka.common.serialization.StringSerializer

# Redis Configuration
spring.redis.host=${REDIS_HOST}
spring.redis.port=${REDIS_PORT}

# Service Discovery
eureka.client.service-url.defaultZone=${EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE}
```

## 📊 Monitoring & Observability

### Spring Actuator Endpoints

| Endpoint | Description |
|----------|-------------|
| `/actuator/health` | Service health status |
| `/actuator/info` | Application information |
| `/actuator/metrics` | Application metrics |

### Business Metrics

The service tracks various business metrics:

- **Order Creation Rate** - Orders created per minute
- **Order Processing Time** - Average time to process orders
- **Order Status Distribution** - Count by status
- **Error Rate** - Failed operations percentage
- **Cache Hit Rate** - Redis cache performance

### Logging

Structured JSON logging with different levels:

```json
{
  "timestamp": "2024-01-15T10:30:00.000Z",
  "level": "INFO",
  "logger": "com.ecommerce.orderservice.service.OrderService",
  "message": "Order created successfully",
  "orderId": 12345,
  "userId": 67890,
  "totalAmount": 99.99
}
```

## 🐳 Docker Deployment

### Dockerfile

```dockerfile
FROM openjdk:17-jdk-slim
VOLUME /tmp
COPY target/order_app.jar app.jar
EXPOSE 8182
ENTRYPOINT ["java","-jar","/app.jar"]
```

### Docker Compose

```yaml
version: '3.8'
services:
  order-service:
    build: ./orderservice
    ports:
      - "8182:8182"
    environment:
      - SPRING_DATASOURCE_URL=jdbc:mysql://order-service-db:3306/order_service_db
      - SPRING_DATASOURCE_USERNAME=user
      - SPRING_DATASOURCE_PASSWORD=test@123
      - KAFKA_BOOTSTRAP_SERVERS=kafka:9092
      - REDIS_HOST=redis
    depends_on:
      - order-service-db
      - kafka
      - redis
    networks:
      - microservice-net

  order-service-db:
    image: mysql:8.0
    environment:
      - MYSQL_DATABASE=order_service_db
      - MYSQL_USER=user
      - MYSQL_PASSWORD=test@123
      - MYSQL_ROOT_PASSWORD=test@123
    ports:
      - "3308:3306"
    networks:
      - microservice-net
```

### Deployment Commands

```bash
# Build and start services
docker-compose up --build

# Run in background
docker-compose up -d

# View logs
docker-compose logs -f order-service

# Stop services
docker-compose down
```

## 🛠️ Development

### Project Structure

```
orderservice/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/ecommerce/orderservice/
│   │   │       ├── config/          # Configuration classes
│   │   │       ├── controller/      # REST & GraphQL controllers
│   │   │       ├── exception/       # Exception handling
│   │   │       ├── integration/     # External service integration
│   │   │       ├── model/           # JPA entities
│   │   │       ├── modeldto/        # Data Transfer Objects
│   │   │       ├── repository/      # Data repositories
│   │   │       ├── service/         # Business logic & strategies
│   │   │       └── util/            # Utility classes
│   │   └── resources/
│   │       ├── application.properties
│   │       └── db/migration/        # Database migrations
│   └── test/                        # Test classes
├── Dockerfile
├── pom.xml
└── README.md
```

### Design Patterns Used

- **Strategy Pattern** - Different order processing strategies
- **State Pattern** - Order status management
- **Repository Pattern** - Data access abstraction
- **Integration Pattern** - External service communication
- **Saga Pattern** - Distributed transaction management

### Running Tests

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=OrderServiceTest

# Run tests with coverage
mvn test jacoco:report
```

### Code Quality

The project follows:
- **Java Coding Standards** (Google Java Style)
- **Spring Boot Best Practices**
- **Reactive Programming Principles**
- **RESTful API Design Principles**
- **SOLID Principles**
- **Clean Architecture**

## 🤝 Contributing

We welcome contributions! Please follow these steps:

1. **Fork** the repository
2. **Create** a feature branch: `git checkout -b feature/amazing-feature`
3. **Commit** your changes: `git commit -m 'Add amazing feature'`
4. **Push** to the branch: `git push origin feature/amazing-feature`
5. **Open** a Pull Request

### Development Guidelines

- Write **unit tests** for new features
- Follow **existing code style**
- Update **documentation** as needed
- Ensure **all tests pass**
- Add **appropriate logging**
- Consider **reactive programming** patterns

## 📄 License

This project is licensed under the **MIT License** - see the [LICENSE](LICENSE) file for details.

## 📞 Contact

**Haben Eyasu** - Senior Backend Developer

- **Email**: [habeneyasu@gmail.com](mailto:habeneyasu@gmail.com)
- **LinkedIn**: [linkedin.com/in/habeneyasu](https://linkedin.com/in/habeneyasu)
- **GitHub**: [github.com/habeneyasu](https://github.com/habeneyasu)

---

## 🎯 Project Status

- ✅ **Core Features**: Complete
- ✅ **Reactive Programming**: WebFlux implementation
- ✅ **Event-Driven Architecture**: Kafka integration
- ✅ **Service Integration**: User, Payment, Notification services
- ✅ **API Documentation**: OpenAPI 3.0 with Swagger UI
- ✅ **GraphQL Support**: Flexible data querying
- ✅ **Caching**: Redis integration
- ✅ **Docker Support**: Containerized deployment ready
- 🔄 **Testing**: Unit and integration tests in progress
- 🔄 **Performance**: Load testing and optimization ongoing

---

**Built with ❤️ by [Haben Eyasu](https://github.com/habeneyasu)**
