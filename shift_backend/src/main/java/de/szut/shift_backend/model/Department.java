package de.szut.shift_backend.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Entity
@Table(name="department")
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
    private List<Employee> employees;
}
