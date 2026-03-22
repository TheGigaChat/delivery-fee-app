package com.example.delivery.mapper.weather;

import com.example.delivery.enums.City;

import java.util.Map;
import java.util.Optional;

public final class StationCityMapper {
    private StationCityMapper() {}

    public static Optional<City> mapStationToCity(String stationName) {
        return switch (stationName) {
            case "Tallinn-Harku" -> Optional.of(City.TALLINN);
            case "Tartu-Toravere" -> Optional.of(City.TARTU);
            case "Pärnu" -> Optional.of(City.PARNU);
            default -> Optional.empty();
        };
    }
}
