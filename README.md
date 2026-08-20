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
* Spring Security and Google OAuth 2.0 / OpenID Connect
* JUnit 5 and Spring Boot Test

Image storage and the React/Vite frontend are intentionally deferred to later
phases.

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
* `GOOGLE_CLIENT_ID`
* `GOOGLE_CLIENT_SECRET`
* `ADMIN_EMAIL` — the sole Google account allowed to receive the server-side
  `ADMIN` role
* `GOOGLE_OAUTH_REDIRECT_URI` — the OAuth callback URI registered with Google
  (for local development: `http://localhost:8080/login/oauth2/code/google`)

Hibernate runs in schema-validation mode; Flyway exclusively creates and evolves
the database schema.

Never commit `.env`, credentials, private keys, or production configuration.

## Google OAuth administrator setup

The backend is an OAuth client, not a Google Cloud project provisioning tool.
Complete these one-time manual steps before testing a real login:

1. In Google Cloud Console, configure the OAuth consent screen for the business.
   While it is in testing, add the administrator Google account as a test user.
2. Create an OAuth 2.0 Client ID of type **Web application**.
3. Add this exact Authorized redirect URI for local development:
   `http://localhost:8080/login/oauth2/code/google`.
4. Copy its client ID and client secret to `GOOGLE_CLIENT_ID` and
   `GOOGLE_CLIENT_SECRET`, set `ADMIN_EMAIL`, and set
   `GOOGLE_OAUTH_REDIRECT_URI` to that same value.
5. For production, add the exact HTTPS callback URI for the deployed API (for
   example, `https://api.example.com/login/oauth2/code/google`) and set the
   environment variable to exactly that URI. Do not use a wildcard URI.

Start the sign-in flow at `/oauth2/authorization/google`. Google identities are
accepted only when their verified OIDC email claim matches `ADMIN_EMAIL`; all
admin API routes remain protected by Spring Security on the server.

## Planned setup documentation

This README will be expanded in the relevant phases with PostgreSQL, Google
OAuth, image storage, frontend, production, deployment, and troubleshooting
instructions.
