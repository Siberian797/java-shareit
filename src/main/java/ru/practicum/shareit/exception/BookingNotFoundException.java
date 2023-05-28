package ru.practicum.shareit.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.practicum.shareit.utils.CommonConstants;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BookingNotFoundException extends RuntimeException {
    public BookingNotFoundException(long itemId) {
        super(String.format(CommonConstants.Exceptions.BOOKING_NOT_FOUND_EXCEPTION_MESSAGE, itemId));
    }
}
