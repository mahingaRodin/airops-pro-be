package com.stack.payloads.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CityRequest {
    @NotBlank(message = "City Name is Required!")
    @Size(max=100)
    private String name;

    @NotBlank(message = "City Code is Required")
    @Size(max=10)
    private String cityCode;

    @NotBlank(message = "Country Code is Required")
    @Size(max=5)
    private String countryCode;

    @NotBlank(message = "Country Name is Required")
    @Size(max=100)
    private String countryName;

    @Size(max=10)
    private String regionCode;

    @Size(max=10)
    private String timeZoneOffset;

}
