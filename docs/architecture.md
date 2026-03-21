# Architecture

## Current State

The repository currently contains an early Spring Boot application with initial domain modeling:

- application bootstrap class in `com.example.delivery`
- default Spring Boot context-load test
- `City` enum with supported cities: `TALLINN`, `TARTU`, `PARNU`
- `WeatherData` JPA entity with weather observation fields and a `City` enum reference
- no service layer, controller layer, or repository layer yet

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

Primary domain concepts already started or expected in the next iterations:

- `WeatherData`
- `City`
- `VehicleType`
- delivery fee calculation rules
- forbidden vehicle usage conditions

## Current Domain Model

### `City`

The `City` enum currently defines:

- `TALLINN`
- `TARTU`
- `PARNU`

### `WeatherData`

The current `WeatherData` entity includes:

- `id`
- `stationName`
- `wmoCode`
- `airTemperature`
- `windSpeed`
- `weatherPhenomenon`
- `observationTimestamp`
- `city`

Note: `WeatherData` currently lives under the `enums` package. That package placement should likely be corrected once package structure cleanup begins.

## Architectural Constraints

- Historical weather data must remain queryable.
- Fee calculation should depend on latest weather for the requested city.
- Exception handling should be centralized.
- Public API behavior should be documented alongside implementation.
