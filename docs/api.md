# API

## Status

No application-specific REST endpoints are implemented yet.

## Planned Endpoint

`GET /api/delivery-fee`

## Planned Query Parameters

- `city`
- `vehicleType`

Example:

```text
GET /api/delivery-fee?city=TARTU&vehicleType=BIKE
```

## Planned Success Response

Suggested response shape:

```json
{
  "city": "TARTU",
  "vehicleType": "BIKE",
  "deliveryFee": 3.0
}
```

## Planned Error Response

Suggested response shape:

```json
{
  "message": "Usage of selected vehicle type is forbidden"
}
```

## Expected Error Cases

- unknown city
- unknown vehicle type
- missing weather data for city
- forbidden vehicle usage due to weather conditions

## Documentation Policy

When the endpoint is implemented, this file should be updated with:

- exact parameter names and accepted values
- example success and failure responses
- HTTP status codes
- validation rules
