package com.stack.location_service.controllers;

import com.stack.location_service.services.CityService;
import com.stack.payloads.requests.CityRequest;
import com.stack.payloads.responses.ApiResponse;
import com.stack.payloads.responses.CityResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(
        name = "Cities",
        description = "Endpoints for creating, retrieving, updating, searching, and deleting cities"
)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cities")
public class CityController {
    private final CityService cityService;

    @Operation(
            summary = "Create a new city",
            description = "Creates a city using its name, city code, country code, country name, region code, and time zone offset."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "201",
                    description = "City created successfully",
                    content = @Content(schema = @Schema(implementation = CityResponse.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "Invalid request body or validation error",
                    content = @Content
            )
    })
    @PostMapping
    public ResponseEntity<CityResponse> createCity(
            @Valid
            @RequestBody(
                    description = "City creation request body",
                    required = true,
                    content = @Content(schema = @Schema(implementation = CityRequest.class))
            )
            @org.springframework.web.bind.annotation.RequestBody CityRequest cityRequest
    ) throws Exception {
        CityResponse res = cityService.createCity(cityRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @Operation(
            summary = "Get city by ID",
            description = "Retrieves a single city using its unique UUID."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "City found",
                    content = @Content(schema = @Schema(implementation = CityResponse.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "City not found",
                    content = @Content
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<CityResponse> getCityById(
            @Parameter(
                    description = "Unique city ID",
                    example = "550e8400-e29b-41d4-a716-446655440000",
                    required = true
            )
            @PathVariable UUID id
    ) throws Exception {
        CityResponse res = cityService.getCityById(id);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @Operation(
            summary = "Get all cities",
            description = "Returns a paginated list of cities. Supports pagination and sorting."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Cities retrieved successfully",
                    content = @Content(schema = @Schema(implementation = CityResponse.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "Invalid pagination or sorting parameters",
                    content = @Content
            )
    })
    @GetMapping("/all")
    public ResponseEntity<Page<CityResponse>> getAllCities(
            @Parameter(description = "Page number starting from 0", example = "0")
            @RequestParam(defaultValue = "0") int page,

            @Parameter(description = "Number of records per page", example = "20")
            @RequestParam(defaultValue = "20") int size,

            @Parameter(description = "Field to sort by", example = "name")
            @RequestParam(defaultValue = "name") String sortBy,

            @Parameter(description = "Sort direction: asc or desc", example = "asc")
            @RequestParam(defaultValue = "asc") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return ResponseEntity.ok(cityService.getAllCities(pageable));
    }

    @Operation(
            summary = "Update city by ID",
            description = "Updates an existing city using its UUID and the provided city request body."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "City updated successfully",
                    content = @Content(schema = @Schema(implementation = CityResponse.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "Invalid request body or validation error",
                    content = @Content
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "City not found",
                    content = @Content
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<CityResponse> updateCity(
            @Parameter(
                    description = "Unique city ID",
                    example = "550e8400-e29b-41d4-a716-446655440000",
                    required = true
            )
            @PathVariable UUID id,

            @Valid
            @RequestBody(
                    description = "City update request body",
                    required = true,
                    content = @Content(schema = @Schema(implementation = CityRequest.class))
            )
            @org.springframework.web.bind.annotation.RequestBody CityRequest req
    ) throws Exception {
        return ResponseEntity.ok(cityService.updateCity(id, req));
    }

    @Operation(
            summary = "Delete city by ID",
            description = "Deletes a city using its unique UUID."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "City deleted successfully",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "City not found",
                    content = @Content
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCity(
            @Parameter(
                    description = "Unique city ID",
                    example = "550e8400-e29b-41d4-a716-446655440000",
                    required = true
            )
            @PathVariable UUID id
    ) throws Exception {
        return ResponseEntity.ok(new ApiResponse("City Deleted Successfully!"));
    }

    @Operation(
            summary = "Search cities",
            description = "Searches cities by a keyword. The keyword can match city-related fields depending on the service implementation."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Search completed successfully",
                    content = @Content(schema = @Schema(implementation = CityResponse.class))
            )
    })
    @GetMapping("/search")
    public ResponseEntity<Page<CityResponse>> searchCities(
            @Parameter(description = "Search keyword", example = "lagos", required = true)
            @RequestParam String keyword,

            @Parameter(description = "Page number starting from 0", example = "0")
            @RequestParam(defaultValue = "0") int page,

            @Parameter(description = "Number of records per page", example = "20")
            @RequestParam(defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(cityService.searchCities(keyword, pageable));
    }

    @Operation(
            summary = "Get cities by country code",
            description = "Returns paginated cities that belong to the provided country code."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Cities retrieved successfully",
                    content = @Content(schema = @Schema(implementation = CityResponse.class))
            )
    })
    @GetMapping("/country/{countryCode}")
    public ResponseEntity<Page<CityResponse>> getCitiesByCountryCode(
            @Parameter(description = "Country code", example = "NG", required = true)
            @PathVariable String countryCode,

            @Parameter(description = "Page number starting from 0", example = "0")
            @RequestParam(defaultValue = "0") int page,

            @Parameter(description = "Number of records per page", example = "20")
            @RequestParam(defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(cityService.getCitiesByCountryCode(countryCode, pageable));
    }

    @Operation(
            summary = "Check if city exists",
            description = "Checks whether a city exists using its city code."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Boolean result returned successfully",
                    content = @Content(schema = @Schema(implementation = Boolean.class))
            )
    })
    @GetMapping("/exists/{cityCode}")
    public ResponseEntity<Boolean> checkCityExists(
            @Parameter(description = "City code", example = "LOS", required = true)
            @PathVariable String cityCode
    ) {
        return ResponseEntity.ok(cityService.cityExists(cityCode));
    }
}