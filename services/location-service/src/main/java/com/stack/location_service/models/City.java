package com.stack.location_service.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "cities")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String cityCode;

    @Column(nullable = false, unique = true)
    private String countryCode;

    @Column(nullable = false)
    private String countryName;

    @Size(max= 100)
    private String regionCode;

    @Column(name = "time_zone_id", length = 50)
    private String timeZoneId;
}
