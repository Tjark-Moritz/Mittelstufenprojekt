package de.szut.shift_backend.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AllMessagesDto {
    //TODO: delete code?
    private GetMessageDto getMessageDto;

    public AllMessagesDto(GetMessageDto getMessageDto) {
        this.getMessageDto = getMessageDto;
    }
}
