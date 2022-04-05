package de.szut.shift_backend.model.dto;

import de.szut.shift_backend.model.Holiday;
import lombok.Data;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class AddHolidayDto {

    @Id
    @NotNull(message = "HolidayId can not be null!")
    private Long holidayId;

    @NotNull(message = "HolidayTypeId can not be null!")
    private Long holidayTypeId;

    @NotNull(message = "StartDate can not be null!")
    private LocalDate startDate;

    @NotNull(message = "EndDate can not be null!")
    private LocalDate endDate;

    @NotNull(message = "EmployeeId can not be null!")
    private Long employeeId;
}
