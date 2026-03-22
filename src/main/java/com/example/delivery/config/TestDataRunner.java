package com.example.delivery.config;

import com.example.delivery.enums.City;
import com.example.delivery.entity.WeatherData;
import com.example.delivery.repository.WeatherDataRepository;
import com.example.delivery.service.WeatherApiClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class TestDataRunner implements CommandLineRunner {

    private final WeatherDataRepository weatherDataRepository;
    private final WeatherApiClient weatherApiClient;

    public TestDataRunner(WeatherDataRepository weatherDataRepository,  WeatherApiClient weatherApiClient) {
        this.weatherDataRepository = weatherDataRepository;
        this.weatherApiClient = weatherApiClient;
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

        System.out.println(weatherApiClient.fetchObservationsXml());

        weatherDataRepository.save(weatherData);
    }
}
