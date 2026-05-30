package com.stack.location_service.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.stack.embeddables.Address;
import com.stack.embeddables.GeoCode;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "air_ports")
public class Airport {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false, length = 3)
    private String iataCode;

    @Column(nullable = false)
    private String name;

    @Embedded
    private Address address;

    @Embedded
    private GeoCode geoCode;

    @Column(name = "time_zone_id", length = 50)
    private String timeZoneId;

    @ManyToOne
    @JsonIgnore
    private City city;

    @JsonIgnore
    public String getDetailedName() {
        if(city!=null && city.getCountryCode() != null){
            return name.toUpperCase() + "/" + city.getCityCode();
        }
        return name.toUpperCase();
    }
}
