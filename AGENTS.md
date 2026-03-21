# Agent Working Agreement

This file defines how work in this repository should be documented and delivered.

## Responsibilities

The agent may:

- review the existing codebase
- implement missing functionality
- write and update tests
- document every meaningful change

## Documentation Rules

For each meaningful task:

1. update the relevant permanent documentation under `docs/`
2. add or amend a dated entry under `docs/changes/`
3. add a decision record under `docs/decisions/` when a design choice has long-term impact

Do not keep active project knowledge only in temporary chat context.

## Testing Rules

- Prefer unit tests for fee calculation rules and parsing logic.
- Add integration tests for repository queries, scheduling boundaries, and REST endpoints when behavior spans layers.
- When code changes affect behavior, tests should be updated in the same task.

## Delivery Rules

- Keep controllers thin.
- Keep business rules in services or dedicated rule components.
- Preserve historical weather observations.
- Avoid undocumented assumptions; record them in `docs/` when they affect behavior.

## Change Tracking

Each change note in `docs/changes/` should state:

- what changed
- why it changed
- tests added or updated
- open risks or follow-up items
