package de.szut.shift_backend.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Data
@Entity
public class Holiday {

    public enum HolidayStatus {
        UNANSWERED,
        ACCEPTED,
        DENIED;

        public static HolidayStatus of(String status) {
            HolidayStatus[] values = HolidayStatus.values();

            for (HolidayStatus holidayStatus : values) {
                if (holidayStatus.name().equals(status)) {
                    return holidayStatus;
                }
            }
            return UNANSWERED;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long holidayId;

    @NotNull(message = "HolidayTypeId can not be null!")
    private Long holidayTypeId;

    @NotNull(message = "EmployeeId can not be null!")
    @OneToOne
    private Employee employee;

    @NotNull(message = "StartDate can not be null!")
    private LocalDate startDate;

    @NotNull(message = "EndDate can not be null!")
    private LocalDate endDate;

    private Holiday.HolidayStatus status;

    @NotNull(message = "RequestDate can not be null!")
    private LocalDateTime requestDate;


}
