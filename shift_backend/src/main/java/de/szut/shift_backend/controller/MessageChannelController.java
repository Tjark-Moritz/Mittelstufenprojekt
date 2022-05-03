package de.szut.shift_backend.controller;

import de.szut.shift_backend.model.Message;
import de.szut.shift_backend.model.MessageChannel;
import de.szut.shift_backend.model.dto.AddMessageChannelDto;
import de.szut.shift_backend.model.dto.GetMessageChannelDto;
import de.szut.shift_backend.model.dto.GetMessageDto;
import de.szut.shift_backend.services.MappingService;
import de.szut.shift_backend.services.MessageChannelService;
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
@RequestMapping("/channel")
public class MessageChannelController {

    private MappingService mappingService;
    private MessageChannelService messageChannelService;

    public MessageChannelController(MappingService mappingService, MessageChannelService messageChannelService) {
        this.mappingService = mappingService;
        this.messageChannelService = messageChannelService;
    }

    @Operation(summary = "create message-channel")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "message-channel was created"),
            @ApiResponse(responseCode = "400", description = "message-channel parameter is null", content = @Content),
            @ApiResponse(responseCode = "401", description = "not authorized", content = @Content),
    })
    @PostMapping
    public ResponseEntity<GetMessageChannelDto> create(@Valid @RequestBody final AddMessageChannelDto addMessageChannelDto)
    {
        MessageChannel messageChannel = this.mappingService.mapAddMessageChannelDtoToMessageChannel(addMessageChannelDto);
        this.messageChannelService.create(messageChannel);
        final GetMessageChannelDto request = this.mappingService.mapMessageChannelToGetMessageChannelDto(messageChannel);
        return new ResponseEntity<>(request, HttpStatus.OK);
    }

    @Operation(summary = "get message-channel by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successfully got message-channel"),
            @ApiResponse(responseCode = "400", description = "message-channel-id parameter is null", content = @Content),
            @ApiResponse(responseCode = "401", description = "not authorized", content = @Content),
    })
    @GetMapping("/{id}")
    public ResponseEntity<GetMessageChannelDto> getById(@Valid @PathVariable("id") final Long messageChannelId)
    {
        MessageChannel messageChannel = this.messageChannelService.getMessageChannelById(messageChannelId);
         GetMessageChannelDto getMessageChannelDto =
                 this.mappingService.mapMessageChannelToGetMessageChannelDto(messageChannel);

         return new ResponseEntity<>(getMessageChannelDto, HttpStatus.OK);
    }

    @Operation(summary = "gets all message-channels")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successfully got all message-channels"),
            @ApiResponse(responseCode = "400", description = "message-channel parameter is null", content = @Content),
            @ApiResponse(responseCode = "401", description = "not authorized", content = @Content),
    })
    @GetMapping
    public ResponseEntity<List<GetMessageChannelDto>> getAll()
    {
        List<MessageChannel> messageChannels = this.messageChannelService.getAll();
        List<GetMessageChannelDto> getMessageChannelDto = new LinkedList<>();

        for (MessageChannel messageChannel : messageChannels) {
            getMessageChannelDto.add(this.mappingService.mapMessageChannelToGetMessageChannelDto(messageChannel));
        }

        return new ResponseEntity<>(getMessageChannelDto, HttpStatus.OK);
    }

    @Operation(summary = "update message-channel")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "updated message-channel successfully"),
            @ApiResponse(responseCode = "400", description = "message-channel parameter is null", content = @Content),
            @ApiResponse(responseCode = "401", description = "not authorized", content = @Content),
    })
    @PatchMapping("/{id}")
    public ResponseEntity<GetMessageChannelDto> update(@Valid @PathVariable("id") final Long messageChannelId,
                                                @Valid @RequestBody final Map<String, Object> fieldsToPatch)
    {
        MessageChannel messageChannel = this.messageChannelService.update(messageChannelId, fieldsToPatch);

        GetMessageChannelDto getMessageChannelDto =
                this.mappingService.mapMessageChannelToGetMessageChannelDto(messageChannel);

        return new ResponseEntity<>(getMessageChannelDto, HttpStatus.OK);
    }

    @Operation(summary = "delete message-channel")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "deleted message-channel successfully"),
            @ApiResponse(responseCode = "400", description = "message-channel parameter is null", content = @Content),
            @ApiResponse(responseCode = "401", description = "not authorized", content = @Content),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@Valid @RequestBody final long messageId)
    {
        this.messageChannelService.delete(messageId);

        return new ResponseEntity<>("", HttpStatus.OK);
    }
}
