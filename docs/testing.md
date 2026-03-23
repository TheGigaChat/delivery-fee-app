# Testing

## Current State

The repository currently has one bootstrap test that verifies the Spring context loads.

Recent weather API integration work is only exercised manually through `TestDataRunner`; there are no automated tests yet for the client or full import path.

The first focused unit tests now cover `StationCityMapper` for:

- `Tallinn-Harku -> City.TALLINN`
- `Tartu-Toravere -> City.TARTU`
- unknown station names returning `Optional.empty()`

The test suite now also covers:

- `WeatherDataService.getLatestWeather(city)` returning the repository result
- `WeatherDataRepository.findFirstByCityOrderByObservationTimestampDesc(city)` for latest-record lookup and no-data cases
- `WeatherXmlParser.parse(xml)` for valid XML, ignored extra tags, and core field extraction
- `WeatherImportService.importWeatherData()` for filtering mapped stations and converting parsed values before save
- `DeliveryFeeService.calculateDeliveryFee(...)` for base fees, weather surcharges, forbidden cases, and missing weather data

## Recommended Test Layers

### Unit Tests

Focus on isolated business rules:

- station-name to city mapping
- base fee per city and vehicle
- temperature-based extra fee
- wind-based extra fee and forbidden cases
- weather phenomenon-based extra fee and forbidden cases
- XML parsing and station filtering logic
- import-service filtering and conversion rules

Priority order for the current codebase:

- mapper tests now
- weather lookup service tests now
- XML parsing tests now
- weather import service tests now
- delivery fee calculation service tests now

Current fee-service unit test focus:

- base fee for each city and vehicle type
- scooter and bike temperature fee rules
- bike wind surcharge and forbidden-use threshold
- snow, sleet, and rain phenomenon surcharges
- glaze, hail, and thunder forbidden-use conditions
- missing weather data raising `WeatherDataNotFoundException`

### Integration Tests

Focus on behavior spanning multiple layers:

- repository query for latest weather by city
- external weather client behavior with mocked HTTP responses
- REST endpoint contract
- weather import persistence flow
- scheduler-triggered import boundaries where practical

These can wait until the business logic and endpoint contract are stable enough to test without excessive setup cost.

Current import-service unit test focus:

- only mapped stations are saved
- unknown stations are ignored
- numeric and string station values are converted into `WeatherData` correctly

## Running Tests

The agent should not run tests directly from its environment for this repository.

Instead:

- the agent adds or updates the relevant tests
- the user runs the requested test command locally
- the user reports the result back so follow-up fixes can be made if needed

## Test Data Guidance

- Use focused fixtures for weather scenarios.
- Make edge cases explicit: threshold values, forbidden conditions, and missing data.
- Prefer readable test names describing business rules.

## Definition of Done

A feature change is not complete unless:

- behavior is documented
- relevant tests exist or are updated
- gaps are explicitly recorded in the associated change note
