package com.stack.location_service.services;

import com.stack.payloads.requests.AirportRequest;
import com.stack.payloads.responses.AirportResponse;

import java.util.List;
import java.util.UUID;

public interface AirportService {
    AirportResponse createAirport(AirportRequest request) throws Exception;
    AirportResponse getAirportById(UUID id) throws Exception;

    List<AirportResponse> getAllAirports();

    AirportResponse updateAirport(AirportRequest req, UUID id) throws Exception;
    void deleteAirport(UUID id) throws Exception;
    List<AirportResponse> getAirportByCityId(UUID cityId);
}
