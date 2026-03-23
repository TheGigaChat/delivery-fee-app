package com.example.delivery.service;

import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class WeatherApiClientTest {

    private static final String WEATHER_API_URL = "https://www.ilmateenistus.ee/ilma_andmed/xml/observations.php";

    private final RestTemplate restTemplate = mock(RestTemplate.class);
    private final WeatherApiClient weatherApiClient = new WeatherApiClient(restTemplate);

    @Test
    void shouldFetchObservationsXmlFromConfiguredUrl() {
        when(restTemplate.getForObject(WEATHER_API_URL, String.class)).thenReturn("<observations />");

        String result = weatherApiClient.fetchObservationsXml();

        assertThat(result).isEqualTo("<observations />");
        verify(restTemplate).getForObject(WEATHER_API_URL, String.class);
    }
}
