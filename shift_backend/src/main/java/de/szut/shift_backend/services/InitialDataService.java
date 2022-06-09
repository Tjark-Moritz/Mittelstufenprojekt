package de.szut.shift_backend.services;

import de.szut.shift_backend.model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class InitialDataService {
    public void createAll() {
        createEmp();
        createHoliday();
        createHolidayType();
        createDepartment();
        createMessage();
        createMessageChannel();
        createShift();
        createShiftPlan();
        createShiftType();
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

        return employee;
    }

    public Holiday createHoliday() {
        Holiday holiday = new Holiday();
        holiday.setHolidayTypeId(createHolidayType());
        holiday.setEmployeeId(0L);
        holiday.setStartDate(LocalDate.of(2022,6,10));
        holiday.setEndDate(LocalDate.of(2022,6,11));
        holiday.setStatus(Holiday.HolidayStatus.UNANSWERED);
        holiday.setRequestDate(LocalDateTime.now());

        return holiday;
    }

    public HolidayType createHolidayType() {
        HolidayType holidayType = new HolidayType();
        holidayType.setName("TestType");
        holidayType.setDescription("TestDescription");

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

        return department;
    }

    public Message createMessage() {
        Message message = new Message();
        message.setMessageChannel(createMessageChannel());
        message.setContent("TestContent");
        message.setSendingEmployeeId(0L);
        message.setType(0L);
        message.setDateTime(LocalDateTime.now());
        message.setStatus(Message.MessageStatus.unsend);

        return message;
    }

    public MessageChannel createMessageChannel() {
        List<Message> messages = new ArrayList<>();
        messages.add(createMessage());

        List<Employee> employees = new ArrayList<>();
        employees.add(createEmp());

        MessageChannel messageChannel = new MessageChannel();
        messageChannel.setName("TestName");
        messageChannel.setDescription("TestDescription");
        messageChannel.setMessages(messages);
        messageChannel.setEmployees(employees);

        return messageChannel;
    }

    public Shift createShift(){
        List<Employee> employees = new ArrayList<>();
        employees.add(createEmp());

        Shift shift = new Shift();
        shift.setShiftDate(LocalDate.now());
        shift.setShiftType(createShiftType());
        shift.setActiveEmployees(employees);

        return shift;
    }

    public ShiftPlan createShiftPlan() {
        List<Shift> shifts = new ArrayList<>();
        shifts.add(createShift());

        List<ShiftType> shiftTypes = new ArrayList<>();
        shiftTypes.add(createShiftType());

        ShiftPlan shiftPlan = new ShiftPlan();
        shiftPlan.setDepartment(createDepartment());
        shiftPlan.setValidMonth(LocalDate.now());
        shiftPlan.setShifts(shifts);
        shiftPlan.setShiftTypes(shiftTypes);

        return shiftPlan;
    }

    public ShiftType createShiftType() {
        ShiftType shiftType = new ShiftType();
        shiftType.setShiftStartTime(LocalTime.now());
        shiftType.setShiftEndTime(LocalTime.of(10,2, 0));
        shiftType.setTypeName("TestTypeName");
        shiftType.setShiftTypeColor("red");

        return shiftType;
    }
}
