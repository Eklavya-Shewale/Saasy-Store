# Saasy Store

Saasy Store is a jewellery catalogue and customer-enquiry website. It will let
customers browse products and contact the business, while catalogue management
will be restricted to an authenticated administrator. It does not process
payments.

## Architecture

The project is a modular monolith:

```text
React/Vite frontend (planned)
            |
Spring Boot REST API
            |
PostgreSQL (planned) ---- Object storage for product images (planned)
```

The backend owns validation, authorization, and all business rules. The package
root is `com.saasy.store`; future feature modules will live beneath it (for
example, `catalog`, `security`, `storage`, and `common`) rather than being split
into services.

## Technology stack

* Java 21
* Maven
* Spring Boot 4.1.0
* Spring MVC with embedded Tomcat
* Spring Data JPA, PostgreSQL, and Flyway
* JUnit 5 and Spring Boot Test

Spring Security/Google OAuth, image storage, and the React/Vite frontend are
intentionally deferred to later phases.

## Local development

### Prerequisites

* JDK 21
* Apache Maven 3.6.3 or later (3.9+ recommended)
* PostgreSQL (for running the API)

### Build and test

```powershell
mvn clean verify
```

### Run the API

```powershell
mvn spring-boot:run
```

The foundation has no public endpoints yet. It starts an embedded server on
port `8080` by default and applies Flyway migrations to the configured database.

## Configuration

`src/main/resources/application.yaml` reads database settings from environment
variables. Copy `.env.example` for reference and configure the variables in
your terminal or IDE; Spring Boot does not load `.env` files by itself.

* `DB_URL` (optional full JDBC URL)
* `DB_HOST`, `DB_PORT`, and `DB_NAME` (used when `DB_URL` is not set)
* `DB_USERNAME`
* `DB_PASSWORD`

Hibernate runs in schema-validation mode; Flyway exclusively creates and evolves
the database schema.

Never commit `.env`, credentials, private keys, or production configuration.

## Planned setup documentation

This README will be expanded in the relevant phases with PostgreSQL, Google
OAuth, image storage, frontend, production, deployment, and troubleshooting
instructions.
