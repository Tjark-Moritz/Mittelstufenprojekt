package de.szut.shift_backend.model.dto;

import lombok.Data;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Data //todo: data???
public class HolidayStatusDto {

    @Id
    @NotNull(message = "HolidayStatusId can not be null!")
    private Long holidayStatusId;

    @NotNull(message = "HolidayStatus can not be null!")
    private String holidayStatus;

}
