package com.babacar.app.streams;
import com.babacar.app.constants.AirCraftConstants;
import com.babacar.app.dto.AircraftState;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.util.logging.Logger;


@Configuration
@EnableKafkaStreams
public class KafkaStreams {

    private static final Logger log =
            Logger.getLogger("OBSERVABILITY");

    @Bean
    public KStream<String, AircraftState> aircraftStream(StreamsBuilder builder) {

        JsonSerde<AircraftState> aircraftSerde =
                new JsonSerde<>(AircraftState.class);

        KStream<String, AircraftState> sourceStream =
                builder.stream(
                        "aircraft-raw",
                        Consumed.with(Serdes.String(), aircraftSerde)
                );

        // CLEAN STREAM
        KStream<String, AircraftState> cleanStream = sourceStream
                .filter((key, value) -> isValid(value));

        // ALERT STREAM
        KStream<String, AircraftState> alertStream = cleanStream
                .filter((key, value) -> isSpeedAnomaly(value));

        alertStream.to("aircraft-alerts");

        // PROCESSED STREAM
        cleanStream.to("aircraft-processed");

        // WINDOWED AGGREGATION
        KTable<Windowed<String>, Long> trafficByCountry =
                cleanStream
                        .groupBy((key, value) -> value.originCountry(),
                                Grouped.with(
                                        Serdes.String(),
                                        new JsonSerde<>(AircraftState.class)
                                )
                        )
                        .windowedBy(TimeWindows.ofSizeWithNoGrace(AirCraftConstants.WINDOW_SIZE))
                        .count();

         //LOG OUTPUT (DEBUG / OBSERVABILITY)
        trafficByCountry.toStream()
                .peek((windowedKey, count) ->
                        log.info("🌍 Country: "+windowedKey.key()+" | Count:"+count+ "| Window:" +windowedKey.window().startTime()+" → "+
                                windowedKey.window().endTime()


                        )
                );

        return sourceStream;
    }

    private boolean isValid(AircraftState a) {
        return a != null
                && a.latitude() != null
                && a.longitude() != null
                && a.originCountry() != null;
    }

    private boolean isSpeedAnomaly(AircraftState a) {
        return a.velocity() != null && a.velocity() > AirCraftConstants.SPEED_ALERT_THRESHOLD;
    }
}