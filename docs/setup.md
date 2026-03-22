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

## Current Configuration

The current `src/main/resources/application.properties` defines:

- `spring.application.name=delivery-fee-app`
- H2 datasource URL `jdbc:h2:mem:deliverydb`
- datasource driver `org.h2.Driver`
- datasource username `sa`
- empty datasource password for local in-memory use
- `spring.jpa.hibernate.ddl-auto=create-drop`
- SQL logging enabled with formatted SQL output
- H2 console enabled at `/h2-console`

## Startup Bootstrapping

Local application startup currently also includes:

- a `CommandLineRunner` component named `TestDataRunner`
- insertion of one sample `WeatherData` row for `City.TALLINN`
- use of `LocalDateTime.now()` for the seed observation timestamp at boot time

This seed data exists to make repository and service development easier before scheduled import is implemented.

## Planned Configuration

Configuration still expected in later iterations:

- scheduler cron expression
- weather source configuration if externalized
- any environment-specific overrides if deployment targets are added

## Environment Notes

The project now has a concrete local-development `application.properties` for H2 and JPA bootstrapping. As environment-specific behavior appears, document profiles and required properties here.
