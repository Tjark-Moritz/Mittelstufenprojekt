package de.szut.shift_backend.model.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class AddShiftPlanDto {

    private Long departmentId;

    private LocalDate validMonth;

    private List<AddShiftDto> shifts;

    private List<AddShiftTypeDto> shiftTypes;
}
