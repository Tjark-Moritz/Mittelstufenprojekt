package de.szut.shift_backend.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class GetMessageChannelDto {

    private Long id;

    private String name;

    private String description;

    private List<GetMessageDto> messages;

    private List<Long> employees;
}
