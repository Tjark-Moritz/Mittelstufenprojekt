package de.szut.shift_backend.controller;

import de.szut.shift_backend.model.Message;
import de.szut.shift_backend.model.dto.AddMessageDto;
import de.szut.shift_backend.model.dto.GetMessageDto;
import de.szut.shift_backend.services.MappingService;
import de.szut.shift_backend.services.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/message")
public class MessageController {

    private final MappingService mappingService;
    private final MessageService messageService;

    public MessageController(MappingService mappingService, MessageService messageService) {
        this.mappingService = mappingService;
        this.messageService = messageService;
    }

    @Operation(summary = "create message")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "message was created"),
            @ApiResponse(responseCode = "400", description = "message parameter is null", content = @Content),
            @ApiResponse(responseCode = "401", description = "not authorized", content = @Content),
    })
    @PostMapping
    public ResponseEntity<GetMessageDto> create(@Valid @RequestBody final AddMessageDto addMessageDto)
    {
        Message message = this.mappingService.mapAddMessageDtoToMessage(addMessageDto);
        this.messageService.create(message);
        final GetMessageDto request = this.mappingService.mapMessageToGetMessageDto(message);
        return new ResponseEntity<>(request, HttpStatus.OK);
    }

    @Operation(summary = "get Message by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successfully got message"),
            @ApiResponse(responseCode = "400", description = "messageId parameter is null", content = @Content),
            @ApiResponse(responseCode = "401", description = "not authorized", content = @Content),
    })
    @GetMapping("/{id}")
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
    @GetMapping("/channel/{id}")
    public ResponseEntity<List<GetMessageDto>> getAllByChannel(@Valid @PathVariable("id") final Long channelId)
    {
        List<Message> messages = this.messageService.getMessagesByChannelId(channelId);
        List<GetMessageDto> messageDtoList = new LinkedList<>();

        for (Message message : messages) {
            messageDtoList.add(this.mappingService.mapMessageToGetMessageDto(message));
        }

        return new ResponseEntity<>(messageDtoList, HttpStatus.OK);
    }

    @Operation(summary = "gets all Messages")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successfully got all messages"),
            @ApiResponse(responseCode = "400", description = "message parameter is null", content = @Content),
            @ApiResponse(responseCode = "401", description = "not authorized", content = @Content),
    })
    @GetMapping
    public ResponseEntity<List<GetMessageDto>> getAll()
    {
        List<Message> messages = this.messageService.getAllMessages();
        List<GetMessageDto> messageDtoList = new LinkedList<>();

        for (Message message : messages) {
            messageDtoList.add(this.mappingService.mapMessageToGetMessageDto(message));
        }

        return new ResponseEntity<>(messageDtoList, HttpStatus.OK);
    }

    @Operation(summary = "update Message")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "updated message successfully"),
            @ApiResponse(responseCode = "400", description = "message parameter is null", content = @Content),
            @ApiResponse(responseCode = "401", description = "not authorized", content = @Content),
    })
    @PatchMapping("/{id}")
    public ResponseEntity<GetMessageDto> update(@Valid @PathVariable("id") final Long messageId,
                                      @Valid @RequestBody final Map<String, Object> fieldsToPatch)
    {
        Message msgUpdate = this.messageService.updateMessage(messageId, fieldsToPatch);

        GetMessageDto getMessageDto = this.mappingService.mapMessageToGetMessageDto(msgUpdate);

        return new ResponseEntity<>(getMessageDto, HttpStatus.OK);
    }

    @Operation(summary = "delete Message")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "deleted message successfully"),
            @ApiResponse(responseCode = "400", description = "messageId parameter is null", content = @Content),
            @ApiResponse(responseCode = "401", description = "not authorized", content = @Content),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@Valid @RequestBody final long messageId)
    {
        this.messageService.delete(messageId);

        return new ResponseEntity<>("", HttpStatus.OK);
    }
}
