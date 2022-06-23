package de.szut.shift_backend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.time.DayOfWeek;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ShiftPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Department department;

    @NotNull
    private LocalDate validMonth;

    @ElementCollection
    private List<DayOfWeek> excludedWeekdays;

    @OneToMany(cascade=CascadeType.ALL)
    private List<Shift> shifts;

    @OneToMany(cascade=CascadeType.ALL)
    private List<ShiftType> shiftTypes;
}
