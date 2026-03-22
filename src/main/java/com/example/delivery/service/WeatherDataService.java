package com.example.delivery.service;

import com.example.delivery.enums.City;
import com.example.delivery.enums.WeatherData;
import com.example.delivery.repository.WeatherDataRepository;

import java.util.Optional;

public class WeatherDataService {

    private WeatherDataRepository weatherDataRepository;

    public WeatherDataService(WeatherDataRepository weatherDataRepository) {
        this.weatherDataRepository = weatherDataRepository;
    }

    public Optional<WeatherData> getLatestWeather(City city) {
        return weatherDataRepository.findFirstByCityOrderByObservationTimestampDesc(city);
    }
}
