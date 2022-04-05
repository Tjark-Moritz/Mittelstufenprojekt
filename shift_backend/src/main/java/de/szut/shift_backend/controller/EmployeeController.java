package de.szut.shift_backend.controller;

import de.szut.shift_backend.exceptionHandling.ResourceNotFoundException;
import de.szut.shift_backend.model.Employee;
import de.szut.shift_backend.model.dto.AddEmployeeDto;
import de.szut.shift_backend.model.dto.GetEmployeeDto;
import de.szut.shift_backend.services.EmployeeService;
import de.szut.shift_backend.services.MappingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private final MappingService mappingService;
    private final EmployeeService employeeService;

    public EmployeeController(MappingService mappingService, EmployeeService employeeService) {
        this.mappingService = mappingService;
        this.employeeService = employeeService;
    }

    @Operation(summary = "creates Employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode =  "200", description = "employee was created"),
            @ApiResponse(responseCode =  "400", description = "employee parameter is null", content = @Content),
            @ApiResponse(responseCode =  "401", description = "not authorized", content = @Content),
    })
    @PostMapping
    public ResponseEntity<GetEmployeeDto> createDepartment(@RequestHeader Map<String,String> headers,
                                                          @Valid @RequestBody final AddEmployeeDto employeeDto)
    {
        Employee emp = this.mappingService.mapAddEmployeeDtoToEmployee(employeeDto);
        this.employeeService.create(emp);
        final GetEmployeeDto request = this.mappingService.mapEmployeeToGetEmployeeDto(emp);
        return new ResponseEntity<>(request, HttpStatus.OK);
    }

    @Operation(summary = "get all Employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode =  "200", description = "employee was created"),
            @ApiResponse(responseCode =  "400", description = "employee parameter is null", content = @Content),
            @ApiResponse(responseCode =  "401", description = "not authorized", content = @Content),
    })
    @GetMapping
    public ResponseEntity<List<GetEmployeeDto>> getAllEmployees(@RequestHeader Map<String,String> headers)
    {
        List<Employee> empList = this.employeeService.getAllEmployees();
        List<GetEmployeeDto> resultList = new LinkedList<>();

        for(Employee emp : empList){
            resultList.add(this.mappingService.mapEmployeeToGetEmployeeDto(emp));
        }

        return new ResponseEntity<>(resultList, HttpStatus.OK);
    }

    @Operation(summary = "get Employee by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode =  "200", description = "employee was created"),
            @ApiResponse(responseCode =  "400", description = "employee parameter is null", content = @Content),
            @ApiResponse(responseCode =  "401", description = "not authorized", content = @Content),
    })
    @GetMapping("/{id}")
    public ResponseEntity<GetEmployeeDto> getEmployeeById( @Valid @PathVariable("id") final Long employeeId )
    {
        Employee emp = this.employeeService.getEmployeeById(employeeId);
        GetEmployeeDto empDto = this.mappingService.mapEmployeeToGetEmployeeDto(emp);

        return new ResponseEntity<>(empDto, HttpStatus.OK);
    }

    @Operation(summary = "deletes Employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode =  "200", description = "employee was created"),
            @ApiResponse(responseCode =  "400", description = "employee parameter is null", content = @Content),
            @ApiResponse(responseCode =  "401", description = "not authorized", content = @Content),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> getAllEmployees(@RequestHeader Map<String,String> headers,
                                                  @Valid @PathVariable("id") final Long employeeId)
    {
        this.employeeService.deleteEmployeeById(employeeId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "updates Employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode =  "200", description = "employee was updated"),
            @ApiResponse(responseCode =  "400", description = "employee parameter is null", content = @Content),
            @ApiResponse(responseCode =  "401", description = "not authorized", content = @Content),
    })
    @PatchMapping("/{id}")
    public ResponseEntity<GetEmployeeDto> updateEmployee(@RequestHeader Map<String,String> headers,
                               @Valid @PathVariable("id") final Long employeeId,
                               @Valid @RequestBody final Map<String,Object> fieldsToPatch) {

            Employee empUpdate = this.employeeService.updateEmployee(employeeId, fieldsToPatch);

            GetEmployeeDto empUpdatedDto = this.mappingService.mapEmployeeToGetEmployeeDto(empUpdate);

            return new ResponseEntity<>(empUpdatedDto, HttpStatus.OK);
    }
}
