package de.szut.shift_backend.controller;

import de.szut.shift_backend.model.Holiday;
import de.szut.shift_backend.model.dto.AddHolidayDto;
import de.szut.shift_backend.model.dto.HolidayRequestDto;
import de.szut.shift_backend.services.HolidayService;
import de.szut.shift_backend.services.MappingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/requests")
public class RequestController {
    private final MappingService mappingService; //todo: add logic
    private final HolidayService holidayService; //todo: add logic

    public RequestController(MappingService mappingService, HolidayService holidayService) {
        this.mappingService = mappingService;
        this.holidayService = holidayService;
    }

    @Operation(summary = "gets holiday requests by status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "get holiday request by status successfully"),
            @ApiResponse(responseCode = "400", description = "get holiday by status request failed", content = @Content),
            @ApiResponse(responseCode = "401", description = "not authorized", content = @Content),
    })
    @GetMapping("/holidays/{departmentId}/{holidayStatus}")
    public ResponseEntity<List<HolidayRequestDto>> getHolidayRequestsByStatus(@RequestHeader Map<String, String> headers,
                                                                              @RequestBody final @Valid long departmentId,
                                                                              @RequestParam Holiday.HolidayStatus holidayStatus) {

        List<Holiday> holidayList = holidayService.getHolidayRequestsByStatusByDeptId(departmentId, holidayStatus);
        List<HolidayRequestDto> resultList = new LinkedList<>();

        for (Holiday holiday : holidayList) {
            resultList.add(this.mappingService.mapHolidayToHolidayRequestDto(holiday));
        }

        return new ResponseEntity<>(resultList, HttpStatus.OK);
    }

    @Operation(summary = "update holiday request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "updated holiday successfully"),
            @ApiResponse(responseCode = "400", description = "update holiday failed", content = @Content),
            @ApiResponse(responseCode = "401", description = "not authorized", content = @Content),
    })
    @PatchMapping("/holidays/{id}")
    public ResponseEntity<AddHolidayDto> updateHolidayRequest(@RequestHeader Map<String, String> headers,
                                                              @Valid @RequestBody final AddHolidayDto dto,
                                                              @PathVariable final Long id) {

        Holiday holiday = mappingService.mapAddHolidayDtoToHoliday(dto);

        this.holidayService.update(holiday, id);
        final AddHolidayDto request = this.mappingService.mapHolidayToAddHolidayDto(holiday);

        return new ResponseEntity<>(request, HttpStatus.OK);

        //todo: beim Update muss alte Request gelöscht werden und neue gesendet
        //todo: Zugriffsbeschränkung!
    }


    @Operation(summary = "answer holiday request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "answered holiday request successfully"),
            @ApiResponse(responseCode = "400", description = "answering holiday request failed", content = @Content),
            @ApiResponse(responseCode = "401", description = "not authorized", content = @Content),
    })
    @PatchMapping("/{id}/{status}")
    public ResponseEntity<AddHolidayDto> answerHolidayRequest(@RequestHeader Map<String, String> headers,
                                                              HttpServletRequest httpServletRequest,
                                                              @PathVariable final Long id) {

        String status = httpServletRequest.getParameter("status");
        Holiday holiday = holidayService.setHolidayStatus(id, status);

        final AddHolidayDto request = this.mappingService.mapHolidayToAddHolidayDto(holiday);

        return new ResponseEntity<>(request, HttpStatus.OK);
    }
}
