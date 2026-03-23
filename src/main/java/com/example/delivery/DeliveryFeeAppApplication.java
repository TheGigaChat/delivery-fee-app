package com.example.delivery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DeliveryFeeAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeliveryFeeAppApplication.class, args);
	}

}
