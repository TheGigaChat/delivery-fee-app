package com.example.delivery.mapper.weather;

import com.example.delivery.enums.City;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class StationCityMapper {

    public Optional<City> mapStationToCity(String stationName) {
        return switch (stationName) {
            case "Tallinn-Harku" -> Optional.of(City.TALLINN);
            case "Tartu-Toravere", "Tartu-Tõravere", "Tartu-TÃµravere" -> Optional.of(City.TARTU);
            case "Pärnu", "PÃ¤rnu" -> Optional.of(City.PARNU);
            default -> Optional.empty();
        };
    }
}
