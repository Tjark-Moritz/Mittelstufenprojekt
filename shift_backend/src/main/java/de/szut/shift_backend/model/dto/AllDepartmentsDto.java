package de.szut.shift_backend.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AllDepartmentsDto {

    private DepartmentDto departmentDto;

    public AllDepartmentsDto(DepartmentDto departmentDto) {
        this.departmentDto = departmentDto;
    }
}
