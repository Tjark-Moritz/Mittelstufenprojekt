package de.szut.shift_backend.services;

import de.szut.shift_backend.model.Department;
import de.szut.shift_backend.model.Holiday;
import de.szut.shift_backend.model.dto.AddHolidayDto;
import de.szut.shift_backend.model.dto.DepartmentDto;
import de.szut.shift_backend.model.dto.GetHolidayDto;
import de.szut.shift_backend.model.dto.HolidayRequestDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MappingService {

    private final HolidayService holidayService;

    public MappingService(HolidayService holidayService) {
        this.holidayService = holidayService;
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

        if (holidayRequestDto.getStatus() != null) { //todo: richtig?
            holiday = holidayRequestDto.getHoliday();
        }

        return holiday;
    }


    public HolidayRequestDto mapHolidayToHolidayRequestDto(Holiday holiday) {
        HolidayRequestDto requestDto = new HolidayRequestDto();

        requestDto.setRequestingEmployeeId(holiday.getEmployeeId());
        requestDto.setHoliday(holiday);
        requestDto.setRequestDate(holiday.getRequestDate());
        requestDto.setStatus(holiday.getHolidayStatus());

        return requestDto;
    }

    public GetHolidayDto mapHolidayToGetHolidayDto(Holiday holiday) {
        GetHolidayDto getHolidayDto = new GetHolidayDto();

        getHolidayDto.setHolidayId(holiday.getHolidayId());
        getHolidayDto.setHolidayTypeId(holiday.getHolidayTypeId());
        getHolidayDto.setEmployeeId(holiday.getEmployeeId());
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
