package de.szut.shift_backend.model.dto;

import de.szut.shift_backend.model.Message;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GetMessageDto {

    private Long id;

    private Long channelId;

    private Long requestedEmployeeId;

    private Long sendingEmployeeId;

    private LocalDateTime dateTime;

    private Long type;

    private Message.MessageStatus status;
}
