package de.szut.shift_backend.services;

import de.szut.shift_backend.exceptionHandling.ResourceNotFoundException;
import de.szut.shift_backend.model.Department;
import de.szut.shift_backend.model.Employee;
import de.szut.shift_backend.model.dto.AddEmployeeDto;
import de.szut.shift_backend.repository.DepartmentRepository;
import de.szut.shift_backend.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final DepartmentService departmentService;

    public EmployeeService(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository, DepartmentService departmentService) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.departmentService = departmentService;
    }

    public Employee create(Employee newEmployee) {
        return employeeRepository.save(newEmployee);
    }

    public Employee getEmployeeById(Long empID){
        Optional<Employee> emp = employeeRepository.findById(empID);

        if (emp.isEmpty())
            throw new ResourceNotFoundException("Employee could not be found");

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
        this.employeeRepository.deleteById(employeeId);
    }

    public void updateEmployee(Long employeeId, Employee empUpdate) throws IllegalAccessException, NoSuchFieldException {
        Employee emp = this.getEmployeeById(employeeId);

        Field[] empFields = empUpdate.getClass().getDeclaredFields();

        for (Field field : empFields){
            field.setAccessible(true);
            if (field.get(this) != null){
                Field empFieldtoUpdate = emp.getClass().getDeclaredField(field.getName());
                empFieldtoUpdate.setAccessible(true);
                empFieldtoUpdate.set(field, field.get(this));
            }
        }

        this.employeeRepository.save(emp);
    }
}
