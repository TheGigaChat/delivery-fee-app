package com.example.delivery.config;

import com.example.delivery.service.WeatherImportService;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class WeatherImportSchedulerTest {

    private final WeatherImportService weatherImportService = mock(WeatherImportService.class);
    private final WeatherImportScheduler weatherImportScheduler = new WeatherImportScheduler(weatherImportService);

    @Test
    void shouldDelegateImportToService() {
        weatherImportScheduler.importWeatherData();

        verify(weatherImportService, times(1)).importWeatherData();
    }
}
