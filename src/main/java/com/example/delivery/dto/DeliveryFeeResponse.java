package com.example.delivery.dto;

import java.math.BigDecimal;

public class DeliveryFeeResponse {

    private String city;
    private String vehicleType;
    private BigDecimal deliveryFee;

    public DeliveryFeeResponse() {
    }

    public DeliveryFeeResponse(String city, String vehicleType, BigDecimal deliveryFee) {
        this.city = city;
        this.vehicleType = vehicleType;
        this.deliveryFee = deliveryFee;
    }

    public String getCity() {
        return city;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public BigDecimal getDeliveryFee() {
        return deliveryFee;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public void setDeliveryFee(BigDecimal deliveryFee) {
        this.deliveryFee = deliveryFee;
    }
}
