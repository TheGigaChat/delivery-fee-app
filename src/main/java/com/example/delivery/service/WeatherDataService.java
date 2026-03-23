package com.example.delivery.service;

import com.example.delivery.enums.City;
import com.example.delivery.entity.WeatherData;
import com.example.delivery.repository.WeatherDataRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WeatherDataService {

    private WeatherDataRepository weatherDataRepository;

    /**
     * Creates the service that loads weather data for fee calculation.
     *
     * @param weatherDataRepository repository used to load weather observations
     */
    public WeatherDataService(WeatherDataRepository weatherDataRepository) {
        this.weatherDataRepository = weatherDataRepository;
    }

    /**
     * Returns the latest weather observation for the requested city.
     *
     * @param city requested city
     * @return latest weather observation when present, otherwise empty
     */
    public Optional<WeatherData> getLatestWeather(City city) {
        return weatherDataRepository.findFirstByCityOrderByObservationTimestampDesc(city);
    }
}
