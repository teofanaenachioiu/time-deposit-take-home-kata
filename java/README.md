# Time Deposit

Spring Boot application for managing time deposits and updating their balances based on plan-specific interest rules.

## Prerequisites

Before running the project, make sure you have:

- Java 25
- Maven 3.6.3 or newer
- PostgreSQL running locally on `localhost:5432`
- Docker running if you want to execute the integration tests based on Testcontainers

The application expects this database configuration:

- Database name: `time_deposits`
- Username: `postgres`
- Password: `postgres`

You only need to create the database itself. The tables and seed data are created automatically by Flyway on startup.

Example PostgreSQL setup with Docker:

```bash
docker run --name time-deposit-db \
  -e POSTGRES_DB=time_deposits \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=postgres \
  -p 5432:5432 \
  -d postgres:16
```

## Run the Project

Start the application from the project root:

```bash
mvn spring-boot:run
```

The application starts on:

```text
http://localhost:8080
```

## Run the Tests

Run unit tests:

```bash
mvn test
```

Run the full verification pipeline, including integration tests:

```bash
mvn verify
```

Note: integration tests require Docker because they use Testcontainers.

## Trigger the Endpoints Using Swagger

After the application is running, open the Swagger UI:

```text
http://localhost:8080/swagger-ui/index.html
```

### Available endpoints

Base path:

```text
/api/v1/time-deposits
```

Endpoints exposed by the API:

- `GET /api/v1/time-deposits` - returns all time deposits together with their withdrawals
- `POST /api/v1/time-deposits/update-balances` - recalculates and persists updated balances for all deposits

### How to call them from Swagger UI

1. Open `http://localhost:8080/swagger-ui/index.html`
2. Expand the `Time Deposits` section
3. Choose the endpoint you want to run
4. Click `Try it out`
5. Click `Execute`

Expected behavior:

- `GET /api/v1/time-deposits` returns a `200 OK` response with the seeded deposits and their withdrawals
- `POST /api/v1/time-deposits/update-balances` returns `204 No Content` and updates balances in the database

If you want to confirm the updated values, trigger the `POST` endpoint first and then call `GET /api/v1/time-deposits` again from Swagger.

## Project Structure

```text
src/
├── main/
│   ├── java/org/ikigaidigital/
│   │   ├── adapter/          
│   │   │   ├── in/web           # API
│   │   │   └── out/persistence  # database
│   │   ├── application          # use cases
│   │   ├── domain               # business logic
│   │   └── port                 # contracts
│   └── resources/
│       ├── application.yml      # configuration
│       └── db/migration         # Flyway scripts
└── test/
    └── java/org/ikigaidigital/
        ├── unit                 # unit tests
        └── integration          # integration tests
```

## Seed Data

On startup, Flyway loads a small initial dataset:

- 5 time deposits
- withdrawals linked to some of those deposits

This allows you to trigger the Swagger endpoints immediately after startup without inserting data manually.

### Inserted time deposits

| ID | Plan Type | Days | Balance |
|---|---|---:|---:|
| 1 | BASIC | 20 | 1000.00 |
| 2 | STUDENT | 100 | 2000.00 |
| 3 | PREMIUM | 50 | 5000.00 |
| 4 | STUDENT | 400 | 3000.00 |
| 5 | BASIC | 60 | 1500.00 |

### Inserted withdrawals

| ID | Time Deposit ID | Amount | Transaction Date |
|---|---:|---:|---|
| 1 | 2 | 150.00 | 2026-01-10 |
| 2 | 2 | 200.00 | 2026-02-15 |
| 3 | 3 | 500.00 | 2026-01-05 |
| 4 | 3 | 300.00 | 2026-02-01 |
| 5 | 3 | 250.00 | 2026-02-20 |
| 6 | 4 | 400.00 | 2026-01-25 |
