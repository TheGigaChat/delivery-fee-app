# Backlog

## Core Work

- create domain model for weather observations
- integrate weather source import
- implement delivery fee calculation rules
- expose fee calculation endpoint
- add centralized error handling
- write unit and integration tests

## In Progress Or Recently Started

- service-layer weather lookup based on the latest persisted observation
- external weather XML fetch through a first API client
- scheduler-driven import execution through configurable cron

## Documentation Work

- update API examples once endpoint exists
- document environment-specific profiles and overrides when they are introduced
- record design decisions when implementation branches

## Optional Work

- support historical fee calculation by datetime
- externalize the weather source URL instead of hardcoding it in the client
- make delivery fee rules configurable instead of hardcoded
