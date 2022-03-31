package de.szut.shift_backend.repository;

import de.szut.shift_backend.model.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface HolidayRepository extends JpaRepository<Holiday, Long> {

    default List<Holiday> findAllByEmployeeId(Long employeeId) {
        List<Holiday> holidayList = findAll();
        List<Holiday> holidays = new ArrayList<>();

        for (Holiday holiday : holidayList) {
            if (holiday.getEmployeeId().equals(employeeId)) {
                holidays.add(holiday);
            }
        }

        return holidays;
    }
}
