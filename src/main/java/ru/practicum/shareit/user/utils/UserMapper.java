package ru.practicum.shareit.user.utils;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

@Component
public class UserMapper {

    public UserDto toUserDto(User user) {
        return UserDto.builder()
                .id(user.getUserId())
                .name(user.getUserName())
                .email(user.getUserMail())
                .build();
    }

    public User toUser(UserDto userDto) {
        return User.builder()
                .userId(userDto.getId())
                .userName(userDto.getName())
                .userMail(userDto.getEmail())
                .build();
    }
}
