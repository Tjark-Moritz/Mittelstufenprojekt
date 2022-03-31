package de.szut.shift_backend.model.dto;

import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class AddEmployeeDTO {

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

    private String base64ProfilePic;

    private GetShiftTypeDTO preferredShiftType;

    private Long departmentId;
}
