package de.szut.shift_backend.model.dto;

import java.util.List;

public class GetEmployeeDTO {

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

    private List<GetHolidayDto> acceptedHolidays;

    private List<GetSickDayDto> sickDays;

    private String base64ProfilePic;

    private GetShiftTypeDTO preferredShiftType;

    private Long departmentId;
}
