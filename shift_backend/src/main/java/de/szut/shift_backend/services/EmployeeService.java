package de.szut.shift_backend.services;

import de.szut.shift_backend.exceptionHandling.ResourceNotFoundException;
import de.szut.shift_backend.model.Department;
import de.szut.shift_backend.model.Employee;
import de.szut.shift_backend.repository.DepartmentRepository;
import de.szut.shift_backend.repository.EmployeeRepository;

import java.util.Optional;

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

}
