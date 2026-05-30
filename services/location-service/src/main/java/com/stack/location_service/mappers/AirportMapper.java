package com.stack.location_service.mappers;

import com.stack.location_service.models.Airport;
import com.stack.payloads.requests.AirportRequest;
import com.stack.payloads.responses.AirportResponse;

public class AirportMapper {
    public static Airport toEntity(AirportRequest request) {
        if(request==null)return null;

        return Airport.builder()
                .iataCode(request.getIataCode())
                .name(request.getName())
                .timeZoneId(request.getIataCode())
                .address(request.getAddress())
                .geoCode(request.getGeoCode())
                .build();
    }

    public static AirportResponse toResponse(Airport airport) {
        if(airport==null) return null;
        return AirportResponse.builder()
                .id(airport.getId())
                .iataCode(airport.getIataCode())
                .name(airport.getName())
                .detailedName(airport.getDetailedName())
//                .timeZone(airport.getTimeZone())
                .address(airport.getAddress())
                .city(CityMapper.toResponse(airport.getCity()))
                .geoCode(airport.getGeoCode())
                .build();
    }

    public static void updateEntity(AirportRequest req,Airport existingAirport) {
        if(req==null || existingAirport==null) return;
        if(req.getIataCode() != null) {
            existingAirport.setIataCode(req.getIataCode());
        }
        if(req.getName() != null) {
            existingAirport.setName(req.getName());
        }
        if(req.getAddress() != null) {
            existingAirport.setAddress(req.getAddress());
        }
        if(req.getGeoCode() != null) {
            existingAirport.setGeoCode(req.getGeoCode());
        }
    }
}
