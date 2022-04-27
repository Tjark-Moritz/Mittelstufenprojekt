package de.szut.shift_backend.model.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AddShiftTradeRequestDto {
    @NotNull
    private Long requestingEmployeeId;

    @NotNull
    private Long replyingEmployeeId;

    @NotNull
    private Long oldShiftId;

    @NotNull
    private Long newShiftId;
}
