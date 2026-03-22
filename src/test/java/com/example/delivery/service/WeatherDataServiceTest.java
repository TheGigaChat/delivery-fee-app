package com.example.delivery.service;

import com.example.delivery.entity.WeatherData;
import com.example.delivery.enums.City;
import com.example.delivery.repository.WeatherDataRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class WeatherDataServiceTest {

    private final WeatherDataRepository weatherDataRepository = mock(WeatherDataRepository.class);
    private final WeatherDataService weatherDataService = new WeatherDataService(weatherDataRepository);

    @Test
    void shouldReturnLatestWeatherForCityFromRepository() {
        WeatherData weatherData = new WeatherData();
        weatherData.setCity(City.TALLINN);
        when(weatherDataRepository.findFirstByCityOrderByObservationTimestampDesc(City.TALLINN))
                .thenReturn(Optional.of(weatherData));

        Optional<WeatherData> result = weatherDataService.getLatestWeather(City.TALLINN);

        assertThat(result).contains(weatherData);
        verify(weatherDataRepository).findFirstByCityOrderByObservationTimestampDesc(City.TALLINN);
    }

    @Test
    void shouldReturnEmptyWhenRepositoryHasNoWeatherForCity() {
        when(weatherDataRepository.findFirstByCityOrderByObservationTimestampDesc(City.TARTU))
                .thenReturn(Optional.empty());

        Optional<WeatherData> result = weatherDataService.getLatestWeather(City.TARTU);

        assertThat(result).isEmpty();
        verify(weatherDataRepository).findFirstByCityOrderByObservationTimestampDesc(City.TARTU);
    }
}
