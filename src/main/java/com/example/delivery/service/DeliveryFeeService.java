package com.example.delivery.service;

import com.example.delivery.config.DeliveryFeeProperties;
import com.example.delivery.entity.WeatherData;
import com.example.delivery.enums.City;
import com.example.delivery.enums.VehicleType;
import com.example.delivery.exception.ForbiddenVehicleUsageException;
import com.example.delivery.exception.WeatherDataNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;

@Service
public class DeliveryFeeService {

    private final WeatherDataService weatherDataService;
    private final DeliveryFeeProperties deliveryFeeProperties;

    /**
     * Creates the service that calculates delivery fees.
     *
     * @param weatherDataService service used to load the latest weather for a city
     */
    public DeliveryFeeService(WeatherDataService weatherDataService, DeliveryFeeProperties deliveryFeeProperties) {
        this.weatherDataService = weatherDataService;
        this.deliveryFeeProperties = deliveryFeeProperties;
    }

    /**
     * Calculates the delivery fee for the requested city and vehicle type.
     *
     * @param city requested city
     * @param vehicleType requested vehicle type
     * @return calculated delivery fee
     */
    public BigDecimal calculateDeliveryFee(City city, VehicleType vehicleType) {
        WeatherData weatherData = weatherDataService.getLatestWeather(city)
                .orElseThrow(() -> new WeatherDataNotFoundException(
                        "No weather data found for city: " + city
                ));

        BigDecimal baseFee = calculateBaseFee(city, vehicleType);
        BigDecimal extraFee = calculateExtraFee(weatherData, vehicleType);

        return baseFee.add(extraFee);
    }

    private BigDecimal calculateBaseFee(City city, VehicleType vehicleType) {
        BigDecimal fee = deliveryFeeProperties.getBaseFees()
                .getOrDefault(city, java.util.Collections.emptyMap())
                .get(vehicleType);

        if (fee == null) {
            throw new IllegalArgumentException(
                    "Base fee not configured for city " + city + " and vehicle type " + vehicleType
            );
        }

        return fee;
    }

    private BigDecimal calculateExtraFee(WeatherData weatherData, VehicleType vehicleType) {
        BigDecimal extraFee = BigDecimal.ZERO;

        extraFee = extraFee.add(calculateAirTemperatureExtraFee(weatherData, vehicleType));
        extraFee = extraFee.add(calculateWindSpeedExtraFee(weatherData, vehicleType));
        extraFee = extraFee.add(calculateWeatherPhenomenonExtraFee(weatherData, vehicleType));

        return extraFee;
    }

    private BigDecimal calculateAirTemperatureExtraFee(WeatherData weatherData, VehicleType vehicleType) {
        if (vehicleType != VehicleType.SCOOTER && vehicleType != VehicleType.BIKE) {
            return BigDecimal.ZERO;
        }

        BigDecimal airTemperature = weatherData.getAirTemperature();
        if (airTemperature == null) {
            return BigDecimal.ZERO;
        }

        if (airTemperature.compareTo(new BigDecimal("-10")) < 0) {
            return new BigDecimal("1.0");
        }

        if (airTemperature.compareTo(BigDecimal.ZERO) <= 0) {
            return new BigDecimal("0.5");
        }

        return BigDecimal.ZERO;
    }

    private BigDecimal calculateWindSpeedExtraFee(WeatherData weatherData, VehicleType vehicleType) {
        if (vehicleType != VehicleType.BIKE) {
            return BigDecimal.ZERO;
        }

        BigDecimal windSpeed = weatherData.getWindSpeed();
        if (windSpeed == null) {
            return BigDecimal.ZERO;
        }

        if (windSpeed.compareTo(new BigDecimal("20")) > 0) {
            throw new ForbiddenVehicleUsageException("Usage of selected vehicle type is forbidden");
        }

        if (windSpeed.compareTo(new BigDecimal("10")) >= 0) {
            return new BigDecimal("0.5");
        }

        return BigDecimal.ZERO;
    }

    private BigDecimal calculateWeatherPhenomenonExtraFee(WeatherData weatherData, VehicleType vehicleType) {
        if (vehicleType != VehicleType.SCOOTER && vehicleType != VehicleType.BIKE) {
            return BigDecimal.ZERO;
        }

        String phenomenon = weatherData.getWeatherPhenomenon();
        if (phenomenon == null || phenomenon.isBlank()) {
            return BigDecimal.ZERO;
        }

        String normalized = phenomenon.toLowerCase();

        if (normalized.contains("glaze") || normalized.contains("hail") || normalized.contains("thunder")) {
            throw new ForbiddenVehicleUsageException("Usage of selected vehicle type is forbidden");
        }

        if (normalized.contains("snow") || normalized.contains("sleet")) {
            return new BigDecimal("1.0");
        }

        if (normalized.contains("rain")) {
            return new BigDecimal("0.5");
        }

        return BigDecimal.ZERO;
    }
}
