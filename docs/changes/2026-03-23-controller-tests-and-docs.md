# 2026-03-23 Controller Tests And Docs

## Summary

Added controller slice tests and updated the permanent documentation to reflect the new delivery-fee REST endpoint, response DTOs, and global exception handling.

## Changes

- added `DeliveryFeeControllerTest` using `@WebMvcTest`
- mocked `DeliveryFeeService` in controller tests
- covered `200 OK` for a valid request
- covered `400 Bad Request` for invalid enum binding
- covered `400 Bad Request` for forbidden vehicle usage
- covered `404 Not Found` for missing weather data
- updated architecture, API, testing, README, and backlog docs to document the controller, response DTOs, and `GlobalExceptionHandler`

## Why

The application now exposes the delivery-fee functionality over HTTP. That changes the public contract of the system, so the API docs and controller-level tests need to reflect the actual endpoint and status-code mapping instead of leaving the endpoint in a planned state.

## Tests

- added `DeliveryFeeControllerTest`
- user should run the relevant Maven test command locally and report the result back

## Follow-Up

- add broader integration coverage once the full endpoint-to-database flow is stabilized
- refine error messages and validation details if the API contract becomes externally consumed
- consider documenting example 500 responses only if unhandled server failures need explicit contract coverage
