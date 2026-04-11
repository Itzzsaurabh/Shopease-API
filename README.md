# ShopEase — E-Commerce REST API

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue)
![JWT](https://img.shields.io/badge/Auth-JWT-red)
![Swagger](https://img.shields.io/badge/Docs-Swagger%20UI-85EA2D)

A **production-ready RESTful E-Commerce backend API** built with Java Spring Boot. Features complete user authentication with JWT, product management, shopping cart, and order processing — following industry-standard layered architecture.

---

## Tech Stack

| Technology | Purpose |
|------------|---------|
| Java 17 | Core language |
| Spring Boot 3 | Application framework |
| Spring Security | Authentication & authorization |
| JWT (JSON Web Token) | Stateless auth tokens |
| Spring Data JPA + Hibernate | ORM & database interaction |
| MySQL | Relational database |
| BCrypt | Password hashing |
| Swagger / OpenAPI 3.0 | API documentation |
| Maven | Build tool |

---

## Features

- User registration and login with JWT token-based authentication
- BCrypt password hashing — passwords never stored in plain text
- Role-based access control (USER / ADMIN)
- Full Product CRUD — create, read, update, delete products
- Product search by name (case-insensitive)
- Shopping cart — add items, auto-merge duplicate products, remove items
- Order placement with automatic total calculation
- Cart auto-clears after order is placed
- Price at purchase time saved permanently in order_items
- Global exception handling — clean error responses for all endpoints
- Full API documentation via Swagger UI

---

## Architecture

The project follows a clean 3-tier layered architecture:

```
Client (Postman / Frontend)
        ↓
Controller Layer   →  Handles HTTP requests and responses
        ↓
Service Layer      →  Business logic and rules
        ↓
Repository Layer   →  Database operations via Spring Data JPA
        ↓
MySQL Database     →  Persistent data storage
```

Every request is intercepted by **JwtAuthFilter** before reaching the controller — invalid or missing tokens are rejected with 401 Unauthorized automatically.

---

## Database Schema

```
users         → id, name, email, password, role
products      → id, name, description, price, stock, category
cart_items    → id, user_id (FK), product_id (FK), quantity
orders        → id, user_id (FK), total_amount, status, created_at
order_items   → id, order_id (FK), product_id (FK), quantity, price_at_purchase
```

---

## How to Run Locally

### Prerequisites
- Java 17+
- MySQL 8.0+
- Maven (or use the included wrapper)

### Steps

**1. Clone the repository**
```bash
git clone https://github.com/Itzzsaurabh/Shopease-API.git
cd Shopease-API
```

**2. Create the database**
```sql
CREATE DATABASE shopease_db;
```

**3. Configure application.properties**
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/shopease_db
spring.datasource.username=root
spring.datasource.password=YOUR_MYSQL_PASSWORD
```

**4. Run the application**
```bash
.\mvnw spring-boot:run
```

**5. Open Swagger UI**
```
http://localhost:8080/swagger-ui/index.html
```

---

## API Endpoints

### Auth
| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | /api/auth/register | Register new user | No |
| POST | /api/auth/login | Login and get JWT token | No |

### Products
| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| GET | /api/products | Get all products | Yes |
| GET | /api/products/{id} | Get product by ID | Yes |
| GET | /api/products/search?name= | Search products by name | Yes |
| POST | /api/products | Create new product | Yes |
| PUT | /api/products/{id} | Update product | Yes |
| DELETE | /api/products/{id} | Delete product | Yes |

### Cart
| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| GET | /api/cart | View current user's cart | Yes |
| POST | /api/cart/add?productId=&quantity= | Add item to cart | Yes |
| DELETE | /api/cart/{itemId} | Remove item from cart | Yes |

### Orders
| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | /api/orders/place | Place order from cart | Yes |
| GET | /api/orders/my | View my order history | Yes |

---

## How to Test

All endpoints can be tested via **Swagger UI** at:
```
http://localhost:8080/swagger-ui/index.html
```

Or import into **Postman**:
1. Register → copy the JWT token from response
2. Add header to all requests: `Authorization: Bearer <your_token>`
3. Create products → add to cart → place order → view orders

---

## Key Design Decisions

**Why JWT?**
Stateless authentication — no session storage needed. Scales horizontally. Token contains the user identity so every request is self-contained.

**Why BCrypt?**
Industry-standard password hashing with salt. Even if the database is compromised, passwords cannot be reversed.

**Why Spring Data JPA?**
Eliminates boilerplate SQL. Repository interfaces auto-generate queries. Allows focus on business logic instead of database plumbing.

**Why layered architecture?**
Separation of concerns — each layer has one job. Makes code testable, maintainable, and easy to extend. Industry standard pattern used in enterprise Java applications.

---

## What I Learned Building This

- Designing and implementing RESTful APIs following HTTP standards
- Stateless authentication using JWT tokens and Spring Security filter chains
- ORM mapping with JPA — entity relationships, foreign keys, cascading
- Repository pattern and how Spring Data generates queries automatically
- Exception handling at the global level using @RestControllerAdvice
- Transactional operations — placing an order creates multiple records atomically
- API documentation using OpenAPI / Swagger

---

## Author

**Saurabh Gupta**
B.Tech IT Final Year Student

[![GitHub](https://img.shields.io/badge/GitHub-Itzzsaurabh-black)](https://github.com/Itzzsaurabh)
[![LinkedIn](https://img.shields.io/badge/LinkedIn-saurabh-gupta-blue)](https://www.linkedin.com/in/saurabh-gupta-98a719232/)

---

> Built as a final year project to demonstrate production-ready Java backend development skills.
