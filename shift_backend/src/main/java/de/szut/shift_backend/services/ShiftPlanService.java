package de.szut.shift_backend.services;

import de.szut.shift_backend.exceptionHandling.ResourceNotFoundException;
import de.szut.shift_backend.model.*;
import de.szut.shift_backend.repository.ShiftPlanRepository;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

@Service
public class ShiftPlanService {
    private final ShiftPlanRepository shiftPlanRepository;

    public ShiftPlanService(ShiftPlanRepository shiftPlanRepository) {
        this.shiftPlanRepository = shiftPlanRepository;
    }

    public ShiftPlan create(ShiftPlan splan) {
        return this.shiftPlanRepository.save(splan);
    }
    public ShiftPlan getShiftPlanById(Long shiftPlanId) {
        Optional<ShiftPlan> shiftPlan = this.shiftPlanRepository.findById(shiftPlanId);

        if (shiftPlan.isEmpty())
            throw new ResourceNotFoundException("Shift with id '" + shiftPlanId + "'could not be found");

        return shiftPlan.get();
    }
    public ShiftPlan generateShiftPlan(ShiftPlan shiftPlan) {
        Department dept = shiftPlan.getDepartment();
        LocalDate activeMonth = shiftPlan.getValidMonth();

        List<Employee> emps = dept.getEmployees();
        List<ShiftType> sTypes = shiftPlan.getShiftTypes();
        List<Shift> shiftsTemp = new ArrayList<>();

        HashMap<Long,List<Employee>> favTypeMap = new HashMap<>();
        int numDaysPerMonth = activeMonth.lengthOfMonth();

        for(ShiftType stype : sTypes)
            favTypeMap.put(stype.getId(), getEmployeesForShiftType(emps, stype));

        for(int i = 0; i < numDaysPerMonth; i++){
            LocalDate currentDay = activeMonth.plusDays(i);
            if(currentDay.getDayOfWeek() != DayOfWeek.SATURDAY
                    && currentDay.getDayOfWeek() != DayOfWeek.SUNDAY) {
                for(ShiftType sType : sTypes){
                    Shift shift = new Shift();

                    shift.setShiftType(sType);
                    shift.setShiftDate(currentDay);

                    List<Employee> availableEmps = checkAvailability(favTypeMap.get(sType.getId()),currentDay);
                    shift.setActiveEmployees(availableEmps);

                    shiftsTemp.add(shift);
                }
            }
        }
        
        shiftPlan.setShifts(shiftsTemp);
        return shiftPlan;
    }

    private List<Employee> checkAvailability(List<Employee> employees, LocalDate date) {
        List<Employee> availableEmps = new ArrayList<>();

        for(Employee emp : employees){
            for(Holiday holiday : emp.getHolidays()){
                if(holiday.getStatus() == Holiday.HolidayStatus.ACCEPTED
                        && !(date.isBefore(holiday.getStartDate()) || date.isAfter(holiday.getEndDate())))
                    availableEmps.add(emp);
            }
        }

        return availableEmps;
    }

    private List<Employee> getEmployeesForShiftType(List<Employee> allEmps, ShiftType sType) {
        List<Employee> matchedEmployees = new ArrayList<>();

        for(Employee emp : allEmps)
            if(emp.getPreferredShiftType().getId().equals(sType.getId()))
                matchedEmployees.add(emp);

        return matchedEmployees;
    }


}
