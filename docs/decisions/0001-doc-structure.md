# 0001: Documentation Structure

## Status

Accepted

## Context

The repository started with a single `plan.md`, an empty `AGENTS.md`, and no stable place to track implementation decisions or per-task changes. That structure would become hard to maintain once coding begins.

## Decision

Use this documentation layout:

- `README.md` for project entry point
- `AGENTS.md` for working rules
- `docs/architecture.md`, `docs/api.md`, `docs/setup.md`, and `docs/testing.md` for persistent technical documentation
- `docs/planning/` for roadmap and backlog
- `docs/decisions/` for architecture and design decisions
- `docs/changes/` for chronological task-level change tracking

## Consequences

- Permanent documentation is easier to find.
- Change history becomes explicit and incremental.
- Planning, decisions, and implementation notes are separated instead of mixed in one file.
