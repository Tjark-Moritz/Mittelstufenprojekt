package de.szut.shift_backend.model.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.time.DayOfWeek;

@Data
public class GetShiftPlanDto {

    private Long id;

    private GetDepartmentDto departmentId;

    private LocalDate validMonth;

    private List<GetShiftDto> shifts;

    private List<DayOfWeek> excludedWeekdays;
}
