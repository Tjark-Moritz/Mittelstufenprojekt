package de.szut.shift_backend.controller;

import de.szut.shift_backend.model.dto.AddMessageDto;
import de.szut.shift_backend.model.dto.GetMessageDto;
import de.szut.shift_backend.services.WebsocketEventEmitterService;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;

@Controller
@MessageMapping("/chat")
public class MessageHandlingController {

    private final MessageController messageController;
    private final WebsocketEventEmitterService websocketEventEmitterService;

    public MessageHandlingController(MessageController messageController, WebsocketEventEmitterService websocketEventEmitterService) {
        this.messageController = messageController;
        this.websocketEventEmitterService = websocketEventEmitterService;
    }

    @MessageMapping("/create")
    public void createMessage(AddMessageDto addMessageDto) throws Exception { //logic aus Controller rein => void
        System.out.println("create");

        this.messageController.create(addMessageDto);

        websocketEventEmitterService.sendTo(
                addMessageDto.getRequestedEmployeeId(),
                "/message",
                addMessageDto
                );
    }

    /*
    @MessageMapping("/message/get/{id}")
    @SendTo("/message/get/{id}")
    public ResponseEntity<GetMessageDto> getMessageById(Long messageId) {
        System.out.println("create");
        return this.messageController.getById(messageId);
    }
    */

    @MessageMapping("/message/getall/channel/{id}")
    @SendTo("/message/getall/channel/{id}")
    public ResponseEntity<List<GetMessageDto>> getAllMessagesByChannelId(Long channelId) {
        System.out.println("getALlByChId");
        return this.messageController.getAllByChannel(channelId);
    }

    @MessageMapping("/message/getall")
    @SendTo("/message/getall")
    public ResponseEntity<List<GetMessageDto>> getAllMessages() {
        System.out.println("getAll");
        return this.messageController.getAll();
    }

    @MessageMapping("/message/update/{id}")
    @SendTo("/message/update/{id}")
    public ResponseEntity<GetMessageDto> updateMessage(Long messageId, Map<String, Object> fieldsToPatch) {
        System.out.println("update");
        return this.messageController.update(messageId, fieldsToPatch);
    }

    @MessageMapping("/message/delete/{id}")
    @SendTo("/message/delete/{id}")
    public ResponseEntity<String> deleteMessage(Long messageId) {
        System.out.println("delete");
        return this.messageController.deleteById(messageId);
    }
}
