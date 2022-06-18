package de.szut.shift_backend.services;

import de.szut.shift_backend.helper.ClassReflectionHelper;
import de.szut.shift_backend.model.*;
import de.szut.shift_backend.model.dto.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MappingService {

    private final HolidayService holidayService;
    private final DepartmentService departmentService;
    private final EmployeeService employeeService;
    private final ShiftService shiftService;
    private final ShiftTypeService shiftTypeService;

    public MappingService(HolidayService holidayService, DepartmentService departmentService, EmployeeService employeeService, ShiftService shiftService, ShiftTypeService shiftTypeService)
    {
        this.holidayService = holidayService;
        this.departmentService = departmentService;
        this.employeeService = employeeService;
        this.shiftService = shiftService;
        this.shiftTypeService = shiftTypeService;
    }

    public GetMessageDto mapMessageToGetMessageDto(Message message) {
        GetMessageDto getMessageDto = new GetMessageDto();
        getMessageDto.setId(message.getId());
        getMessageDto.setChannelId(message.getChannelId());
        getMessageDto.setRequestedEmployeeId(message.getRequestedEmployeeId());
        getMessageDto.setSendingEmployeeId(message.getSendingEmployeeId());
        getMessageDto.setDateTime(message.getDateTime());
        getMessageDto.setStatus(message.getStatus());

        return getMessageDto;
    }

    public Message mapAddMessageDtoToMessage(AddMessageDto addMessageDto) {
        Message message = new Message();
        message.setId(addMessageDto.getId());
        message.setChannelId(addMessageDto.getChannelId());
        message.setRequestedEmployeeId(addMessageDto.getRequestedEmployeeId());
        message.setSendingEmployeeId(addMessageDto.getSendingEmployeeId());
        message.setDateTime(addMessageDto.getDateTime());
        message.setStatus(addMessageDto.getStatus());

        return message;
    }

    public MessageChannel mapAddMessageChannelDtoToMessageChannel(AddMessageChannelDto addMessageChannelDto) {
        MessageChannel messageChannel = new MessageChannel();
        messageChannel.setId(addMessageChannelDto.getId());
        messageChannel.setName(addMessageChannelDto.getName());
        messageChannel.setDescription(addMessageChannelDto.getDescription());
        messageChannel.setMessages(addMessageChannelDto.getMessages());

        return messageChannel;
    }

    public GetMessageChannelDto mapMessageChannelToGetMessageChannelDto(MessageChannel messageChannel) {
        GetMessageChannelDto getMessageChannelDto = new GetMessageChannelDto();
        getMessageChannelDto.setId(messageChannel.getId());
        getMessageChannelDto.setName(messageChannel.getName());
        getMessageChannelDto.setDescription(messageChannel.getDescription());
        getMessageChannelDto.setMessages(messageChannel.getMessages());

        return getMessageChannelDto;
    }

    public GetDepartmentDto mapDepToDepDto(Department department) {
        List<GetEmployeeDto> getEmployeeDtoList = new ArrayList<>();

        List<Employee> employeeList = department.getEmployees();

        for (Employee employee : employeeList) {
            GetEmployeeDto getEmployeeDto = mapEmployeeToGetEmployeeDto(employee);
            getEmployeeDtoList.add(getEmployeeDto);
        }

        GetDepartmentDto getDepartmentDto = new GetDepartmentDto();
        GetDepartmentDto tempDepDto = new GetDepartmentDto();
        tempDepDto.setDepartmentId(department.getDepartmentId());
        tempDepDto.setName(department.getName());
        tempDepDto.setAbbreviatedName(department.getAbbreviatedName());
        tempDepDto.setLeadEmployee(department.getLeadEmployee().getId());
        tempDepDto.setEmployees(getEmployeeDtoList);

        return getDepartmentDto;
    }

    public Department mapAddDepartmentDtoToDepartment(AddDepartmentDto deptDto){
        Department dept = new Department();

        dept.setName(deptDto.getName());
        dept.setAbbreviatedName(deptDto.getAbbreviatedName());

        dept.setLeadEmployee(employeeService.getEmployeeById(deptDto.getLeadEmployeeId()));

        List<Employee> empList = new ArrayList<>();
        List<ShiftType> stypeList = new ArrayList<>();

        for (Long empId : deptDto.getEmployeeIds()){
            Employee tempEmp = employeeService.getEmployeeById(empId);
            tempEmp.setDepartment(dept);
            empList.add(tempEmp);
        }

        for (AddShiftTypeDto stDto : deptDto.getShiftTypes()){
            ShiftType st = this.mapAddShiftTypeDtoToShiftType(stDto);
            stypeList.add(this.shiftTypeService.create(st));
        }

        dept.setEmployees(empList);
        dept.setShiftTypes(stypeList);

        return dept;
    }

    public Department mapDepDtoToDep(GetDepartmentDto getDepartmentDto) {
        List<Employee> employeeList = new ArrayList<>();
        Department department = new Department();
        Department tempDep = new Department();

        Employee leadEmployee = employeeService.getEmployeeById(getDepartmentDto.getLeadEmployee());
        List<GetEmployeeDto> getEmployeeDtoList = getDepartmentDto.getEmployees();

        for (GetEmployeeDto getEmployeeDto : getEmployeeDtoList) {
            Employee employee = mapGetEmployeeDtoToEmployee(getEmployeeDto);
            employeeList.add(employee);
        }

        tempDep.setDepartmentId(getDepartmentDto.getDepartmentId());
        tempDep.setName(getDepartmentDto.getName());
        tempDep.setAbbreviatedName(getDepartmentDto.getAbbreviatedName());
        tempDep.setLeadEmployee(leadEmployee);
        tempDep.setEmployees(employeeList);

        return department;
    }

    public Employee mapGetEmployeeDtoToEmployee(GetEmployeeDto getEmployeeDto) {
        Employee employee = new Employee();

        ShiftType shiftType = mapGetShiftTypeDtoToShiftType(getEmployeeDto.getPreferredShiftType());

        employee.setId(getEmployeeDto.getId());
        employee.setUsername(getEmployeeDto.getUsername());
        employee.setFirstName(getEmployeeDto.getFirstName());
        employee.setLastName(getEmployeeDto.getLastName());
        employee.setStreet(getEmployeeDto.getStreet());
        employee.setZipcode(getEmployeeDto.getZipcode());
        employee.setCity(getEmployeeDto.getCity());
        employee.setPhone(getEmployeeDto.getPhone());
        employee.setEmail(getEmployeeDto.getEmail());
        employee.setNumHolidaysLeft(getEmployeeDto.getNumHolidaysLeft());
        employee.setBase64ProfilePic(getEmployeeDto.getBase64ProfilePic());
        employee.setPreferredShiftType(shiftType);
        employee.setDepartment(this.departmentService.getDepartmentById(getEmployeeDto.getDepartmentId()));

        return employee;
    }

    public ShiftType mapGetShiftTypeDtoToShiftType(GetShiftTypeDto getShiftTypeDto) {
        ShiftType shiftType = new ShiftType();
        shiftType.setId(getShiftTypeDto.getId());
        shiftType.setTypeName(getShiftTypeDto.getTypeName());
        shiftType.setShiftStartTime(getShiftTypeDto.getShiftStartTime());
        shiftType.setShiftEndTime(getShiftTypeDto.getShiftEndTime());
        shiftType.setShiftTypeColor(getShiftTypeDto.getShiftTypeColor());

        return shiftType;
    }

    public AddHolidayDto mapHolidayToAddHolidayDto(Holiday holiday) {
        AddHolidayDto addHolidayDto = new AddHolidayDto();
        addHolidayDto.setId(holiday.getHolidayId());
        addHolidayDto.setTypeId(holiday.getHolidayTypeId().getId());
        addHolidayDto.setStartDate(holiday.getStartDate());
        addHolidayDto.setEndDate(holiday.getEndDate());
        addHolidayDto.setEmployeeId(holiday.getEmployeeId());

        return addHolidayDto;
    }

    public Holiday mapAddHolidayDtoToHoliday(AddHolidayDto addHolidayDto) {
        Holiday holiday = new Holiday();

        Employee employee = employeeService.getEmployeeById(addHolidayDto.getEmployeeId());
        Optional<HolidayType> holidayType = holidayService.getByTypeId(addHolidayDto.getTypeId());

        holidayType.ifPresent(holiday::setHolidayTypeId);

        holiday.setHolidayId(addHolidayDto.getId());
        holiday.setEmployeeId(employee.getId());
        holiday.setStartDate(addHolidayDto.getStartDate());
        holiday.setEndDate(addHolidayDto.getEndDate());
        holiday.setRequestDate(LocalDateTime.now());
        holiday.setStatus(Holiday.HolidayStatus.UNANSWERED);

        return holiday;
    }

    public GetHolidayDto mapHolidayToGetHolidayDto(Holiday holiday) {
        GetHolidayDto getHolidayDto = new GetHolidayDto();

        getHolidayDto.setHolidayId(holiday.getHolidayId());
        getHolidayDto.setHolidayTypeId(holiday.getHolidayTypeId().getId());
        getHolidayDto.setStartDate(holiday.getStartDate());
        getHolidayDto.setEndDate(holiday.getEndDate());
        getHolidayDto.setEmployeeId(holiday.getEmployeeId());

        return getHolidayDto;
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

        //optional parameters
        if (empDto.getBase64ProfilePic() != null)
            emp.setBase64ProfilePic(empDto.getBase64ProfilePic());
        else
            emp.setBase64ProfilePic("");

        if (empDto.getDepartmentId() != null)
            emp.setDepartment(this.departmentService.getDepartmentById(empDto.getDepartmentId()));

        if (empDto.getPreferredShiftTypeId() != null)
            emp.setPreferredShiftType(this.shiftTypeService.getShiftTypeById(empDto.getPreferredShiftTypeId()));

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

        List<GetHolidayDto> holidays = new ArrayList<>();
        if(emp.getHolidays() != null) {
            for (Holiday hol : emp.getHolidays())
                holidays.add(this.mapHolidayToGetHolidayDto(hol));
        }

        empDto.setHolidays(holidays);
        empDto.setBase64ProfilePic(emp.getBase64ProfilePic());

        if(emp.getDepartment() != null)
            empDto.setDepartmentId(emp.getDepartment().getDepartmentId());

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

    public GetDepartmentDto mapDepartmentToGetDepartmentDto(Department department) {
        GetDepartmentDto getDepartmentDto = new GetDepartmentDto();
        List<GetEmployeeDto> getEmployeeDtoList = new ArrayList<>();
        List<GetShiftTypeDto> getShiftTypeDtoList = new ArrayList<>();

        List<Employee> employeeList = department.getEmployees();
        List<ShiftType> sTypeList = department.getShiftTypes();

        for (Employee employee : employeeList) {
            GetEmployeeDto getEmployeeDto = mapEmployeeToGetEmployeeDto(employee);
            getEmployeeDtoList.add(getEmployeeDto);
        }

        for(ShiftType s : sTypeList){
            GetShiftTypeDto sDto = mapShiftTypeToGetShiftTypeDto(s);
            getShiftTypeDtoList.add(sDto);
        }
        getDepartmentDto.setDepartmentId(department.getDepartmentId());
        getDepartmentDto.setName(department.getName());
        getDepartmentDto.setAbbreviatedName(department.getAbbreviatedName());
        getDepartmentDto.setLeadEmployee(department.getLeadEmployee().getId());
        getDepartmentDto.setEmployees(getEmployeeDtoList);
        getDepartmentDto.setShiftTypes(getShiftTypeDtoList);

        return getDepartmentDto;
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

    private ShiftType mapAddShiftTypeDtoToShiftType(AddShiftTypeDto shiftTypeDto){
        ShiftType stype = new ShiftType();

        return ClassReflectionHelper.FastParamMap(stype, shiftTypeDto);
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

    public ShiftPlan mapAddShiftPlanDtoToShiftPlan(AddShiftPlanDto shiftPlanDto) {
        ShiftPlan splan = new ShiftPlan();

        splan.setDepartment(this.departmentService.getDepartmentById(shiftPlanDto.getDepartmentId()));
        splan.setValidMonth(shiftPlanDto.getValidMonth());
        splan.setExcludedWeekdays(shiftPlanDto.getExcludedWeekdays());

        return splan;
    }

    public GetShiftPlanDto mapShiftPlanToGetShiftPlanDto(ShiftPlan shiftPlan){
        GetShiftPlanDto shiftPlanDto = new GetShiftPlanDto();

        shiftPlanDto.setId(shiftPlan.getId());
        shiftPlanDto.setValidMonth(shiftPlan.getValidMonth());

        shiftPlanDto.setDepartmentId(this.mapDepartmentToGetDepartmentDto(shiftPlan.getDepartment()));

        List<GetShiftDto> shiftDto = new ArrayList<>();
        for(Shift s : shiftPlan.getShifts())
            shiftDto.add(this.mapShiftToGetShiftDto(s));

        shiftPlanDto.setShifts(shiftDto);

        return shiftPlanDto;
    }

}
