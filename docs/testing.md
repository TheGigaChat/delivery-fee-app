# Testing

## Current Automated Coverage

The test suite currently covers:

- Spring Boot context startup
- application main method bootstrapping
- station-to-city mapping
- XML parser success and failure paths
- API client delegation to `RestTemplate`
- weather import filtering and value conversion
- scheduler delegation
- latest-weather repository lookup
- latest-weather service lookup
- delivery fee calculation rules and edge cases
- controller request and error mapping

## Test Classes

- `DeliveryFeeAppApplicationTests`
- `DeliveryFeeAppApplicationMainTest`
- `StationCityMapperTest`
- `WeatherXmlParserTest`
- `WeatherApiClientTest`
- `WeatherImportServiceTest`
- `WeatherImportSchedulerTest`
- `WeatherDataRepositoryTest`
- `WeatherDataServiceTest`
- `DeliveryFeeServiceTest`
- `DeliveryFeeControllerTest`

## Delivery Fee Service Coverage

`DeliveryFeeServiceTest` covers:

- base fee for each city and vehicle type
- configuration binding from `src/test/resources/application-test.yml`
- missing base-fee configuration
- scooter and bike temperature rules
- bike wind surcharge
- bike forbidden-use threshold
- snow and sleet surcharge
- rain surcharge
- forbidden phenomena
- missing weather data
- `null` air temperature
- `null` wind speed
- `null` and blank phenomenon

## Controller Coverage

`DeliveryFeeControllerTest` uses `@WebMvcTest` with a mocked `DeliveryFeeService`.

Covered statuses:

- `200 OK`
- `400 Bad Request` for invalid enums
- `400 Bad Request` for forbidden vehicle usage
- `404 Not Found` for missing weather data
- `500 Internal Server Error` for unexpected service failures

## Import And Parsing Coverage

`WeatherXmlParserTest` covers:

- valid XML parsing
- unknown extra tags ignored by DTO mapping
- station-name and air-temperature extraction
- parser failure wrapping

`WeatherImportServiceTest` covers:

- only mapped stations are saved
- unknown stations are ignored
- parsed source values are converted into `WeatherData`
- invalid, blank, and null numeric source values become `null`

## Repository And Scheduler Coverage

`WeatherDataRepositoryTest` covers:

- saving weather rows
- latest-by-city lookup
- missing-city lookup
- persisted entity `id` access

`WeatherImportSchedulerTest` covers:

- scheduler method delegates once to `WeatherImportService`

## Test Configuration

`src/test/resources/application-test.yml` provides a dedicated fee table for fee-service tests.

This avoids coupling all fee assertions directly to the runtime values in `src/main/resources/application.yml`.

## Running Tests

The agent should not run tests from its own environment in this repository.

Expected workflow:

- the agent adds or updates tests
- the user runs the relevant Maven command locally
- the user reports the result back

Typical command:

```powershell
.\mvnw.cmd test
```

## Remaining Gaps

Areas not covered by a full end-to-end integration test:

- scheduler timing against the real cron trigger
- live external HTTP integration against the real weather service
- full XML fetch -> parse -> persist -> API flow in one test

Those gaps are acceptable for this project because the core business logic and wiring boundaries are already covered at unit and slice/repository level.
