# Development Status

## Completed phases

* Phase 0 — Repository and environment inspection (2026-08-18)
* Phase 1 — Backend project foundation (implemented and Maven-verified, 2026-08-18)

## Current state

The repository now contains the Spring Boot foundation plus a migration-controlled
PostgreSQL catalogue domain. Security, storage, APIs, and frontend code have not
been started.

## Current phase

Phase 2 — Database + product/category domain is implemented and awaiting Maven
verification in the VS Code terminal.

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

## Known issues

The Codex execution shell does not currently resolve `mvn`, although Maven is
available in the VS Code terminal. PostgreSQL must be configured locally before
running the API. The Git repository has no commits; no secrets or generated
artifacts from the project build are present.

## Next recommended task

Run `mvn clean verify` from the VS Code terminal to verify Phase 2. After it
passes, the next implementation phase is Phase 3: Google OAuth-based admin
authentication and server-side authorization.
