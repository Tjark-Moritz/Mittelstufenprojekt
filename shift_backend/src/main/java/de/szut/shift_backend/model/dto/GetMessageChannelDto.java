package de.szut.shift_backend.model.dto;

import de.szut.shift_backend.model.Message;
import lombok.Data;

import java.util.List;

@Data
public class GetMessageChannelDto {

    private Long id;

    private String name;

    private String description;

    private List<GetMessageDto> messages; //todo: DTOs dürfen nur DTOs haben, right?

    private List<GetEmployeeDto> employees;
}
