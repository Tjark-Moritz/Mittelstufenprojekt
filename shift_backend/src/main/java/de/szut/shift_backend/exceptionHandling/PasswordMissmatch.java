package de.szut.shift_backend.exceptionHandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class PasswordMissmatch extends RuntimeException{
    public PasswordMissmatch(String message) {
        super(message);
    }
}
