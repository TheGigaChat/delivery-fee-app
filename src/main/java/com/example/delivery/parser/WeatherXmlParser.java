package com.example.delivery.parser;

import com.example.delivery.dto.ObservationsXmlDto;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.stereotype.Component;

@Component
public class WeatherXmlParser {

    private final XmlMapper xmlMapper;

    public WeatherXmlParser() {
        this.xmlMapper = new XmlMapper();
    }

    public ObservationsXmlDto parse(String xml) {
        try {
            return xmlMapper.readValue(xml, ObservationsXmlDto.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse weather XML", e);
        }
    }
}
