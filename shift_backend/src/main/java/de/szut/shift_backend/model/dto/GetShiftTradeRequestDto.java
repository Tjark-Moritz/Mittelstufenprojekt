package de.szut.shift_backend.model.dto;

import lombok.Data;

@Data
public class GetShiftTradeRequestDto {

    private Long id;

    private GetEmployeeDto requestingEmployee;

    private GetEmployeeDto replyingEmployee;

    private GetShiftDto oldShift;

    private GetShiftDto newShift;
}
