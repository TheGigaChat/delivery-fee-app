package com.example.delivery.config;

import com.example.delivery.service.WeatherImportService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class WeatherImportScheduler {

    private final WeatherImportService weatherImportService;

    /**
     * Creates the scheduler that delegates weather imports to the service layer.
     *
     * @param weatherImportService service responsible for weather import orchestration
     */
    public WeatherImportScheduler(WeatherImportService weatherImportService) {
        this.weatherImportService = weatherImportService;
    }

    /**
     * Triggers scheduled weather import execution.
     */
    @Scheduled(cron = "${weather.import.cron}")
    public void importWeatherData() {
        weatherImportService.importWeatherData();
    }
}
