package com.example.delivery.exception;

public class ForbiddenVehicleUsageException extends RuntimeException {

    /**
     * Creates an exception for weather conditions that forbid the selected vehicle type.
     *
     * @param message exception message
     */
    public ForbiddenVehicleUsageException(String message) {
        super(message);
    }
}
