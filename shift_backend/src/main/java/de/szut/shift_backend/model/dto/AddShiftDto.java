package de.szut.shift_backend.model.dto;

import de.szut.shift_backend.model.ShiftType;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;

@Data
public class AddShiftDto {
    @NotBlank
    private LocalDate shiftDate;

    @NotBlank
    private ShiftType shiftType;

    private List<Long> activeEmployeesIds;
}
