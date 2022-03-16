package de.szut.shift_backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class DepartmentGetDto {

    private Long departmentId;
    private String name;
    private String abbreviatedName;
    private Long leadEmployee;
    //private Emloyee employees;
}
