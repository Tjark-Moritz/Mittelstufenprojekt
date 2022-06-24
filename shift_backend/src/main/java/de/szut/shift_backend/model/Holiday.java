package de.szut.shift_backend.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    @OneToOne
    private HolidayType holidayTypeId;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Employee employeeId;

    private LocalDate startDate;

    private LocalDate endDate;

    private Holiday.HolidayStatus status;

    private LocalDateTime requestDate;
}
