package ru.practicum.shareit.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.practicum.shareit.utils.CommonConstants;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BookingNotValidException extends RuntimeException {
    public BookingNotValidException(String field) {
        super(String.format(CommonConstants.Exceptions.BOOKING_NOT_VALID_EXCEPTION_MESSAGE, field));
    }
}
