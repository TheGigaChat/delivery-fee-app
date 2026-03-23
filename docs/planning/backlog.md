# Backlog

## Completed Core Work

- create the weather observation domain model
- implement weather source import
- persist historical weather observations
- implement latest-weather lookup by city
- implement delivery fee calculation rules
- expose the delivery-fee REST endpoint
- add centralized exception handling
- add test coverage for the main business flows
- document architecture, API, setup, and testing

## Remaining Optional Work

- externalize the weather source URL instead of hardcoding it in `WeatherApiClient`
- add a full import-to-API integration test with mocked external HTTP
- support historical fee calculation by explicit datetime
- move more weather rule constants into configuration if operational tuning becomes necessary

## Documentation State

The permanent documentation now reflects the final delivered implementation.

Future updates should only be needed for:

- bug fixes
- optional enhancements
- deployment-specific configuration
