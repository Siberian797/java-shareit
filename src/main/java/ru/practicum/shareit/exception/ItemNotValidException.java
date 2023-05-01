package ru.practicum.shareit.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.practicum.shareit.utils.CommonConstants;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ItemNotValidException extends RuntimeException {
    public ItemNotValidException(String field) {
        super(String.format(CommonConstants.ITEM_NOT_VALID_EXCEPTION_MESSAGE, field));
    }
}
