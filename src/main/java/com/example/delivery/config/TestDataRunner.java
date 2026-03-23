//package com.example.delivery.config;
//
//import com.example.delivery.enums.City;
//import com.example.delivery.entity.WeatherData;
//import com.example.delivery.parser.WeatherXmlParser;
//import com.example.delivery.repository.WeatherDataRepository;
//import com.example.delivery.service.WeatherApiClient;
//import com.example.delivery.service.WeatherImportService;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//
//@Component
//public class TestDataRunner implements CommandLineRunner {
//
//    private final WeatherImportService weatherImportService;
//
//    public TestDataRunner(
//            WeatherImportService weatherImportService) {
//        this.weatherImportService = weatherImportService;
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//        weatherImportService.importWeatherData();
//    }
//}
