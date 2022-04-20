package de.szut.shift_backend.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class AddHolidayDto {

    @JsonIgnore
    private Long id;

    @NotNull(message = "HolidayTypeId can not be null!")
    private Long typeId;

    @NotNull(message = "StartDate can not be null!")
    private LocalDate startDate;

    @NotNull(message = "EndDate can not be null!")
    private LocalDate endDate;

    @NotNull(message = "EmployeeId can not be null!")
    private Long employeeId;

}
