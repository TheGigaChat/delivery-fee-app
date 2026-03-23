# Roadmap

## Goal

Build a Spring Boot application that:

- stores weather data in H2
- imports weather from the Estonian Environment Agency on a schedule
- calculates delivery fees by city, vehicle, and latest weather
- exposes a REST endpoint for fee requests

## Final Delivery Outcome

All planned core milestones for the assignment are completed.

## Completed Milestones

### Milestone 1

Completed:

- project configuration
- package structure
- H2 setup
- core enums and entity model

### Milestone 2

Completed:

- external weather XML fetch
- XML parsing
- station filtering and mapping
- historical weather persistence
- latest-by-city repository lookup

### Milestone 3

Completed:

- delivery fee calculation service
- custom domain exceptions
- REST controller
- global exception handling

### Milestone 4

Completed:

- unit and slice/repository tests
- final project documentation
- change tracking across implementation steps

## Post-Completion Ideas

These are outside the current assignment scope:

- externalize the weather source URL
- add end-to-end integration tests with mocked HTTP
- support historical fee calculation by requested timestamp
- move more fee rules into configuration if runtime tuning is required
