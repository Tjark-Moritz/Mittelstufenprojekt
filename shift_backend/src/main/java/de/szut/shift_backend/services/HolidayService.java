package de.szut.shift_backend.services;

import de.szut.shift_backend.exceptionHandling.ResourceNotFoundException;
import de.szut.shift_backend.helper.ClassReflectionHelper;
import de.szut.shift_backend.model.Department;
import de.szut.shift_backend.model.Holiday;
import de.szut.shift_backend.model.HolidayType;
import de.szut.shift_backend.repository.HolidayRepository;
import de.szut.shift_backend.repository.HolidayTypeRepository;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.*;

@Service
public class HolidayService {
    private final HolidayRepository holidayRepository;
    private final EmployeeService employeeService;
    private final HolidayTypeRepository holidayTypeRepository;

    public HolidayService(HolidayRepository holidayRepository, EmployeeService employeeService, HolidayTypeRepository holidayTypeRepository) {
        this.holidayRepository = holidayRepository;
        this.employeeService = employeeService;
        this.holidayTypeRepository = holidayTypeRepository;
    }

    public Holiday create(Holiday newHoliday) {
        return holidayRepository.save(newHoliday);
    }

    public Holiday update(Long holidayId, Map<String, Object> holUpdate) throws ConstraintViolationException {
        Holiday holiday = this.getById(holidayId);

        if(holUpdate.containsKey("holidayTypeId")){
            Optional<HolidayType> ht = this.holidayTypeRepository.findById(((Integer) holUpdate.get("holidayTypeId")).longValue());

            if (ht.isPresent())
                holUpdate.put("holidayTypeId", ht.get());
            else
                throw new ResourceNotFoundException("Could not find HolidayType with Id: " + holUpdate.get("holidayTypeId"));
        }
        Holiday holidayUpdated = ClassReflectionHelper.UpdateFields(holiday, holUpdate);

        this.holidayRepository.save(holidayUpdated);

        return holidayUpdated;
    }

    public Holiday answer(Long holidayId, Holiday.HolidayStatus holidayStatus) {
        Holiday holiday = this.getById(holidayId);

        holiday.setStatus(holidayStatus);

        this.holidayRepository.save(holiday);

        return holiday;
    }

    public Holiday getById(Long id) {
        Optional<Holiday> holiday = holidayRepository.findById(id);

        if (holiday.isEmpty()) {
            throw new ResourceNotFoundException("Holiday with Id: " + id + " could not be found!");
        }
        return holiday.get();
    }

    public Optional<HolidayType> getByTypeId(Long id) {
        return this.holidayTypeRepository.findById(id);
    }

    private List<Holiday> getAllByStatus(Holiday.HolidayStatus status) {
        List<Holiday> holidayList = holidayRepository.findAll();
        List<Holiday> matchedHolidays = new ArrayList<>();

        for (Holiday holiday : holidayList) {
            if (holiday.getStatus() == status)
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
            Department department = employeeService.getDepartmentByEmployeeId(holiday.getEmployeeId().getId());

            if (department.getDepartmentId() == departmentId)
                matchedHolidays.add(holiday);
        }

        return matchedHolidays;
    }

    public void delete(long holidayId) {
        holidayRepository.deleteById(holidayId);
    }

    public Holiday setHolidayStatus(Long id, Holiday.HolidayStatus status) {
        Holiday holiday = getById(id);

        holiday.setStatus(status);

        //todo: if holidayStatus == accepted => calculateFreeHolidayCounter() (Anzahl der freien Urlaubstage reduzieren)
        //todo: [Bedarf] bei "unanswered" => geplante Urlaubstage erhöhen

        return holiday;
    }

    public boolean checkIfHolidayExists(Holiday holidayToCheck) {
        List<Holiday> holidayList = getAllHolidays();

        for (Holiday holiday : holidayList) {
            if (holidayToCheck.getEmployeeId().getId().equals(holiday.getEmployeeId().getId()) &&
                holidayToCheck.getStartDate().equals(holiday.getStartDate()) &&
                holidayToCheck.getEndDate().equals(holiday.getEndDate())
            ){
                return true;
            }
        }
        return false;
    }
}
