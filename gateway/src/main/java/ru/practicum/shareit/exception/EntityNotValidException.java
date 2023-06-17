package ru.practicum.shareit.exception;

import ru.practicum.shareit.utils.CommonConstants;

public class EntityNotValidException extends RuntimeException {
    public EntityNotValidException(String entity, String field) {
        super(String.format(CommonConstants.Exceptions.ENTITY_NOT_VALID_EXCEPTION_MESSAGE, entity, field));
    }
}
