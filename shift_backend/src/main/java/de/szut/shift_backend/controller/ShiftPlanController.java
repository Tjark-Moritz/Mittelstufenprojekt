package de.szut.shift_backend.controller;

import de.szut.shift_backend.model.Employee;
import de.szut.shift_backend.model.ShiftPlan;
import de.szut.shift_backend.model.dto.AddEmployeeDto;
import de.szut.shift_backend.model.dto.AddShiftPlanDto;
import de.szut.shift_backend.model.dto.GetEmployeeDto;
import de.szut.shift_backend.model.dto.GetShiftPlanDto;
import de.szut.shift_backend.services.MappingService;
import de.szut.shift_backend.services.ShiftPlanService;
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
@RequestMapping("/shiftplan")
public class ShiftPlanController {
    private final MappingService mappingService;
    private final ShiftPlanService shiftPlanService;

    public ShiftPlanController(MappingService mappingService, ShiftPlanService shiftPlanService) {
        this.mappingService = mappingService;
        this.shiftPlanService = shiftPlanService;
    }

    @Operation(summary = "creates a shiftplan")
    @ApiResponses(value = {
            @ApiResponse(responseCode =  "200", description = "shiftplan was created"),
            @ApiResponse(responseCode =  "400", description = "shiftplan parameter is null", content = @Content),
            @ApiResponse(responseCode =  "401", description = "not authorized", content = @Content),
    })
    @PostMapping
    public ResponseEntity<GetShiftPlanDto> createShiftPlan(@Valid @RequestBody final AddShiftPlanDto shiftPlanDto)
    {
        ShiftPlan shiftPlan = this.mappingService.mapAddShiftPlanDtoToShiftPlan(shiftPlanDto);

        shiftPlan = this.shiftPlanService.generateShiftPlan(shiftPlan);
        shiftPlan = this.shiftPlanService.create(shiftPlan);

        final GetShiftPlanDto request = this.mappingService.mapShiftPlanToGetShiftPlanDto(shiftPlan);

        return new ResponseEntity<>(request, HttpStatus.OK);
    }

    @Operation(summary = "get shiftplan by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode =  "200", description = "Shiftplan was found"),
            @ApiResponse(responseCode =  "400", description = "Request is malformed", content = @Content),
            @ApiResponse(responseCode =  "401", description = "not authorized", content = @Content),
    })
    @GetMapping("/{id}")
    public ResponseEntity<GetShiftPlanDto> getShiftPlan(@Valid @PathVariable("id") final Long shiftPlanId)
    {
        ShiftPlan splan = this.shiftPlanService.getShiftPlanById(shiftPlanId);
        final GetShiftPlanDto request = this.mappingService.mapShiftPlanToGetShiftPlanDto(splan);

        return new ResponseEntity<>(request, HttpStatus.OK);
    }

    @Operation(summary = "get all shiftplans for given department id")
    @ApiResponses(value = {
            @ApiResponse(responseCode =  "200", description = "Shiftplans for department were found"),
            @ApiResponse(responseCode =  "400", description = "Request is malformed", content = @Content),
            @ApiResponse(responseCode =  "401", description = "not authorized", content = @Content),
    })
    @GetMapping("/department/{id}")
    public ResponseEntity<List<GetShiftPlanDto>> getShiftPlansByDeptId(@Valid @PathVariable("id") final Long deptId)
    {
        List<ShiftPlan> shiftPlans = this.shiftPlanService.getShiftPlansByDeptId(deptId);

        List<GetShiftPlanDto> shiftPlansDto = new ArrayList<>();

        for (ShiftPlan shiftPlan : shiftPlans) {
            shiftPlansDto.add(this.mappingService.mapShiftPlanToGetShiftPlanDto(shiftPlan));
        }

        return new ResponseEntity<>(shiftPlansDto, HttpStatus.OK);
    }

    @Operation(summary = "deletes a Shiftplan ")
    @ApiResponses(value = {
            @ApiResponse(responseCode =  "200", description = "Shiftplan with given ID was deleted"),
            @ApiResponse(responseCode =  "400", description = "Request is malformed", content = @Content),
            @ApiResponse(responseCode =  "401", description = "not authorized", content = @Content),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteShifplanById(@Valid @PathVariable("id") final Long shiftplanId)
    {
        this.shiftPlanService.deleteShiftplanById(shiftplanId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
