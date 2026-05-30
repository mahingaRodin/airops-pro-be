package com.stack.location_service.controllers;

import com.stack.location_service.services.CityService;
import com.stack.payloads.requests.CityRequest;
import com.stack.payloads.responses.ApiResponse;
import com.stack.payloads.responses.CityResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cities")
public class CityController {
    private final CityService cityService;

    @PostMapping
    public ResponseEntity<CityResponse> createCity(
            @Valid @RequestBody CityRequest cityRequest
            ) throws Exception {
        CityResponse res = cityService.createCity(cityRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CityResponse> getCityById(
            @PathVariable UUID id
            ) throws Exception {
        CityResponse res = cityService.getCityById(id);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<CityResponse>> getAllCities(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size,sort);
        return ResponseEntity.ok(cityService.getAllCities(pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CityResponse> updateCity(
            @PathVariable UUID id,
            @Valid @RequestBody CityRequest req
    ) throws Exception {
        return ResponseEntity.ok(cityService.updateCity(id,req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCity(
            @PathVariable UUID id
    ) throws Exception {
        return ResponseEntity.ok(new ApiResponse("City Deleted Successfully!"));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<CityResponse>> searchCities(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page,size);
        return ResponseEntity.ok(cityService.searchCities(keyword,pageable));
    }

    @GetMapping("/country/{countryCode}")
    public ResponseEntity<Page<CityResponse>> getCitiesByCountryCode(
            @PathVariable String countryCode,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page,size);
        return ResponseEntity.ok(cityService.getCitiesByCountryCode(countryCode,pageable));
    }

    @GetMapping("/exists/{cityCode}")
    public ResponseEntity<Boolean> checkCityExists(@PathVariable String cityCode) {
        return ResponseEntity.ok(cityService.cityExists(cityCode));
    }
}
