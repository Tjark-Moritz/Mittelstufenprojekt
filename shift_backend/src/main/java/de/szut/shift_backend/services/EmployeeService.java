package de.szut.shift_backend.services;

import de.szut.shift_backend.model.Employee;
import de.szut.shift_backend.repository.EmployeeRepository;

public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee create(Employee newEmployee) {
        return employeeRepository.save(newEmployee);
    }

}
