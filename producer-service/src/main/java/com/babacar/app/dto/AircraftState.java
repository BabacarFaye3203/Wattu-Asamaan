package com.babacar.app.dto;

public record AircraftState(
        String icao24,
        String callsign,
        Double longitude,
        Double latitude,
        Double altitude,
        Double velocity,
        String originCountry,
        Long timestamp
) {
    public static AircraftState fromArray(Object[] data) {

        return new AircraftState(
                (String) data[0],
                (String) data[1],
                data[5] != null ? ((Number) data[5]).doubleValue() : null,
                data[6] != null ? ((Number) data[6]).doubleValue() : null,
                data[7] != null ? ((Number) data[7]).doubleValue() : null,
                data[9] != null ? ((Number) data[9]).doubleValue() : null,
                (String) data[2],
                data[4] != null ? ((Number) data[4]).longValue() : null
        );
    }
}