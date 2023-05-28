package ru.practicum.shareit.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.utils.CommonConstants;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

@Data
@Builder
public class UserDto {
//    private long id;
//    @NotBlank
//    private String name;
//    @NotBlank
//    @Email(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
//            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")
//    @Size(min = 1)
//    private String email;

    @Null
    private Long id;
    @NotBlank(groups = {New.class})
    @Size(groups = {Update.class, New.class}, min = 1)
    private String name;
    @NotBlank(groups = {New.class})
    @Size(groups = {Update.class, New.class}, min = 1)
    @Email(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@ [^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$", groups = {New.class, Update.class})
    private String email;

    public interface New {
    }

    public interface Update {
    }
}
