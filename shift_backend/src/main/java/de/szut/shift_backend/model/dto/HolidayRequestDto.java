package de.szut.shift_backend.model.dto;

import de.szut.shift_backend.model.Holiday;
import lombok.Data;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class HolidayRequestDto {

    @Id
    @NotNull(message = "HolidayRequestId can not be null!")
    private Long holidayRequestId;

    @Id
    @NotNull(message = "RequestingEmployeeId can not be null!")
    private Long requestingEmployeeId;

    @NotNull(message = "Holiday can not be null!")
    private Holiday holiday;

    @NotNull(message = "Status can not be null!")
    private Boolean status;

    @NotNull(message = "RequestDate can not be null!")
    private LocalDateTime requestDate;

}
