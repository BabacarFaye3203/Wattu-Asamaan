package com.babacar.app.consumer;

import com.babacar.app.dto.AircraftState;
import com.babacar.app.dto.DashboardStats;
import com.babacar.app.entities.AirCraftProsseed;
import com.babacar.app.repositories.AirCraftProcessedRepository;
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
public class ProsseedAirCraftConsumer {

    private final AirCraftProcessedRepository repository;
    private final SimpMessagingTemplate messagingTemplate;
    private final AircraftWebSocketService aircraftWebSocketService;
    private final AircraftStatsService aircraftStatsService;

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

        log.info("🔥 PROCESSED WS SEND TEST");
        aircraftWebSocketService.sendProcessedAircraft(airCraft);
        aircraftStatsService.incrementAircraft(aircraftState.velocity());
        aircraftStatsService.incrementCountry(aircraftState.originCountry());

        DashboardStats stats = DashboardStats.builder()
                .totalAircrafts(aircraftStatsService.getTotalAircrafts().get())
                .averageSpeed(aircraftStatsService.getAverageSpeed())
                .activeAlerts(aircraftStatsService.getTotalAlerts().get())
                .mostActiveCountry(aircraftStatsService.getTopCountry())
                .build();

        aircraftWebSocketService.sendStats(stats);
    }
}
