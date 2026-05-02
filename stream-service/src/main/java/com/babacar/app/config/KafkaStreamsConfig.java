package com.babacar.app.config;

import com.babacar.app.dto.AircraftState;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.time.Duration;

@Configuration
@EnableKafkaStreams
@Slf4j
public class KafkaStreamsConfig {

    private static final Duration WINDOW_SIZE = Duration.ofSeconds(10);
    private static final long SPEED_ALERT_THRESHOLD = 900L;

    @Bean
    public KStream<String, AircraftState> aircraftStream(StreamsBuilder builder) {

        KStream<String, AircraftState> sourceStream =
                builder.stream("aircraft-raw");

        // CLEAN STREAM
        KStream<String, AircraftState> cleanStream = sourceStream
                .filter((key, value) -> isValid(value));

        // ALERT STREAM
        KStream<String, AircraftState> alertStream = cleanStream
                .filter((key, value) -> isSpeedAnomaly(value));

        alertStream.to("aircraft-alerts");

        // PROCESSED STREAM
        cleanStream.to("aircraft-processed");

        // WINDOWED AGGREGATION (PRO LEVEL)
        KTable<Windowed<String>, Long> trafficByCountry =
                cleanStream
                        .groupBy((key, value) -> value.originCountry(),
                                Grouped.with(
                                        Serdes.String(),
                                        new JsonSerde<>(AircraftState.class)
                                )
                        )
                        .windowedBy(TimeWindows.ofSizeWithNoGrace(WINDOW_SIZE))
                        .count();

        // LOG OUTPUT (DEBUG / OBSERVABILITY)
//        trafficByCountry.toStream()
//                .peek((windowedKey, count) ->
//                        log.info("🌍 Country: {} | Count: {} | Window: {} → {}",
//                                windowedKey.key(),
//                                count,
//                                windowedKey.window().startTime(),
//                                windowedKey.window().endTime()
//                        )
//                );

        return sourceStream;
    }

    private boolean isValid(AircraftState a) {
        return a != null
                && a.latitude() != null
                && a.longitude() != null
                && a.originCountry() != null;
    }

    private boolean isSpeedAnomaly(AircraftState a) {
        return a.velocity() != null && a.velocity() > SPEED_ALERT_THRESHOLD;
    }
}