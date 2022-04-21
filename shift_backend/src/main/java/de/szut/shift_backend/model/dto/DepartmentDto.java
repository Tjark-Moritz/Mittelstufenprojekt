package de.szut.shift_backend.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
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
    private List<GetEmployeeDto> employees; //getEmployeeDto in List<>
}
