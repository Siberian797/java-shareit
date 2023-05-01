package ru.practicum.shareit.utils;

import lombok.experimental.UtilityClass;

import java.util.regex.Pattern;

@UtilityClass
public class CommonConstants {
    public static final String ID_OF_USER_WHO_ADDS_HEADER = "X-Sharer-User-Id";
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    public static final String EMAIL_DUPLICATE_EXCEPTION_MESSAGE = "Email %s is invalid";
    public static final String USER_NOT_FOUND_EXCEPTION_MESSAGE = "User with id %d was not found";
    public static final String ITEM_NOT_VALID_EXCEPTION_MESSAGE = "Check out the field %s";
}
