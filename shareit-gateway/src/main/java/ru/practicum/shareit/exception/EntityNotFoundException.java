package ru.practicum.shareit.exception;

import ru.practicum.shareit.utils.CommonConstants;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String entity, Long id) {
        super(String.format(CommonConstants.Exceptions.ENTITY_NOT_FOUND_EXCEPTION_MESSAGE, entity, id));
    }
}
