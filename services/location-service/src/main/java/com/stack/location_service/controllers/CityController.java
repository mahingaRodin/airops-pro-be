package com.stack.location_service.controllers;

import com.stack.location_service.services.CityService;
import com.stack.payloads.requests.CityRequest;
import com.stack.payloads.responses.CityResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cities")
public class CityController {
    private final CityService cityService;

    public ResponseEntity<CityResponse> createCity(
            @Valid @RequestBody CityRequest cityRequest
            ) throws Exception {
        CityResponse res = cityService.createCity(cityRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }
}
