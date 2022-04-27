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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/shiftplan")
public class ShiftPlanController {
    private final MappingService mappingService;
    private final ShiftPlanService shiftPlanService;

    public ShiftPlanController(MappingService mappingService, ShiftPlanService shiftPlanService) {
        this.mappingService = mappingService;
        this.shiftPlanService = shiftPlanService;
    }

    @Operation(summary = "creates Employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode =  "200", description = "employee was created"),
            @ApiResponse(responseCode =  "400", description = "employee parameter is null", content = @Content),
            @ApiResponse(responseCode =  "401", description = "not authorized", content = @Content),
    })
    @PostMapping
    public ResponseEntity<GetShiftPlanDto> createDepartment(@Valid @RequestBody final AddShiftPlanDto shiftPlanDto)
    {
        ShiftPlan shiftPlan = this.mappingService.mapAddShiftPlanDtoToShiftPlan(shiftPlanDto);

        shiftPlan = this.shiftPlanService.generateShiftPlan(shiftPlan);
        shiftPlan = this.shiftPlanService.create(shiftPlan);

        final GetShiftPlanDto request = this.mappingService.mapShiftPlanToGetShiftPlanDto(shiftPlan);

        return new ResponseEntity<>(request, HttpStatus.OK);
    }

}
