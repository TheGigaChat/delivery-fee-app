# 2026-03-23 Delivery Fee Test Profile Configuration

## What Changed

- updated `DeliveryFeeServiceTest` to load fee configuration from the Spring test profile instead of relying on an implicit setup
- kept the delivery-fee test values in `src/test/resources/application-test.yml` so fee assertions stay stable when runtime configuration changes
- added a test assertion that confirms the base-fee table is bound from the test profile
- updated `docs/testing.md` to document the dedicated fee-service test configuration

## Why It Changed

The delivery-fee service now reads base fees from configuration properties. A dedicated test profile prevents unit tests from breaking whenever production fee values are refactored or externalized.

## Tests Added Or Updated

- updated `DeliveryFeeServiceTest`
- added configuration-binding coverage inside `DeliveryFeeServiceTest`

## Open Risks Or Follow-Up Items

- if the assignment's required fee values change, both `src/main/resources/application.yml` and `src/test/resources/application-test.yml` must be reviewed deliberately so test expectations still reflect the intended business rules
