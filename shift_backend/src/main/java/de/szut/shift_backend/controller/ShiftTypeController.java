package de.szut.shift_backend.controller;

import de.szut.shift_backend.model.Department;
import de.szut.shift_backend.model.ShiftType;
import de.szut.shift_backend.model.dto.AddShiftTypeDto;
import de.szut.shift_backend.model.dto.GetShiftTypeDto;
import de.szut.shift_backend.services.DepartmentService;
import de.szut.shift_backend.services.MappingService;
import de.szut.shift_backend.services.ShiftTypeService;
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
import java.util.Map;

@RestController
@RequestMapping("/shifttype")
public class ShiftTypeController {
    private final MappingService mappingService;
    private final ShiftTypeService shiftTypeService;
    private final DepartmentService departmentService;

    public ShiftTypeController(MappingService mappingService, ShiftTypeService shiftTypeService, DepartmentService departmentService) {
        this.mappingService = mappingService;
        this.shiftTypeService = shiftTypeService;
        this.departmentService = departmentService;
    }

    @Operation(summary = "gets all ShiftTypes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "get all ShiftType successfully"),
            @ApiResponse(responseCode = "400", description = "get all ShiftType failed", content = @Content),
            @ApiResponse(responseCode = "401", description = "not authorized", content = @Content),
    })
    @GetMapping
    public ResponseEntity<List<GetShiftTypeDto>> getAllShiftTypes() {
        List<ShiftType> stList = this.shiftTypeService.getAllShiftTypes();
        List<GetShiftTypeDto> stDtoList = new ArrayList<>();

        for (ShiftType st : stList) {
            stDtoList.add(this.mappingService.mapShiftTypeToGetShiftTypeDto(st));
        }

        return new ResponseEntity<>(stDtoList, HttpStatus.OK);
    }

    @Operation(summary = "creates ShiftType in Department")
    @ApiResponses(value = {
            @ApiResponse(responseCode =  "200", description = "ShiftType was created"),
            @ApiResponse(responseCode =  "400", description = "ShiftType parameter is null", content = @Content),
            @ApiResponse(responseCode =  "401", description = "not authorized", content = @Content),
    })
    @PostMapping("/department/{id}")
    public ResponseEntity<GetShiftTypeDto> createShiftType(@Valid @PathVariable("id") final Long deptId,
                                                         @Valid @RequestBody final AddShiftTypeDto stDto) {
        ShiftType st = this.shiftTypeService.create(this.mappingService.mapAddShiftTypeDtoToShiftType(stDto));
        Department dept = this.departmentService.getDepartmentById(deptId);
        dept.getShiftTypes().add(st);
        this.departmentService.create(dept);

        final GetShiftTypeDto request = this.mappingService.mapShiftTypeToGetShiftTypeDto(st);

        return new ResponseEntity<>(request, HttpStatus.OK);
    }

    @Operation(summary = "updates ShiftType")
    @ApiResponses(value = {
            @ApiResponse(responseCode =  "200", description = "ShiftType was updated"),
            @ApiResponse(responseCode =  "400", description = "ShiftType parameter is null", content = @Content),
            @ApiResponse(responseCode =  "401", description = "not authorized", content = @Content),
    })
    @PatchMapping("/{id}")
    public ResponseEntity<GetShiftTypeDto> updateShiftType(@Valid @PathVariable("id") final Long shifttypeId,
                                                         @Valid @RequestBody final Map<String,Object> fieldsToPatch) {

        ShiftType stUpdate = this.shiftTypeService.updateShiftType(shifttypeId, fieldsToPatch);

        GetShiftTypeDto stUpdatedDto = this.mappingService.mapShiftTypeToGetShiftTypeDto(stUpdate);

        return new ResponseEntity<>(stUpdatedDto, HttpStatus.OK);
    }

    @Operation(summary = "delete shiftType")
    @ApiResponses(value = {
            @ApiResponse(responseCode =  "200", description = "shiftType was deleted"),
            @ApiResponse(responseCode =  "400", description = "shiftType parameter is null", content = @Content),
            @ApiResponse(responseCode =  "401", description = "not authorized", content = @Content),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<GetShiftTypeDto> deleteDepartment(@Valid @PathVariable("id") final Long shiftTypeId)
    {
        this.shiftTypeService.delete(shiftTypeId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
