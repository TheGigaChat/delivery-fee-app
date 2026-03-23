package com.example.delivery.dto;

import java.time.LocalDateTime;

public class ErrorResponse {

    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;

    /**
     * Creates an empty error response.
     */
    public ErrorResponse() {

    }

    /**
     * Creates an error response with all response fields populated.
     *
     * @param timestamp error timestamp
     * @param status HTTP status code
     * @param error HTTP status reason phrase
     * @param message error message
     * @param path request path that caused the error
     */
    public ErrorResponse(LocalDateTime timestamp, int status, String error, String message, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

    /**
     * Returns the error timestamp.
     *
     * @return error timestamp
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Returns the HTTP status code.
     *
     * @return HTTP status code
     */
    public int getStatus() {
        return status;
    }

    /**
     * Returns the HTTP error reason phrase.
     *
     * @return error reason phrase
     */
    public String getError() {
        return error;
    }

    /**
     * Returns the error message.
     *
     * @return error message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Returns the request path that caused the error.
     *
     * @return request path
     */
    public String getPath() {
        return path;
    }

    /**
     * Sets the error timestamp.
     *
     * @param timestamp error timestamp
     */
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Sets the HTTP status code.
     *
     * @param status HTTP status code
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * Sets the HTTP error reason phrase.
     *
     * @param error error reason phrase
     */
    public void setError(String error) {
        this.error = error;
    }

    /**
     * Sets the error message.
     *
     * @param message error message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Sets the request path that caused the error.
     *
     * @param path request path
     */
    public void setPath(String path) {
        this.path = path;
    }
}
