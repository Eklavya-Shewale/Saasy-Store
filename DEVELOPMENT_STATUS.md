# Development Status

## Completed phases

* Phase 0 — Repository and environment inspection (2026-08-18)
* Phase 1 — Backend project foundation (implemented and Maven-verified, 2026-08-18)
* Phase 2 — Database + product/category domain (implemented, verified with local PostgreSQL, committed and pushed)
* Phase 3 — Google OAuth/OIDC administrator authentication and server-side authorization (implemented and Maven-verified, 2026-08-20)

## Current state

The repository contains the Spring Boot foundation, a migration-controlled
PostgreSQL catalogue domain, and Phase 3 Google OAuth/OIDC administrator
authentication with server-side authorization. Storage, catalogue APIs, and
frontend code have not been started.

## Current phase

Phase 3 is complete. Phase 4 has not been started.

## Environment findings

* Windows 64-bit (Windows NT 10.0.26200.0)
* Java: Eclipse Temurin OpenJDK 21.0.8 LTS
* Node.js: v24.19.0; npm 11.17.0 is available as `npm.cmd` (the PowerShell
  `npm` shim is blocked by execution policy)
* Maven is installed and the Phase 1 `mvn clean verify` build passed
* Gradle and Docker are not installed or not on `PATH`

## Important architectural decisions

Use a modular monolith: React/Vite frontend, Spring Boot REST API, PostgreSQL,
and external object storage for product images. The API foundation uses Java 21,
Maven, and Spring Boot 4.1.0 with Spring MVC; feature packages will be added
only when their corresponding phase begins.

The catalogue persistence layer uses Spring Data JPA, PostgreSQL, and Flyway.
Products belong to exactly one category through a lazy many-to-one association;
the database schema is created only by Flyway and validated by Hibernate.

Admin sign-in uses Spring Security's OAuth2 client support and Google OIDC. The
`ADMIN_EMAIL` environment variable determines the sole permitted administrator;
the OIDC user service grants `ROLE_ADMIN` only when the verified Google email
claim matches it. `/api/admin/**` is enforced server-side, while planned public
catalogue GET routes remain public. OAuth credentials are environment-only.

## Known issues

PostgreSQL must be configured locally before running the API. A Google Cloud
OAuth Web application and exact callback URI must be configured manually before
testing real login.

## Next recommended task

Phase 4: implement product/category REST APIs with DTOs, validation, service
layer separation, and the Phase 3 `/api/admin/**` authorization boundary.
