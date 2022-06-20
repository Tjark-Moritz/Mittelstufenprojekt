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
        Random rand = new Random();
        Department dept = shiftPlan.getDepartment();
        LocalDate activeMonth = shiftPlan.getValidMonth();

        List<Employee> emps = dept.getEmployees();
        List<ShiftType> sTypes = dept.getShiftTypes();
        List<Shift> dailyShiftsBuffer = new ArrayList<>();
        List<Shift> allShiftsBuffer = new ArrayList<>();

        int numDaysPerMonth = activeMonth.lengthOfMonth();

        for(int i = 0; i < numDaysPerMonth; i++){
            LocalDate currentDay = activeMonth.plusDays(i);
            if(!shiftPlan.getExcludedWeekdays().contains(currentDay.getDayOfWeek())) {
                HashMap<Long,LinkedList<Employee>> empsMap = getEmployeesForShiftType(checkAvailability(emps, currentDay));

                for (ShiftType stype : sTypes) {
                    int numEmpsInShift = 0;

                    Shift s = new Shift();
                    s.setShiftType(stype);
                    s.setShiftDate(currentDay);

                    List<Employee> activeEmps = new ArrayList<>();

                    //try to fill shifts with emps who want to be in that type of shift
                    while(empsMap.get(stype.getId()).size() > 0 && numEmpsInShift <= stype.getTargetNumOfEmps()) {
                        int randEmpIndex = 0;

                        if (empsMap.get(stype.getId()).size() > 1)
                            randEmpIndex = rand.nextInt(empsMap.get(stype.getId()).size());

                        activeEmps.add(empsMap.get(stype.getId()).get(randEmpIndex));
                        empsMap.get(stype.getId()).remove(randEmpIndex);
                        numEmpsInShift++;
                    }

                    s.setActiveEmployees(activeEmps);
                    dailyShiftsBuffer.add(s);
                }

                //in case some TargetNumOfEmps is not met try filling
                //them with random emps, starting from people with no pref
                List<Long> sTypeIds = new ArrayList<>();
                for (Long key : empsMap.keySet())
                    if(!empsMap.get(key).isEmpty())
                        sTypeIds.add(key);

                for (Shift s : dailyShiftsBuffer){
                    while (s.getActiveEmployees().size() < s.getShiftType().getTargetNumOfEmps()
                            && sTypeIds.size() > 0)
                    {
                        int randIndex = 0;

                        if (!empsMap.get(0L).isEmpty()){
                            randIndex = rand.nextInt(empsMap.get(0L).size());
                            s.getActiveEmployees().add(empsMap.get(0L).get(randIndex));
                            empsMap.get(0L).remove(randIndex);
                            continue;
                        }

                        if (sTypeIds.size() > 1)
                            randIndex = 1 + rand.nextInt(sTypeIds.size() - 1);

                        s.getActiveEmployees().add(empsMap.get(sTypeIds.get(randIndex)).getFirst());
                        empsMap.get(sTypeIds.get(randIndex)).removeFirst();

                        if(empsMap.get(sTypeIds.get(randIndex)).isEmpty())
                            sTypeIds.remove(randIndex);
                    }
                }

                //evenly distribute some of the remaining emps based on pref
                while(sTypeIds.size() > 0){
                    if (sTypeIds.size() == 1){
                        Iterator<Shift> shiftIt = dailyShiftsBuffer.listIterator();
                        while(!empsMap.get(sTypeIds.get(0)).isEmpty()){
                            //it isn't pretty but I got deadlines to meet
                            if (!shiftIt.hasNext())
                                shiftIt = dailyShiftsBuffer.listIterator();

                            Shift s = shiftIt.next();
                            s.getActiveEmployees().add(empsMap.get(sTypeIds.get(0)).getFirst());
                            empsMap.get(sTypeIds.get(0)).removeFirst();
                        }

                        sTypeIds.remove(0);
                        continue;
                    }

                    for (int j = 1; j < sTypeIds.size(); j++){
                        //Whacky workaround for filtering
                        final int constJ = j;
                        Optional<Shift> shift = dailyShiftsBuffer.stream()
                                                .filter(s -> s.getShiftType().getId().equals(sTypeIds.get(constJ)))
                                                .findFirst();

                        if(shift.isPresent()){
                            int randTypeIndex = rand.nextInt(sTypeIds.size());
                            int index = empsMap.get(sTypeIds.get(j)).isEmpty() ? randTypeIndex : j;

                            Employee emp = empsMap.get(sTypeIds.get(index)).getFirst();
                            shift.get().getActiveEmployees().add(emp);
                            empsMap.get(sTypeIds.get(index)).removeFirst();

                            if(empsMap.get(sTypeIds.get(index)).isEmpty())
                                sTypeIds.remove(index);
                        }
                    }
                }

                allShiftsBuffer.addAll(dailyShiftsBuffer);
                dailyShiftsBuffer.clear();
            }
        }
        
        shiftPlan.setShifts(allShiftsBuffer);
        return shiftPlan;
    }

    private List<Employee> getAlternativeEmps(HashMap<Long, List<Employee>> empsByPrefShiftType, Long id) {
        List<Employee> emps = new ArrayList<>();

        //Get a different set of employees as the preferred set cannot be used
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

        //Checks if employees have any accepted holidays for the needed day
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

    private HashMap<Long,LinkedList<Employee>> getEmployeesForShiftType(List<Employee> allEmps) {
        HashMap<Long,LinkedList<Employee>> empsByPrefShiftType = new HashMap<>();

        //Create Filler key for all emps without preference
        empsByPrefShiftType.put(0L, new LinkedList<>());

        for(Employee emp : allEmps){
            //Check if preferred shiftType is null and add it to 0 key to aggregate possible filler emps
            if(emp.getPreferredShiftType() == null){
                empsByPrefShiftType.get(0L).add(emp);
                continue;
            }

            Long prefShifTypeId = emp.getPreferredShiftType().getId();

            //Create Key if shiftTypeId is not already in the Map
            if (!empsByPrefShiftType.containsKey(prefShifTypeId))
                empsByPrefShiftType.put(prefShifTypeId, new LinkedList<>());

            //Add Emp to shiftType key
            empsByPrefShiftType.get(prefShifTypeId).add(emp);
        }

        return empsByPrefShiftType;
    }
}
