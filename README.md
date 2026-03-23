# Delivery Fee App

Spring Boot service for importing weather observations, storing them in H2, and calculating delivery fees by city and vehicle type.

## Status

This repository is in the initial setup phase. The current codebase contains:

- Spring Boot application bootstrap
- REST controller and global exception handler for delivery-fee requests
- project dependencies for web, validation, JPA, H2, and XML parsing
- in-memory H2 datasource and JPA configuration in `application.properties`
- `VehicleType` enum for fee calculation inputs
- `WeatherData` in an `entity` package for cleaner package structure
- `WeatherDataRepository` query support for loading the latest weather by city
- `DeliveryFeeService` for weather-based fee calculation
- `WeatherApiClient`, DTOs, and `WeatherXmlParser` for fetching and parsing observation XML
- `WeatherImportService` for import orchestration
- scheduled weather import enabled through `@EnableScheduling` and `WeatherImportScheduler`
- documentation structure for implementation, decisions, and change tracking

## Tech Stack

- Java 21
- Spring Boot 3.5
- Spring Web
- Spring Data JPA
- H2
- Jackson XML
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
- [Current Change](docs/changes/2026-03-23-controller-tests-and-docs.md)

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
