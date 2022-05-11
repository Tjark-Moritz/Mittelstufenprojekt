package de.szut.shift_backend.controller;

import de.szut.shift_backend.model.Message;
import de.szut.shift_backend.model.dto.AddMessageDto;
import de.szut.shift_backend.model.dto.GetMessageDto;
import de.szut.shift_backend.services.MappingService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MessageHandlingController {

    private final MappingService mappingService;

    public MessageHandlingController(MappingService mappingService) {
        this.mappingService = mappingService;
    }

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public GetMessageDto send(AddMessageDto addMessageDto) throws Exception {
        Message message = this.mappingService.mapAddMessageDtoToMessage(addMessageDto);
        return this.mappingService.mapMessageToGetMessageDto(message);
    }
}
