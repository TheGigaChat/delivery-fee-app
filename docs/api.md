# API

## Endpoint

`GET /api/delivery-fee`

## Query Parameters

- `city`: `TALLINN`, `TARTU`, `PARNU`
- `vehicleType`: `CAR`, `SCOOTER`, `BIKE`

Example:

```text
GET /api/delivery-fee?city=TARTU&vehicleType=BIKE
```

## Success Response

HTTP status:

- `200 OK`

Body:

```json
{
  "city": "TARTU",
  "vehicleType": "BIKE",
  "deliveryFee": 3.0
}
```

Response fields:

- `city`
- `vehicleType`
- `deliveryFee`

## Error Response

Error body shape:

```json
{
  "timestamp": "2026-03-23T12:00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Usage of selected vehicle type is forbidden",
  "path": "/api/delivery-fee"
}
```

Error fields:

- `timestamp`
- `status`
- `error`
- `message`
- `path`

## Status Mapping

- `200 OK` for valid requests
- `400 Bad Request` for invalid enum/query parameter values
- `400 Bad Request` for forbidden vehicle usage caused by weather conditions
- `404 Not Found` when weather data for the requested city is missing
- `500 Internal Server Error` for uncaught server-side failures

## Implemented Business Rules

The endpoint delegates to `DeliveryFeeService`.

Applied rules:

- base fee by city and vehicle type from configuration
- scooter and bike temperature surcharge
- bike wind surcharge
- bike forbidden-use threshold for high wind
- scooter and bike phenomenon surcharge
- scooter and bike forbidden-use conditions for dangerous phenomena

## Current Base Fee Configuration

Configured in `src/main/resources/application.yml`:

- Tallinn: `CAR 4.0`, `SCOOTER 3.5`, `BIKE 3.0`
- Tartu: `CAR 3.5`, `SCOOTER 3.0`, `BIKE 2.5`
- Parnu: `CAR 3.0`, `SCOOTER 2.5`, `BIKE 2.0`

## Weather Rule Summary

- temperature `< -10`: add `1.0` for scooter and bike
- temperature `>= -10` and `<= 0`: add `0.5` for scooter and bike
- bike wind `>= 10` and `<= 20`: add `0.5`
- bike wind `> 20`: forbidden
- `snow` or `sleet`: add `1.0` for scooter and bike
- `rain`: add `0.5` for scooter and bike
- `glaze`, `hail`, or `thunder`: forbidden for scooter and bike

## Error Cases To Expect

- unsupported `city`
- unsupported `vehicleType`
- no imported weather data yet for the requested city
- forbidden vehicle usage due to wind or phenomenon
- missing base-fee configuration for a city/vehicle pair
