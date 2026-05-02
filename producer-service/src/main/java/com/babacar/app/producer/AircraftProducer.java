package com.babacar.app.producer;

import com.babacar.app.dto.AircraftState;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class AircraftProducer {

    private final KafkaTemplate<String, AircraftState> kafkaTemplate;

    public AircraftProducer(KafkaTemplate<String, AircraftState> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(AircraftState aircraft) {
        kafkaTemplate.send("aircraft-raw", aircraft);
    }
}