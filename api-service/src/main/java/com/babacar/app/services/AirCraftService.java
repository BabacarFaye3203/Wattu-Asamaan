package com.babacar.app.services;

import com.babacar.app.dto.responses.AircraftResponse;
import com.babacar.app.mappers.AirCraftMapper;
import com.babacar.app.repositories.AirCraftAlertRepository;
import com.babacar.app.repositories.AirCraftProcessedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AirCraftService {
    private final AirCraftAlertRepository airCraftAlertRepository;
    private final AirCraftProcessedRepository airCraftProsseedRepository;
    private final AirCraftMapper airCraftMapper;

    public List<AircraftResponse> getAllAlerts(){
        return airCraftAlertRepository.findAll()
                .stream()
                .map(airCraftProsseed->new AircraftResponse(
                        airCraftProsseed.getUuid(),
                        airCraftProsseed.getIcao24(),
                        airCraftProsseed.getCallsign(),
                        airCraftProsseed.getLongitude(),
                        airCraftProsseed.getLatitude(),
                        airCraftProsseed.getAltitude(),
                        airCraftProsseed.getVelocity(),
                        airCraftProsseed.getOriginCountry(),
                        airCraftProsseed.getTimestamp()))
                .collect(Collectors.toList());
    }

    public List<AircraftResponse> getAllProcessed(){
        return airCraftProsseedRepository.findAll()
                .stream()
                .map(airCraftProsseed->new AircraftResponse(
                        airCraftProsseed.getUuid(),
                        airCraftProsseed.getIcao24(),
                        airCraftProsseed.getCallsign(),
                        airCraftProsseed.getLongitude(),
                        airCraftProsseed.getLatitude(),
                        airCraftProsseed.getAltitude(),
                        airCraftProsseed.getVelocity(),
                        airCraftProsseed.getOriginCountry(),
                        airCraftProsseed.getTimestamp()
                ))
                .collect(Collectors.toList());
    }
}
