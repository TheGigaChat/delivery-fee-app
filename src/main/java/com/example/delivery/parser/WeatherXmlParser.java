package com.example.delivery.parser;

import com.example.delivery.dto.ObservationsXmlDto;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.stereotype.Component;

@Component
public class WeatherXmlParser {

    private final XmlMapper xmlMapper;

    /**
     * Creates the XML parser used for weather observation payloads.
     */
    public WeatherXmlParser() {
        this.xmlMapper = new XmlMapper();
    }

    /**
     * Parses raw weather XML into the observation DTO structure.
     *
     * @param xml raw XML payload
     * @return parsed observation DTO
     */
    public ObservationsXmlDto parse(String xml) {
        try {
            return xmlMapper.readValue(xml, ObservationsXmlDto.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse weather XML", e);
        }
    }
}
