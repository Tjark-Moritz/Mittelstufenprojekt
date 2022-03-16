package de.szut.shift_backend.model.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import de.szut.shift_backend.model.Employee;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class DepartmentDto {

    @Id
    @NotNull(message = "DepartmentId can not be null!")
    private Long departmentId;

    @NotNull(message = "Name can not be null!")
    private String name;

    @NotNull(message = "AbbreviatedName can not be null!")
    private String abbreviatedName;

    @NotNull(message = "LeadEmployee can not be null!")
    private Long leadEmployee;

    @OneToMany
    private List<Employee> employees;
}
