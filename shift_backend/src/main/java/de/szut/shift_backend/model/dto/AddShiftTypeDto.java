package de.szut.shift_backend.model.dto;

import lombok.Data;

import java.time.LocalTime;

@Data
public class AddShiftTypeDto {
    private LocalTime shiftStartTime;
    private LocalTime shiftEndTime;
    private String typeName;
    private String shiftTypeColor;
}
