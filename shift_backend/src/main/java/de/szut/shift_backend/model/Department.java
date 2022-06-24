package de.szut.shift_backend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@Entity
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long departmentId;

    @NotNull
    private String name;

    @NotNull
    private String abbreviatedName;

    @OneToOne
    private Employee leadEmployee;

    @OneToMany
    @JoinColumn(name = "department_id")
    private List<Employee> employees;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "shiftType_id")
    @OneToMany
    private List<ShiftType> shiftTypes;
}

