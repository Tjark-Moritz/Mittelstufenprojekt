package de.szut.shift_backend.model.dto;

import de.szut.shift_backend.model.Message;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class GetMessageDto {

    private Long id;

    private Long channelId;

    private Long sendingEmployeeId;

    private LocalDateTime dateTime;

    private Long type;

    private String content;

    private Message.MessageStatus status;
}
