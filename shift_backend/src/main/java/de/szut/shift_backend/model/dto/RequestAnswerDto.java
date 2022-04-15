package de.szut.shift_backend.model.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RequestAnswerDto {

    @NotNull
    private boolean accepted;
}
