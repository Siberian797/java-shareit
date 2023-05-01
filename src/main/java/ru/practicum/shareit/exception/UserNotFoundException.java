package ru.practicum.shareit.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.practicum.shareit.utils.CommonConstants;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(long userId) {
        super(String.format(CommonConstants.USER_NOT_FOUND_EXCEPTION_MESSAGE, userId));
    }
}
