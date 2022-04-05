package de.szut.shift_backend.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@Data
@Entity
public class Holiday {

    public enum HolidayStatus {
        UNANSWERED,
        ACCEPTED,
        DENIED
    }

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

    @NotNull
    private HolidayStatus holidayStatus;
}
