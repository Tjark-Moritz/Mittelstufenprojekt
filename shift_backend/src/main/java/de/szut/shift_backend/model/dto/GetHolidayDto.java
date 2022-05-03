package de.szut.shift_backend.model.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class GetHolidayDto {

    private Long holidayId;

    private Long holidayTypeId;

    private LocalDate startDate;

    private LocalDate endDate;

    private Long employeeId;
}
