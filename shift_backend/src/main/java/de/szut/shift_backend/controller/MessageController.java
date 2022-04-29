package de.szut.shift_backend.controller;

import de.szut.shift_backend.model.Employee;
import de.szut.shift_backend.model.dto.AddEmployeeDto;
import de.szut.shift_backend.model.dto.GetEmployeeDto;
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
    public ResponseEntity<xxx> create(@Valid @RequestBody final Dto dto)
    {

        return new ResponseEntity<>(request, HttpStatus.OK);
    }

    @Operation(summary = "get Message by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "xxx was created"),
            @ApiResponse(responseCode = "400", description = "xxx parameter is null", content = @Content),
            @ApiResponse(responseCode = "401", description = "not authorized", content = @Content),
    })
    @PostMapping
    public ResponseEntity<xxx> getById(@Valid @PathVariable("id") final Long messageId)
    {

        return new ResponseEntity<>(request, HttpStatus.OK);
    }

    @Operation(summary = "get Messages by Channel")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "xxx was created"),
            @ApiResponse(responseCode = "400", description = "xxx parameter is null", content = @Content),
            @ApiResponse(responseCode = "401", description = "not authorized", content = @Content),
    })
    @PostMapping
    public ResponseEntity<xxx> getByChannel(@Valid @PathVariable("id") final Long channelId)
    {

        return new ResponseEntity<>(request, HttpStatus.OK);
    }

    @Operation(summary = "gets all Messages")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "xxx was created"),
            @ApiResponse(responseCode = "400", description = "xxx parameter is null", content = @Content),
            @ApiResponse(responseCode = "401", description = "not authorized", content = @Content),
    })
    @PostMapping
    public ResponseEntity<xxx> getAll()
    {

        return new ResponseEntity<>(request, HttpStatus.OK);
    }

    @Operation(summary = "update Message")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "xxx was created"),
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
            @ApiResponse(responseCode = "200", description = "xxx was created"),
            @ApiResponse(responseCode = "400", description = "xxx parameter is null", content = @Content),
            @ApiResponse(responseCode = "401", description = "not authorized", content = @Content),
    })
    @PostMapping
    public ResponseEntity<xxx> delete()
    {

        return new ResponseEntity<>(request, HttpStatus.OK);
    }
}
