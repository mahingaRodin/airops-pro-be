package com.stack.payloads.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CityResponse {
    private UUID id;
    private String name;
    private String cityCode;
    private String countryName;
    private String countryCode;
    private String regionCode;
    private String timeZoneOffset;
}
