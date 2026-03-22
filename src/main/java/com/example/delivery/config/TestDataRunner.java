package com.example.delivery.config;

import com.example.delivery.enums.City;
import com.example.delivery.enums.WeatherData;
import com.example.delivery.repository.WeatherDataRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class TestDataRunner implements CommandLineRunner {

    private final WeatherDataRepository weatherDataRepository;
    public TestDataRunner(WeatherDataRepository weatherDataRepository) {
        this.weatherDataRepository = weatherDataRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        WeatherData weatherData = new WeatherData();
        weatherData.setStationName("Tallinn-Harku");
        weatherData.setWmoCode("26038");
        weatherData.setAirTemperature(new BigDecimal("2.5"));
        weatherData.setWindSpeed(new BigDecimal("4.7"));
        weatherData.setWeatherPhenomenon("Clear");
        weatherData.setObservationTimestamp(LocalDateTime.now());
        weatherData.setCity(City.TALLINN);

        weatherDataRepository.save(weatherData);
    }
}
