package ru.practicum.shareit.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Map;

@RestControllerAdvice
@Slf4j
@SuppressWarnings("unused")
public class ShareitExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @org.springframework.web.bind.annotation.ExceptionHandler
    @SuppressWarnings(value = "unused")
    public Map<String, String> handleNotExistingFilm(final MethodArgumentTypeMismatchException e) {
        log.debug("Server got bad request: {}", e.getMessage(), e);
        return Map.of("error", "Unknown " + e.getName() + ": " + e.getValue());
    }
}
