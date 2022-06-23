package de.szut.shift_backend.socket;

import de.szut.shift_backend.model.dto.AddMessageDto;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WebsocketEventEmitterService {

    private final SimpMessagingTemplate simp;

    public WebsocketEventEmitterService(SimpMessagingTemplate simp) {
        this.simp = simp;
    }

    public <T> void sendTo(Long userId, String targetDestination, T payload) {
        simp.convertAndSendToUser(String.valueOf(userId), targetDestination, payload);
    }

    public <T> void sendTo(List<Long> userIds, String targetDestination, T payload) {
        for (Long userId: userIds) {
            sendTo(userId, targetDestination, payload);
        }
    }

    public <T> void sendToAll(String targetDestination, T payload) {
        simp.convertAndSend(targetDestination, payload);
    }
}
