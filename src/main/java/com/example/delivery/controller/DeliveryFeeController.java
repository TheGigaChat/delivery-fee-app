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

    /**
     * Creates the controller for delivery-fee requests.
     *
     * @param deliveryFeeService service that calculates delivery fees
     */
    public DeliveryFeeController(DeliveryFeeService deliveryFeeService) {
        this.deliveryFeeService = deliveryFeeService;
    }

    /**
     * Returns the calculated delivery fee for the requested city and vehicle type.
     *
     * @param city requested delivery city
     * @param vehicleType requested vehicle type
     * @return response DTO containing the calculated fee
     */
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
