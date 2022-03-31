package de.szut.shift_backend.model.dto;

import java.time.LocalDate;

public class GetShiftTypeDTO {
    private Long id;
    private LocalDate shiftStartTime;
    private LocalDate shiftEndTime;
    private String typeName;
    private String shiftTypeColor;
}
