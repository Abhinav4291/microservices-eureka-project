# Microservices Eureka Project

## Description

This project is a Microservices based application built using Spring Boot and Spring Cloud Netflix Eureka to demonstrate service registration, service discovery, and inter service communication. The application is divided into four independent services: user service, product service, order service, and service registry, where each service is responsible for a specific business functionality. The application demonstrates service registration and discovery through Eureka, and the order service communicates with the other business services using the service names registered in the registry.

## Services

- `service-registry`: Eureka server for service registration and discovery
- `user-service`: User registration and profile management
- `product-service`: Product catalog management
- `order-service`: Order placement and retrieval

## Prerequisites

- JDK 21 or later
- Maven 3.9 or later
- Spring Tools Suite or Eclipse with Maven support

## Technologies Used

- Java
- Spring Boot
- Spring Cloud Netflix Eureka
- Spring Data JPA
- OpenFeign
- H2 Database
- Maven

## Setup

1. Clone the repository.
2. Open the project in Spring Tools Suite or Eclipse as an existing Maven project.
3. Update Maven dependencies if required.
4. Ensure ports `8761`, `8081`, `8082`, and `8083` are available.

## Run

Start the services in the following order:

1. `service-registry`
2. `user-service`
3. `product-service`
4. `order-service`

After startup, open `http://localhost:8761` to verify that all services are registered in Eureka.

## API Endpoints

- `http://localhost:8081/api/users`
- `http://localhost:8082/api/products`
- `http://localhost:8083/api/orders`

These endpoints can be used to verify application output after the services are running.
