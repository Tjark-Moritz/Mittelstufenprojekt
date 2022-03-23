package de.szut.shift_backend.services;

import de.szut.shift_backend.exceptionHandling.ResourceNotFoundException;
import de.szut.shift_backend.model.Employee;
import de.szut.shift_backend.model.Holiday;
import de.szut.shift_backend.repository.EmployeeRepository;
import de.szut.shift_backend.repository.HolidayRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HolidayService {
    private final HolidayRepository holidayRepository;
    private final EmployeeRepository employeeRepository;

    public HolidayService(HolidayRepository holidayRepository, EmployeeRepository employeeRepository) {
        this.holidayRepository = holidayRepository;
        this.employeeRepository = employeeRepository;
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
}
