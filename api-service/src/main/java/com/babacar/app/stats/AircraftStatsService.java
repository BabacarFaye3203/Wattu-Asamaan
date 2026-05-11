package com.babacar.app.stats;

import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Data
@Service
public class AircraftStatsService {
    private final ConcurrentHashMap<String, AtomicInteger> countryCount = new ConcurrentHashMap<>();

    private AtomicInteger totalAircrafts = new AtomicInteger(0);

    private AtomicInteger totalAlerts = new AtomicInteger(0);

    private AtomicLong totalSpeed = new AtomicLong(0);

    public void incrementAircraft(double speed) {

        totalAircrafts.incrementAndGet();

        totalSpeed.addAndGet((long) speed);
    }

    public void incrementAlerts() {
        totalAlerts.incrementAndGet();
    }

    public double getAverageSpeed() {

        if(totalAircrafts.get() == 0) {
            return 0;
        }
        return totalSpeed.get() / (double) totalAircrafts.get();
    }

    public void incrementCountry(String country) {

        countryCount
                .computeIfAbsent(country, c -> new AtomicInteger(0))
                .incrementAndGet();
    }

    public String getTopCountry() {

        return countryCount.entrySet()
                .stream()
                .max((a, b) -> Integer.compare(
                        a.getValue().get(),
                        b.getValue().get()
                ))
                .map(entry -> entry.getKey())
                .orElse("N/A");
    }
    public int getTopCountryCount() {

        return countryCount.entrySet()
                .stream()
                .map(e -> e.getValue().get())
                .max(Integer::compareTo)
                .orElse(0);
    }
}