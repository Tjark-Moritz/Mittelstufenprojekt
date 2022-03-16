package de.szut.shift_backend.services;

import de.szut.shift_backend.exceptionHandling.ResourceNotFoundException;
import de.szut.shift_backend.model.Department;
import de.szut.shift_backend.model.Employee;
import de.szut.shift_backend.repository.DepartmentRepository;
import de.szut.shift_backend.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    public DepartmentService(DepartmentRepository departmentRepository, EmployeeRepository employeeRepository) {
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
    }

    public Department add(Department newDepartment) {
       return departmentRepository.save(newDepartment);
    }

    public Department update(Department newDepartment, Long id) {
        EmployeeService employeeService = new EmployeeService(employeeRepository);

        Department department = getById(id);

        department.setDepartmentId(newDepartment.getDepartmentId());
        department.setName(newDepartment.getName());
        department.setAbbreviatedName(newDepartment.getAbbreviatedName());
        department.setLeadEmployee(newDepartment.getLeadEmployee());
        department.setEmployees(newDepartment.getEmployees());

        List<Employee> employeeList = newDepartment.getEmployees();

        if (employeeList.size() != 0)
            department.setEmployees(employeeList);

        department = departmentRepository.save(department);
        return department;
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
}
