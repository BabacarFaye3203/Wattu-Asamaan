package com.babacar.app.schuduler;

import com.babacar.app.dto.AircraftState;
import com.babacar.app.producer.AircraftProducer;
import com.babacar.app.service.OpenSkyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class AircraftScheduler {

    private final OpenSkyService openSkyService;
    private final AircraftProducer producer;

    public AircraftScheduler(OpenSkyService openSkyService, AircraftProducer producer) {
        this.openSkyService = openSkyService;
        this.producer = producer;
    }
    private long lastCallTime = 0;
    private static final long MIN_INTERVAL = 300000;

    @Scheduled(fixedDelay = 10000)
    public void fetchAndSend() {

        long now = System.currentTimeMillis();

        if (now - lastCallTime < MIN_INTERVAL) {
            return; // skip call
        }

        lastCallTime = now;

        try {
            log.info(" Fetching OpenSky data...");

            List<AircraftState> aircrafts = openSkyService.fetchAircrafts();

            aircrafts.stream()
                    .limit(100)
                    .forEach(producer::send);
            log.info("📡 Sent {} aircrafts", aircrafts.size());

        } catch (Exception e) {
            log.error("Scheduler error: {}", e.getMessage(), e);
        }
    }

}