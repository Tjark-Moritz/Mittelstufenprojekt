package de.szut.shift_backend.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.szut.shift_backend.model.Message;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class AddMessageDto {

    @JsonIgnore
    private Long id;

    @NotNull(message = "Channel can not be null!")
    private Long channelId;

    @NotNull(message = "Requested-EmployeeId can not be null!")
    private Long requestedEmployeeId;

    @NotNull(message = "Sending employeeId can not be null!")
    private Long sendingEmployeeId;

    private LocalDateTime dateTime;

    @NotNull(message = "Message-Type can not be null!")
    private Long type;

    private Message.MessageStatus status;
}
