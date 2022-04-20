package de.szut.shift_backend.repository;

import de.szut.shift_backend.model.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface HolidayRepository extends JpaRepository<Holiday, Long> {

//    default List<Holiday> findAllByEmployeeId(Long employeeId) {
//        List<Holiday> holidayList = findAll();
//        List<Holiday> holidays = new ArrayList<>();
//
//        for (Holiday holiday : holidayList) {
//            if (holiday.getEmployee().getId().equals(employeeId)) {
//                holidays.add(holiday);
//            }
//        }
//
//        return holidays;
//    }

    @Query("SELECT holiday FROM Holiday holiday WHERE holiday.employee.id = :employeeId")
    List<Holiday> findAllByEmployeeId(@Param("employeeId") Long employeeId);
}
