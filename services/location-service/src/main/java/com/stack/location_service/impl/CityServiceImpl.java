package com.stack.location_service.impl;

import com.stack.location_service.mappers.CityMapper;
import com.stack.location_service.models.City;
import com.stack.location_service.repositories.CityRepository;
import com.stack.location_service.services.CityService;
import com.stack.payloads.requests.CityRequest;
import com.stack.payloads.responses.CityResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {
    private final CityRepository cityRep;

    @Override
    public CityResponse createCity(CityRequest req) throws Exception {
    if(cityRep.existsByCityCode(req.getCityCode())){
        throw new Exception("City with given code already exists!");
    }
        City city = CityMapper.toEntity(req);
        City res = cityRep.save(city);
        return CityMapper.toResponse(res);
    }

    @Override
    public CityResponse getCityById(UUID id) throws Exception{
        City city = cityRep.findById(id).orElseThrow(
                () -> new Exception("City with given Id Doesn't Exist!")
        );
        return CityMapper.toResponse(city);
    }

    @Override
    public CityResponse updateCity(UUID id, CityRequest req) throws Exception {
        City city = cityRep.findById(id).orElseThrow(
                () -> new Exception("City with given Id Doesn't Exist!")
        );
        if(cityRep.existsByCityCode(req.getCityCode())){
            throw new Exception("City with given code already exists!");
        }
        City updatedCity = cityRep.save(CityMapper.updateEntity(city,req));
        return CityMapper.toResponse(updatedCity);
    }

    @Override
    public void deleteCity(UUID id) throws Exception {
        City city = cityRep.findById(id).orElseThrow(
                () -> new Exception("City with given Id Doesn't Exist!")
        );
        cityRep.delete(city);
    }

    @Override
    public Page<CityResponse> getAllCities(Pageable pageable) {
        return cityRep.findAll(pageable).map(CityMapper::toResponse);
    }

    @Override
    public Page<CityResponse> searchCities(String keyword, Pageable pageable) {
        return cityRep.searchByKeyword(keyword,pageable).map(CityMapper::toResponse);
    }

    @Override
    public Page<CityResponse> getCitiesByCountryCode(String countryCode, Pageable pageable) {
        return cityRep.findByCountryCodeIgnoreCase(countryCode, pageable).map(
                CityMapper::toResponse
        );
    }

    @Override
    public boolean cityExists(String cityCode) {
        return cityRep.existsByCityCode(cityCode);
    }
}
