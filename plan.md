# Delivery Fee App — Plan

## Goal

Build a Spring Boot application that:

- stores weather data in H2
- imports weather from the Estonian Environment Agency on a schedule
- calculates delivery fee by city, vehicle, and latest weather
- exposes a REST endpoint for fee requests :contentReference[oaicite:0]{index=0}

---

## 1. Project setup

- Create Spring Boot project
- Use Java, Spring Boot, H2, Spring Web, Spring Data JPA, Validation
- Make sure the app starts successfully
- Create basic package structure:
    - `controller`
    - `service`
    - `repository`
    - `entity`
    - `dto`
    - `config`
    - `exception`

---

## 2. Configure the application

- Add `application.properties`
- Configure:
    - server port
    - H2 database
    - JPA settings
    - H2 console
    - cron expression property for scheduler
- Enable scheduling in the app

---

## 3. Design the domain model

Create the main database entity for weather data.

### `WeatherData`
Fields:
- id
- stationName
- wmoCode
- airTemperature
- windSpeed
- weatherPhenomenon
- observationTimestamp
- city

The task requires storing:
- station name
- WMO code
- air temperature
- wind speed
- weather phenomenon
- timestamp of observations :contentReference[oaicite:1]{index=1}

---

## 4. Create enums

Create enums for safer logic:

- `City`
    - TALLINN
    - TARTU
    - PARNU

- `VehicleType`
    - CAR
    - SCOOTER
    - BIKE

Optional:
- map station names to cities

Required stations:
- Tallinn-Harku
- Tartu-Tõravere
- Pärnu :contentReference[oaicite:2]{index=2}

---

## 5. Create repository layer

Create `WeatherDataRepository`.

Needed queries:
- save weather entries
- find latest weather entry by city
- optionally find weather by city and timestamp later for bonus

---

## 6. Implement weather import feature

Create a service that:
- calls the XML weather API
- parses the XML
- filters only the required stations
- maps station data to `WeatherData`
- saves new rows to database

The task requires:
- configurable cron job
- default run time: every hour at `HH:15`
- history must be preserved
- new imports must insert new rows, not overwrite old ones :contentReference[oaicite:3]{index=3}

---

## 7. Implement XML parsing

Choose one parsing approach:
- JAXB
- Jackson XML
- DOM parser

Keep it simple.

Need to extract:
- station name
- WMO code
- air temperature
- wind speed
- weather phenomenon
- observation timestamp

---

## 8. Implement scheduled job

Create a scheduler class that:
- runs based on cron property
- calls weather import service
- logs success/failure
- handles errors safely

Default cron should match task requirement: once every hour at minute 15. :contentReference[oaicite:4]{index=4}

---

## 9. Implement delivery fee rules

Create a fee calculation service.

### Step 1 — regional base fee
Implement base fee by city and vehicle:

- Tallinn:
    - Car = 4
    - Scooter = 3.5
    - Bike = 3
- Tartu:
    - Car = 3.5
    - Scooter = 3
    - Bike = 2.5
- Pärnu:
    - Car = 3
    - Scooter = 2.5
    - Bike = 2 :contentReference[oaicite:5]{index=5}

### Step 2 — extra fees
Use latest weather for selected city.

#### Air temperature
For Scooter or Bike:
- below -10 → +1
- between -10 and 0 → +0.5 :contentReference[oaicite:6]{index=6}

#### Wind speed
For Bike:
- between 10 and 20 → +0.5
- above 20 → forbidden vehicle usage :contentReference[oaicite:7]{index=7}

#### Weather phenomenon
For Scooter or Bike:
- snow or sleet → +1
- rain → +0.5
- glaze, hail, thunder → forbidden vehicle usage :contentReference[oaicite:8]{index=8}

### Step 3 — total fee
Total fee = base fee + all applicable extra fees

Calculation must use the **latest weather data** for the city. :contentReference[oaicite:9]{index=9}

---

## 10. Handle forbidden usage and errors

Create custom exceptions, for example:

- `WeatherDataNotFoundException`
- `ForbiddenVehicleUsageException`
- `InvalidInputException`

Add a global exception handler to return clean JSON error responses.

Task explicitly mentions:
- error handling
- returning an error message when vehicle usage is forbidden :contentReference[oaicite:10]{index=10} :contentReference[oaicite:11]{index=11}

---

## 11. Create REST API

Create endpoint for fee calculation.

Example:
- `GET /api/delivery-fee?city=TARTU&vehicleType=BIKE`

Input:
- city
- vehicle type

Output:
- total delivery fee
- or error message

The task requires REST interface documentation too. :contentReference[oaicite:12]{index=12}

---

## 12. Create DTOs

Suggested DTOs:

- `DeliveryFeeResponse`
- `ErrorResponse`

Optional:
- request DTO if using POST instead of GET

---

## 13. Document the API

Add API documentation in one of these ways:

### Simple option
Document in `README.md`:
- endpoint
- parameters
- example requests
- example responses
- error cases

### Better option
Add Swagger / OpenAPI

The task says the REST interface must be documented. :contentReference[oaicite:13]{index=13}

---

## 14. Add tests

Write tests for the most important parts.

### Unit tests
- base fee rules
- temperature extra fee rules
- wind rules
- weather phenomenon rules
- forbidden cases

### Integration tests
- controller endpoint
- repository query for latest weather
- weather import flow if possible

The task explicitly mentions test coverage. :contentReference[oaicite:14]{index=14}

---

## 15. Clean up the codebase

Before finishing:
- remove unused code
- rename unclear methods/classes
- add JavaDoc for public methods
- check layering
- keep controllers thin
- keep business logic in services
- keep repository logic in repositories

The task will evaluate:
- OOP design
- layering
- readability
- documented public methods :contentReference[oaicite:15]{index=15}

---

## 16. Prepare README

Write a small `README.md` with:

- project purpose
- tech stack
- how to run
- how scheduler works
- how to call API
- example request/response
- notes about assumptions
- possible improvements

---

## 17. Final manual checks

Before submission, verify:

- app starts
- scheduler works
- weather rows are inserted
- history is preserved
- fee calculation matches rules
- forbidden cases return proper error
- endpoint works
- README is clear
- code is committed cleanly

---

## 18. Optional bonus

If there is time, add one of these:

### Bonus A
Manage fee rules through REST CRUD instead of hardcoding them. :contentReference[oaicite:16]{index=16}

### Bonus B
Add optional datetime parameter so fee can be calculated using historical weather/business rules for a specific moment. :contentReference[oaicite:17]{index=17}

---

## Recommended implementation order

1. Project setup
2. Config
3. Entity + enum + repository
4. Manual weather insert test
5. Weather API import
6. Scheduler
7. Fee calculation service
8. Exceptions
9. REST controller
10. Tests
11. README
12. Cleanup

---

## Minimal version to finish safely

If time is limited, finish this first:

- one weather table
- scheduler importing required stations
- latest weather lookup
- hardcoded fee rules in service
- one REST endpoint
- basic exception handler
- a few solid tests
- clear README

That is enough to cover the main assignment requirements well.
