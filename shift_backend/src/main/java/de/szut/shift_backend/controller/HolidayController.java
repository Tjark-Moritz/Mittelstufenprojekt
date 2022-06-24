package de.szut.shift_backend.controller;

import de.szut.shift_backend.model.Employee;
import de.szut.shift_backend.model.Holiday;
import de.szut.shift_backend.model.dto.AddHolidayDto;
import de.szut.shift_backend.model.dto.GetEmployeeDto;
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
@RequestMapping("/requests/holiday")
public class HolidayController {
    private final MappingService mappingService;
    private final HolidayService holidayService;

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
    public ResponseEntity<GetHolidayDto> create(@RequestHeader Map<String, String> headers,
                                                @Valid @RequestBody final AddHolidayDto addHolidayDto) {
        Holiday holiday = this.mappingService.mapAddHolidayDtoToHoliday(addHolidayDto);

        final GetHolidayDto request = this.mappingService.mapHolidayToGetHolidayDto(this.holidayService.create(holiday));
        return new ResponseEntity<>(request, HttpStatus.OK);
    }

    @Operation(summary = "updates holiday")
    @ApiResponses(value = {
            @ApiResponse(responseCode =  "200", description = "holiday was updated"),
            @ApiResponse(responseCode =  "400", description = "holiday parameter is null", content = @Content),
            @ApiResponse(responseCode =  "401", description = "not authorized", content = @Content),
    })
    @PatchMapping("/{id}")
    public ResponseEntity<GetHolidayDto> updateEmployee(@Valid @PathVariable("id") final Long holidayId,
                                                         @Valid @RequestBody final Map<String,Object> fieldsToPatch) {

        Holiday holUpdate = this.holidayService.update(holidayId, fieldsToPatch);

        GetHolidayDto holUpdateDto = this.mappingService.mapHolidayToGetHolidayDto(holUpdate);

        return new ResponseEntity<>(holUpdateDto, HttpStatus.OK);
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

    @Operation(summary = "delete holidays")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "deleted holiday successfully"),
            @ApiResponse(responseCode = "400", description = "delete holiday failed", content = @Content),
            @ApiResponse(responseCode = "401", description = "not authorized", content = @Content),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@Valid @PathVariable("id") final long id) {

        this.holidayService.delete(id);

        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @Operation(summary = "answer holiday request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "answered holiday request successfully"),
            @ApiResponse(responseCode = "400", description = "answering holiday request failed", content = @Content),
            @ApiResponse(responseCode = "401", description = "not authorized", content = @Content),
    })

    @PostMapping("{id}/answer")
    public ResponseEntity<GetHolidayDto> answerHolidayRequest(@PathVariable("id") Long id,
                                                              @RequestParam Holiday.HolidayStatus holidayStatus
    ) {
        GetHolidayDto ans = this.mappingService.mapHolidayToGetHolidayDto(this.holidayService.answer(id, holidayStatus));

        return new ResponseEntity<>(ans, HttpStatus.OK);
    }

    @Operation(summary = "gets holiday requests by status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "get holiday request by status successfully"),
            @ApiResponse(responseCode = "400", description = "get holiday by status request failed", content = @Content),
            @ApiResponse(responseCode = "401", description = "not authorized", content = @Content),
    })
    @GetMapping("/deptartment/{id}")
    public ResponseEntity<List<GetHolidayDto>> getHolidayRequestsByStatus(@PathVariable("id") Long departmentId,
                                                                          @RequestParam Holiday.HolidayStatus holidayStatus
    ) {
        List<Holiday> holidayList = holidayService.getHolidayRequestsByStatusByDeptId(departmentId, holidayStatus);
        List<GetHolidayDto> resultList = new LinkedList<>();

        for (Holiday holiday : holidayList) {
            resultList.add(this.mappingService.mapHolidayToGetHolidayDto(holiday));
        }

        return ResponseEntity.ok(resultList);
    }
}
