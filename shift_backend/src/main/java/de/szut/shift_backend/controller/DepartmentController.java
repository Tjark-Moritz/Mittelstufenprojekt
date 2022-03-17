package de.szut.shift_backend.controller;

import de.szut.shift_backend.exceptionHandling.ResourceNotFoundException;
import de.szut.shift_backend.model.Department;
import de.szut.shift_backend.model.dto.DepartmentDto;
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
import java.util.Map;

@RestController
@RequestMapping("/department")
public class DepartmentController {
    private final MappingService mappingService; //todo: add logic
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
    public ResponseEntity<DepartmentDto> createDepartment(@RequestHeader Map<String,String> headers,
                                                          @Valid @RequestBody final DepartmentDto departmentDto)
    {
        Department depDto = this.mappingService.mapDepDtoToDep(departmentDto);
        this.departmentService.create(depDto);
        final DepartmentDto request = this.mappingService.mapDepToDepDto(depDto);
        return new ResponseEntity<>(request, HttpStatus.OK);
    }

    @Operation(summary = "update department")
    @ApiResponses(value = {
            @ApiResponse(responseCode =  "200", description = "department was updated"),
            @ApiResponse(responseCode =  "400", description = "department parameter is null", content = @Content),
            @ApiResponse(responseCode =  "401", description = "not authorized", content = @Content),
    })
    @PostMapping("/{id}")
    public ResponseEntity<DepartmentDto> updateDepartment(@RequestHeader Map<String, String> headers,
                                                          @Valid @RequestBody final DepartmentDto departmentDto)
    {
        //todo: add token here

        Department depDto = this.mappingService.mapDepDtoToDep(departmentDto);
        this.departmentService.update(depDto, departmentDto.getDepartmentId());
        final DepartmentDto request = this.mappingService.mapDepToDepDto(depDto);
        return new ResponseEntity<>(request, HttpStatus.OK);
    }

    @Operation(summary = "delete department")
    @ApiResponses(value = {
            @ApiResponse(responseCode =  "200", description = "department was deleted"),
            @ApiResponse(responseCode =  "400", description = "department parameter is null", content = @Content),
            @ApiResponse(responseCode =  "401", description = "not authorized", content = @Content),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<DepartmentDto> deleteDepartment(@RequestHeader Map<String, String> headers,
                                                          @Valid @RequestBody final DepartmentDto departmentDto)
    {
        //todo: add token here

        Department depDto = this.mappingService.mapDepDtoToDep(departmentDto);
        this.departmentService.delete(departmentDto.getDepartmentId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "find department by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode =  "200", description = "department was found"),
            @ApiResponse(responseCode =  "400", description = "department parameter is null", content = @Content),
            @ApiResponse(responseCode =  "401", description = "not authorized", content = @Content),
    })
    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDto> findDepartmentById(@RequestHeader Map<String, String> headers,
                                                            @Valid @RequestBody final DepartmentDto departmentDto)
    {
        //todo: add token here

        Department depDto = this.mappingService.mapDepDtoToDep(departmentDto);
        this.departmentService.getById(departmentDto.getDepartmentId());
        final DepartmentDto request = this.mappingService.mapDepToDepDto(depDto);
        return new ResponseEntity<>(request, HttpStatus.OK);
    }
}
