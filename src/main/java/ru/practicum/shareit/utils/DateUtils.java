package ru.practicum.shareit.utils;

import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;

@UtilityClass
public class DateUtils {
    public LocalDateTime getCurrentTime() {
        return LocalDateTime.now();
    }
}
