package de.szut.shift_backend.services;

import de.szut.shift_backend.model.Department;
import de.szut.shift_backend.model.Employee;
import de.szut.shift_backend.model.dto.DepartmentDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MappingService {

    public DepartmentDto mapDepToDepDto(Department department) {
        DepartmentDto departmentDto = new DepartmentDto();
        DepartmentDto tempDepDto = new DepartmentDto();
            tempDepDto.setDepartmentId(department.getDepartmentId());
            tempDepDto.setName(department.getName());
            tempDepDto.setAbbreviatedName(department.getAbbreviatedName());
            tempDepDto.setLeadEmployee(department.getLeadEmployee());
            tempDepDto.setEmployees(department.getEmployees());

        return departmentDto;
    }

    public Department mapDepDtoToDep(DepartmentDto departmentDto) {
        Department department = new Department();
        Department tempDep = new Department();
            tempDep.setDepartmentId(departmentDto.getDepartmentId());
            tempDep.setName(departmentDto.getName());
            tempDep.setAbbreviatedName(departmentDto.getAbbreviatedName());
            tempDep.setLeadEmployee(departmentDto.getLeadEmployee());
            tempDep.setEmployees(departmentDto.getEmployees());

        return department;
    }

    /*
    public List<Employee> mapEmpDtoToEmp(List<EmployeeDto> input) {

    }

    public List<EmployeeDto> mapEmpToEmpDto(List<Employee> input) {

    }
    */
}
