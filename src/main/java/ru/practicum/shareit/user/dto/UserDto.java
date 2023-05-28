package ru.practicum.shareit.user.dto;

import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.utils.CommonConstants;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
public class UserDto {
    private long id;
    @NotBlank
    private String name;
    @NotBlank
    @Email(regexp = CommonConstants.VALID_EMAIL_ADDRESS_REGEX)
    @Size(min = 1)
    private String email;
}
