package com.stack.location_service.controllers;

import com.stack.location_service.services.AirportService;
import com.stack.payloads.requests.AirportRequest;
import com.stack.payloads.responses.AirportResponse;
import com.stack.payloads.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/airports")
public class AirportController {
    private final AirportService airportService;

    @PostMapping
    public ResponseEntity<AirportResponse> createAirport(
            @Valid @RequestBody AirportRequest airportRequest
    ) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                airportService.createAirport(airportRequest)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<AirportResponse> getAirportById(@PathVariable UUID id) throws Exception {
        return ResponseEntity.ok(airportService.getAirportById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<Iterable<AirportResponse>> getAllAirports() {
        return ResponseEntity.ok(airportService.getAllAirports());
    }

    @GetMapping("/city/{cityId}")
    public ResponseEntity<Iterable<AirportResponse>> getAirportByCityId(@PathVariable UUID cityId) {
        return ResponseEntity.ok(airportService.getAirportByCityId(cityId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteAirportById(@PathVariable UUID id) throws Exception {
        airportService.deleteAirport(id);
        return ResponseEntity.ok(new ApiResponse("Airport Deleted Successfully!"));
    }
}
