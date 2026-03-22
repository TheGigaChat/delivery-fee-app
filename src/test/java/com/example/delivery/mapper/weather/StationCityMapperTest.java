package com.example.delivery.mapper.weather;

import com.example.delivery.enums.City;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class StationCityMapperTest {

    @Test
    void shouldMapTallinnHarkuToTallinn() {
        Optional<City> result = StationCityMapper.mapStationToCity("Tallinn-Harku");

        assertThat(result).contains(City.TALLINN);
    }

    @Test
    void shouldMapTartuToravereToTartu() {
        Optional<City> result = StationCityMapper.mapStationToCity("Tartu-Toravere");

        assertThat(result).contains(City.TARTU);
    }

    @Test
    void shouldReturnEmptyForUnknownStation() {
        Optional<City> result = StationCityMapper.mapStationToCity("Narva");

        assertThat(result).isEmpty();
    }
}
