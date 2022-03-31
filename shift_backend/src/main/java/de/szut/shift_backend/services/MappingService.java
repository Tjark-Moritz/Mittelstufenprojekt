package de.szut.shift_backend.services;

import de.szut.shift_backend.model.Employee;
import de.szut.shift_backend.model.dto.AddEmployeeDTO;
import org.springframework.stereotype.Service;

@Service
public class MappingService {
    public void mapEmployeeToGetEmployeeDTO(Employee emp){

    }

    public Employee mapAddEmployeeDTOtoEmployee(AddEmployeeDTO addEmp){

        return new Employee();
    }
}
