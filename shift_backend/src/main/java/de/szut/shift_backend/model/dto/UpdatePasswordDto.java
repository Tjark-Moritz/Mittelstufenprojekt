package de.szut.shift_backend.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UpdatePasswordDto {

    @NotBlank
    private String newPassword;

    @NotBlank
    private String confirmPassword;
}
