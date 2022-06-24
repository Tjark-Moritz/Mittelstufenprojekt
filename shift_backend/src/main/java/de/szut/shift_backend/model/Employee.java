package de.szut.shift_backend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Employee {

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
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "employee_id")
    private List<Holiday> holidays;

    @Column(length=1000000)
    private String base64ProfilePic;

    @OneToOne
    private ShiftType preferredShiftType;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "department_id")
    private Department department;
}
