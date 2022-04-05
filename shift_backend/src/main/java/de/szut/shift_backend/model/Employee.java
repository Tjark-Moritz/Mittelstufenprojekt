package de.szut.shift_backend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class    Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String username;

    @NotBlank
    private String lastName;

    @NotBlank
    private String firstName;

    @NotBlank
    private String street;

    @NotBlank
    private String zipcode;

    @NotBlank
    private String city;

    @NotBlank
    private String phone;

    @NotBlank
    private String email;

    @NotNull
    private long numHolidaysLeft;

    @OneToMany
    private List<Holiday> acceptedHolidays;

    //@OneToMany
    //private List<SickDay> sickDays;

    private String base64ProfilePic;

    @OneToOne
    private ShiftType preferredShiftType;

    private Long departmentId;
}
