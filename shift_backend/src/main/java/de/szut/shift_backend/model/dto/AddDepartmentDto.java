package de.szut.shift_backend.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class AddDepartmentDto {
    @NotBlank
    private String name;

    @NotBlank
    private String abbreviatedName;

    @NotNull
    private Long leadEmployeeId;

    private List<Long> employeeIds;

}
