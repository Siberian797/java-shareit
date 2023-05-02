package ru.practicum.shareit.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.practicum.shareit.utils.CommonConstants;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EmailNotValidException extends RuntimeException {
    public EmailNotValidException(String email) {
        super(String.format(CommonConstants.EMAIL_DUPLICATE_EXCEPTION_MESSAGE, email));
    }
}
