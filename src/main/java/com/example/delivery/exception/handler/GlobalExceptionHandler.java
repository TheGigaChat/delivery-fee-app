package com.example.delivery.exception.handler;

import com.example.delivery.dto.ErrorResponse;
import com.example.delivery.exception.ForbiddenVehicleUsageException;
import com.example.delivery.exception.WeatherDataNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Converts missing weather data errors into a 404 response.
     *
     * @param ex thrown domain exception
     * @param request current HTTP request
     * @return structured not-found error response
     */
    @ExceptionHandler(WeatherDataNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleWeatherDataNotFound(
            WeatherDataNotFoundException ex,
            HttpServletRequest request
    ) {
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    /**
     * Converts forbidden vehicle usage errors into a 400 response.
     *
     * @param ex thrown domain exception
     * @param request current HTTP request
     * @return structured bad-request error response
     */
    @ExceptionHandler(ForbiddenVehicleUsageException.class)
    public ResponseEntity<ErrorResponse> handleForbiddenVehicleUsageException(
            ForbiddenVehicleUsageException ex,
            HttpServletRequest request
    ) {
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    /**
     * Converts invalid request parameter values into a 400 response.
     *
     * @param ex thrown binding exception
     * @param request current HTTP request
     * @return structured bad-request error response
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException ex,
            HttpServletRequest request
    ) {
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "Invalid request parameter value",
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    /**
     * Converts unhandled exceptions into a 500 response.
     *
     * @param ex thrown exception
     * @param request current HTTP request
     * @return structured internal-server-error response
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(
            Exception ex,
            HttpServletRequest request
    ) {
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
