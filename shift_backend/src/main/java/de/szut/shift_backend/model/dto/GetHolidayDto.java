package de.szut.shift_backend.model.dto;

import lombok.Data;

import javax.persistence.Id;
import java.time.LocalDate;

@Data
public class GetHolidayDto {

    @Id
    private Long holidayId;

    @Id
    private Long holidayTypeId;

    private LocalDate startDate;

    private LocalDate endDate;

    @Id
    private Long employeeId;
}
