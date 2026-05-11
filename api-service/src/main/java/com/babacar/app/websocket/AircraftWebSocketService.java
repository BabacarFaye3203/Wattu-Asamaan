package com.babacar.app.websocket;

import com.babacar.app.entities.AirCraftAlert;
import com.babacar.app.entities.AirCraftProsseed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

@Service
@RequiredArgsConstructor
@Slf4j
public class AircraftWebSocketService {

    private final SimpMessagingTemplate messagingTemplate;
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        log.info("Client WebSocket connecté");
    }

    public void sendProcessedAircraft(AirCraftProsseed aircraft) {
        System.out.println("✈️ WS SEND PROCESSED => " + aircraft);

        messagingTemplate.convertAndSend("/topic/processed", aircraft);
    }
    public void sendAlertAircraft(AirCraftAlert aircraft) {
        System.out.println("🚨 WS SEND ALERT => " + aircraft);

        messagingTemplate.convertAndSend("/topic/alerts", aircraft);
    }

    public void sendStats(Object stats) {
        System.out.println("📡 WS SEND STATS => " + stats);

        messagingTemplate.convertAndSend("/topic/stats", stats);
    }
}