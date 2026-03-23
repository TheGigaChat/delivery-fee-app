package com.example.delivery.exception;

public class ForbiddenVehicleUsageException extends RuntimeException {
    public ForbiddenVehicleUsageException(String message) {
        super(message);
    }
}
