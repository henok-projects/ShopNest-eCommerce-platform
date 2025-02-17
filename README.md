Project Documentation: ShopNest

1. Introduction
ShopNest is a scalable, secure, and efficient eCommerce platform built using a microservices architecture. The platform is designed to handle high traffic, ensure data security, and provide a seamless shopping experience. The system is composed of 10 microservices, each responsible for a specific domain of the eCommerce business.

2. System Architecture

The system follows a microservices architecture with the following components:

 . API Gateway: Central entry point for all client requests.

 . Service Discovery (Eureka): Enables dynamic service registration and discovery.

 . Microservices: 10 independent services, each handling a specific domain.

 . Event-Driven Communication: Kafka is used for asynchronous communication between services.

 . Database: MySQL is used for structured data storage.

 . Containerization: Docker is used to containerize each microservice for easy deployment and scalability.

 . Security: JWT-based authentication and role-based access control (RBAC) are implemented.

 # 

 1. Eureka Server (Service Discovery)
    
Why Eureka?

Service Discovery: Eureka allows microservices to register themselves and discover other services dynamically.

Load Balancing: Enables client-side load balancing using Ribbon or Spring Cloud LoadBalancer.

Fault Tolerance: Integrates with Hystrix for fault tolerance.
