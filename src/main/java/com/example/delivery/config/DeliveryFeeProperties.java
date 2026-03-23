package com.example.delivery.config;

import com.example.delivery.enums.City;
import com.example.delivery.enums.VehicleType;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.math.BigDecimal;
import java.util.Map;

@ConfigurationProperties(prefix = "delivery.fee")
public class DeliveryFeeProperties {

    private Map<City, Map<VehicleType, BigDecimal>> baseFees;

    public Map<City, Map<VehicleType, BigDecimal>> getBaseFees() {
        return baseFees;
    }

    public void setBaseFees(Map<City, Map<VehicleType, BigDecimal>> baseFees) {
        this.baseFees = baseFees;
    }
}
