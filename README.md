# Delivery Fee App

Spring Boot service for importing weather observations, storing them in H2, and calculating delivery fees by city and vehicle type.

## Status

This repository is in the initial setup phase. The current codebase contains:

- Spring Boot application bootstrap
- basic test bootstrap
- project dependencies for web, validation, JPA, and H2
- in-memory H2 datasource and JPA configuration in `application.properties`
- `VehicleType` enum for planned fee calculation inputs
- `WeatherDataRepository` query support for loading the latest weather by city
- a `WeatherDataService` for latest-observation lookup
- a startup `CommandLineRunner` that seeds one sample weather observation
- documentation structure for implementation, decisions, and change tracking

## Tech Stack

- Java 21
- Spring Boot 3.5
- Spring Web
- Spring Data JPA
- H2
- Maven

## Run

```powershell
.\mvnw.cmd spring-boot:run
```

## Test

```powershell
.\mvnw.cmd test
```

## Documentation

- [Setup](docs/setup.md)
- [Architecture](docs/architecture.md)
- [API](docs/api.md)
- [Testing](docs/testing.md)
- [Roadmap](docs/planning/roadmap.md)
- [Backlog](docs/planning/backlog.md)
- [Decision Records](docs/decisions/0001-doc-structure.md)
- [Change Log](docs/changes/2026-03-21-initial-review.md)
- [Latest Change](docs/changes/2026-03-22-service-and-config-docs.md)

## Current Scope

The target application should:

- import weather data from the Estonian Environment Agency on a schedule
- persist weather history instead of overwriting old observations
- calculate delivery fees using city, vehicle type, and latest weather rules
- expose a REST API for fee calculation
- include tests for business rules and integration points

## Notes

- The generated `HELP.md` file is kept as framework reference only.
- Operational rules for future agent work live in `AGENTS.md`.
