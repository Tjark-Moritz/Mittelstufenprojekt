package de.szut.shift_backend.controller;

import de.szut.shift_backend.model.Holiday;
import de.szut.shift_backend.model.dto.AddHolidayDto;
import de.szut.shift_backend.model.dto.GetHolidayDto;
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
import java.util.LinkedList;
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
            @ApiResponse(responseCode = "200", description = "holiday was created"),
            @ApiResponse(responseCode = "400", description = "holiday parameter is null", content = @Content),
            @ApiResponse(responseCode = "401", description = "not authorized", content = @Content),
    })
    @PostMapping
    public ResponseEntity<AddHolidayDto> create(@RequestHeader Map<String, String> headers,
                                                @Valid @RequestBody final AddHolidayDto addHolidayDto) {
        Holiday holiday= this.mappingService.mapAddHolidayDtoToHoliday(addHolidayDto);
        this.holidayService.create(holiday);
        final AddHolidayDto request = this.mappingService.mapHolidayToAddHolidayDto(holiday);
        return new ResponseEntity<>(request, HttpStatus.OK);
    }

    @Operation(summary = "gets all holidays")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "get all holidays successfully"),
            @ApiResponse(responseCode = "400", description = "get all holidays failed", content = @Content),
            @ApiResponse(responseCode = "401", description = "not authorized", content = @Content),
    })
    @GetMapping
    public ResponseEntity<List<GetHolidayDto>> getAll() {
        List<Holiday> holidayList = this.holidayService.getAllHolidays();
        List<GetHolidayDto> holidayDtoList = new LinkedList<>();

        for (Holiday holiday : holidayList) {
            holidayDtoList.add(this.mappingService.mapHolidayToGetHolidayDto(holiday));
        }

        return new ResponseEntity<>(holidayDtoList, HttpStatus.OK);
    }

    @Operation(summary = "delete holiday request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "deleted holiday successfully"),
            @ApiResponse(responseCode = "400", description = "delete holiday failed", content = @Content),
            @ApiResponse(responseCode = "401", description = "not authorized", content = @Content),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@RequestHeader Map<String, String> headers,
                                             @Valid @RequestBody final long id) {
        this.holidayService.delete(id);
        return new ResponseEntity<>("", HttpStatus.OK);
    }


}
