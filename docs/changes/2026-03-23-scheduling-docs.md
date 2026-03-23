# 2026-03-23 Scheduling Docs

## Summary

Updated the permanent documentation to reflect scheduled weather import configuration and application-level scheduling enablement.

## Changes

- documented `@EnableScheduling` on the main Spring Boot application class
- documented `WeatherImportScheduler` as the active import trigger
- documented the `weather.import.cron` property from `application.properties`
- updated setup and architecture notes to show that scheduled execution has replaced manual startup import as the intended path
- updated the backlog to reflect that configurable scheduled import is now started

## Why

The codebase now includes a real scheduler-driven import trigger, but the docs still described import execution as primarily manual startup behavior. This change aligns the permanent documentation with the current application flow and configuration.

## Tests

- no tests were added or updated in this task
- user indicated no new tests were needed for this documentation update

## Follow-Up

- add tests for scheduler boundaries or scheduling configuration when that behavior becomes important enough to verify automatically
- decide whether the commented `TestDataRunner` should be removed entirely once the scheduled flow is stable
- consider environment-specific cron overrides if deployment profiles are added
