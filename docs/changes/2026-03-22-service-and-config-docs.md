# 2026-03-22 Service And Config Docs

## Summary

Updated the permanent documentation to reflect the newly added service, startup configuration, enum, and latest-weather repository query.

## Changes

- documented `VehicleType` as a newly added domain enum
- updated architecture notes to show that `WeatherDataRepository` now supports latest-by-city lookup
- documented `WeatherDataService` as the first service-layer component for weather retrieval
- documented `TestDataRunner` as startup configuration that seeds sample weather data for local development
- updated repository status notes in `README.md`
- adjusted the backlog to reflect that latest-weather lookup is started and that temporary startup seeding is in use

## Why

The codebase now includes the first service-layer logic and local startup data bootstrapping, but the docs still described an earlier state with no service layer and only a basic repository. This change aligns the project documentation with the actual implementation.

## Tests

- no tests were added or updated
- documentation only in this task

## Follow-Up

- add tests for the latest-by-city repository query and `WeatherDataService`
- decide whether `TestDataRunner` should remain unconditional or move behind a dedicated development profile
- revisit package placement for `WeatherData` and `VehicleType`, which still live under `com.example.delivery.enums`
