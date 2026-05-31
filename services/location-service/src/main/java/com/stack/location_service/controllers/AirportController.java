package com.stack.location_service.controllers;

import com.stack.location_service.services.AirportService;
import com.stack.payloads.requests.AirportRequest;
import com.stack.payloads.responses.AirportResponse;
import com.stack.payloads.responses.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(
        name = "Airports",
        description = "Endpoints for creating, retrieving, listing, and deleting airports"
)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/airports")
public class AirportController {
    private final AirportService airportService;

    @Operation(
            summary = "Create a new airport",
            description = "Creates a new airport using its IATA code, name, city ID, address, and geocode information."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "201",
                    description = "Airport created successfully",
                    content = @Content(schema = @Schema(implementation = AirportResponse.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "Invalid request body or validation error",
                    content = @Content
            )
    })
    @PostMapping
    public ResponseEntity<AirportResponse> createAirport(
            @Valid
            @RequestBody(
                    description = "Airport creation request body",
                    required = true,
                    content = @Content(schema = @Schema(implementation = AirportRequest.class))
            )
            @org.springframework.web.bind.annotation.RequestBody AirportRequest airportRequest
    ) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                airportService.createAirport(airportRequest)
        );
    }

    @Operation(
            summary = "Get airport by ID",
            description = "Retrieves a single airport using its unique UUID."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Airport found",
                    content = @Content(schema = @Schema(implementation = AirportResponse.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "Airport not found",
                    content = @Content
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<AirportResponse> getAirportById(
            @Parameter(
                    description = "Unique airport ID",
                    example = "550e8400-e29b-41d4-a716-446655440000",
                    required = true
            )
            @PathVariable UUID id
    ) throws Exception {
        return ResponseEntity.ok(airportService.getAirportById(id));
    }

    @Operation(
            summary = "Get all airports",
            description = "Returns all airports available in the location service."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Airports retrieved successfully",
                    content = @Content(schema = @Schema(implementation = AirportResponse.class))
            )
    })
    @GetMapping("/all")
    public ResponseEntity<Iterable<AirportResponse>> getAllAirports() {
        return ResponseEntity.ok(airportService.getAllAirports());
    }

    @Operation(
            summary = "Get airports by city ID",
            description = "Returns all airports that belong to a specific city."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Airports for the city retrieved successfully",
                    content = @Content(schema = @Schema(implementation = AirportResponse.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "City not found or no airports found for the city",
                    content = @Content
            )
    })
    @GetMapping("/city/{cityId}")
    public ResponseEntity<Iterable<AirportResponse>> getAirportByCityId(
            @Parameter(
                    description = "Unique city ID",
                    example = "550e8400-e29b-41d4-a716-446655440000",
                    required = true
            )
            @PathVariable UUID cityId
    ) {
        return ResponseEntity.ok(airportService.getAirportByCityId(cityId));
    }

    @Operation(
            summary = "Delete airport by ID",
            description = "Deletes an airport using its unique UUID."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Airport deleted successfully",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "Airport not found",
                    content = @Content
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteAirportById(
            @Parameter(
                    description = "Unique airport ID",
                    example = "550e8400-e29b-41d4-a716-446655440000",
                    required = true
            )
            @PathVariable UUID id
    ) throws Exception {
        airportService.deleteAirport(id);
        return ResponseEntity.ok(new ApiResponse("Airport Deleted Successfully!"));
    }
}