package com.example.delivery.service;

import com.example.delivery.entity.WeatherData;
import com.example.delivery.enums.City;
import com.example.delivery.enums.VehicleType;
import com.example.delivery.exception.ForbiddenVehicleUsageException;
import com.example.delivery.exception.WeatherDataNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DeliveryFeeServiceTest {

    private final WeatherDataService weatherDataService = mock(WeatherDataService.class);
    private final DeliveryFeeService deliveryFeeService = new DeliveryFeeService(weatherDataService);

    @ParameterizedTest
    @CsvSource({
            "TALLINN,CAR,4.0",
            "TALLINN,SCOOTER,3.5",
            "TALLINN,BIKE,3.0",
            "TARTU,CAR,3.5",
            "TARTU,SCOOTER,2.5",
            "TARTU,BIKE,3.0",
            "PARNU,CAR,3.0",
            "PARNU,SCOOTER,2.0",
            "PARNU,BIKE,2.5"
    })
    void shouldReturnBaseFeeForEachCityAndVehicle(City city, VehicleType vehicleType, String expectedFee) {
        when(weatherDataService.getLatestWeather(city)).thenReturn(Optional.of(weather(city, "5", "5", "Clear")));

        BigDecimal result = deliveryFeeService.calculateDeliveryFee(city, vehicleType);

        assertThat(result).isEqualByComparingTo(expectedFee);
    }

    @Test
    void shouldAddScooterTemperatureFeeWhenBelowMinusTen() {
        when(weatherDataService.getLatestWeather(City.TALLINN))
                .thenReturn(Optional.of(weather(City.TALLINN, "-11", "5", "Clear")));

        BigDecimal result = deliveryFeeService.calculateDeliveryFee(City.TALLINN, VehicleType.SCOOTER);

        assertThat(result).isEqualByComparingTo("4.5");
    }

    @Test
    void shouldAddBikeTemperatureFeeWhenBetweenMinusTenAndZero() {
        when(weatherDataService.getLatestWeather(City.TARTU))
                .thenReturn(Optional.of(weather(City.TARTU, "-5", "5", "Clear")));

        BigDecimal result = deliveryFeeService.calculateDeliveryFee(City.TARTU, VehicleType.BIKE);

        assertThat(result).isEqualByComparingTo("3.5");
    }

    @Test
    void shouldAddBikeWindFeeWhenWindIsBetweenTenAndTwenty() {
        when(weatherDataService.getLatestWeather(City.PARNU))
                .thenReturn(Optional.of(weather(City.PARNU, "5", "15", "Clear")));

        BigDecimal result = deliveryFeeService.calculateDeliveryFee(City.PARNU, VehicleType.BIKE);

        assertThat(result).isEqualByComparingTo("3.0");
    }

    @Test
    void shouldThrowWhenBikeWindSpeedIsAboveTwenty() {
        when(weatherDataService.getLatestWeather(City.TALLINN))
                .thenReturn(Optional.of(weather(City.TALLINN, "5", "21", "Clear")));

        assertThatThrownBy(() -> deliveryFeeService.calculateDeliveryFee(City.TALLINN, VehicleType.BIKE))
                .isInstanceOf(ForbiddenVehicleUsageException.class)
                .hasMessage("Usage of selected vehicle type is forbidden");
    }

    @Test
    void shouldAddPhenomenonFeeForSnowOrSleet() {
        when(weatherDataService.getLatestWeather(City.TARTU))
                .thenReturn(Optional.of(weather(City.TARTU, "2", "5", "Light snow")));

        BigDecimal result = deliveryFeeService.calculateDeliveryFee(City.TARTU, VehicleType.SCOOTER);

        assertThat(result).isEqualByComparingTo("3.5");
    }

    @Test
    void shouldAddPhenomenonFeeForRain() {
        when(weatherDataService.getLatestWeather(City.PARNU))
                .thenReturn(Optional.of(weather(City.PARNU, "2", "5", "Moderate rain")));

        BigDecimal result = deliveryFeeService.calculateDeliveryFee(City.PARNU, VehicleType.SCOOTER);

        assertThat(result).isEqualByComparingTo("2.5");
    }

    @ParameterizedTest
    @CsvSource({
            "glaze",
            "hail",
            "thunder"
    })
    void shouldThrowWhenPhenomenonForbidsVehicleUsage(String phenomenon) {
        when(weatherDataService.getLatestWeather(City.TALLINN))
                .thenReturn(Optional.of(weather(City.TALLINN, "2", "5", phenomenon)));

        assertThatThrownBy(() -> deliveryFeeService.calculateDeliveryFee(City.TALLINN, VehicleType.BIKE))
                .isInstanceOf(ForbiddenVehicleUsageException.class)
                .hasMessage("Usage of selected vehicle type is forbidden");
    }

    @Test
    void shouldThrowWhenWeatherDataIsMissing() {
        when(weatherDataService.getLatestWeather(City.TARTU)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> deliveryFeeService.calculateDeliveryFee(City.TARTU, VehicleType.CAR))
                .isInstanceOf(WeatherDataNotFoundException.class)
                .hasMessage("No weather data found for city: TARTU");
    }

    private WeatherData weather(City city, String airTemperature, String windSpeed, String phenomenon) {
        WeatherData weatherData = new WeatherData();
        weatherData.setCity(city);
        weatherData.setStationName("Test station");
        weatherData.setWmoCode("26038");
        weatherData.setObservationTimestamp(LocalDateTime.of(2026, 3, 23, 12, 0));
        weatherData.setAirTemperature(new BigDecimal(airTemperature));
        weatherData.setWindSpeed(new BigDecimal(windSpeed));
        weatherData.setWeatherPhenomenon(phenomenon);
        return weatherData;
    }
}
