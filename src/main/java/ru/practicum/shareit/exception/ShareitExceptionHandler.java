package ru.practicum.shareit.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Map;

@RestControllerAdvice
@Slf4j
@SuppressWarnings("unused")
public class ShareitExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    @SuppressWarnings(value = "unused")
    public Map<String, String> handleNotExistingFilm(final MethodArgumentTypeMismatchException e) {
        log.debug("Server got bad request: {}", e.getMessage(), e);
        return Map.of("error", "Unknown " + e.getName() + ": " + e.getValue());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler
    @SuppressWarnings(value = "unused")
    public Map<String, String> handleNotFoundEntity(final EntityNotFoundException e) {
        log.debug("Server was unable to find: {}", e.getMessage());
        return Map.of("error", e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    @SuppressWarnings(value = "unused")
    public Map<String, String> handleNotValidEntity(final EntityNotValidException e) {
        log.debug("Server got bad parameter: {}", e.getMessage());
        return Map.of("error", e.getMessage());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler
    @SuppressWarnings(value = "unused")
    public Map<String, String> handleDuplicatedEntity(final EntityDuplicateException e) {
        log.debug("Server got bad parameter: {}", e.getMessage());
        return Map.of("error", e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    @SuppressWarnings(value = "unused")
    public Map<String, String> handleNotValidParameters(final MethodArgumentNotValidException e) {
        log.debug("Server got bad parameter: {}", e.getParameter().getParameterName(), e);
        return Map.of("error", "Unknown parameter: {}" + e.getParameter().getParameterName());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    @SuppressWarnings(value = "unused")
    public Map<String, String> handleUnprocessedExceptions(final Throwable e) {
        log.debug("Server got bad parameter: {}", e.getMessage());
        return Map.of("error", e.getMessage());
    }
}
