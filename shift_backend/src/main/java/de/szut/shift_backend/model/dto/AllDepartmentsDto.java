package de.szut.shift_backend.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AllDepartmentsDto {

    private GetDepartmentDto getDepartmentDto;

    public AllDepartmentsDto(GetDepartmentDto getDepartmentDto) {
        this.getDepartmentDto = getDepartmentDto;
    }
}
