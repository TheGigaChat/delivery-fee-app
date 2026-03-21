# Setup

## Prerequisites

- Java 21
- Maven Wrapper included in the repository

## Local Run

```powershell
.\mvnw.cmd spring-boot:run
```

## Local Test

```powershell
.\mvnw.cmd test
```

## Current Dependencies

- `spring-boot-starter-web`
- `spring-boot-starter-data-jpa`
- `spring-boot-starter-validation`
- `h2`
- `spring-boot-starter-test`

## Planned Configuration

Application configuration should cover:

- server port
- H2 datasource settings
- JPA behavior
- H2 console
- scheduler cron expression
- weather source configuration if externalized

## Environment Notes

The project currently has only the default `application.properties`. As environment-specific behavior appears, document profiles and required properties here.
