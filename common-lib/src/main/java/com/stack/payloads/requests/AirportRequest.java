package com.stack.payloads.requests;

import com.stack.embeddables.Address;
import com.stack.embeddables.GeoCode;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AirportRequest {
    @NotBlank(message = "IATA code is mandatory!")
    @Size(min=3, max=3, message = "IATA code must be exactly 3 characters!")
    private String iataCode;

    @NotBlank(message = "Airport name is required")
    private String name;

    @Valid
    private Address address;

    @NotNull(message = "City ID is mandatory!")
    private UUID cityId;

    @Valid
    private GeoCode geoCode;
}
