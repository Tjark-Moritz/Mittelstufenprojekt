package de.szut.shift_backend.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.szut.shift_backend.exceptionHandling.ResourceNotFoundException;
import de.szut.shift_backend.helper.ClassReflectionHelper;
import de.szut.shift_backend.model.Department;
import de.szut.shift_backend.model.Employee;
import de.szut.shift_backend.model.ShiftType;
import de.szut.shift_backend.repository.DepartmentRepository;
import de.szut.shift_backend.repository.EmployeeRepository;
import de.szut.shift_backend.repository.ShiftTypeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.*;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;
    private final ShiftTypeRepository shiftTypeRepository;

    public DepartmentService(DepartmentRepository departmentRepository, EmployeeRepository employeeRepository, ShiftTypeRepository shiftTypeRepository) {
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
        this.shiftTypeRepository = shiftTypeRepository;
    }

    public Department create(Department newDepartment) {
       return departmentRepository.save(newDepartment);
    }

    public Department update(Long deptId, Map<String,Object> fieldsToPatch) {
        Department deptToUpdate = this.getDepartmentById(deptId);

        //Dirty solution but hey it works ¯\_(ツ)_/¯
        if(fieldsToPatch.containsKey("employees")){
            List<?> temp = (List<?>) fieldsToPatch.get("employees");
            List<Employee> emps = new ArrayList<>();

            for(Object item : temp){
                Optional<Employee> emp = this.employeeRepository.findById(((Integer) item).longValue());
                emp.ifPresent(emps::add);
            }

            fieldsToPatch.put("employees", emps);
        }

        if(fieldsToPatch.containsKey("shiftTypes")){
            ObjectMapper om = new ObjectMapper();
            List<?> temp = (List<?>) fieldsToPatch.get("shiftTypes");
            List<ShiftType> stypes = new ArrayList<>();

            for(Object item : temp){
                LinkedHashMap<String,String> itemMap = (LinkedHashMap<String, String>) item;
                ShiftType s = new ShiftType();

                s.setShiftStartTime(LocalTime.parse(itemMap.get("shiftStartTime")));
                s.setShiftEndTime(LocalTime.parse(itemMap.get("shiftStartTime")));
                s.setTypeName(itemMap.get("typeName"));
                s.setShiftTypeColor(itemMap.get("shiftTypeColor"));

                Optional<ShiftType> oS = this.shiftTypeRepository.findByShiftStartTimeAndShiftEndTime(s.getShiftStartTime()
                        , s.getShiftEndTime());

                if (oS.isEmpty()) {
                    stypes.add(this.shiftTypeRepository.save(s));
                } else {
                    stypes.add(oS.get());
                }

            }

            fieldsToPatch.put("shiftTypes", stypes);
        }

        Department deptUpdated = ClassReflectionHelper.UpdateFields(deptToUpdate, fieldsToPatch);

        deptUpdated = departmentRepository.save(deptUpdated);
        return deptUpdated;
    }

    public Department getById(Long id) {
        Optional<Department> department = departmentRepository.findById(id);

        if (department.isEmpty()) {
            throw new ResourceNotFoundException("Department with Id: " + id + "could not be found!");
        }
        return department.get();
    }

    public void delete(Long id) {
        departmentRepository.deleteById(id);
    }

    public Department getDepartmentById(Long depID){
        Optional<Department> dep = departmentRepository.findById(depID);

        if (dep.isEmpty())
            throw new ResourceNotFoundException("Department could not be found");

        return dep.get();
    }

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }
}
