package de.szut.shift_backend.controller;

import de.szut.shift_backend.model.Employee;
import de.szut.shift_backend.model.GetShiftTradeRequest;
import de.szut.shift_backend.model.dto.*;
import de.szut.shift_backend.services.MappingService;
import de.szut.shift_backend.services.ShiftTradeRequestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/request/shifttrade")
public class ShiftTradeRequestController {
    private final MappingService mappingService;
    private final ShiftTradeRequestService shiftTradeRequestService;

    public ShiftTradeRequestController(MappingService mappingService, ShiftTradeRequestService shiftTradeRequestService) {
        this.mappingService = mappingService;
        this.shiftTradeRequestService = shiftTradeRequestService;
    }

    @Operation(summary = "creates a shift trade request")
    @ApiResponses(value = {
            @ApiResponse(responseCode =  "200", description = "shift trade request was created"),
            @ApiResponse(responseCode =  "400", description = "shift trade request is malformed", content = @Content),
            @ApiResponse(responseCode =  "401", description = "not authorized", content = @Content),
    })
    @PostMapping
    public ResponseEntity<GetShiftTradeRequestDto> createShiftTradeRequest(@Valid @RequestBody final AddShiftTradeRequestDto requestDto)
    {
        GetShiftTradeRequest request = this.mappingService.mapAddShiftTradeRequestDtoToShiftTradeRequest(requestDto);
        GetShiftTradeRequest savedRequest = this.shiftTradeRequestService.create(request);

        final GetShiftTradeRequestDto requestConfirmation = this.mappingService.mapShiftTradeRequestToGetShiftTradeRequestDto(savedRequest);
        return new ResponseEntity<>(requestConfirmation, HttpStatus.OK);
    }

    @Operation(summary = "answer a shift trade request")
    @ApiResponses(value = {
            @ApiResponse(responseCode =  "200", description = "shift trade was successfully answered"),
            @ApiResponse(responseCode =  "400", description = "answer is Null or requestId is invalid", content = @Content),
            @ApiResponse(responseCode =  "401", description = "not authorized", content = @Content),
    })
    @PostMapping("/{id}")
    public ResponseEntity<List<GetShiftTradeRequestDto>> answerShiftTradeRequest(@Valid @PathVariable("id") final Long requestId,
                                                                                 @Valid @RequestBody final RequestAnswerDto answerDto)
    {
        GetShiftTradeRequest request = this.shiftTradeRequestService.getShiftTradeRequestByRequestId(requestId);
        this.shiftTradeRequestService.updateShiftTradeRequests(request, answerDto.isAccepted());

        List<GetShiftTradeRequest> updatedRequestList = this.shiftTradeRequestService.getShiftTradeRequestsForRespondingEmployee(request.getReplyingEmployee().getId());
        List<GetShiftTradeRequestDto> updatedRequestDtoList = new ArrayList<>();

        for (GetShiftTradeRequest req : updatedRequestList){
            updatedRequestDtoList.add(this.mappingService.mapShiftTradeRequestToGetShiftTradeRequestDto(req));
        }

        return new ResponseEntity<>(updatedRequestDtoList, HttpStatus.OK);
    }

    @Operation(summary = "get all shift trade requests for a given responding Employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode =  "200", description = "all shift trades have been returned"),
            @ApiResponse(responseCode =  "400", description = "employeeId is invalid", content = @Content),
            @ApiResponse(responseCode =  "401", description = "not authorized", content = @Content),
    })
    @GetMapping("/respondent/{id}")
    public ResponseEntity<List<GetShiftTradeRequestDto>> getShiftTradeRequestsForRespondingEmployee(@Valid @PathVariable("id") final Long employeeId )
    {
        List<GetShiftTradeRequest> requestList = this.shiftTradeRequestService.getShiftTradeRequestsForRespondingEmployee(employeeId);
        List<GetShiftTradeRequestDto> resultList = new ArrayList<>();

        for(GetShiftTradeRequest request : requestList){
            resultList.add(this.mappingService.mapShiftTradeRequestToGetShiftTradeRequestDto(request));
        }

        return new ResponseEntity<>(resultList, HttpStatus.OK);
    }

    @Operation(summary = "get all shift trade requests for a given requesting Employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode =  "200", description = "all shift trades have been returned"),
            @ApiResponse(responseCode =  "400", description = "employeeId is invalid", content = @Content),
            @ApiResponse(responseCode =  "401", description = "not authorized", content = @Content),
    })
    @GetMapping("/requestor/{id}")
    public ResponseEntity<List<GetShiftTradeRequestDto>> getShiftTradeRequestsForRequestingEmployee(@Valid @PathVariable("id") final Long employeeId )
    {
        List<GetShiftTradeRequest> requestList = this.shiftTradeRequestService.getShiftTradeRequestsForRequestingEmployee(employeeId);
        List<GetShiftTradeRequestDto> resultList = new ArrayList<>();

        for(GetShiftTradeRequest request : requestList){
            resultList.add(this.mappingService.mapShiftTradeRequestToGetShiftTradeRequestDto(request));
        }

        return new ResponseEntity<>(resultList, HttpStatus.OK);
    }
}
