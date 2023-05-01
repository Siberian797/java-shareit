package ru.practicum.shareit.user.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class UserDto {
    private long userId;
    @NotBlank
    private String userName;
    @NotBlank
    private String userMail;
}
