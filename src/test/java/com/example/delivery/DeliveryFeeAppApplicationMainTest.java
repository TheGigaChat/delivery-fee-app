package com.example.delivery;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.boot.SpringApplication;

import static org.mockito.Mockito.mockStatic;

class DeliveryFeeAppApplicationMainTest {

    @Test
    void shouldRunSpringApplicationFromMain() {
        String[] args = {"--spring.profiles.active=test"};

        try (MockedStatic<SpringApplication> springApplication = mockStatic(SpringApplication.class)) {
            DeliveryFeeAppApplication.main(args);

            springApplication.verify(() -> SpringApplication.run(DeliveryFeeAppApplication.class, args));
        }
    }
}
