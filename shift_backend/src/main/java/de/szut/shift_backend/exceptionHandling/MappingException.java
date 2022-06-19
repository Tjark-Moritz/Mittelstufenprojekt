package de.szut.shift_backend.exceptionHandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class MappingException extends RuntimeException {
    public MappingException(String message) {
        super(message);
    }
}
