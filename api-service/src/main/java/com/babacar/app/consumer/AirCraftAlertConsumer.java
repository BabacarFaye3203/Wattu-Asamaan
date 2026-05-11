package com.babacar.app.consumer;

import com.babacar.app.dto.AircraftState;
import com.babacar.app.entities.AirCraftAlert;
import com.babacar.app.repositories.AirCraftAlertRepository;
import com.babacar.app.stats.AircraftStatsService;
import com.babacar.app.websocket.AircraftWebSocketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AirCraftAlertConsumer {
    private final AirCraftAlertRepository repository;

    private final SimpMessagingTemplate messagingTemplate;
    private final AircraftWebSocketService aircraftWebSocketService;
    private final AircraftStatsService aircraftStatsService;

    @KafkaListener(topics = "aircraft-alerts",groupId ="aircraft-alerts-id" )
    public void getAircraftAlerts(AircraftState aircraftState){

        log.info("consomation des alertes{}",aircraftState);
        AirCraftAlert airCraft=AirCraftAlert.builder()
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
        log.info("🔥 ALERT WS SEND TEST");
        aircraftWebSocketService.sendAlertAircraft(airCraft);
        aircraftStatsService.incrementCountry(aircraftState.originCountry());
        aircraftStatsService.incrementAlerts();


    }
}
