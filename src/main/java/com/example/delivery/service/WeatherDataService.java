package com.example.delivery.service;

import com.example.delivery.enums.City;
import com.example.delivery.entity.WeatherData;
import com.example.delivery.repository.WeatherDataRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WeatherDataService {

    private WeatherDataRepository weatherDataRepository;

    public WeatherDataService(WeatherDataRepository weatherDataRepository) {
        this.weatherDataRepository = weatherDataRepository;
    }

    public Optional<WeatherData> getLatestWeather(City city) {
        return weatherDataRepository.findFirstByCityOrderByObservationTimestampDesc(city);
    }
}
