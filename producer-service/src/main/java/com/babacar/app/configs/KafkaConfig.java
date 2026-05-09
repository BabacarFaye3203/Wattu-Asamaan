package com.babacar.app.configs;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic aircraftRawTopic(){
        return TopicBuilder
                .name("aircraft-raw")
                .partitions(3)
                .build();
    }
}
