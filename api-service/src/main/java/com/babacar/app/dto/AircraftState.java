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
) {}