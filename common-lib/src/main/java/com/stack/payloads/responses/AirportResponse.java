package com.stack.payloads.responses;

import com.stack.embeddables.Address;
import com.stack.embeddables.GeoCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZoneId;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AirportResponse {
    private UUID id;
    private String iataCode;
    private String name;
    private String detailedName;
    private ZoneId timeZone;
    private Address address;
    private CityResponse city;
    private GeoCode geoCode;
}
