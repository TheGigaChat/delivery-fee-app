package com.example.delivery.dto;

import java.math.BigDecimal;

public class DeliveryFeeResponse {

    private String city;
    private String vehicleType;
    private BigDecimal deliveryFee;

    /**
     * Creates an empty delivery-fee response.
     */
    public DeliveryFeeResponse() {
    }

    /**
     * Creates a delivery-fee response with all response fields populated.
     *
     * @param city response city value
     * @param vehicleType response vehicle type value
     * @param deliveryFee calculated delivery fee
     */
    public DeliveryFeeResponse(String city, String vehicleType, BigDecimal deliveryFee) {
        this.city = city;
        this.vehicleType = vehicleType;
        this.deliveryFee = deliveryFee;
    }

    /**
     * Returns the response city value.
     *
     * @return city name
     */
    public String getCity() {
        return city;
    }

    /**
     * Returns the response vehicle type value.
     *
     * @return vehicle type name
     */
    public String getVehicleType() {
        return vehicleType;
    }

    /**
     * Returns the calculated delivery fee.
     *
     * @return delivery fee amount
     */
    public BigDecimal getDeliveryFee() {
        return deliveryFee;
    }

    /**
     * Sets the response city value.
     *
     * @param city city name
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Sets the response vehicle type value.
     *
     * @param vehicleType vehicle type name
     */
    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    /**
     * Sets the calculated delivery fee.
     *
     * @param deliveryFee delivery fee amount
     */
    public void setDeliveryFee(BigDecimal deliveryFee) {
        this.deliveryFee = deliveryFee;
    }
}
