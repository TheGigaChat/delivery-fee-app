# Architecture

## Final System Overview

The application imports weather observations from the Estonian Environment Agency, maps supported stations to internal delivery cities, stores the observations in H2, and exposes a REST endpoint that calculates delivery fees from the latest persisted weather data.

## Package Layout

- `com.example.delivery`
- `com.example.delivery.config`
- `com.example.delivery.controller`
- `com.example.delivery.dto`
- `com.example.delivery.entity`
- `com.example.delivery.enums`
- `com.example.delivery.exception`
- `com.example.delivery.mapper`
- `com.example.delivery.parser`
- `com.example.delivery.repository`
- `com.example.delivery.service`

## Main Runtime Flow

1. `WeatherImportScheduler` triggers import using `weather.import.cron`.
2. `WeatherApiClient` downloads the latest observation XML.
3. `WeatherXmlParser` deserializes XML into `ObservationsXmlDto` and `StationXmlDto`.
4. `StationCityMapper` filters source stations down to supported cities.
5. `WeatherImportService` converts source values into `WeatherData` entities and stores them.
6. `DeliveryFeeController` receives a fee request.
7. `DeliveryFeeService` loads the latest weather for the requested city through `WeatherDataService`.
8. `DeliveryFeeService` calculates the fee from configured base fees plus weather-based rules.
9. `GlobalExceptionHandler` converts domain and binding failures into structured HTTP responses.

## Application Layer Responsibilities

### Bootstrap

`DeliveryFeeAppApplication`:

- starts the Spring Boot application
- enables scheduling
- enables binding for `DeliveryFeeProperties`

### Configuration

`AppConfig`:

- provides the shared `RestTemplate` bean

`DeliveryFeeProperties`:

- binds `delivery.fee.base-fees` from `application.yml`
- supplies configured base fees by city and vehicle type

`WeatherImportScheduler`:

- runs `importWeatherData()` on the configured cron schedule
- delegates work to `WeatherImportService`

### Controller

`DeliveryFeeController`:

- exposes `GET /api/delivery-fee`
- accepts `city` and `vehicleType` query parameters
- delegates to `DeliveryFeeService`
- returns `DeliveryFeeResponse`

### Services

`WeatherApiClient`:

- fetches raw XML from `https://www.ilmateenistus.ee/ilma_andmed/xml/observations.php`

`WeatherImportService`:

- orchestrates fetch, parse, map, convert, and persist
- ignores unsupported stations
- converts numeric strings to `BigDecimal`
- stores a new `WeatherData` row per imported supported station

`WeatherDataService`:

- returns the latest observation for a city

`DeliveryFeeService`:

- resolves latest weather for a city
- reads base fees from configuration
- applies temperature, wind, and phenomenon rules
- throws `WeatherDataNotFoundException` when no weather exists
- throws `ForbiddenVehicleUsageException` for forbidden weather conditions
- throws `IllegalArgumentException` when base-fee configuration is missing for a city/vehicle pair

### Persistence

`WeatherDataRepository`:

- extends `JpaRepository<WeatherData, Long>`
- provides `findFirstByCityOrderByObservationTimestampDesc(City city)`

### Mapping And Parsing

`StationCityMapper`:

- maps supported external station names to `City`
- supports Tallinn, Tartu, and Parnu station names
- returns `Optional.empty()` for unsupported stations

`WeatherXmlParser`:

- uses Jackson XML
- ignores unknown source tags via the DTO layer
- wraps parse failures in `RuntimeException("Failed to parse weather XML", cause)`

## Domain Model

### `City`

Supported values:

- `TALLINN`
- `TARTU`
- `PARNU`

### `VehicleType`

Supported values:

- `CAR`
- `SCOOTER`
- `BIKE`

### `WeatherData`

Persisted fields:

- `id`
- `stationName`
- `wmoCode`
- `airTemperature`
- `windSpeed`
- `weatherPhenomenon`
- `observationTimestamp`
- `city`

The application preserves historical observations by inserting new rows instead of updating the latest one in place.

## DTO Model

### API DTOs

`DeliveryFeeResponse`:

- Java record
- fields: `city`, `vehicleType`, `deliveryFee`

`ErrorResponse`:

- Java record
- fields: `timestamp`, `status`, `error`, `message`, `path`

### XML DTOs

`ObservationsXmlDto`:

- source timestamp
- list of `station` entries

`StationXmlDto`:

- station name
- WMO code
- air temperature
- wind speed
- phenomenon

Unknown XML fields are ignored so non-essential source changes do not immediately break parsing.

## Fee Calculation Rules

Base fees are externalized in `src/main/resources/application.yml`.

Configured base fees:

- Tallinn: `CAR 4.0`, `SCOOTER 3.5`, `BIKE 3.0`
- Tartu: `CAR 3.5`, `SCOOTER 3.0`, `BIKE 2.5`
- Parnu: `CAR 3.0`, `SCOOTER 2.5`, `BIKE 2.0`

Extra-fee rules:

- scooter and bike: temperature below `-10` adds `1.0`
- scooter and bike: temperature from `-10` to `0` adds `0.5`
- bike: wind from `10` to `20` adds `0.5`
- bike: wind above `20` is forbidden
- scooter and bike: `snow` or `sleet` adds `1.0`
- scooter and bike: `rain` adds `0.5`
- scooter and bike: `glaze`, `hail`, or `thunder` are forbidden

Null or blank weather values do not add extra fees.

## Exception Handling

`GlobalExceptionHandler` maps:

- `WeatherDataNotFoundException -> 404 Not Found`
- `ForbiddenVehicleUsageException -> 400 Bad Request`
- `MethodArgumentTypeMismatchException -> 400 Bad Request`
- unhandled `Exception -> 500 Internal Server Error`

## Configuration Files

`src/main/resources/application.properties`:

- Spring application name
- H2 datasource
- JPA settings
- H2 console
- import cron

`src/main/resources/application.yml`:

- base-fee configuration

`src/test/resources/application-test.yml`:

- dedicated fee values for fee-service tests

## Architectural Constraints

- controllers stay thin and delegate business rules
- fee calculation depends on the latest persisted weather per city
- historical observations are preserved
- API errors are centralized in the global handler
- documentation under `docs/` remains the source of truth for project state
