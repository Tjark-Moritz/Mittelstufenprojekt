package de.szut.shift_backend.services;

import de.szut.shift_backend.exceptionHandling.ResourceNotFoundException;
import de.szut.shift_backend.model.Department;
import de.szut.shift_backend.model.Holiday;
import de.szut.shift_backend.repository.HolidayRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class HolidayService {
    private final HolidayRepository holidayRepository;
    private final EmployeeService employeeService;

    public HolidayService(HolidayRepository holidayRepository, EmployeeService employeeService) {
        this.holidayRepository = holidayRepository;
        this.employeeService = employeeService;
    }

    public Holiday create(Holiday newHoliday) {
        return holidayRepository.save(newHoliday);
    }

    public Holiday update(Holiday newHoliday, Long id) {
        Holiday holiday = getById(id);

        holiday.setHolidayId(newHoliday.getHolidayId());
        holiday.setHolidayTypeId(newHoliday.getHolidayTypeId());
        holiday.setStartDate(newHoliday.getStartDate());
        holiday.setEndDate(newHoliday.getEndDate());
        holiday.setEmployeeId(newHoliday.getEmployeeId());
        //todo: update for status?

        holiday = holidayRepository.save(holiday);
        return holiday;
    }

    public Holiday getById(Long id) {
        Optional<Holiday> holiday = holidayRepository.findById(id);

        if (holiday.isEmpty()) {
            throw new ResourceNotFoundException("Holiday with Id: " + id + "could not be found!");
        }
        return holiday.get();
    }

    private List<Holiday> getAllByStatus(Holiday.HolidayStatus status) {
        List<Holiday> holidayList = holidayRepository.findAll();
        List<Holiday> matchedHolidays = new ArrayList<>();

        for (Holiday holiday : holidayList) {
            if (holiday.getHolidayStatus() == status)
                matchedHolidays.add(holiday);
        }

        return matchedHolidays;
    }

    public List<Holiday> getAllHolidays() {
        return holidayRepository.findAll();
    }

    public List<Holiday> getHolidayRequestsByStatusByDeptId(long departmentId, Holiday.HolidayStatus status) {
        List<Holiday> holidayList = getAllByStatus(status);
        List<Holiday> matchedHolidays = new ArrayList<>();

        for (Holiday holiday : holidayList) {
            Department department = employeeService.getDepartmentByEmployeeId(holiday.getEmployeeId());

            if (department.getDepartmentId() == departmentId)
                matchedHolidays.add(holiday);
        }

        return matchedHolidays;
    }

    public void delete(long holidayId) {
        holidayRepository.deleteById(holidayId);
    }

    public Holiday setHolidayStatus(Long id, String status) {
        Holiday holiday = getById(id);

        Holiday.HolidayStatus holidayStatus = Holiday.HolidayStatus.of(status);
        holiday.setHolidayStatus(holidayStatus);

        //todo: if holidayStatus == accepted => calculateFreeHolidayCounter() (Anzahl der freien Urlaubstage reduzieren)
        //todo: [Bedarf] bei "unanswered" => geplante Urlaubstage erhöhen

        return holiday;
    }
}
