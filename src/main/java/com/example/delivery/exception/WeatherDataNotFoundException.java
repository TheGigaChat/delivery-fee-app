package com.example.delivery.exception;

public class WeatherDataNotFoundException extends RuntimeException {
    public WeatherDataNotFoundException(String message) {
        super(message);
    }
}
