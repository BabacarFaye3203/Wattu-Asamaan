package com.babacar.app.controllers;

import com.babacar.app.dto.responses.AircraftResponse;
import com.babacar.app.services.AirCraftService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController("/api/v1/aircrafts/")
@Tag(name = "AirCrafts APIs")
@RequiredArgsConstructor
public class AirCraftControllers {

    private final AirCraftService airCraftService;

    @GetMapping("/all-allerts")
    @Operation(summary = "get all alerts")
    public List<AircraftResponse> getAllAlerts(){
        return airCraftService.getAllAlerts();
    }

    @GetMapping("/all-procsseed")
    @Operation(summary = "get all prosseed aircrafts")
    public List<AircraftResponse> getAllProsseedAirCrats(){
        return airCraftService.getAllProcessed();
    }

}
