package ru.practicum.shareit.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.utils.CommonConstants;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
public class UserDto {
    private long id;
    @NotBlank(groups = Create.class)
    private String name;
    @NotBlank(groups = {Create.class})
    @Email(groups = {Create.class, Update.class}, regexp = CommonConstants.VALID_EMAIL_ADDRESS_REGEX)
    private String email;

    public interface Create {}
    public interface Update {}
}
