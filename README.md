# Volunteer Hours Service

_Created by **Dejan Jarc**._


A Quarkus-based application for managing volunteers, events, and volunteer hour logs.

This project was created within the **PUŠ v delovno okolje 2024-2027** project in collaboration with _Faculty of Computer and Information Science, University of Ljubljana_.

Below is a breakdown of the most important information regarding this project.

---
## Prerequisites

- Java 25
- Maven Wrapper (`./mvnw` is included)
- Docker + Docker Compose
- Quarkus CLI

## Modules

- `entity` - JPA entities, enums, Liquibase changelog resources
- `service` - business logic, repositories, commands, exceptions
- `api` - REST controllers, DTOs, validation, exception mappers, tests
- `helm` - Kubernetes/Helm deployment files


## OpenAPI / Swagger

When the app is running, the OpenAPI/Swagger UI is available at:

- OpenAPI: `http://localhost:8080/q/openapi`
- Swagger UI: `http://localhost:8080/q/swagger-ui`

## Project status

The project currently includes:

- CRUD-style REST endpoints for volunteers, events, and hour logs
- DTO validation
- custom event date validator
- Liquibase migrations
- PostgreSQL dev/test setup using Docker Compose
- Quarkus JVM tests and integration tests
- Helm deployment files


---
## Setup

### 1) Project initialization

Before building the project, create local `.env` files from the provided examples.

#### a) Main `.env` in root

Create a `.env` file in the project root next to `.env.example`.

Example:

```env
DEV_DB_USERNAME=postgres
DEV_DB_PASSWORD=postgres
TEST_DB_USERNAME=postgres
TEST_DB_PASSWORD=postgres
```

#### b) `api/.env`

Create a second `.env` file inside the `api` module next to `api/.env.example`.

Example:

```env
DEV_DB_USERNAME=postgres
DEV_DB_PASSWORD=postgres
TEST_DB_USERNAME=postgres
TEST_DB_PASSWORD=postgres
```

> Both `.env` files must exist. If `api/.env` is missing, test/build startup can fail because Quarkus will not receive datasource credentials.

### 2) Start databases

The project expects the Docker containers, which hold our Postgres DBs (DEV and TEST), to be running before build/test execution.

From the project root:

```bash
docker compose pull
docker compose up -d
```

This firstly pulls the required images and then starts:

- `volunteer-hours-postgres` - dev database on `localhost:55432`
- `volunteer-hours-postgres-test` - test database on `localhost:55433`

### 3) Build

From the project root:

```bash
quarkus build
```

> **Note:** The test Postgres DB should be running to enable all integration tests to successfully pass.

If you only want to package without running tests:

```bash
quarkus build -DskipTests
```

### 4) Run the application in dev mode

Firstly, move to the `api` module:

```bash
cd api
```
and then run: 
```bash
quarkus dev
```

The application will run with the dev datasource configured for `volunteer_hours`.

---

## Running tests separately

These are a couple of steps you might find helpful if you prefer to do testing separate to the build process.

### JVM tests

From the project root:

```bash
./mvnw -pl api -am test
```

### Full verification, including integration tests

```bash
./mvnw -pl api -am verify -DskipITs=false
```

## Test database cleanup

As mentioned previously, tests use the separate test database (`volunteer_hours_test`).
Cleanup is handled by the shared test base so that test data does not accumulate between runs.

## Local Kubernetes / Helm

Helm files are provided under `helm/`.
For local Helm testing, the deployment can be configured to use an existing external PostgreSQL instance.

Useful commands:

```bash
helm lint ./helm/volunteer-hours-service
helm template volunteer-hours-service ./helm/volunteer-hours-service
helm upgrade --install volunteer-hours-service ./helm/volunteer-hours-service -n default
```

---