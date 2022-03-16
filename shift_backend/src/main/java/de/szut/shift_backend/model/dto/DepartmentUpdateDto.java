package de.szut.shift_backend.model.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

public class DepartmentUpdateDto {

    private Long departmentId;
    private String name;
    private String abbreviatedName;
    private Long leadEmployee;
    //private Emloyee employees;

    @JsonCreator
    public DepartmentUpdateDto(Long departmentId, String name, String abbreviatedName, Long leadEmployee /* Employee employees */) { //todo: uncomment
        this.departmentId = departmentId;
        this.name = name;
        this.abbreviatedName = abbreviatedName;
        this.leadEmployee = leadEmployee;
        // this.employees = employees; //todo: uncomment
    }
}
