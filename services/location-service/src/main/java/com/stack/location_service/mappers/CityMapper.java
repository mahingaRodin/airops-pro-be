package com.stack.location_service.mappers;

import com.stack.location_service.models.City;
import com.stack.payloads.requests.CityRequest;
import com.stack.payloads.responses.CityResponse;

public class CityMapper {
    public static City toEntity(CityRequest request) {
        if(request==null) return null;

        return City.builder()
                .name(request.getName())
                .cityCode(request.getCityCode())
                .countryCode(request.getCountryCode())
                .countryName(request.getCountryName())
                .regionCode(request.getRegionCode())
                .timeZoneId(request.getTimeZoneOffset())
                .build();
    }

    public static CityResponse toResponse(City city) {
        if(city==null) return null;

        return CityResponse.builder()
                .id(city.getId())
                .name(city.getName())
                .cityCode(city.getCityCode())
                .countryCode(city.getCountryCode())
                .countryName(city.getCountryName())
                .regionCode(city.getRegionCode())
                .build();
    }

    public static City updateEntity(City city, CityRequest req) {
        if(req.getName() != null) {
            city.setName(req.getName().trim());
        }
        if(req.getCityCode() != null) {
            city.setCityCode(req.getCityCode().toUpperCase().trim());
        }
        if(req.getCountryCode() != null){
            city.setCountryCode(req.getCountryCode().toUpperCase().trim());
        }
        if(req.getCountryName() != null){
            city.setCountryName(req.getCountryName().trim());
        }
        if(req.getRegionCode() != null){
            city.setRegionCode(req.getRegionCode().toUpperCase().trim());
        }
        return city;
    }
}
