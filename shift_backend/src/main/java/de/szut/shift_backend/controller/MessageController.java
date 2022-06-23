package de.szut.shift_backend.controller;

import de.szut.shift_backend.model.Message;
import de.szut.shift_backend.model.dto.GetMessageDto;
import de.szut.shift_backend.services.MappingService;
import de.szut.shift_backend.services.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController {

    private final MappingService mappingService;
    private final MessageService messageService;

    public MessageController(MappingService mappingService, MessageService messageService) {
        this.mappingService = mappingService;
        this.messageService = messageService;
    }

    @Operation(summary = "get Message by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successfully got message"),
            @ApiResponse(responseCode = "400", description = "messageId parameter is null", content = @Content),
            @ApiResponse(responseCode = "401", description = "not authorized", content = @Content),
    })
    @GetMapping("/get/{id}")
    public ResponseEntity<GetMessageDto> getById(@Valid @PathVariable("id") final Long messageId)
    {
        Message message = this.messageService.getMessageById(messageId);
        GetMessageDto getMessageDto = this.mappingService.mapMessageToGetMessageDto(message);

        return new ResponseEntity<>(getMessageDto, HttpStatus.OK);
    }

    @Operation(summary = "get Messages by Channel")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successfully got messages by channel"),
            @ApiResponse(responseCode = "400", description = "channelId parameter is null", content = @Content),
            @ApiResponse(responseCode = "401", description = "not authorized", content = @Content),
    })
    @GetMapping("/getAll/channel/{id}")
    public ResponseEntity<List<GetMessageDto>> getAllByChannel(@Valid @PathVariable("id") final Long channelId)
    {
        List<Message> messages = this.messageService.getMessagesByChannelId(channelId);
        List<GetMessageDto> messageDtoList = new LinkedList<>();

        for (Message message : messages) {
            messageDtoList.add(this.mappingService.mapMessageToGetMessageDto(message));
        }

        return new ResponseEntity<>(messageDtoList, HttpStatus.OK);
    }

    @Operation(summary = "get latest X Messages by Channel")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successfully got latest messages by channel"),
            @ApiResponse(responseCode = "400", description = "channelId parameter is null", content = @Content),
            @ApiResponse(responseCode = "401", description = "not authorized", content = @Content),
    })
    @GetMapping("/getSpecific/channel/{id}")
    public ResponseEntity<List<GetMessageDto>> getXMessagesByChannelId(@Valid @PathVariable("id") Long channelId) {
        List<Message> messages = this.messageService.findXMessagesByChannelId(10, channelId);
        List<GetMessageDto> messageList = new ArrayList<>();

        for (Message message: messages) {
            messageList.add(this.mappingService.mapMessageToGetMessageDto(message));
        }

        return new ResponseEntity<>(messageList, HttpStatus.OK);
    }

    @Operation(summary = "gets all Messages")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successfully got all messages"),
            @ApiResponse(responseCode = "400", description = "message parameter is null", content = @Content),
            @ApiResponse(responseCode = "401", description = "not authorized", content = @Content),
    })
    @GetMapping("/getALl/employee/{id}")
    public ResponseEntity<List<GetMessageDto>> getAllByEmployeeId(@Valid @PathVariable("id") final Long employeeId)
    {
        List<Message> messages = this.messageService.getAllMessagesFromMessageChannel(employeeId);
        List<GetMessageDto> messageDtoList = new LinkedList<>();

        for (Message message : messages) {
            messageDtoList.add(this.mappingService.mapMessageToGetMessageDto(message));
        }

        return new ResponseEntity<>(messageDtoList, HttpStatus.OK);
    }
}
