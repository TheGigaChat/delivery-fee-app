package com.example.delivery.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "observations")
public class ObservationsXmlDto {

    @JacksonXmlProperty(isAttribute = true, localName = "timestamp")
    private String timestamp;

    @JacksonXmlProperty(localName = "station")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<StationXmlDto> stations;

    /**
     * Returns the source timestamp attribute value.
     *
     * @return source timestamp as string
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the source timestamp attribute value.
     *
     * @param timestamp source timestamp as string
     */
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Returns the parsed station entries.
     *
     * @return parsed stations
     */
    public List<StationXmlDto> getStations() {
        return stations;
    }

    /**
     * Sets the parsed station entries.
     *
     * @param stations parsed stations
     */
    public void setStations(List<StationXmlDto> stations) {
        this.stations = stations;
    }
}
