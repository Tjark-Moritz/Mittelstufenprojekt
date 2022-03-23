package de.szut.shift_backend.services;

import de.szut.shift_backend.model.Department;
import de.szut.shift_backend.model.Employee;
import de.szut.shift_backend.model.Holiday;
import de.szut.shift_backend.model.dto.AddHolidayDto;
import de.szut.shift_backend.model.dto.DepartmentDto;
import de.szut.shift_backend.model.dto.GetHolidayDto;
import de.szut.shift_backend.model.dto.HolidayRequestDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MappingService {

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
            addHolidayDto.setHolidayId(holiday.getHolidayId());
            addHolidayDto.setHolidayTypeId(holiday.getHolidayTypeId());
            addHolidayDto.setStartDate(holiday.getStartDate());
            addHolidayDto.setEndDate(holiday.getEndDate());
            addHolidayDto.setEmployeeId(holiday.getEmployeeId());

        return addHolidayDto;
    }

    public Holiday mapAddHolidayDtoToHoliday(AddHolidayDto holidayDto) {
        Holiday holiday = new Holiday();
            holiday.setHolidayId(holidayDto.getHolidayId());
            holiday.setHolidayTypeId(holidayDto.getHolidayTypeId());
            holiday.setStartDate(holidayDto.getStartDate());
            holiday.setEndDate(holidayDto.getEndDate());
            holiday.setEmployeeId(holidayDto.getEmployeeId());

        return holiday;
    }

    public Holiday mapGetHolidayDtoToHoliday(GetHolidayDto holidayDto) {
        Holiday holiday = new Holiday();
            holiday.setHolidayId(holidayDto.getHolidayId());
            holiday.setHolidayTypeId(holidayDto.getHolidayTypeId());
            holiday.setStartDate(holidayDto.getStartDate());
            holiday.setEndDate(holidayDto.getEndDate());
            holiday.setEmployeeId(holidayDto.getEmployeeId());

        return holiday;
    }

    public Holiday mapHolidayRequestDtoToHoliday(HolidayRequestDto holidayRequestDto) {
        Holiday holiday = new Holiday();
        if (holidayRequestDto.getStatus()) {
            Holiday newHoliday = holidayRequestDto.getHoliday();
            holiday.setHolidayId(newHoliday.getHolidayId());
            holiday.setHolidayTypeId(newHoliday.getHolidayTypeId());
            holiday.setStartDate(newHoliday.getStartDate());
            holiday.setEndDate(newHoliday.getEndDate());
            holiday.setEmployeeId(newHoliday.getEmployeeId());
        }

        return holiday;
    }


    /*
    public List<Employee> mapEmpDtoToEmp(List<EmployeeDto> input) {

    }

    public List<EmployeeDto> mapEmpToEmpDto(List<Employee> input) {

    }
    */
}
