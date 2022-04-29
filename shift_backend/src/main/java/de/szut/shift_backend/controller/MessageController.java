package de.szut.shift_backend.controller;

import de.szut.shift_backend.model.Employee;
import de.szut.shift_backend.model.dto.AddEmployeeDto;
import de.szut.shift_backend.model.dto.AddMessageDto;
import de.szut.shift_backend.model.dto.GetEmployeeDto;
import de.szut.shift_backend.model.dto.GetMessageDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/message")
public class MessageController {

    @Operation(summary = "create")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "xxx was created"),
            @ApiResponse(responseCode = "400", description = "xxx parameter is null", content = @Content),
            @ApiResponse(responseCode = "401", description = "not authorized", content = @Content),
    })
    @PostMapping
    public ResponseEntity<GetMessageDto> create(@Valid @RequestBody final AddMessageDto addMessageDto)
    {

        return new ResponseEntity<>(request, HttpStatus.OK);
    }

    @Operation(summary = "get Message by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successfully got message"),
            @ApiResponse(responseCode = "400", description = "xxx parameter is null", content = @Content),
            @ApiResponse(responseCode = "401", description = "not authorized", content = @Content),
    })
    @GetMapping("/{id}")
    public ResponseEntity<xxx> getById(@Valid @PathVariable("id") final Long messageId)
    {

        return new ResponseEntity<>(request, HttpStatus.OK);
    }

    @Operation(summary = "get Messages by Channel")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successfully got messages by channel"),
            @ApiResponse(responseCode = "400", description = "xxx parameter is null", content = @Content),
            @ApiResponse(responseCode = "401", description = "not authorized", content = @Content),
    })
    @GetMapping("/channel/{id}")
    public ResponseEntity<Object> getByChannel(@Valid @PathVariable("id") final Long channelId)
    {

        return new ResponseEntity<>(request, HttpStatus.OK);
    }

    @Operation(summary = "gets all Messages")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successfully got all messages"),
            @ApiResponse(responseCode = "400", description = "xxx parameter is null", content = @Content),
            @ApiResponse(responseCode = "401", description = "not authorized", content = @Content),
    })
    @GetMapping
    public ResponseEntity<xxx> getAll()
    {

        return new ResponseEntity<>(request, HttpStatus.OK);
    }

    @Operation(summary = "update Message")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "updated message successfully"),
            @ApiResponse(responseCode = "400", description = "xxx parameter is null", content = @Content),
            @ApiResponse(responseCode = "401", description = "not authorized", content = @Content),
    })
    @PostMapping
    public ResponseEntity<xxx> update(@Valid @PathVariable("id") final Long messageId,
                                      @Valid @RequestBody final Map<String, Object> fieldsToPatch)
    {

        return new ResponseEntity<>(request, HttpStatus.OK);
    }

    @Operation(summary = "delete Message")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "deleted message successfully"),
            @ApiResponse(responseCode = "400", description = "xxx parameter is null", content = @Content),
            @ApiResponse(responseCode = "401", description = "not authorized", content = @Content),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<xxx> deleteById()
    {

        return new ResponseEntity<>(request, HttpStatus.OK);
    }
}
