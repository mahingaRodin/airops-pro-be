package com.stack.location_service.repositories;

import com.stack.location_service.models.Airport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AirportRepository extends JpaRepository<Airport,UUID> {
    Optional<Airport> findByIataCode(String iataCode);
    List<Airport> findByCityId(UUID cityId);
}
