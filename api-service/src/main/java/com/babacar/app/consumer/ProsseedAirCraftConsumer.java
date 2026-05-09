package com.babacar.app.consumer;

import com.babacar.app.dto.AircraftState;
import com.babacar.app.entities.AirCraftProsseed;
import com.babacar.app.repositories.AirCraftProsseedRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProsseedAirCraftConsumer {

    private final AirCraftProsseedRepository repository;


    @KafkaListener(topics = "aircraft-processed",groupId = "aircraft-processed-id")
    public void getAircraftAlerts(AircraftState aircraftState){
        log.info("consomation des alertes{}",aircraftState);
        AirCraftProsseed airCraft=AirCraftProsseed.builder()
                .latitude(aircraftState.latitude())
                .icao24(aircraftState.icao24())
                .altitude(aircraftState.altitude())
                .timestamp(aircraftState.timestamp())
                .callsign(aircraftState.callsign())
                .velocity(aircraftState.velocity())
                .longitude(aircraftState.longitude())
                .originCountry(aircraftState.originCountry())
                .uuid(UUID.randomUUID().toString())
                .build();
        repository.insert(airCraft);


    }
}
