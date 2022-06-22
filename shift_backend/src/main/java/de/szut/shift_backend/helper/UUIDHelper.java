package de.szut.shift_backend.helper;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UUIDHelper {

        public Optional<String> extractUUIDOf(String requestURI) {
            String splittedString[] = requestURI.split("/");

            if (splittedString.length < 2) {
                return Optional.empty();
            }

            return Optional.of(splittedString[2]);
    }
}
