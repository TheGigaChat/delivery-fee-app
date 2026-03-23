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

    public String getWmocode() {
        return wmocode;
    }

    public String getName() {
        return name;
    }

    public String getAirtemperature() {
        return airtemperature;
    }

    public String getWindspeed() {
        return windspeed;
    }

    public String getPhenomenon() {
        return phenomenon;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWmocode(String wmocode) {
        this.wmocode = wmocode;
    }

    public void setAirtemperature(String airtemperature) {
        this.airtemperature = airtemperature;
    }

    public void setWindspeed(String windspeed) {
        this.windspeed = windspeed;
    }

    public void setPhenomenon(String phenomenon) {
        this.phenomenon = phenomenon;
    }
}
