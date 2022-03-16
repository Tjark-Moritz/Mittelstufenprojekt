package de.szut.shift_backend.controller;

import de.szut.shift_backend.model.dto.DepartmentDto;
import de.szut.shift_backend.services.DepartmentService;
import de.szut.shift_backend.services.MappingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
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
    public ResponseEntity<DepartmentDto> createDepartment(@RequestHeader Map<String,String> headers, @Valid@RequestBody final DepartmentDto departmentDto) {
        //todo: add code here

        return new ResponseEntity<>(new DepartmentDto(), HttpStatus.OK);
    }

    //todo: add update, findById, delete here
}
