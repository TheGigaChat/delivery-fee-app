# Architecture

## Current State

The repository currently contains a minimal Spring Boot application:

- application bootstrap class in `com.example.delivery`
- default Spring Boot context-load test
- no domain model, service layer, controller layer, or persistence layer yet

## Target Component Layout

Recommended package structure:

- `controller`
- `service`
- `repository`
- `entity`
- `dto`
- `config`
- `exception`

Additional packages can be introduced if the logic grows:

- `scheduler`
- `client` for external weather API access
- `mapper`
- `rules` for delivery fee rule evaluation

## Planned Flow

1. a scheduled job triggers weather import
2. the import service fetches and parses source XML
3. relevant stations are mapped into `WeatherData` entities
4. new observations are inserted into H2 without overwriting history
5. the fee calculation service loads the latest observation for a city
6. the REST controller returns the calculated fee or an error response

## Core Domain Direction

Primary domain concepts expected in the next iterations:

- `WeatherData`
- `City`
- `VehicleType`
- delivery fee calculation rules
- forbidden vehicle usage conditions

## Architectural Constraints

- Historical weather data must remain queryable.
- Fee calculation should depend on latest weather for the requested city.
- Exception handling should be centralized.
- Public API behavior should be documented alongside implementation.
