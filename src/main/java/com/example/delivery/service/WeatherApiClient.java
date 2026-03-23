package com.example.delivery.service;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherApiClient {

    private static final String WEATHER_API_URL = "https://www.ilmateenistus.ee/ilma_andmed/xml/observations.php";

    private final RestTemplate restTemplate;

    public WeatherApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String fetchObservationsXml() {
        return restTemplate.getForObject(WEATHER_API_URL, String.class);
    }
}
