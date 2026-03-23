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
- scheduled import cron property `weather.import.cron=0 15 * * * *`

## Spring Beans

The current application configuration also includes:

- `AppConfig`, which registers a shared `RestTemplate` bean
- injection of that `RestTemplate` into `WeatherApiClient` for outbound HTTP calls
- a Spring-managed `WeatherXmlParser` component for XML-to-DTO parsing
- injected import collaborators such as `StationCityMapper`, `WeatherImportService`, and `WeatherImportScheduler`

## Scheduling

Scheduled execution is enabled through `@EnableScheduling` on the main application class.

The current scheduled import setup includes:

- `WeatherImportScheduler`
- `@Scheduled(cron = "${weather.import.cron}")`
- an application property that controls the import cadence without code changes

## Startup Bootstrapping

The previous `TestDataRunner`-based import bootstrap is currently commented out.

The intended execution path is now the scheduler-driven import flow.

## Planned Configuration

Configuration still expected in later iterations:

- any environment-specific overrides if deployment targets are added
- weather source configuration if externalized

## Environment Notes

The project now has a concrete local-development `application.properties` for H2, JPA, and import scheduling bootstrapping. The external weather API URL is still hardcoded in `WeatherApiClient`; if that moves into configuration, document the property here.
