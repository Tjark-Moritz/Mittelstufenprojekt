package de.szut.shift_backend.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class GetEmployeeDto {

    private Long id;

    private String username;

    private String lastName;

    private String firstName;

    private String street;

    private String zipcode;

    private String city;

    private String phone;

    private String email;

    private Long numHolidaysLeft;

    private List<GetHolidayDto> holidays;

    private List<GetSickDayDto> sickDays;

    private String base64ProfilePic;

    private GetShiftTypeDto preferredShiftType;

    private Long departmentId;
}
