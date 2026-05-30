package com.stack.location_service.impl;

import com.stack.location_service.mappers.AirportMapper;
import com.stack.location_service.models.Airport;
import com.stack.location_service.models.City;
import com.stack.location_service.repositories.AirportRepository;
import com.stack.location_service.repositories.CityRepository;
import com.stack.location_service.services.AirportService;
import com.stack.payloads.requests.AirportRequest;
import com.stack.payloads.responses.AirportResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AirportServiceImpl implements AirportService {
    private final AirportRepository airportRepository;
    private final CityRepository cityRepository;

    @Override
    public AirportResponse createAirport(AirportRequest request) throws Exception {
        if(airportRepository.findByIataCode(request.getIataCode()).isPresent()) {
            throw new Exception("Airport with IATA Code already exists!");
        }
        City city = cityRepository.findById(request.getCityId()).orElseThrow(
                () -> new Exception("City with ID not found")
        );
        Airport airport = AirportMapper.toEntity(request);
        airport.setCity(city);
        Airport savedAirport = airportRepository.save(airport);
        return AirportMapper.toResponse(savedAirport);
    }

    @Override
    public AirportResponse getAirportById(UUID id) throws Exception {
        Airport airport = airportRepository.findById(id).orElseThrow(
                () -> new Exception("Airport with given ID not found!")
        );
        return AirportMapper.toResponse(airport);
    }

    @Override
    public List<AirportResponse> getAllAirports() {
        return airportRepository.findAll().stream()
                .map(AirportMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public AirportResponse updateAirport(AirportRequest req, UUID id) throws Exception {
        Airport existingAirport = airportRepository.findById(id).orElseThrow(
                () -> new Exception("Airport with given ID not found!")
        );
        if(req.getIataCode() != null && !req.getIataCode().equals(existingAirport.getIataCode()) &&
        airportRepository.findByIataCode(req.getIataCode()).isPresent()
        ) {
            throw new Exception("Airport with given IATA Code already exists!");
        }
        AirportMapper.updateEntity(req, existingAirport);
        Airport updatedAirport = airportRepository.save(existingAirport);
        return AirportMapper.toResponse(updatedAirport);
    }

    @Override
    public void deleteAirport(UUID id) throws Exception {
    Airport airport = airportRepository.findById(id).orElseThrow(
            () -> new Exception("Airport with given ID not found!")
    );
    airportRepository.delete(airport);
    }

    @Override
    public List<AirportResponse> getAirportByCityId(UUID cityId) {
        return airportRepository.findByCityId(cityId).stream()
                .map(AirportMapper::toResponse)
                .collect(Collectors.toList());
    }
}
