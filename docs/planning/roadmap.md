# Roadmap

## Goal

Build a Spring Boot application that:

- stores weather data in H2
- imports weather from the Estonian Environment Agency on a schedule
- calculates delivery fee by city, vehicle, and latest weather
- exposes a REST endpoint for fee requests

## Recommended Implementation Order

1. project configuration and package structure
2. domain model and enums
3. repository layer
4. weather import client and XML parsing
5. scheduled import
6. fee calculation service
7. exception handling
8. REST controller
9. test coverage
10. final cleanup and API documentation

## Delivery Milestones

### Milestone 1

Application starts with project structure, configuration, H2 setup, and initial domain primitives such as enums and the first entity.

### Milestone 2

Weather data can be imported and persisted with history preserved.

### Milestone 3

Delivery fee rules are implemented and exposed through REST.

### Milestone 4

Tests and documentation cover the main flows and edge cases.
