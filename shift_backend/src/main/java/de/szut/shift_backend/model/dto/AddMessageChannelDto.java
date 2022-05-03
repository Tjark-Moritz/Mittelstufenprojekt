package de.szut.shift_backend.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.szut.shift_backend.model.Message;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class AddMessageChannelDto {

    @JsonIgnore
    private Long id;

    @NotNull(message = "Channel name can not be null!")
    private String name;

    @NotNull(message = "Channel description can not be null!")
    private String description;

    private List<Message> messages;
}

