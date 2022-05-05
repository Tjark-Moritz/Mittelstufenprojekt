package de.szut.shift_backend.services;

import de.szut.shift_backend.model.*;
import de.szut.shift_backend.repository.ShiftPlanRepository;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShiftPlanService {
    private final ShiftPlanRepository shiftPlanRepository;
    private final DepartmentService departmentService;

    public ShiftPlanService(ShiftPlanRepository shiftPlanRepository, DepartmentService departmentService) {
        this.shiftPlanRepository = shiftPlanRepository;
        this.departmentService = departmentService;
    }

    public ShiftPlan create(ShiftPlan splan) {
        return this.shiftPlanRepository.save(splan);
    }

    public ShiftPlan generateShiftPlan(ShiftPlan shiftPlan) {
        Department dept = shiftPlan.getDepartment();
        LocalDate activeMonth = shiftPlan.getValidMonth();
        List<Employee> emps = dept.getEmployees();
        List<ShiftType> sTypes = shiftPlan.getShiftTypes();
        List<Shift> shiftsTemp = new ArrayList<>();
        int numDaysPerMonth = activeMonth.lengthOfMonth();

        for(int i = 0; i < numDaysPerMonth; i++){
            if(activeMonth.plusDays(i).getDayOfWeek() != DayOfWeek.SATURDAY
                    || activeMonth.plusDays(i).getDayOfWeek() != DayOfWeek.SUNDAY) {
                for(ShiftType sType : sTypes){
                    Shift shift = new Shift();

                    shift.setShiftType(sType);
                    shift.setShiftDate(activeMonth.plusDays(i));

                    shift.setActiveEmployees(emps);
                    shiftsTemp.add(shift);
                }
            }
        }
        
        shiftPlan.setShifts(shiftsTemp);
        return shiftPlan;
    }
}
