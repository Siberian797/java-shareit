package ru.practicum.shareit.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.practicum.shareit.utils.CommonConstants;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CommentNotValidException extends RuntimeException {
    public CommentNotValidException(String field) {
        super(String.format(CommonConstants.Exceptions.COMMENT_NOT_VALID_EXCEPTION_MESSAGE, field));
    }
}
