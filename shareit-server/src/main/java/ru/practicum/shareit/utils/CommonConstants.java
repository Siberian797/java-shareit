package ru.practicum.shareit.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CommonConstants {
    public static final String ID_OF_USER_WHO_ADDS_HEADER = "X-Sharer-User-Id";
    public static final String VALID_EMAIL_ADDRESS_REGEX = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

    public interface Exceptions {
        String ENTITY_DUPLICATE_EXCEPTION_MESSAGE = "Entity %s has duplicated field %s";
        String ENTITY_NOT_VALID_EXCEPTION_MESSAGE = "Entity %s has invalid field %s";
        String ENTITY_NOT_FOUND_EXCEPTION_MESSAGE = "Entity %s with id %d was not found";
    }
}
