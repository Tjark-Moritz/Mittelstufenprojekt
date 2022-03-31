package de.szut.shift_backend.controller;

import de.szut.shift_backend.model.Department;
import de.szut.shift_backend.model.Holiday;
import de.szut.shift_backend.model.dto.AddHolidayDto;
import de.szut.shift_backend.model.dto.DepartmentDto;
import de.szut.shift_backend.model.dto.GetHolidayDto;
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

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/holiday")
public class HolidayController {
    private final MappingService mappingService; //todo: add logic
    private final HolidayService holidayService; //todo: add logic

    public HolidayController(MappingService mappingService, HolidayService holidayService) {
        this.mappingService = mappingService;
        this.holidayService = holidayService;
    }

    @Operation(summary = "creates holiday")
    @ApiResponses(value = {
            @ApiResponse(responseCode =  "200", description = "holiday was created"),
            @ApiResponse(responseCode =  "400", description = "holiday parameter is null", content = @Content),
            @ApiResponse(responseCode =  "401", description = "not authorized", content = @Content),
    })
    @PostMapping
    public ResponseEntity<AddHolidayDto> createHoliday(@RequestHeader Map<String,String> headers,
                                                          @Valid @RequestBody final AddHolidayDto addHolidayDto)
    {
        Holiday holidayDto = this.mappingService.mapAddHolidayDtoToHoliday(addHolidayDto);
        this.holidayService.create(holidayDto);
        final AddHolidayDto request = this.mappingService.mapHolidayToAddHolidayDto(holidayDto);
        return new ResponseEntity<>(request, HttpStatus.OK);
    }

    @Operation(summary = "gets unanswered holiday requests")
    @ApiResponses(value = {
            @ApiResponse(responseCode =  "200", description = "get holiday request successfully"),
            @ApiResponse(responseCode =  "400", description = "get holiday request failed", content = @Content),
            @ApiResponse(responseCode =  "401", description = "not authorized", content = @Content),
    })
    @GetMapping
    public ResponseEntity<> getUnansweredHolidayRequests(@RequestHeader Map<String,String> headers,
                                                                      @Valid @RequestBody final long departmentId) {

        List<Holiday> holidayList = holidayService.getHolidayRequestsByStatusByDeptId(departmentId);
        mappingService.mapHolidaylistToHolidayRequestDto(holidayList);



            Holiday holidayDto = this.mappingService.mapHolidayRequestDtoToHoliday(holidayRequestDto);
            this.holidayService.create(holidayDto);
            final AddHolidayDto request = this.mappingService.mapHolidayToAddHolidayDto(holidayDto);
            return new ResponseEntity<AddHolidayDto>(request, HttpStatus.OK);
    }
}
