package com.example.delivery.repository;

import com.example.delivery.enums.City;
import com.example.delivery.enums.WeatherData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WeatherDataRepository extends JpaRepository<WeatherData, Long> {
    Optional<WeatherData> findFirstByCityOrderByObservationTimestampDesc(City city);
}
