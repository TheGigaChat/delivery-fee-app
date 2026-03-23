package com.example.delivery.exception;

public class WeatherDataNotFoundException extends RuntimeException {

    /**
     * Creates an exception for missing weather data.
     *
     * @param message exception message
     */
    public WeatherDataNotFoundException(String message) {
        super(message);
    }
}
