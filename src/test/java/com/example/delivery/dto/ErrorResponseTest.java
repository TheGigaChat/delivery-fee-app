package com.example.delivery.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class ErrorResponseTest {

    @Test
    void shouldCreateWithNoArgsConstructorAndUseGettersAndSetters() {
        ErrorResponse response = new ErrorResponse();
        LocalDateTime timestamp = LocalDateTime.of(2026, 3, 23, 12, 0);

        response.setTimestamp(timestamp);
        response.setStatus(400);
        response.setError("Bad Request");
        response.setMessage("Invalid request parameter value");
        response.setPath("/api/delivery-fee");

        assertThat(response.getTimestamp()).isEqualTo(timestamp);
        assertThat(response.getStatus()).isEqualTo(400);
        assertThat(response.getError()).isEqualTo("Bad Request");
        assertThat(response.getMessage()).isEqualTo("Invalid request parameter value");
        assertThat(response.getPath()).isEqualTo("/api/delivery-fee");
    }
}
