package de.szut.shift_backend.exceptionHandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class DataIncompleteException extends RuntimeException {
    public DataIncompleteException(String message) {
        super(message);
    }
}
