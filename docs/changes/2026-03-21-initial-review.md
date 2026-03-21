# 2026-03-21 Initial Repository Review

## Summary

Created the initial documentation baseline for the repository and replaced the monolithic planning file with a structured `docs/` layout.

## Changes

- added root `README.md`
- populated `AGENTS.md` with repository working rules
- added architecture, API, setup, and testing documents
- added planning documents for roadmap and backlog
- added the first decision record for documentation structure
- removed legacy `plan.md`
- documented the newly added `City` enum and current `WeatherData` entity state

## Why

The project needs a durable place to record implementation progress, decisions, and testing expectations before feature work starts.

## Tests

- no code behavior changed
- no tests were added in this task

## Follow-Up

- refine the roadmap once the implementation order is confirmed
- update API and architecture docs as real components are introduced
- add `VehicleType` when implemented and align package structure for `WeatherData`
