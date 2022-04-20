package de.szut.shift_backend.services;

import de.szut.shift_backend.model.Department;
import de.szut.shift_backend.model.Employee;
import de.szut.shift_backend.model.Holiday;
import de.szut.shift_backend.model.dto.AddHolidayDto;
import de.szut.shift_backend.model.dto.DepartmentDto;
import de.szut.shift_backend.model.dto.GetHolidayDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MappingService {

    private final EmployeeService employeeService;

    public MappingService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public DepartmentDto mapDepToDepDto(Department department) {
        DepartmentDto departmentDto = new DepartmentDto();
        DepartmentDto tempDepDto = new DepartmentDto();
        tempDepDto.setDepartmentId(department.getDepartmentId());
        tempDepDto.setName(department.getName());
        tempDepDto.setAbbreviatedName(department.getAbbreviatedName());
        tempDepDto.setLeadEmployee(department.getLeadEmployee());
        tempDepDto.setEmployees(department.getEmployees());

        return departmentDto;
    }

    public Department mapDepDtoToDep(DepartmentDto departmentDto) {
        Department department = new Department();
        Department tempDep = new Department();
        tempDep.setDepartmentId(departmentDto.getDepartmentId());
        tempDep.setName(departmentDto.getName());
        tempDep.setAbbreviatedName(departmentDto.getAbbreviatedName());
        tempDep.setLeadEmployee(departmentDto.getLeadEmployee());
        tempDep.setEmployees(departmentDto.getEmployees());

        return department;
    }

    public AddHolidayDto mapHolidayToAddHolidayDto(Holiday holiday) {
        AddHolidayDto addHolidayDto = new AddHolidayDto();
        addHolidayDto.setId(holiday.getHolidayId());
        addHolidayDto.setTypeId(holiday.getHolidayTypeId());
        addHolidayDto.setStartDate(holiday.getStartDate());
        addHolidayDto.setEndDate(holiday.getEndDate());
        addHolidayDto.setEmployeeId(holiday.getEmployee().getId());

        return addHolidayDto;
    }

    public Holiday mapAddHolidayDtoToHoliday(AddHolidayDto addHolidayDto) {
        Holiday holiday = new Holiday();

        Employee employee = employeeService.getEmployeeById(addHolidayDto.getEmployeeId());

        holiday.setHolidayId(addHolidayDto.getId());
        holiday.setHolidayTypeId(addHolidayDto.getTypeId());
        holiday.setEmployee(employee);
        holiday.setStartDate(addHolidayDto.getStartDate());
        holiday.setEndDate(addHolidayDto.getEndDate());
        holiday.setRequestDate(LocalDateTime.now());

        return holiday;
    }

    public GetHolidayDto mapHolidayToGetHolidayDto(Holiday holiday) {
        GetHolidayDto getHolidayDto = new GetHolidayDto();

        getHolidayDto.setHolidayId(holiday.getHolidayId());
        getHolidayDto.setHolidayTypeId(holiday.getHolidayTypeId());
        getHolidayDto.setEmployeeId(holiday.getEmployee().getId());
        getHolidayDto.setStartDate(holiday.getStartDate());
        getHolidayDto.setEndDate(holiday.getEndDate());

        return getHolidayDto;
    }

    /*
    public List<Employee> mapEmpDtoToEmp(List<EmployeeDto> input) {

    }

    public List<EmployeeDto> mapEmpToEmpDto(List<Employee> input) {

    }
    */
}
