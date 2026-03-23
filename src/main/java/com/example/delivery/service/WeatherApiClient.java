package com.example.delivery.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherApiClient {

    private static final String WEATHER_API_URL = "https://www.ilmateenistus.ee/ilma_andmed/xml/observations.php";

    private final RestTemplate restTemplate;

    /**
     * Creates the weather API client.
     *
     * @param restTemplate shared HTTP client used for external calls
     */
    public WeatherApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Fetches the latest weather observations XML from the external source.
     *
     * @return raw weather observations XML
     */
    public String fetchObservationsXml() {
        return restTemplate.getForObject(WEATHER_API_URL, String.class);
    }
}
