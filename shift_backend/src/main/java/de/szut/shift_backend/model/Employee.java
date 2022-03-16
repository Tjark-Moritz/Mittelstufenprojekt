package de.szut.shift_backend.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name="employee")
public class Employee {
    @Id
    @NotNull
    private Long id;

    //todo: add values here
}
