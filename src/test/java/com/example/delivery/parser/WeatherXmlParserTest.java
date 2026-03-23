package com.example.delivery.parser;

import com.example.delivery.dto.ObservationsXmlDto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class WeatherXmlParserTest {

    private final WeatherXmlParser weatherXmlParser = new WeatherXmlParser();

    @Test
    void shouldParseValidXml() {
        String xml = """
                <observations timestamp="2026-03-23T10:00:00Z">
                    <station>
                        <name>Tallinn-Harku</name>
                        <wmocode>26038</wmocode>
                        <airtemperature>2.5</airtemperature>
                        <windspeed>4.7</windspeed>
                        <phenomenon>Clear</phenomenon>
                    </station>
                </observations>
                """;

        ObservationsXmlDto result = weatherXmlParser.parse(xml);

        assertThat(result).isNotNull();
        assertThat(result.getTimestamp()).isEqualTo("2026-03-23T10:00:00Z");
        assertThat(result.getStations()).hasSize(1);
    }

    @Test
    void shouldIgnoreUnknownExtraTags() {
        String xml = """
                <observations timestamp="2026-03-23T10:00:00Z">
                    <extraRootTag>ignored</extraRootTag>
                    <station>
                        <name>Tallinn-Harku</name>
                        <wmocode>26038</wmocode>
                        <airtemperature>2.5</airtemperature>
                        <unknownTag>still ignored</unknownTag>
                    </station>
                </observations>
                """;

        ObservationsXmlDto result = weatherXmlParser.parse(xml);

        assertThat(result).isNotNull();
        assertThat(result.getStations()).hasSize(1);
        assertThat(result.getStations().getFirst().getName()).isEqualTo("Tallinn-Harku");
    }

    @Test
    void shouldReadStationNameAndAirTemperature() {
        String xml = """
                <observations timestamp="2026-03-23T10:00:00Z">
                    <station>
                        <name>Tartu-Toravere</name>
                        <wmocode>26242</wmocode>
                        <airtemperature>-1.2</airtemperature>
                    </station>
                </observations>
                """;

        ObservationsXmlDto result = weatherXmlParser.parse(xml);

        assertThat(result.getStations()).hasSize(1);
        assertThat(result.getStations().getFirst().getName()).isEqualTo("Tartu-Toravere");
        assertThat(result.getStations().getFirst().getAirtemperature()).isEqualTo("-1.2");
    }

    @Test
    void shouldWrapParserErrorsInRuntimeException() {
        String invalidXml = "<observations><station></observations>";

        assertThatThrownBy(() -> weatherXmlParser.parse(invalidXml))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Failed to parse weather XML")
                .hasCauseInstanceOf(Exception.class);
    }
}
