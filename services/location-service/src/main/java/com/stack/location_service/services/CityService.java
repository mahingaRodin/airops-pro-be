package com.stack.location_service.services;

import com.stack.payloads.requests.CityRequest;
import com.stack.payloads.responses.CityResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CityService {
    CityResponse createCity(CityRequest req) throws Exception;
    CityResponse getCityById(UUID id) throws Exception;

    CityResponse updateCity(UUID id, CityRequest req) throws Exception;
    void deleteCity(UUID id) throws Exception;
    Page<CityResponse> getAllCities(Pageable pageable);

    Page<CityResponse> searchCities(String keyword, Pageable pageable);
    Page<CityResponse> getCitiesByCountryCode(String countryCode, Pageable pageable);

    boolean cityExists(String cityCode);
}
