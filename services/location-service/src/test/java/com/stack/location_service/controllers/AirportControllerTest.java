package com.stack.location_service.controllers;

import com.stack.location_service.services.AirportService;
import com.stack.payloads.requests.AirportRequest;
import com.stack.payloads.responses.AirportResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AirportController.class)
class AirportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AirportService airportService;

    @Test
    @DisplayName("POST /api/airports should create an airport")
    void createAirportShouldReturnCreatedAirport() throws Exception {
        AirportRequest request = AirportRequest.builder()
                .iataCode("LOS")
                .name("Murtala Muhammed International Airport")
                .cityId(UUID.randomUUID())
                .build();

        AirportResponse response = new AirportResponse();

        Mockito.when(airportService.createAirport(any(AirportRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/airports")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        Mockito.verify(airportService).createAirport(any(AirportRequest.class));
    }


    @Test
    @DisplayName("GET /api/airports/{id} should return airport by id")
    void getAirportByIdShouldReturnAirport() throws Exception {
        UUID airportId = UUID.randomUUID();

        AirportResponse response = new AirportResponse();

        Mockito.when(airportService.getAirportById(airportId))
                .thenReturn(response);

        mockMvc.perform(get("/api/airports/{id}", airportId))
                .andExpect(status().isOk());

        Mockito.verify(airportService).getAirportById(airportId);
    }

    @Test
    @DisplayName("GET /api/airports/all should return all airports")
    void getAllAirportsShouldReturnAirports() throws Exception {
        AirportResponse response = new AirportResponse();

        Mockito.when(airportService.getAllAirports())
                .thenReturn(List.of(response));

        mockMvc.perform(get("/api/airports/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

        Mockito.verify(airportService).getAllAirports();
    }

    @Test
    @DisplayName("GET /api/airports/city/{cityId} should return airports by city id")
    void getAirportByCityIdShouldReturnAirports() throws Exception {
        UUID cityId = UUID.randomUUID();

        AirportResponse response = new AirportResponse();

        Mockito.when(airportService.getAirportByCityId(cityId))
                .thenReturn(List.of(response));

        mockMvc.perform(get("/api/airports/city/{cityId}", cityId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

        Mockito.verify(airportService).getAirportByCityId(cityId);
    }

    @Test
    @DisplayName("DELETE /api/airports/{id} should delete airport")
    void deleteAirportByIdShouldReturnSuccessMessage() throws Exception {
        UUID airportId = UUID.randomUUID();

        Mockito.doNothing()
                .when(airportService)
                .deleteAirport(airportId);

        mockMvc.perform(delete("/api/airports/{id}", airportId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Airport Deleted Successfully!"));

        Mockito.verify(airportService).deleteAirport(airportId);
    }
}