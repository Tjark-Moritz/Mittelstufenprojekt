package de.szut.shift_backend.model.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class GetShiftTypeDto {
    private Long id;
    private LocalTime shiftStartTime;
    private LocalTime shiftEndTime;
    private String typeName;
    private String shiftTypeColor;
}
