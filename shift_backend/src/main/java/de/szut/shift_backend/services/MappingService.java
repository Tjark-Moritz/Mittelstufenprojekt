package de.szut.shift_backend.services;

import de.szut.shift_backend.model.*;
import de.szut.shift_backend.model.dto.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class MappingService {

    private final HolidayService holidayService;
    private final EmployeeService employeeService;
    private final ShiftService shiftService;

    public MappingService(HolidayService holidayService, EmployeeService employeeService, ShiftService shiftService)
    {
        this.holidayService = holidayService;
        this.employeeService = employeeService;
        this.shiftService = shiftService;
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

        if (holidayRequestDto.getStatus()) {
            holiday = holidayRequestDto.getHoliday();
        }

        return holiday;
    }


    public void mapHolidaylistToHolidayRequestDto(List<Holiday> holidays) {
        HolidayRequestDto requestDto = new HolidayRequestDto();

        for (Holiday holiday : holidays) {
            requestDto.setRequestingEmployeeId(holiday.getEmployeeId());
            requestDto.setHoliday(holiday);
            requestDto.setRequestDate(LocalDateTime.now());
          //todo: refactor here

        }

    }

    public Employee mapAddEmployeeDtoToEmployee(AddEmployeeDto empDto){
        Employee emp = new Employee();

        emp.setUsername(empDto.getUsername());
        emp.setLastName(empDto.getLastName());
        emp.setFirstName(empDto.getFirstName());
        emp.setStreet(empDto.getStreet());
        emp.setZipcode(empDto.getZipcode());
        emp.setCity(empDto.getCity());
        emp.setPhone(empDto.getPhone());
        emp.setEmail(empDto.getEmail());
        emp.setNumHolidaysLeft(empDto.getNumHolidaysLeft());
        emp.setBase64ProfilePic(empDto.getBase64ProfilePic());
        emp.setDepartmentId(empDto.getDepartmentId());

        return emp;
    }

    public GetEmployeeDto mapEmployeeToGetEmployeeDto(Employee emp){
        GetEmployeeDto empDto = new GetEmployeeDto();

        empDto.setId(emp.getId());
        empDto.setUsername(emp.getUsername());
        empDto.setLastName(emp.getLastName());
        empDto.setFirstName(emp.getFirstName());
        empDto.setStreet(emp.getStreet());
        empDto.setZipcode(emp.getZipcode());
        empDto.setCity(emp.getCity());
        empDto.setPhone(emp.getPhone());
        empDto.setEmail(emp.getEmail());
        empDto.setNumHolidaysLeft(emp.getNumHolidaysLeft());
        empDto.setBase64ProfilePic(emp.getBase64ProfilePic());
        empDto.setDepartmentId(emp.getDepartmentId());

        return empDto;
    }

    public ShiftTradeRequest mapAddShiftTradeRequestDtoToShiftTradeRequest(AddShiftTradeRequestDto requestDto){
        ShiftTradeRequest request = new ShiftTradeRequest();

        request.setRequestingEmployee(this.employeeService.getEmployeeById(requestDto.getRequestingEmployeeId()));
        request.setReplyingEmployee(this.employeeService.getEmployeeById(requestDto.getReplyingEmployeeId()));
        request.setOldShift(this.shiftService.getShiftById(requestDto.getOldShiftId()));
        request.setNewShift(this.shiftService.getShiftById(requestDto.getNewShiftId()));

        return request;
    }

    public GetShiftTradeRequestDto mapShiftTradeRequestToGetShiftTradeRequestDto(ShiftTradeRequest request){
        GetShiftTradeRequestDto requestDto = new GetShiftTradeRequestDto();

        GetEmployeeDto requestingEmployeeDto = this.mapEmployeeToGetEmployeeDto(request.getRequestingEmployee());
        GetEmployeeDto replyingEmployeeDto = this.mapEmployeeToGetEmployeeDto(request.getReplyingEmployee());
        GetShiftDto oldShiftDto = this.mapShiftToGetShiftDto(request.getOldShift());
        GetShiftDto newShiftDto = this.mapShiftToGetShiftDto(request.getNewShift());


        requestDto.setId(request.getId());
        requestDto.setRequestingEmployee(requestingEmployeeDto);
        requestDto.setReplyingEmployee(replyingEmployeeDto);
        requestDto.setOldShift(oldShiftDto);
        requestDto.setNewShift(newShiftDto);

        return requestDto;
    }

    private GetShiftDto mapShiftToGetShiftDto(Shift shift) {
        GetShiftDto shiftDto = new GetShiftDto();

        GetShiftTypeDto shiftTypeDto = this.mapShiftTypeToGetShiftTypeDto(shift.getShiftType());
        List<GetEmployeeDto> employeeDtoList = new ArrayList<>();

        for(Employee emp : shift.getActiveEmployees()){
            employeeDtoList.add(this.mapEmployeeToGetEmployeeDto(emp));
        }

        shiftDto.setId(shift.getId());
        shiftDto.setShiftDate(shift.getShiftDate());
        shiftDto.setShiftType(shiftTypeDto);
        shiftDto.setActiveEmployees(employeeDtoList);

        return shiftDto;
    }

    private GetShiftTypeDto mapShiftTypeToGetShiftTypeDto(ShiftType shiftType) {
        GetShiftTypeDto shiftTypeDto = new GetShiftTypeDto();

        shiftTypeDto.setId(shiftType.getId());
        shiftTypeDto.setShiftStartTime(shiftType.getShiftStartTime());
        shiftTypeDto.setShiftEndTime(shiftType.getShiftEndTime());
        shiftTypeDto.setTypeName(shiftType.getTypeName());
        shiftTypeDto.setShiftTypeColor(shiftType.getShiftTypeColor());

        return shiftTypeDto;
    }
}
