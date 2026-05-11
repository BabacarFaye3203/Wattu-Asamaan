package com.babacar.app.websocket;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class WebSocketTestController {

    private final SimpMessagingTemplate messagingTemplate;
    private final AircraftWebSocketService aircraftWebSocketService;

    @GetMapping("/test-ws")
    public String testWs() {

        messagingTemplate.convertAndSend(
                "/topic/stats",
                "WEBSOCKET OK"
        );

        return "Message envoyé";
    }
    @GetMapping("/force-ws")
    public String force() {

        aircraftWebSocketService.sendStats(
                Map.of("status", "WS OK")
        );

        return "sent";
    }
}