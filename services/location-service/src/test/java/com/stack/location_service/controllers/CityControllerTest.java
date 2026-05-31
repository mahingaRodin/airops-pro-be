package com.stack.location_service.controllers;

import com.stack.location_service.services.CityService;
import com.stack.payloads.requests.CityRequest;
import com.stack.payloads.responses.CityResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CityController.class)
class CityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CityService cityService;

    @Test
    @DisplayName("POST /api/cities should create a city")
    void createCityShouldReturnCreatedCity() throws Exception {
        CityRequest request = CityRequest.builder()
                .name("Lagos")
                .cityCode("LOS")
                .countryCode("NG")
                .countryName("Nigeria")
                .regionCode("LA")
                .timeZoneOffset("+01:00")
                .build();

        CityResponse response = new CityResponse();

        Mockito.when(cityService.createCity(any(CityRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/cities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        Mockito.verify(cityService).createCity(any(CityRequest.class));
    }

    @Test
    @DisplayName("GET /api/cities/{id} should return city by id")
    void getCityByIdShouldReturnCity() throws Exception {
        UUID cityId = UUID.randomUUID();

        CityResponse response = new CityResponse();

        Mockito.when(cityService.getCityById(cityId))
                .thenReturn(response);

        mockMvc.perform(get("/api/cities/{id}", cityId))
                .andExpect(status().isOk());

        Mockito.verify(cityService).getCityById(cityId);
    }

    @Test
    @DisplayName("GET /api/cities/all should return paginated cities")
    void getAllCitiesShouldReturnPaginatedCities() throws Exception {
        CityResponse response = new CityResponse();

        Page<CityResponse> page = new PageImpl<>(
                List.of(response),
                PageRequest.of(0, 20, Sort.by(Sort.Direction.ASC, "name")),
                1
        );

        Mockito.when(cityService.getAllCities(any(Pageable.class)))
                .thenReturn(page);

        mockMvc.perform(get("/api/cities/all")
                        .param("page", "0")
                        .param("size", "20")
                        .param("sortBy", "name")
                        .param("sortDirection", "asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(1))
                .andExpect(jsonPath("$.totalElements").value(1));

        Mockito.verify(cityService).getAllCities(any(Pageable.class));
    }

    @Test
    @DisplayName("PUT /api/cities/{id} should update city")
    void updateCityShouldReturnUpdatedCity() throws Exception {
        UUID cityId = UUID.randomUUID();

        CityRequest request = CityRequest.builder()
                .name("Lagos")
                .cityCode("LOS")
                .countryCode("NG")
                .countryName("Nigeria")
                .regionCode("LA")
                .timeZoneOffset("+01:00")
                .build();

        CityResponse response = new CityResponse();

        Mockito.when(cityService.updateCity(eq(cityId), any(CityRequest.class)))
                .thenReturn(response);

        mockMvc.perform(put("/api/cities/{id}", cityId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        Mockito.verify(cityService).updateCity(eq(cityId), any(CityRequest.class));
    }

    @Test
    @DisplayName("DELETE /api/cities/{id} should return success message")
    void deleteCityShouldReturnSuccessMessage() throws Exception {
        UUID cityId = UUID.randomUUID();

        mockMvc.perform(delete("/api/cities/{id}", cityId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("City Deleted Successfully!"));
    }

    @Test
    @DisplayName("GET /api/cities/search should search cities by keyword")
    void searchCitiesShouldReturnMatchingCities() throws Exception {
        CityResponse response = new CityResponse();

        Page<CityResponse> page = new PageImpl<>(
                List.of(response),
                PageRequest.of(0, 20),
                1
        );

        Mockito.when(cityService.searchCities(eq("lagos"), any(Pageable.class)))
                .thenReturn(page);

        mockMvc.perform(get("/api/cities/search")
                        .param("keyword", "lagos")
                        .param("page", "0")
                        .param("size", "20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(1))
                .andExpect(jsonPath("$.totalElements").value(1));

        Mockito.verify(cityService).searchCities(eq("lagos"), any(Pageable.class));
    }

    @Test
    @DisplayName("GET /api/cities/country/{countryCode} should return cities by country code")
    void getCitiesByCountryCodeShouldReturnCities() throws Exception {
        CityResponse response = new CityResponse();

        Page<CityResponse> page = new PageImpl<>(
                List.of(response),
                PageRequest.of(0, 20),
                1
        );

        Mockito.when(cityService.getCitiesByCountryCode(eq("NG"), any(Pageable.class)))
                .thenReturn(page);

        mockMvc.perform(get("/api/cities/country/{countryCode}", "NG")
                        .param("page", "0")
                        .param("size", "20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(1))
                .andExpect(jsonPath("$.totalElements").value(1));

        Mockito.verify(cityService).getCitiesByCountryCode(eq("NG"), any(Pageable.class));
    }

    @Test
    @DisplayName("GET /api/cities/exists/{cityCode} should return whether city exists")
    void checkCityExistsShouldReturnBoolean() throws Exception {
        Mockito.when(cityService.cityExists("LOS"))
                .thenReturn(true);

        mockMvc.perform(get("/api/cities/exists/{cityCode}", "LOS"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        Mockito.verify(cityService).cityExists("LOS");
    }
}