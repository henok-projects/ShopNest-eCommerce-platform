Project Documentation: ShopNest
1. Introduction

ShopNest is a scalable, secure, and efficient eCommerce platform built using a microservices architecture. 
The platform is designed to handle high traffic, ensure data security, and provide a seamless shopping experience. 
The system is composed of 10 microservices, each responsible for a specific domain of the eCommerce business.
 
2. System Architecture
The system follows a microservices architecture with the following components:
1.	API Gateway: Central entry point for all client requests.
2.	Service Discovery (Eureka): Enables dynamic service registration and discovery.
3.	Microservices: 10 independent services, each handling a specific domain.
4.	Event-Driven Communication: Kafka is used for asynchronous communication between services.
5.	Database: MySQL is used for structured data storage.
6.	Containerization: Docker is used to containerize each microservice for easy deployment and scalability.
7.	Security: JWT-based authentication and role-based access control (RBAC) are implemented.
