package de.szut.shift_backend.services;

import de.szut.shift_backend.model.*;
import de.szut.shift_backend.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class InitialDataService {
    private EmployeeRepository employeeRepository;
    private HolidayRepository holidayRepository;
    private HolidayTypeRepository holidayTypeRepository;
    private DepartmentRepository departmentRepository;
    private MessageRepository messageRepository;
    private MessageChannelRepository messageChannelRepository;
    private ShiftRepository shiftRepository;
    private ShiftPlanRepository shiftPlanRepository;
    private ShiftTypeRepository shiftTypeRepository;

    public InitialDataService(
            EmployeeRepository employeeRepository,
            HolidayRepository holidayRepository,
            HolidayTypeRepository holidayTypeRepository,
            DepartmentRepository departmentRepository,
            MessageRepository messageRepository,
            MessageChannelRepository messageChannelRepository,
            ShiftRepository shiftRepository,
            ShiftPlanRepository shiftPlanRepository,
            ShiftTypeRepository shiftTypeRepository
    ) {
        this.employeeRepository = employeeRepository;
        this.holidayRepository = holidayRepository;
        this.holidayTypeRepository = holidayTypeRepository;
        this.departmentRepository = departmentRepository;
        this.messageRepository = messageRepository;
        this.messageChannelRepository = messageChannelRepository;
        this.shiftRepository = shiftRepository;
        this.shiftPlanRepository = shiftPlanRepository;
        this.shiftTypeRepository = shiftTypeRepository;
    }


    public void createAll() {
        createEmp();
        createHoliday();
        createHolidayType();
        createDepartment();
        createMessage();
        createShift();
    }

    public Employee createEmp() {
        List<Holiday> holidayList = new ArrayList<>();
        holidayList.add(createHoliday());

        Employee employee = new Employee();
        employee.setUsername("TestUsername");
        employee.setFirstName("TestFirstname");
        employee.setLastName("TestLastname");
        employee.setStreet("Teststreet 12");
        employee.setZipcode("12345");
        employee.setCity("Testcity");
        employee.setEmail("test@test.test");
        employee.setPhone("12356789");
        employee.setNumHolidaysLeft(25);
        employee.setHolidays(holidayList);

        employeeRepository.save(employee);

        return employee;
    }

    public Holiday createHoliday() {
        Holiday holiday = new Holiday();
        holiday.setHolidayTypeId(createHolidayType());
        holiday.setEmployeeId(0L);
        holiday.setStartDate(LocalDate.of(2022, 6, 10));
        holiday.setEndDate(LocalDate.of(2022, 6, 11));
        holiday.setStatus(Holiday.HolidayStatus.UNANSWERED);
        holiday.setRequestDate(LocalDateTime.now());

        holidayRepository.save(holiday);

        return holiday;
    }

    public HolidayType createHolidayType() {
        HolidayType holidayType = new HolidayType();
        holidayType.setName("TestType");
        holidayType.setDescription("TestDescription");

        holidayTypeRepository.save(holidayType);

        return holidayType;
    }

    public Department createDepartment() {
        List<Employee> employees = new ArrayList<>();
        employees.add(createEmp());

        Department department = new Department();
        department.setName("TestDept");
        department.setAbbreviatedName("TestAbbrName");
        department.setLeadEmployee(createEmp());
        department.setEmployees(employees);

        departmentRepository.save(department);

        return department;
    }

    public void createMessage() {
        Message message = new Message();

        message.setMessageChannel(createMessageChannel());
        message.setContent("TestContent");
        message.setSendingEmployeeId(0L);
        message.setType(0L);
        message.setDateTime(LocalDateTime.now());
        message.setStatus(Message.MessageStatus.unsend);

        messageRepository.save(message);
    }

    public MessageChannel createMessageChannel() {
        List<Message> messages = new ArrayList<>();
        List<Employee> employees = new ArrayList<>();
        employees.add(createEmp());

        MessageChannel messageChannel = new MessageChannel();
        messageChannel.setName("TestName");
        messageChannel.setDescription("TestDescription");
        messageChannel.setMessages(messages);
        messageChannel.setEmployees(employees);

        messageChannelRepository.save(messageChannel);

        return messageChannel;
    }

    public ShiftType createShiftType() {
        ShiftType shiftType = new ShiftType();
        shiftType.setShiftStartTime(LocalTime.now());
        shiftType.setShiftEndTime(LocalTime.of(10, 2, 0));
        shiftType.setTypeName("TestTypeName");
        shiftType.setShiftTypeColor("red");

        shiftTypeRepository.save(shiftType);

        return shiftType;
    }

    public void createShift() {
        List<Employee> employees = new ArrayList<>();
        employees.add(createEmp());

        Shift shift = new Shift();
        shift.setShiftDate(LocalDate.now());
        shift.setShiftType(createShiftType());
        shift.setActiveEmployees(employees);

        shiftRepository.save(shift);
    }
}
