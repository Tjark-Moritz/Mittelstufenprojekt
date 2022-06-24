package de.szut.shift_backend.controller;

import de.szut.shift_backend.exceptionHandling.ResourceNotFoundException;
import de.szut.shift_backend.model.Employee;
import de.szut.shift_backend.model.dto.AddEmployeeDto;
import de.szut.shift_backend.model.dto.GetEmployeeDto;
import de.szut.shift_backend.model.dto.UpdatePasswordDto;
import de.szut.shift_backend.services.EmployeeService;
import de.szut.shift_backend.services.MappingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.keycloak.common.VerificationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;

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
    public ResponseEntity<GetEmployeeDto> createEmployee(@Valid @RequestBody final AddEmployeeDto employeeDto) {
        Employee emp = this.employeeService.create(this.mappingService.mapAddEmployeeDtoToEmployee(employeeDto));

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
    public ResponseEntity<List<GetEmployeeDto>> getAllEmployees()
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

    @Operation(summary = "get Employee by username")
    @ApiResponses(value = {
            @ApiResponse(responseCode =  "200", description = "employee was found"),
            @ApiResponse(responseCode =  "400", description = "username parameter is null", content = @Content),
            @ApiResponse(responseCode =  "401", description = "not authorized", content = @Content),
    })
    @GetMapping("/username/{username}")
    public ResponseEntity<GetEmployeeDto> getEmployeeByUsername( @Valid @PathVariable("username") final String username )
    {
        Employee emp = this.employeeService.getEmployeeByUsername(username);
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
    public ResponseEntity<Object> getAllEmployees(@Valid @PathVariable("id") final Long employeeId)
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
    public ResponseEntity<GetEmployeeDto> updateEmployee(@Valid @PathVariable("id") final Long employeeId,
                                                         @Valid @RequestBody final Map<String,Object> fieldsToPatch) {

            Employee empUpdate = this.employeeService.updateEmployee(employeeId, fieldsToPatch);

            GetEmployeeDto empUpdatedDto = this.mappingService.mapEmployeeToGetEmployeeDto(empUpdate);

            return new ResponseEntity<>(empUpdatedDto, HttpStatus.OK);
    }

    @Operation(summary = "change employee role")
    @ApiResponses(value = {
            @ApiResponse(responseCode =  "200", description = "employee was updated"),
            @ApiResponse(responseCode =  "400", description = "employee parameter is null", content = @Content),
            @ApiResponse(responseCode =  "401", description = "not authorized", content = @Content),
    })
    @PatchMapping("/{id}/role")
    public ResponseEntity<GetEmployeeDto> changeEmployeeRole(@Valid @PathVariable("id") final Long employeeId,
                                                             @Valid @RequestBody final Employee.EMPLOYEE_ROLE role) {

        this.employeeService.changeEmployeeRole(employeeId, role);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "change Employee password")
    @ApiResponses(value = {
            @ApiResponse(responseCode =  "200", description = "password was updated"),
            @ApiResponse(responseCode =  "400", description = "password parameter is invalid", content = @Content),
            @ApiResponse(responseCode =  "401", description = "not authorized", content = @Content),
    })
    @PostMapping("/password")
    public ResponseEntity<Object> changeEmployeePassword(@Valid @RequestBody final UpdatePasswordDto pwUpdate,
                                                         @RequestHeader("Authorization") String token) throws VerificationException {

        this.employeeService.updateEmployeePassword(token, pwUpdate);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "change Employee password")
    @ApiResponses(value = {
            @ApiResponse(responseCode =  "200", description = "password was updated"),
            @ApiResponse(responseCode =  "400", description = "password parameter is invalid", content = @Content),
            @ApiResponse(responseCode =  "401", description = "not authorized", content = @Content),
    })
    @PostMapping("/{id}/password")
    public ResponseEntity<Object> changeEmployeePasswordAsAdmin(@Valid @PathVariable("id") final Long employeeId,
                                                                @Valid @RequestBody final UpdatePasswordDto pwUpdate,
                                                                @RequestHeader("Authorization") String token) throws VerificationException {

        this.employeeService.updateEmployeePasswordAsAdmin(token, employeeId, pwUpdate);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
