package com.babacar.app.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic aircraftAlertTopic(){
        return new NewTopic("aircraft-alerts",
                3,
                (short) 3
        );
    }
    @Bean
    public NewTopic aircraftProcessedTopic(){
        return new NewTopic("aircraft-processed",
                3,
                (short) 3
        );
    }
}
