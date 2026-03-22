# Testing

## Current State

The repository currently has one bootstrap test that verifies the Spring context loads.

Recent weather API integration work is only exercised manually through `TestDataRunner`; there are no automated tests yet for the client or import path.

The first focused unit tests now cover `StationCityMapper` for:

- `Tallinn-Harku -> City.TALLINN`
- `Tartu-Toravere -> City.TARTU`
- unknown station names returning `Optional.empty()`

The test suite now also covers:

- `WeatherDataService.getLatestWeather(city)` returning the repository result
- `WeatherDataRepository.findFirstByCityOrderByObservationTimestampDesc(city)` for latest-record lookup and no-data cases

## Recommended Test Layers

### Unit Tests

Focus on isolated business rules:

- station-name to city mapping
- base fee per city and vehicle
- temperature-based extra fee
- wind-based extra fee and forbidden cases
- weather phenomenon-based extra fee and forbidden cases
- XML parsing and station filtering logic

Priority order for the current codebase:

- mapper tests now
- weather lookup service tests now
- delivery fee calculation service tests as soon as the fee rules are implemented
- parsing tests once XML parsing moves beyond raw client fetches

### Integration Tests

Focus on behavior spanning multiple layers:

- repository query for latest weather by city
- external weather client behavior with mocked HTTP responses
- REST endpoint contract
- weather import persistence flow
- scheduler-triggered import boundaries where practical

These can wait until the business logic and endpoint contract are stable enough to test without excessive setup cost.

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
