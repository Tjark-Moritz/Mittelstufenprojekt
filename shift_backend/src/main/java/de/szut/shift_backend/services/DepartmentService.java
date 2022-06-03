package de.szut.shift_backend.services;

import de.szut.shift_backend.exceptionHandling.ResourceNotFoundException;
import de.szut.shift_backend.helper.ClassReflectionHelper;
import de.szut.shift_backend.model.Department;
import de.szut.shift_backend.model.Employee;
import de.szut.shift_backend.repository.DepartmentRepository;
import de.szut.shift_backend.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    public DepartmentService(DepartmentRepository departmentRepository, EmployeeRepository employeeRepository) {
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
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
