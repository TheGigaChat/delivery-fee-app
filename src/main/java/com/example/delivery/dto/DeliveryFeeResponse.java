package com.example.delivery.dto;

import java.math.BigDecimal;

public record DeliveryFeeResponse(
        String city,
        String vehicleType,
        BigDecimal deliveryFee
) {}
