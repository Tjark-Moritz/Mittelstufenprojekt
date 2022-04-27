package de.szut.shift_backend.services;

import de.szut.shift_backend.exceptionHandling.ResourceNotFoundException;
import de.szut.shift_backend.helper.ClassReflectionHelper;
import de.szut.shift_backend.model.Department;
import de.szut.shift_backend.model.Employee;
import de.szut.shift_backend.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentService departmentService;

    public EmployeeService(EmployeeRepository employeeRepository, DepartmentService departmentService) {
        this.employeeRepository = employeeRepository;
        this.departmentService = departmentService;
    }

    public void create(Employee newEmployee) {
        employeeRepository.save(newEmployee);
    }

    public Employee getEmployeeById(Long empID){
        Optional<Employee> emp = employeeRepository.findById(empID);

        if (emp.isEmpty())
            throw new ResourceNotFoundException("Employee with id '"+ empID + "' could not be found");

        return emp.get();
    }

    public Department getDepartmentByEmployeeId(Long employeeId) {
        Employee emp = this.getEmployeeById(employeeId);

        return departmentService.getDepartmentById(emp.getDepartmentId());
    }

    public List<Employee> getAllEmployees(){
        return this.employeeRepository.findAll();
    }

    public void deleteEmployeeById(Long employeeId){

        if (!this.employeeRepository.existsById(employeeId))
            throw new ResourceNotFoundException("Employee with id '"+ employeeId + "' could not be found");

        this.employeeRepository.deleteById(employeeId);
    }

    public Employee updateEmployee(Long employeeId, Map<String, Object> empUpdate) throws ConstraintViolationException{
        Employee emp = this.getEmployeeById(employeeId);

        Employee empUpdated = ClassReflectionHelper.UpdateFields(emp, empUpdate);

        this.employeeRepository.save(empUpdated);

        return empUpdated;
    }
}
