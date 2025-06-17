# 👥 User Authentication Microservice

A Spring Boot-based microservice that handles user registration, login, email verification, and Redis-based OTP caching. This service is designed to be part of a microservices architecture and communicates with other services via REST or message queues (e.g., RabbitMQ).

---

## 🚀 Features

- ✅ User registration with email verification
- ✅ User login with username/email
- ✅ Resend verification code (OTP) via email
- ✅ Verify user account using OTP
- ✅ Redis-based temporary OTP storage
- ✅ Email sending using Spring Mail
- ✅ RESTful API design

---

## 🧰 Tech Stack

| Component        | Tech / Tool                     |
|------------------|----------------------------------|
| Language         | Java 17+                         |
| Framework        | Spring Boot 3.x                  |
| Web              | Spring Web (REST APIs)           |
| Data Persistence | Spring Data JPA + H2/PostgreSQL  |
| Caching          | Spring Boot Starter Redis        |
| Mail             | Spring Boot Starter Mail         |
| Validation       | Hibernate Validator              |
| Testing          | JUnit, Mockito                   |

---

## 📘 API Endpoints

| Method | Endpoint                | Description                                                  |
|--------|-------------------------|--------------------------------------------------------------|
| `GET`  | `/username/{username}`  | Checks if the given username already exists in the system.   |
| `POST` | `/register`             | Registers a new user and sends a verification OTP via email. |
| `POST` | `/login`                | Authenticates user using username or email and password.     |
| `POST` | `/resend-code`          | Resends the verification OTP to the user's email address.    |
| `POST` | `/verify`               | Verifies the OTP and activates the user account.             |

---


