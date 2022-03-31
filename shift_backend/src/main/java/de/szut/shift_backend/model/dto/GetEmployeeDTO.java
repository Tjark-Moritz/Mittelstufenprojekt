package de.szut.shift_backend.model.dto;

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

    private List<GetHolidayDTO> acceptedHolidays;

    private List<GetSickDayDTO> sickDays;

    private String base64ProfilePic;

    private GetShiftTypeDTO preferredShiftType;

    private Long departmentId;
}
