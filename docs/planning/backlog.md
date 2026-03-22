# Backlog

## Core Work

- create domain model for weather observations
- implement required enums and input validation
- integrate weather source import
- implement scheduled import at configurable cron
- implement delivery fee calculation rules
- expose fee calculation endpoint
- add centralized error handling
- write unit and integration tests

## In Progress Or Recently Started

- service-layer weather lookup based on the latest persisted observation
- local startup seed data until external import replaces manual bootstrapping

## Documentation Work

- update API examples once endpoint exists
- document environment-specific profiles and overrides when they are introduced
- record design decisions when implementation branches

## Optional Work

- support historical fee calculation by datetime
- make delivery fee rules configurable instead of hardcoded
