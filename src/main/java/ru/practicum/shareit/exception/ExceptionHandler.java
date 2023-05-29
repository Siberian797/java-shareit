package ru.practicum.shareit.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Map;

@RestControllerAdvice
@SuppressWarnings("unused")
public class ExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @org.springframework.web.bind.annotation.ExceptionHandler
    @SuppressWarnings(value = "unused")
    public Map<String, String> handleNotExistingFilm(final MethodArgumentTypeMismatchException e) {
        return Map.of("error", "Unknown " + e.getName() + ": " + e.getValue());
    }
}
