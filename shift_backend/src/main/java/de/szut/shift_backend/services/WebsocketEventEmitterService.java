package de.szut.shift_backend.services;

import de.szut.shift_backend.model.dto.AddMessageDto;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebsocketEventEmitterService {

    private final SimpMessagingTemplate simp;

    public WebsocketEventEmitterService(SimpMessagingTemplate simp) {
        this.simp = simp;
    }

    public void sendTo(Long userId, String targetDestination, AddMessageDto messageDto) {
        simp.convertAndSendToUser(String.valueOf(userId), targetDestination, messageDto);
    }

    public void sendToAll(String targetDestination, AddMessageDto messageDto) {
        simp.convertAndSend(targetDestination, messageDto);
    }


}
