package com.babacar.app.dto.responses;

public record AircraftResponse(
        String uuid,
        String icao24,
        String callsign,
        Double longitude,
        Double latitude,
        Double altitude,
        Double velocity,
        String originCountry,
        Long timestamp

) {
}
