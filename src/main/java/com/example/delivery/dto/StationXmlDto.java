package com.example.delivery.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StationXmlDto {

    @JacksonXmlProperty(localName = "name")
    private String name;

    @JacksonXmlProperty(localName = "wmocode")
    private String wmocode;

    @JacksonXmlProperty(localName = "airtemperature")
    private String airtemperature;

    @JacksonXmlProperty(localName = "windspeed")
    private String windspeed;

    @JacksonXmlProperty(localName = "phenomenon")
    private String phenomenon;

    /**
     * Returns the station WMO code.
     *
     * @return WMO code
     */
    public String getWmocode() {
        return wmocode;
    }

    /**
     * Returns the station name.
     *
     * @return station name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the air temperature as received from the XML source.
     *
     * @return air temperature string
     */
    public String getAirtemperature() {
        return airtemperature;
    }

    /**
     * Returns the wind speed as received from the XML source.
     *
     * @return wind speed string
     */
    public String getWindspeed() {
        return windspeed;
    }

    /**
     * Returns the weather phenomenon as received from the XML source.
     *
     * @return weather phenomenon string
     */
    public String getPhenomenon() {
        return phenomenon;
    }

    /**
     * Sets the station name.
     *
     * @param name station name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the station WMO code.
     *
     * @param wmocode WMO code
     */
    public void setWmocode(String wmocode) {
        this.wmocode = wmocode;
    }

    /**
     * Sets the source air temperature string.
     *
     * @param airtemperature air temperature string
     */
    public void setAirtemperature(String airtemperature) {
        this.airtemperature = airtemperature;
    }

    /**
     * Sets the source wind speed string.
     *
     * @param windspeed wind speed string
     */
    public void setWindspeed(String windspeed) {
        this.windspeed = windspeed;
    }

    /**
     * Sets the source weather phenomenon string.
     *
     * @param phenomenon weather phenomenon string
     */
    public void setPhenomenon(String phenomenon) {
        this.phenomenon = phenomenon;
    }
}
