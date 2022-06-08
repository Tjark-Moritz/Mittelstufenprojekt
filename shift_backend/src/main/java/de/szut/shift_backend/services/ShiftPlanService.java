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

    public List<ShiftPlan> getShiftPlansByDeptId(Long deptId){
        return this.shiftPlanRepository.findShiftPlansByDepartment_DepartmentId(deptId);
    }

    public ShiftPlan getShiftPlanById(Long shiftPlanId) {
        Optional<ShiftPlan> shiftPlan = this.shiftPlanRepository.findById(shiftPlanId);

        if (shiftPlan.isEmpty())
            throw new ResourceNotFoundException("Shift with id '" + shiftPlanId + "'could not be found");

        return shiftPlan.get();
    }

    public void deleteShiftplanById(Long shiftplanId) {
        this.shiftPlanRepository.deleteById(shiftplanId);
    }

    public ShiftPlan generateShiftPlan(ShiftPlan shiftPlan) {
        Department dept = shiftPlan.getDepartment();
        LocalDate activeMonth = shiftPlan.getValidMonth();

        List<Employee> emps = dept.getEmployees();
        List<ShiftType> sTypes = shiftPlan.getShiftTypes();
        List<Shift> shiftsTemp = new ArrayList<>();

        HashMap<Long,List<Employee>> empsByPrefShiftType = getEmployeesForShiftType(emps);
        int numDaysPerMonth = activeMonth.lengthOfMonth();

        for(int i = 0; i < numDaysPerMonth; i++){
            LocalDate currentDay = activeMonth.plusDays(i);
            if(currentDay.getDayOfWeek() != DayOfWeek.SATURDAY
                    && currentDay.getDayOfWeek() != DayOfWeek.SUNDAY) {
                for(ShiftType sType : sTypes){
                    Shift shift = new Shift();

                    shift.setShiftType(sType);
                    shift.setShiftDate(currentDay);

                    List<Employee> possibleEmps = empsByPrefShiftType.get(sType.getId());

                    if (possibleEmps == null)
                        possibleEmps = getAlternativeEmps(empsByPrefShiftType, sType.getId());

                    List<Employee> availableEmps = checkAvailability(possibleEmps,currentDay);

                    shift.setActiveEmployees(availableEmps);

                    shiftsTemp.add(shift);
                }
            }
        }
        
        shiftPlan.setShifts(shiftsTemp);
        return shiftPlan;
    }

    private List<Employee> getAlternativeEmps(HashMap<Long, List<Employee>> empsByPrefShiftType, Long id) {
        List<Employee> emps = new ArrayList<>();

        for(Long stypeId : empsByPrefShiftType.keySet()){
            List<Employee> empsForShiftTypeId = empsByPrefShiftType.get(stypeId);
            if (stypeId.equals(id) || empsForShiftTypeId.isEmpty())
                continue;

            emps = empsForShiftTypeId;
            break;
        }

        return emps;
    }

    private List<Employee> checkAvailability(List<Employee> employees, LocalDate date) {
        List<Employee> availableEmps = new ArrayList<>();

        for(Employee emp : employees){
            boolean isAvailable = true;
            for(Holiday holiday : emp.getHolidays()){
                if(holiday.getStatus() == Holiday.HolidayStatus.ACCEPTED
                        && !(date.isBefore(holiday.getStartDate()) || date.isAfter(holiday.getEndDate())))
                    isAvailable = false;
            }

            if (isAvailable)
                availableEmps.add(emp);
        }

        return availableEmps;
    }

    private HashMap<Long,List<Employee>> getEmployeesForShiftType(List<Employee> allEmps) {
        HashMap<Long,List<Employee>> empsByPrefShiftType = new HashMap<>();

        empsByPrefShiftType.put(0L, new ArrayList<>());

        for(Employee emp : allEmps){
            if(emp.getPreferredShiftType() == null){
                empsByPrefShiftType.get(0L).add(emp);
                continue;
            }

            Long prefShifTypeId = emp.getPreferredShiftType().getId();

            if (!empsByPrefShiftType.containsKey(prefShifTypeId))
                empsByPrefShiftType.put(prefShifTypeId, new ArrayList<>());

            empsByPrefShiftType.get(prefShifTypeId).add(emp);
        }

        return empsByPrefShiftType;
    }
}
