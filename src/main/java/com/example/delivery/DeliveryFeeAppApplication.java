package com.example.delivery;

import com.example.delivery.config.DeliveryFeeProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties(DeliveryFeeProperties.class)
public class DeliveryFeeAppApplication {

    /**
     * Starts the Spring Boot application.
     *
     * @param args command-line arguments passed to the application
     */
	public static void main(String[] args) {
		SpringApplication.run(DeliveryFeeAppApplication.class, args);
	}

}
