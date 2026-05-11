package com.babacar.app.entities;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(collection = "airCraftAlert")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AirCraftAlert {
    @MongoId
    private String id;
    private String uuid;
    private String icao24;
    private String callsign;
    private Double longitude;
    private Double latitude;
    private Double altitude;
    private Double velocity;
    private String originCountry;
    private Long timestamp;
}
