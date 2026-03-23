package com.example.delivery.service;

import com.example.delivery.dto.ObservationsXmlDto;
import com.example.delivery.dto.StationXmlDto;
import com.example.delivery.entity.WeatherData;
import com.example.delivery.mapper.weather.StationCityMapper;
import com.example.delivery.parser.WeatherXmlParser;
import com.example.delivery.repository.WeatherDataRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class WeatherImportServiceTest {

    private final WeatherApiClient weatherApiClient = mock(WeatherApiClient.class);
    private final WeatherXmlParser weatherXmlParser = mock(WeatherXmlParser.class);
    private final WeatherDataRepository weatherDataRepository = mock(WeatherDataRepository.class);
    private final WeatherImportService weatherImportService = new WeatherImportService(
            weatherApiClient,
            weatherXmlParser,
            new StationCityMapper(),
            weatherDataRepository
    );

    @Test
    void shouldSaveOnlyMappedStations() {
        String xml = "<observations />";
        ObservationsXmlDto observations = new ObservationsXmlDto();
        observations.setStations(List.of(
                station("Tallinn-Harku", "26038", "2.5", "4.7", "Clear"),
                station("Narva", "26000", "1.0", "3.0", "Cloudy")
        ));

        when(weatherApiClient.fetchObservationsXml()).thenReturn(xml);
        when(weatherXmlParser.parse(xml)).thenReturn(observations);

        weatherImportService.importWeatherData();

        verify(weatherDataRepository, times(1)).save(any(WeatherData.class));
    }

    @Test
    void shouldIgnoreUnknownStations() {
        String xml = "<observations />";
        ObservationsXmlDto observations = new ObservationsXmlDto();
        observations.setStations(List.of(
                station("Narva", "26000", "1.0", "3.0", "Cloudy"),
                station("Viljandi", "26001", "-2.0", "5.0", "Snow")
        ));

        when(weatherApiClient.fetchObservationsXml()).thenReturn(xml);
        when(weatherXmlParser.parse(xml)).thenReturn(observations);

        weatherImportService.importWeatherData();

        verify(weatherDataRepository, never()).save(any(WeatherData.class));
    }

    @Test
    void shouldConvertValuesCorrectlyBeforeSaving() {
        String xml = "<observations />";
        ObservationsXmlDto observations = new ObservationsXmlDto();
        observations.setStations(List.of(
                station("Tallinn-Harku", "26038", "2.5", "4.7", "Clear")
        ));

        when(weatherApiClient.fetchObservationsXml()).thenReturn(xml);
        when(weatherXmlParser.parse(xml)).thenReturn(observations);

        weatherImportService.importWeatherData();

        ArgumentCaptor<WeatherData> savedWeatherData = ArgumentCaptor.forClass(WeatherData.class);
        verify(weatherDataRepository).save(savedWeatherData.capture());

        WeatherData result = savedWeatherData.getValue();
        assertThat(result.getStationName()).isEqualTo("Tallinn-Harku");
        assertThat(result.getWmoCode()).isEqualTo("26038");
        assertThat(result.getAirTemperature()).isEqualByComparingTo(new BigDecimal("2.5"));
        assertThat(result.getWindSpeed()).isEqualByComparingTo(new BigDecimal("4.7"));
        assertThat(result.getWeatherPhenomenon()).isEqualTo("Clear");
        assertThat(result.getCity()).isNotNull();
        assertThat(result.getObservationTimestamp()).isNotNull();
    }

    @Test
    void shouldReturnNullWhenParseBigDecimalGetsNull() throws Exception {
        assertThat(invokeParseBigDecimal(null)).isNull();
    }

    @Test
    void shouldReturnNullWhenParseBigDecimalGetsBlank() throws Exception {
        assertThat(invokeParseBigDecimal("   ")).isNull();
    }

    @Test
    void shouldReturnNullWhenParseBigDecimalGetsInvalidNumber() throws Exception {
        assertThat(invokeParseBigDecimal("abc")).isNull();
    }

    private BigDecimal invokeParseBigDecimal(String value) throws Exception {
        Method method = WeatherImportService.class.getDeclaredMethod("parseBigDecimal", String.class);
        method.setAccessible(true);
        return (BigDecimal) method.invoke(weatherImportService, value);
    }

    private StationXmlDto station(String name, String wmoCode, String airTemperature, String windSpeed, String phenomenon) {
        StationXmlDto station = new StationXmlDto();
        station.setName(name);
        station.setWmocode(wmoCode);
        station.setAirtemperature(airTemperature);
        station.setWindspeed(windSpeed);
        station.setPhenomenon(phenomenon);
        return station;
    }
}
