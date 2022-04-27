package de.szut.shift_backend.model.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class GetShiftDto {
    private Long id;

    private LocalDate shiftDate;

    private GetShiftTypeDto shiftType;

    private List<GetEmployeeDto> activeEmployees;
}
