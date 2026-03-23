package com.example.delivery.entity;

import com.example.delivery.enums.City;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "weather_data")
public class WeatherData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String stationName;

    @Column(nullable = false)
    private String wmoCode;

    @Column(precision = 5, scale = 2)
    private BigDecimal airTemperature;

    @Column(precision = 5, scale = 2)
    private BigDecimal windSpeed;

    private String weatherPhenomenon;

    @Column(nullable = false)
    private LocalDateTime observationTimestamp;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private City city;

    /**
     * Creates an empty weather data entity.
     */
    public WeatherData() {
    }

    /**
     * Returns the generated entity identifier.
     *
     * @return entity identifier
     */
    public Long getId() {
        return id;
    }

    /**
     * Returns the station name.
     *
     * @return station name
     */
    public String getStationName() {
        return stationName;
    }

    /**
     * Sets the station name.
     *
     * @param stationName station name
     */
    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    /**
     * Returns the WMO code.
     *
     * @return WMO code
     */
    public String getWmoCode() {
        return wmoCode;
    }

    /**
     * Sets the WMO code.
     *
     * @param wmoCode WMO code
     */
    public void setWmoCode(String wmoCode) {
        this.wmoCode = wmoCode;
    }

    /**
     * Returns the observed air temperature.
     *
     * @return air temperature
     */
    public BigDecimal getAirTemperature() {
        return airTemperature;
    }

    /**
     * Sets the observed air temperature.
     *
     * @param airTemperature air temperature
     */
    public void setAirTemperature(BigDecimal airTemperature) {
        this.airTemperature = airTemperature;
    }

    /**
     * Returns the observed wind speed.
     *
     * @return wind speed
     */
    public BigDecimal getWindSpeed() {
        return windSpeed;
    }

    /**
     * Sets the observed wind speed.
     *
     * @param windSpeed wind speed
     */
    public void setWindSpeed(BigDecimal windSpeed) {
        this.windSpeed = windSpeed;
    }

    /**
     * Returns the observed weather phenomenon.
     *
     * @return weather phenomenon
     */
    public String getWeatherPhenomenon() {
        return weatherPhenomenon;
    }

    /**
     * Sets the observed weather phenomenon.
     *
     * @param weatherPhenomenon weather phenomenon
     */
    public void setWeatherPhenomenon(String weatherPhenomenon) {
        this.weatherPhenomenon = weatherPhenomenon;
    }

    /**
     * Returns the observation timestamp.
     *
     * @return observation timestamp
     */
    public LocalDateTime getObservationTimestamp() {
        return observationTimestamp;
    }

    /**
     * Sets the observation timestamp.
     *
     * @param observationTimestamp observation timestamp
     */
    public void setObservationTimestamp(LocalDateTime observationTimestamp) {
        this.observationTimestamp = observationTimestamp;
    }

    /**
     * Returns the mapped city.
     *
     * @return mapped city
     */
    public City getCity() {
        return city;
    }

    /**
     * Sets the mapped city.
     *
     * @param city mapped city
     */
    public void setCity(City city) {
        this.city = city;
    }
}
