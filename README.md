# Delivery Fee App

Spring Boot application for importing weather observations from the Estonian Environment Agency, storing historical observations in H2, and calculating delivery fees for Tallinn, Tartu, and Parnu.

## Final Scope

- scheduled weather import from the external XML feed
- XML parsing into DTOs with unknown-tag tolerance
- station-to-city mapping for supported Estonian delivery cities
- historical weather persistence in H2
- latest-weather lookup by city
- delivery fee calculation by city, vehicle type, and weather conditions
- REST API with structured success and error responses
- centralized exception handling
- unit and slice/integration-style test coverage for the main business flows

## Tech Stack

- Java 21
- Spring Boot 3.5.12
- Spring Web
- Spring Data JPA
- H2
- Jackson XML
- Maven Wrapper

## Features
- Scheduled weather import from XML API
- Weather history stored in H2
- Delivery fee calculation based on business rules
- REST API for fee calculation
- Error handling with proper HTTP status codes
- Unit and integration tests

## Supported Inputs

Cities:

- `TALLINN`
- `TARTU`
- `PARNU`

Vehicle types:

- `CAR`
- `SCOOTER`
- `BIKE`

## Fee Rules

Base fees are configured in `src/main/resources/application.yml`.

Current configured base fees:

- Tallinn: `CAR 4.0`, `SCOOTER 3.5`, `BIKE 3.0`
- Tartu: `CAR 3.5`, `SCOOTER 3.0`, `BIKE 2.5`
- Parnu: `CAR 3.0`, `SCOOTER 2.5`, `BIKE 2.0`

Weather-based rules:

- scooter and bike: temperature below `-10` adds `1.0`
- scooter and bike: temperature from `-10` to `0` adds `0.5`
- bike: wind speed from `10` to `20` adds `0.5`
- bike: wind speed above `20` is forbidden
- scooter and bike: `snow` or `sleet` adds `1.0`
- scooter and bike: `rain` adds `0.5`
- scooter and bike: `glaze`, `hail`, or `thunder` are forbidden

## API

Endpoint:

```text
GET /api/delivery-fee?city=TARTU&vehicleType=BIKE
```

Success response example:

```json
{
  "city": "TARTU",
  "vehicleType": "BIKE",
  "deliveryFee": 3.0
}
```

Error response example:

```json
{
  "timestamp": "2026-03-23T12:00:00",
  "status": 404,
  "error": "Not Found",
  "message": "No weather data found for city: TARTU",
  "path": "/api/delivery-fee"
}
```

## Configuration

`src/main/resources/application.properties` contains:

- application name
- H2 datasource configuration
- JPA and SQL logging configuration
- H2 console settings
- `weather.import.cron`

`src/main/resources/application.yml` contains:

- `delivery.fee.base-fees` by city and vehicle type

`src/test/resources/application-test.yml` contains:

- a dedicated fee table used by `DeliveryFeeServiceTest`

## Run

Start the application:

```powershell
.\mvnw.cmd spring-boot:run
```

H2 console:

```text
http://localhost:8080/h2-console
```

## Tests

Run the full test suite locally:

```powershell
.\mvnw.cmd test
```

Main covered areas:

- station mapping
- XML parsing
- weather import orchestration
- latest-weather repository/service lookup
- delivery fee rule calculation
- controller status mapping
- scheduler delegation
- API client delegation

## Project Structure

- `controller` REST endpoint
- `service` business logic and import orchestration
- `repository` JPA access
- `entity` persisted weather model
- `dto` API and XML payload DTOs
- `parser` XML parsing
- `mapper` station-to-city mapping
- `config` Spring beans, scheduler, and configuration properties
- `exception` domain exceptions and global handler
- `docs` architecture, API, setup, testing, planning, decisions, and change history

## Documentation

- [Setup](docs/setup.md)
- [Architecture](docs/architecture.md)
- [API](docs/api.md)
- [Testing](docs/testing.md)
- [Roadmap](docs/planning/roadmap.md)
- [Backlog](docs/planning/backlog.md)
- [Decision Records](docs/decisions/0001-doc-structure.md)
- [Changes](docs/changes/)

## Notes

- Weather observations are preserved as historical rows; imports do not overwrite previous data.
- The external weather source URL is still hardcoded in `WeatherApiClient`.
- Agent working rules for this repository are defined in `AGENTS.md`.
