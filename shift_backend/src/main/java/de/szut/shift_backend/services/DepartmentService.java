package de.szut.shift_backend.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.szut.shift_backend.exceptionHandling.DataIncompleteException;
import de.szut.shift_backend.exceptionHandling.ResourceNotFoundException;
import de.szut.shift_backend.helper.ClassReflectionHelper;
import de.szut.shift_backend.model.Department;
import de.szut.shift_backend.model.Employee;
import de.szut.shift_backend.model.Shift;
import de.szut.shift_backend.model.ShiftType;
import de.szut.shift_backend.repository.DepartmentRepository;
import de.szut.shift_backend.repository.EmployeeRepository;
import de.szut.shift_backend.repository.ShiftTypeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;
    private final ShiftTypeService shiftTypeService;

    public DepartmentService(DepartmentRepository departmentRepository, EmployeeRepository employeeRepository, ShiftTypeService ShiftTypeService) {
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
        this.shiftTypeService = ShiftTypeService;
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

        if(fieldsToPatch.containsKey("shiftTypes")) {
            if (fieldsToPatch.get("shiftTypes") == null) {
                for (ShiftType st : deptToUpdate.getShiftTypes())
                    this.shiftTypeService.delete(st.getId());

                deptToUpdate.setShiftTypes(new ArrayList<>());
                fieldsToPatch.remove("shiftTypes");
            } else {
                List<?> rawStList = (List<?>) fieldsToPatch.get("shiftTypes");
                Map<Long, ShiftType> deptShiftTypes = new HashMap<>();

                for (ShiftType deptSt : deptToUpdate.getShiftTypes())
                    deptShiftTypes.put(deptSt.getId(), deptSt);

                for (Object rawSt : rawStList) {
                    @SuppressWarnings("unchecked")
                    LinkedHashMap<String, Object> stObj = (LinkedHashMap<String,Object>) rawSt;

                    if (stObj.containsKey("id") && stObj.get("id") == null)
                        stObj.remove("id");

                    ShiftType st = ClassReflectionHelper.UpdateFields(new ShiftType(),stObj);
                    if (st.getId() != null && deptShiftTypes.containsKey(st.getId())) {
                        Long updateId = deptShiftTypes.get(st.getId()).getId();

                        ShiftType updatedSt = this.shiftTypeService.updateShiftTypeWithObj(updateId, st);
                        deptShiftTypes.put(updateId, updatedSt);
                    } else {
                        try{
                            ShiftType newSt = this.shiftTypeService.create(st);
                            deptShiftTypes.put(newSt.getId(), newSt);
                        } catch (Exception e){
                            throw new DataIncompleteException("[DepartmentService] ShiftType could not be saved: "
                                                                + e.getMessage());
                        }
                    }
                }

                deptToUpdate.setShiftTypes(new ArrayList<>(deptShiftTypes.values()));
                fieldsToPatch.remove("shiftTypes");
            }
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
