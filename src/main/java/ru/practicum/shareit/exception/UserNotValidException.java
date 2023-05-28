package ru.practicum.shareit.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.practicum.shareit.utils.CommonConstants;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UserNotValidException extends RuntimeException {
    public UserNotValidException(long userId) {
        super(String.format(CommonConstants.Exceptions.USER_NOT_VALID_EXCEPTION_MESSAGE, userId));
    }
}
