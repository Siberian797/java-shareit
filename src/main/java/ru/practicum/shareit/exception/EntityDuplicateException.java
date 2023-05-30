package ru.practicum.shareit.exception;

import ru.practicum.shareit.utils.CommonConstants;

public class EntityDuplicateException extends RuntimeException {
    public EntityDuplicateException(String entity, String email) {
        super(String.format(CommonConstants.Exceptions.ENTITY_DUPLICATE_EXCEPTION_MESSAGE, entity, email));
    }
}
