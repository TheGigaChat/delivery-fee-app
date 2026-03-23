package com.example.delivery.repository;

import com.example.delivery.entity.WeatherData;
import com.example.delivery.enums.City;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class WeatherDataRepositoryTest {

    @Autowired
    private WeatherDataRepository weatherDataRepository;

    @Test
    void shouldReturnLatestObservationForRequestedCity() {
        WeatherData olderTallinn = createWeatherData("Tallinn-Harku", City.TALLINN, LocalDateTime.of(2026, 3, 22, 10, 0));
        WeatherData newerTallinn = createWeatherData("Tallinn-Harku", City.TALLINN, LocalDateTime.of(2026, 3, 22, 12, 0));
        WeatherData tartuRecord = createWeatherData("Tartu-Toravere", City.TARTU, LocalDateTime.of(2026, 3, 22, 13, 0));

        weatherDataRepository.save(olderTallinn);
        weatherDataRepository.save(newerTallinn);
        weatherDataRepository.save(tartuRecord);

        Optional<WeatherData> result = weatherDataRepository.findFirstByCityOrderByObservationTimestampDesc(City.TALLINN);

        assertThat(result).isPresent();
        assertThat(result.get().getCity()).isEqualTo(City.TALLINN);
        assertThat(result.get().getObservationTimestamp()).isEqualTo(LocalDateTime.of(2026, 3, 22, 12, 0));
    }

    @Test
    void shouldReturnEmptyWhenCityHasNoStoredObservations() {
        weatherDataRepository.save(createWeatherData("Tallinn-Harku", City.TALLINN, LocalDateTime.of(2026, 3, 22, 10, 0)));

        Optional<WeatherData> result = weatherDataRepository.findFirstByCityOrderByObservationTimestampDesc(City.PARNU);

        assertThat(result).isEmpty();
    }

    private WeatherData createWeatherData(String stationName, City city, LocalDateTime observationTimestamp) {
        WeatherData weatherData = new WeatherData();
        weatherData.setStationName(stationName);
        weatherData.setWmoCode("26038");
        weatherData.setAirTemperature(new BigDecimal("2.5"));
        weatherData.setWindSpeed(new BigDecimal("4.7"));
        weatherData.setWeatherPhenomenon("Clear");
        weatherData.setObservationTimestamp(observationTimestamp);
        weatherData.setCity(city);
        return weatherData;
    }
}
