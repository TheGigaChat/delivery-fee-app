# 2026-03-23 Delivery Fee Service Tests And Docs

## Summary

Added delivery-fee service unit tests and updated the permanent documentation to reflect the implemented fee calculation rules and custom domain exceptions.

## Changes

- added `DeliveryFeeServiceTest`
- covered base fee calculation for every city and vehicle combination
- covered scooter and bike temperature surcharge rules
- covered bike wind surcharge and forbidden-use threshold
- covered snow, sleet, and rain phenomenon surcharge rules
- covered glaze, hail, and thunder forbidden-use conditions
- covered missing weather data throwing `WeatherDataNotFoundException`
- updated architecture, API, testing, and backlog docs to document `DeliveryFeeService` and the custom exception types

## Why

The application now contains the core business logic for delivery-fee calculation. That logic is the highest-value unit-test target in the project, and the permanent docs need to show that fee rules are implemented even though the REST endpoint is still pending.

## Tests

- added `DeliveryFeeServiceTest`
- user should run the relevant Maven test command locally and report the result back

## Follow-Up

- expose `DeliveryFeeService` through a REST controller
- add controller tests once the endpoint contract is implemented
- add centralized exception-to-response mapping for `WeatherDataNotFoundException` and `ForbiddenVehicleUsageException`
