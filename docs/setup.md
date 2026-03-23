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

## Runtime Configuration

### `src/main/resources/application.properties`

Defines:

- `spring.application.name=delivery-fee-app`
- H2 datasource URL `jdbc:h2:mem:deliverydb`
- datasource driver `org.h2.Driver`
- datasource username `sa`
- empty datasource password
- `spring.jpa.hibernate.ddl-auto=create-drop`
- SQL logging enabled
- formatted SQL output
- `spring.jpa.open-in-view=false`
- H2 console enabled at `/h2-console`
- scheduled import cron via `weather.import.cron=0 15 * * * *`

### `src/main/resources/application.yml`

Defines:

- `delivery.fee.base-fees`

Current configured values:

- Tallinn: `CAR 4.0`, `SCOOTER 3.5`, `BIKE 3.0`
- Tartu: `CAR 3.5`, `SCOOTER 3.0`, `BIKE 2.5`
- Parnu: `CAR 3.0`, `SCOOTER 2.5`, `BIKE 2.0`

### `src/test/resources/application-test.yml`

Defines the fee table used by `DeliveryFeeServiceTest`.

This keeps fee-service tests stable when runtime fee values are changed deliberately.

## Spring Beans

The application config includes:

- `AppConfig` for the shared `RestTemplate`
- `DeliveryFeeProperties` for base-fee binding
- `WeatherXmlParser` as a Spring component
- `StationCityMapper` as a Spring component
- `WeatherImportScheduler` as the scheduled trigger

## Scheduling

Scheduling is enabled through `@EnableScheduling` in the application bootstrap class.

The active scheduled flow is:

1. `WeatherImportScheduler`
2. `WeatherImportService`
3. `WeatherApiClient`
4. `WeatherXmlParser`
5. `WeatherDataRepository`

## External Dependency

The application fetches weather data from:

```text
https://www.ilmateenistus.ee/ilma_andmed/xml/observations.php
```

The source URL is still hardcoded in `WeatherApiClient`.

## Local Access

Application endpoint:

```text
http://localhost:8080/api/delivery-fee
```

H2 console:

```text
http://localhost:8080/h2-console
```
