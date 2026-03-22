# Testing

## Current State

The repository currently has one bootstrap test that verifies the Spring context loads.

Recent weather API integration work is only exercised manually through `TestDataRunner`; there are no automated tests yet for the client, mapping, or import path.

## Recommended Test Layers

### Unit Tests

Focus on isolated business rules:

- base fee per city and vehicle
- temperature-based extra fee
- wind-based extra fee and forbidden cases
- weather phenomenon-based extra fee and forbidden cases
- station-name to city mapping
- XML parsing and station filtering logic

### Integration Tests

Focus on behavior spanning multiple layers:

- repository query for latest weather by city
- external weather client behavior with mocked HTTP responses
- REST endpoint contract
- weather import persistence flow
- scheduler-triggered import boundaries where practical

## Test Data Guidance

- Use focused fixtures for weather scenarios.
- Make edge cases explicit: threshold values, forbidden conditions, and missing data.
- Prefer readable test names describing business rules.

## Definition of Done

A feature change is not complete unless:

- behavior is documented
- relevant tests exist or are updated
- gaps are explicitly recorded in the associated change note
