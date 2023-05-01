package ru.practicum.shareit.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.practicum.shareit.utils.CommonConstants;

@ResponseStatus(HttpStatus.CONFLICT)
public class EmailDuplicateException extends RuntimeException {
    public EmailDuplicateException(String email) {
        super(String.format(CommonConstants.EMAIL_DUPLICATE_EXCEPTION_MESSAGE, email));
    }
}
