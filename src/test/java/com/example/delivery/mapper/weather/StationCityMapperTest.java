package com.example.delivery.mapper.weather;

import com.example.delivery.enums.City;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class StationCityMapperTest {

    private final StationCityMapper stationCityMapper = new StationCityMapper();

    @Test
    void shouldMapTallinnHarkuToTallinn() {
        Optional<City> result = stationCityMapper.mapStationToCity("Tallinn-Harku");

        assertThat(result).contains(City.TALLINN);
    }

    @Test
    void shouldMapTartuToravereToTartu() {
        Optional<City> result = stationCityMapper.mapStationToCity("Tartu-Toravere");

        assertThat(result).contains(City.TARTU);
    }

    @Test
    void shouldMapParnuToParnu() {
        Optional<City> result = stationCityMapper.mapStationToCity("Pärnu");

        assertThat(result).contains(City.PARNU);
    }

    @Test
    void shouldReturnEmptyForUnknownStation() {
        Optional<City> result = stationCityMapper.mapStationToCity("Narva");

        assertThat(result).isEmpty();
    }
}
