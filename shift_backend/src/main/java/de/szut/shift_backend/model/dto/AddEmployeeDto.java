package de.szut.shift_backend.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class AddEmployeeDto {

    private String username;

    private String lastName;

    private String firstName;

    private String street;

    private String zipcode;

    private String city;

    private String phone;

    private String email;

    private long numHolidaysLeft;

    private String base64ProfilePic;

    private Long preferredShiftType;

    private Long departmentId;
}
