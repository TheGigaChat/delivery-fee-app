# API

## Status

The delivery-fee REST endpoint is now implemented.

## Endpoint

`GET /api/delivery-fee`

## Query Parameters

- `city`
- `vehicleType`

Example:

```text
GET /api/delivery-fee?city=TARTU&vehicleType=BIKE
```

## Success Response

Current response shape:

```json
{
  "city": "TARTU",
  "vehicleType": "BIKE",
  "deliveryFee": 3.0
}
```

## Error Response

Current error response shape:

```json
{
  "timestamp": "2026-03-23T12:00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Usage of selected vehicle type is forbidden",
  "path": "/api/delivery-fee"
}
```

## Implemented Behavior

The endpoint delegates to `DeliveryFeeService`, which currently applies:

- base fee by `city` and `vehicleType`
- scooter and bike temperature surcharge rules
- bike wind surcharge and forbidden-use rules
- weather phenomenon surcharge and forbidden-use rules

## HTTP Status Mapping

- `200 OK` for valid requests
- `400 Bad Request` for invalid enum/request parameter values
- `400 Bad Request` for `ForbiddenVehicleUsageException`
- `404 Not Found` for `WeatherDataNotFoundException`
- `500 Internal Server Error` for uncaught server-side failures

## Expected Error Cases

- unknown city
- unknown vehicle type
- missing weather data for city
- forbidden vehicle usage due to weather conditions
