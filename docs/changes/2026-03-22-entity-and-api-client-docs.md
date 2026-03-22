# 2026-03-22 Entity And Api Client Docs

## Summary

Updated the permanent documentation to reflect the entity package refactor, the first external weather API client, and the startup runner changes used to exercise that client.

## Changes

- documented that `WeatherData` now lives under `com.example.delivery.entity`
- documented `AppConfig` and the shared `RestTemplate` bean
- documented `WeatherApiClient` as the first external integration component for fetching raw weather observation XML
- documented `StationCityMapper` as preparation for mapping external stations to supported cities
- updated `TestDataRunner` documentation to show that it now calls the external API client and then seeds a sample weather record
- updated testing notes to record that the new API client path is only manually verified so far

## Why

The implementation now includes an external dependency boundary and a cleaner package layout for the weather entity, but the docs still described only a local repository/service setup. This change brings the documentation up to date with the current code and the actual startup behavior.

## Tests

- no automated tests were added or updated in this documentation task
- the codebase currently uses `TestDataRunner` as a manual smoke-check for `WeatherApiClient`

## Follow-Up

- add automated tests for `WeatherApiClient`, `StationCityMapper`, and the import flow
- replace raw XML console output in `TestDataRunner` with structured parsing or remove it once dedicated tests exist
- externalize the weather observations URL instead of hardcoding it in the client
