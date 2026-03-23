package com.example.delivery.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class DeliveryFeeResponseTest {

    @Test
    void shouldCreateWithNoArgsConstructorAndUseGettersAndSetters() {
        DeliveryFeeResponse response = new DeliveryFeeResponse();

        response.setCity("TARTU");
        response.setVehicleType("BIKE");
        response.setDeliveryFee(new BigDecimal("3.5"));

        assertThat(response.getCity()).isEqualTo("TARTU");
        assertThat(response.getVehicleType()).isEqualTo("BIKE");
        assertThat(response.getDeliveryFee()).isEqualByComparingTo("3.5");
    }
}
