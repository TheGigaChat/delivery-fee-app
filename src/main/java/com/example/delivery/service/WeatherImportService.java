package com.example.delivery.service;

import com.example.delivery.dto.ObservationsXmlDto;
import com.example.delivery.dto.StationXmlDto;
import com.example.delivery.entity.WeatherData;
import com.example.delivery.enums.City;
import com.example.delivery.mapper.weather.StationCityMapper;
import com.example.delivery.parser.WeatherXmlParser;
import com.example.delivery.repository.WeatherDataRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class WeatherImportService {

    private final WeatherApiClient weatherApiClient;
    private final WeatherXmlParser weatherXmlParser;
    private final StationCityMapper stationCityMapper;
    private final WeatherDataRepository weatherDataRepository;

    /**
     * Creates the service that imports weather observations.
     *
     * @param weatherApiClient client used to fetch source XML
     * @param weatherXmlParser parser used to deserialize the source XML
     * @param stationCityMapper mapper used to map source stations to supported cities
     * @param weatherDataRepository repository used to persist imported weather data
     */
    public WeatherImportService(
        WeatherApiClient weatherApiClient,
        WeatherXmlParser weatherXmlParser,
        StationCityMapper stationCityMapper,
        WeatherDataRepository weatherDataRepository
    ) {
        this.weatherApiClient = weatherApiClient;
        this.weatherXmlParser = weatherXmlParser;
        this.stationCityMapper = stationCityMapper;
        this.weatherDataRepository = weatherDataRepository;
    }

    /**
     * Imports the latest weather observations from the external source.
     */
    public void importWeatherData() {

        String xml = weatherApiClient.fetchObservationsXml();
        ObservationsXmlDto observations = weatherXmlParser.parse(xml);

        for (StationXmlDto station : observations.getStations()) {

            var cityOptional = stationCityMapper.mapStationToCity(station.getName());

            if (cityOptional.isEmpty()) {
                continue;
            }

            City city = cityOptional.get();

            WeatherData weatherData = new WeatherData();
            weatherData.setStationName(station.getName());
            weatherData.setWmoCode(station.getWmocode());
            weatherData.setAirTemperature(parseBigDecimal(station.getAirtemperature()));
            weatherData.setWindSpeed(parseBigDecimal(station.getWindspeed()));
            weatherData.setWeatherPhenomenon(station.getPhenomenon());
            weatherData.setObservationTimestamp(LocalDateTime.now());
            weatherData.setCity(city);

            weatherDataRepository.save(weatherData);
        }
    }

    private BigDecimal parseBigDecimal(String value) {
        try {
            if (value == null || value.isBlank()) return null;
            return new BigDecimal(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
