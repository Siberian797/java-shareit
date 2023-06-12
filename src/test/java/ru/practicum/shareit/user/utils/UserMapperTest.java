package ru.practicum.shareit.user.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.practicum.shareit.user.dto.UserDto;

import static org.junit.jupiter.api.Assertions.*;

@ContextConfiguration(classes = {UserMapper.class})
@ExtendWith(SpringExtension.class)
class UserMapperTest {
    @MockBean
    @SuppressWarnings("unused")
    private UserMapper userMapper;

    @Test
    void toUser() {
        UserDto userDto = UserDto.builder()
                .id(1L)
                .name("name")
                .email("email@email.com")
                .build();
        assertNull(userMapper.toUser(userDto));
    }
}