package com.example.delivery.controller;

import com.example.delivery.enums.City;
import com.example.delivery.enums.VehicleType;
import com.example.delivery.exception.ForbiddenVehicleUsageException;
import com.example.delivery.exception.WeatherDataNotFoundException;
import com.example.delivery.exception.handler.GlobalExceptionHandler;
import com.example.delivery.service.DeliveryFeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DeliveryFeeController.class)
@Import(GlobalExceptionHandler.class)
class DeliveryFeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DeliveryFeeService deliveryFeeService;

    @Test
    void shouldReturn200ForValidRequest() throws Exception {
        when(deliveryFeeService.calculateDeliveryFee(eq(City.TARTU), eq(VehicleType.BIKE)))
                .thenReturn(new BigDecimal("3.5"));

        mockMvc.perform(get("/api/delivery-fee")
                        .param("city", "TARTU")
                        .param("vehicleType", "BIKE")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.city").value("TARTU"))
                .andExpect(jsonPath("$.vehicleType").value("BIKE"))
                .andExpect(jsonPath("$.deliveryFee").value(3.5));
    }

    @Test
    void shouldReturn400ForInvalidEnum() throws Exception {
        mockMvc.perform(get("/api/delivery-fee")
                        .param("city", "INVALID")
                        .param("vehicleType", "BIKE")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("Invalid request parameter value"))
                .andExpect(jsonPath("$.path").value("/api/delivery-fee"));
    }

    @Test
    void shouldReturn400ForForbiddenUsage() throws Exception {
        when(deliveryFeeService.calculateDeliveryFee(eq(City.TALLINN), eq(VehicleType.BIKE)))
                .thenThrow(new ForbiddenVehicleUsageException("Usage of selected vehicle type is forbidden"));

        mockMvc.perform(get("/api/delivery-fee")
                        .param("city", "TALLINN")
                        .param("vehicleType", "BIKE")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("Usage of selected vehicle type is forbidden"))
                .andExpect(jsonPath("$.path").value("/api/delivery-fee"));
    }

    @Test
    void shouldReturn404WhenWeatherIsMissing() throws Exception {
        when(deliveryFeeService.calculateDeliveryFee(eq(City.PARNU), eq(VehicleType.CAR)))
                .thenThrow(new WeatherDataNotFoundException("No weather data found for city: PARNU"));

        mockMvc.perform(get("/api/delivery-fee")
                        .param("city", "PARNU")
                        .param("vehicleType", "CAR")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("No weather data found for city: PARNU"))
                .andExpect(jsonPath("$.path").value("/api/delivery-fee"));
    }

    @Test
    void shouldReturn500ForUnhandledServiceError() throws Exception {
        when(deliveryFeeService.calculateDeliveryFee(eq(City.TARTU), eq(VehicleType.SCOOTER)))
                .thenThrow(new RuntimeException("Unexpected service error"));

        mockMvc.perform(get("/api/delivery-fee")
                        .param("city", "TARTU")
                        .param("vehicleType", "SCOOTER")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status").value(500))
                .andExpect(jsonPath("$.message").value("Unexpected service error"))
                .andExpect(jsonPath("$.path").value("/api/delivery-fee"));
    }
}
