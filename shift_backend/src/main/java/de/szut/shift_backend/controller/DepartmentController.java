package de.szut.shift_backend.controller;

import de.szut.shift_backend.model.Department;
import de.szut.shift_backend.model.ShiftType;
import de.szut.shift_backend.model.dto.AddDepartmentDto;
import de.szut.shift_backend.model.dto.GetDepartmentDto;
import de.szut.shift_backend.model.dto.GetShiftTypeDto;
import de.szut.shift_backend.services.DepartmentService;
import de.szut.shift_backend.services.MappingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/department")
public class DepartmentController {
    private final MappingService mappingService;
    private final DepartmentService departmentService;

    public DepartmentController(MappingService mappingService, DepartmentService departmentService) {
        this.mappingService = mappingService;
        this.departmentService = departmentService;
    }

    @Operation(summary = "creates department")
    @ApiResponses(value = {
            @ApiResponse(responseCode =  "200", description = "department was created"),
            @ApiResponse(responseCode =  "400", description = "department parameter is null", content = @Content),
            @ApiResponse(responseCode =  "401", description = "not authorized", content = @Content),
    })
    @PostMapping
    public ResponseEntity<GetDepartmentDto> createDepartment(@RequestHeader Map<String,String> headers,
                                                             @Valid @RequestBody final AddDepartmentDto departmentDto)
    {
        Department dept = this.mappingService.mapAddDepartmentDtoToDepartment(departmentDto);
        Department savedDept = this.departmentService.create(dept);

        final GetDepartmentDto request = this.mappingService.mapDepartmentToGetDepartmentDto(savedDept);
        return new ResponseEntity<>(request, HttpStatus.OK);
    }

    @Operation(summary = "gets all departments")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "get all departments successfully"),
            @ApiResponse(responseCode = "400", description = "get all departments failed", content = @Content),
            @ApiResponse(responseCode = "401", description = "not authorized", content = @Content),
    })
    @GetMapping
    public ResponseEntity<List<GetDepartmentDto>> getAll() {
        List<Department> departmentList = this.departmentService.getAllDepartments();
        List<GetDepartmentDto> departmentDtoList = new LinkedList<>();

        for (Department department : departmentList) {
            departmentDtoList.add(this.mappingService.mapDepartmentToGetDepartmentDto(department));
        }

        return new ResponseEntity<>(departmentDtoList, HttpStatus.OK);
    }

    @Operation(summary = "gets all shifttypes for department")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "get all shifttypes successful"),
            @ApiResponse(responseCode = "400", description = "get all shifttypes failed", content = @Content),
            @ApiResponse(responseCode = "401", description = "not authorized", content = @Content),
    })
    @GetMapping("/{id}/shifttypes")
    public ResponseEntity<List<GetShiftTypeDto>> getAllShiftTypes(@Valid @PathVariable("id") final Long departmentId) {
        Department dept = this.departmentService.getDepartmentById(departmentId);
        List<GetShiftTypeDto> stDtoList = new ArrayList<>();

        for (ShiftType st : dept.getShiftTypes()) {
            stDtoList.add(this.mappingService.mapShiftTypeToGetShiftTypeDto(st));
        }

        return new ResponseEntity<>(stDtoList, HttpStatus.OK);
    }

    @Operation(summary = "update department")
    @ApiResponses(value = {
            @ApiResponse(responseCode =  "200", description = "department was updated"),
            @ApiResponse(responseCode =  "400", description = "department parameter is null", content = @Content),
            @ApiResponse(responseCode =  "401", description = "not authorized", content = @Content),
    })
    @PatchMapping("/{id}")
    public ResponseEntity<GetDepartmentDto> updateDepartment(@Valid @RequestBody final Map<String,Object> fieldsToPatch,
                                                             @Valid @PathVariable("id") final Long departmentId)
    {
        Department updatedDept = this.departmentService.update(departmentId, fieldsToPatch);
        final GetDepartmentDto request = this.mappingService.mapDepartmentToGetDepartmentDto(updatedDept);
        return new ResponseEntity<>(request, HttpStatus.OK);
    }

    @Operation(summary = "delete department")
    @ApiResponses(value = {
            @ApiResponse(responseCode =  "200", description = "department was deleted"),
            @ApiResponse(responseCode =  "400", description = "department parameter is null", content = @Content),
            @ApiResponse(responseCode =  "401", description = "not authorized", content = @Content),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<GetDepartmentDto> deleteDepartment(@Valid @PathVariable("id") final Long departmentId)
    {
        this.departmentService.delete(departmentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "find department by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode =  "200", description = "department was found"),
            @ApiResponse(responseCode =  "400", description = "department parameter is null", content = @Content),
            @ApiResponse(responseCode =  "401", description = "not authorized", content = @Content),
    })
    @GetMapping("/{id}")
    public ResponseEntity<GetDepartmentDto> findDepartmentById(@Valid @PathVariable("id") final Long departmentId)
    {
        Department department = departmentService.getById(departmentId);

        final GetDepartmentDto request = this.mappingService.mapDepartmentToGetDepartmentDto(department);
        return new ResponseEntity<>(request, HttpStatus.OK);
    }
}
