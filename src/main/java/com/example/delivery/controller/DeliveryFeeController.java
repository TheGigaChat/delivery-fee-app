package com.example.delivery.controller;

import com.example.delivery.dto.DeliveryFeeResponse;
import com.example.delivery.enums.City;
import com.example.delivery.enums.VehicleType;
import com.example.delivery.service.DeliveryFeeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/delivery-fee")
public class DeliveryFeeController {

    private final DeliveryFeeService deliveryFeeService;

    public DeliveryFeeController(DeliveryFeeService deliveryFeeService) {
        this.deliveryFeeService = deliveryFeeService;
    }

    @GetMapping
    public DeliveryFeeResponse getDeliveryFee(
            @RequestParam City city,
            @RequestParam VehicleType vehicleType
    ) {
        BigDecimal deliveryFee = deliveryFeeService.calculateDeliveryFee(city, vehicleType);

        return new DeliveryFeeResponse(
                city.name(),
                vehicleType.name(),
                deliveryFee
        );
    }
}
