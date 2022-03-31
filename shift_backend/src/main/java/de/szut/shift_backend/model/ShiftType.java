package de.szut.shift_backend.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

public class ShiftType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private LocalDate shiftStartTime;

    @NotBlank
    private LocalDate shiftEndTime;

    @NotBlank
    private String typeName;

    @NotBlank
    private String shiftTypeColor;
}
