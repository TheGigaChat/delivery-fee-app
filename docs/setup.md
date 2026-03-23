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
- `jackson-dataformat-xml`
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

## Spring Beans

The current application configuration also includes:

- `AppConfig`, which registers a shared `RestTemplate` bean
- injection of that `RestTemplate` into `WeatherApiClient` for outbound HTTP calls
- a Spring-managed `WeatherXmlParser` component for XML-to-DTO parsing
- injected import collaborators such as `StationCityMapper` and `WeatherImportService`

## Startup Bootstrapping

Local application startup currently also includes:

- a `CommandLineRunner` component named `TestDataRunner`
- delegation to `WeatherImportService.importWeatherData()`
- fetch, parse, map, and persistence executed through the service layer

This startup path currently exercises the import service end-to-end while automated scheduling is still being built.

## Planned Configuration

Configuration still expected in later iterations:

- scheduler cron expression
- weather source configuration if externalized
- any environment-specific overrides if deployment targets are added

## Environment Notes

The project now has a concrete local-development `application.properties` for H2 and JPA bootstrapping. The external weather API URL is still hardcoded in `WeatherApiClient`; if that moves into configuration, document the property here.
