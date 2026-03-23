package com.example.delivery.service;

import com.example.delivery.config.DeliveryFeeProperties;
import com.example.delivery.entity.WeatherData;
import com.example.delivery.enums.City;
import com.example.delivery.enums.VehicleType;
import com.example.delivery.exception.ForbiddenVehicleUsageException;
import com.example.delivery.exception.WeatherDataNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = DeliveryFeeServiceTest.TestConfig.class)
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.yml")
class DeliveryFeeServiceTest {

    @TestConfiguration
    @EnableConfigurationProperties(DeliveryFeeProperties.class)
    static class TestConfig {

        @Bean
        DeliveryFeeService deliveryFeeService(WeatherDataService weatherDataService, DeliveryFeeProperties deliveryFeeProperties) {
            return new DeliveryFeeService(weatherDataService, deliveryFeeProperties);
        }
    }

    @MockBean
    private WeatherDataService weatherDataService;

    @Autowired
    private DeliveryFeeService deliveryFeeService;

    @Autowired
    private DeliveryFeeProperties deliveryFeeProperties;

    @Test
    void shouldLoadBaseFeesFromTestConfiguration() {
        assertThat(deliveryFeeProperties.getBaseFees().get(City.TALLINN).get(VehicleType.CAR))
                .isEqualByComparingTo("4.0");
        assertThat(deliveryFeeProperties.getBaseFees().get(City.TARTU).get(VehicleType.SCOOTER))
                .isEqualByComparingTo("2.5");
        assertThat(deliveryFeeProperties.getBaseFees().get(City.PARNU).get(VehicleType.BIKE))
                .isEqualByComparingTo("2.5");
    }

    @Test
    void shouldThrowWhenBaseFeeIsNotConfigured() {
        when(weatherDataService.getLatestWeather(City.TALLINN))
                .thenReturn(Optional.of(weather(City.TALLINN, new BigDecimal("5"), new BigDecimal("5"), "Clear")));

        Map<City, Map<VehicleType, BigDecimal>> originalBaseFees = copyBaseFees(deliveryFeeProperties.getBaseFees());
        deliveryFeeProperties.getBaseFees().get(City.TALLINN).remove(VehicleType.CAR);

        try {
            assertThatThrownBy(() -> deliveryFeeService.calculateDeliveryFee(City.TALLINN, VehicleType.CAR))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Base fee not configured for city TALLINN and vehicle type CAR");
        } finally {
            deliveryFeeProperties.setBaseFees(originalBaseFees);
        }
    }

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
        when(weatherDataService.getLatestWeather(city)).thenReturn(Optional.of(weather(city, new BigDecimal("5"), new BigDecimal("5"), "Clear")));

        BigDecimal result = deliveryFeeService.calculateDeliveryFee(city, vehicleType);

        assertThat(result).isEqualByComparingTo(expectedFee);
    }

    @Test
    void shouldAddScooterTemperatureFeeWhenBelowMinusTen() {
        when(weatherDataService.getLatestWeather(City.TALLINN))
                .thenReturn(Optional.of(weather(City.TALLINN, new BigDecimal("-11"), new BigDecimal("5"), "Clear")));

        BigDecimal result = deliveryFeeService.calculateDeliveryFee(City.TALLINN, VehicleType.SCOOTER);

        assertThat(result).isEqualByComparingTo("4.5");
    }

    @Test
    void shouldAddBikeTemperatureFeeWhenBetweenMinusTenAndZero() {
        when(weatherDataService.getLatestWeather(City.TARTU))
                .thenReturn(Optional.of(weather(City.TARTU, new BigDecimal("-5"), new BigDecimal("5"), "Clear")));

        BigDecimal result = deliveryFeeService.calculateDeliveryFee(City.TARTU, VehicleType.BIKE);

        assertThat(result).isEqualByComparingTo("3.5");
    }

    @Test
    void shouldIgnoreNullAirTemperature() {
        when(weatherDataService.getLatestWeather(City.TALLINN))
                .thenReturn(Optional.of(weather(City.TALLINN, null, new BigDecimal("5"), "Clear")));

        BigDecimal result = deliveryFeeService.calculateDeliveryFee(City.TALLINN, VehicleType.SCOOTER);

        assertThat(result).isEqualByComparingTo("3.5");
    }

    @Test
    void shouldAddBikeWindFeeWhenWindIsBetweenTenAndTwenty() {
        when(weatherDataService.getLatestWeather(City.PARNU))
                .thenReturn(Optional.of(weather(City.PARNU, new BigDecimal("5"), new BigDecimal("15"), "Clear")));

        BigDecimal result = deliveryFeeService.calculateDeliveryFee(City.PARNU, VehicleType.BIKE);

        assertThat(result).isEqualByComparingTo("3.0");
    }

    @Test
    void shouldThrowWhenBikeWindSpeedIsAboveTwenty() {
        when(weatherDataService.getLatestWeather(City.TALLINN))
                .thenReturn(Optional.of(weather(City.TALLINN, new BigDecimal("5"), new BigDecimal("21"), "Clear")));

        assertThatThrownBy(() -> deliveryFeeService.calculateDeliveryFee(City.TALLINN, VehicleType.BIKE))
                .isInstanceOf(ForbiddenVehicleUsageException.class)
                .hasMessage("Usage of selected vehicle type is forbidden");
    }

    @Test
    void shouldIgnoreNullWindSpeed() {
        when(weatherDataService.getLatestWeather(City.TARTU))
                .thenReturn(Optional.of(weather(City.TARTU, new BigDecimal("5"), null, "Clear")));

        BigDecimal result = deliveryFeeService.calculateDeliveryFee(City.TARTU, VehicleType.BIKE);

        assertThat(result).isEqualByComparingTo("3.0");
    }

    @Test
    void shouldAddPhenomenonFeeForSnowOrSleet() {
        when(weatherDataService.getLatestWeather(City.TARTU))
                .thenReturn(Optional.of(weather(City.TARTU, new BigDecimal("2"), new BigDecimal("5"), "Light snow")));

        BigDecimal result = deliveryFeeService.calculateDeliveryFee(City.TARTU, VehicleType.SCOOTER);

        assertThat(result).isEqualByComparingTo("3.5");
    }

    @Test
    void shouldAddPhenomenonFeeForRain() {
        when(weatherDataService.getLatestWeather(City.PARNU))
                .thenReturn(Optional.of(weather(City.PARNU, new BigDecimal("2"), new BigDecimal("5"), "Moderate rain")));

        BigDecimal result = deliveryFeeService.calculateDeliveryFee(City.PARNU, VehicleType.SCOOTER);

        assertThat(result).isEqualByComparingTo("2.5");
    }

    @Test
    void shouldIgnoreNullPhenomenon() {
        when(weatherDataService.getLatestWeather(City.PARNU))
                .thenReturn(Optional.of(weather(City.PARNU, new BigDecimal("2"), new BigDecimal("5"), null)));

        BigDecimal result = deliveryFeeService.calculateDeliveryFee(City.PARNU, VehicleType.SCOOTER);

        assertThat(result).isEqualByComparingTo("2.0");
    }

    @Test
    void shouldIgnoreBlankPhenomenon() {
        when(weatherDataService.getLatestWeather(City.PARNU))
                .thenReturn(Optional.of(weather(City.PARNU, new BigDecimal("2"), new BigDecimal("5"), "   ")));

        BigDecimal result = deliveryFeeService.calculateDeliveryFee(City.PARNU, VehicleType.SCOOTER);

        assertThat(result).isEqualByComparingTo("2.0");
    }

    @ParameterizedTest
    @CsvSource({
            "glaze",
            "hail",
            "thunder"
    })
    void shouldThrowWhenPhenomenonForbidsVehicleUsage(String phenomenon) {
        when(weatherDataService.getLatestWeather(City.TALLINN))
                .thenReturn(Optional.of(weather(City.TALLINN, new BigDecimal("2"), new BigDecimal("5"), phenomenon)));

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

    private Map<City, Map<VehicleType, BigDecimal>> copyBaseFees(Map<City, Map<VehicleType, BigDecimal>> source) {
        Map<City, Map<VehicleType, BigDecimal>> copy = new EnumMap<>(City.class);
        for (Map.Entry<City, Map<VehicleType, BigDecimal>> entry : source.entrySet()) {
            copy.put(entry.getKey(), new EnumMap<>(entry.getValue()));
        }
        return copy;
    }

    private WeatherData weather(City city, BigDecimal airTemperature, BigDecimal windSpeed, String phenomenon) {
        WeatherData weatherData = new WeatherData();
        weatherData.setCity(city);
        weatherData.setStationName("Test station");
        weatherData.setWmoCode("26038");
        weatherData.setObservationTimestamp(LocalDateTime.of(2026, 3, 23, 12, 0));
        weatherData.setAirTemperature(airTemperature);
        weatherData.setWindSpeed(windSpeed);
        weatherData.setWeatherPhenomenon(phenomenon);
        return weatherData;
    }
}
