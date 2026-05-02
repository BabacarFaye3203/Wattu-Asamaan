package com.babacar.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ProducerService {
    public static void main(String[] args) {
        SpringApplication.run(ProducerService.class, args);
    }
}