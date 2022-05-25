package de.szut.shift_backend.exceptionHandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class CreationException extends RuntimeException {
    public CreationException(String message) {
        super(message);
    }
}
